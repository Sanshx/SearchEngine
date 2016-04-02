package allServlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javaClasses.Database;
import javaClasses.PageResultBean;
import javaClasses.ProcessStopWords;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/SearchInput")
public class SearchInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	Connection conn = new Database().returnConnection();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String query = request.getParameter("query");
		String type = request.getParameter("type");
		int page = Integer.parseInt(request.getParameter("page"));
		String[] words = new ProcessStopWords().removeStopWords(query);
		ArrayList<PageResultBean> urls = getPageLinks(SQLQuery(getWordId(words),type, page));
		String results = "<RESULTS>";
		for(PageResultBean obj : urls){
			results = results.concat("<RESULT>");
			results = results.concat("<TITLE>"+obj.getPage_title().replaceAll("[^a-zA-Z0-9., ]", "")+"</TITLE>");
			results = results.concat("<INFO>"+obj.getPage_info().replaceAll("[^a-zA-Z0-9., ]", "")+"</INFO>");
			results = results.concat("<LINK>"+obj.getPage_url()+"</LINK>").replaceAll("&", "amp;");
			results = results.concat("</RESULT>");
		}
		results = results.concat("</RESULTS>");
		out.write(results);
	}
	
	protected String getWordId(String[] words) {
		String array = "(";
		boolean first = true;
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT word_id FROM words_table WHERE word_name=?");
			for(String word : words){
				stmt.setString(1, word);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					if(!first) array = array.concat(",");
					array = array.concat(String.valueOf(rs.getInt("word_id")));
					first = false;
				}
			}
			array = array.concat(")");
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while getting word_ids for query words");
		}
		return array;
	}
	
	protected String SQLQuery(String array, String type, int page){
		int lowerlimit = (page*10)-10;
		/*String query_without_HITS = "SELECT page_id, COUNT(page_id) AS matchCount, AVG(degree) AS avgDeg, SUM(frequency) as sumFreq\n" + 
				"FROM words_detail WHERE type_id="+type+" and word_id IN ###\n" + 
				"GROUP BY page_id ORDER BY matchCount DESC, avgDeg, sumFreq DESC limit "+lowerlimit+",10";*/
		String query = "SELECT B.page_id FROM allpages_table AS A INNER JOIN \n" + 
				"(SELECT page_id, COUNT(page_id) AS matchCount, AVG(degree) AS avgDeg, SUM(frequency) as sumFreq \n" + 
				"FROM words_detail WHERE type_id="+type+" and word_id IN ### \n" + 
				"GROUP BY page_id ORDER BY matchCount DESC, avgDeg, sumFreq DESC limit "+lowerlimit+",10) AS B \n" + 
				"ON A.page_id=B.page_id ORDER BY (A.incoming_link + A.outgoing_link)/2.0 DESC;";
		query = query.replaceAll("###", array);
		return query;
	}

	
	
	protected ArrayList<PageResultBean> getPageLinks(String query){
		ArrayList<PageResultBean> urls = new ArrayList<PageResultBean>();
		try {
			PreparedStatement stmt1 = conn.prepareStatement(query);
			PreparedStatement stmt2 = conn.prepareStatement("SELECT page_url, page_title, page_info FROM allpages_table where page_id=?");
			
			ResultSet rs1 = stmt1.executeQuery();
			while(rs1.next()){
				PageResultBean obj = new PageResultBean();
				stmt2.setInt(1, rs1.getInt("page_id"));
				ResultSet rs2 = stmt2.executeQuery();
				if(rs2.next()) {
					obj.setPage_url(rs2.getString("page_url"));
					obj.setPage_title(trimString(rs2.getString("page_title")));
					obj.setPage_info(trimString(rs2.getString("page_info")));
				}
				urls.add(obj);
			}
		} catch (SQLException e) {}
		return urls;
	}
	
	protected String trimString(String totrim){
		int length = totrim.length();
		if(length>100) {
			totrim = totrim.substring(0,100);
			totrim = totrim.concat(".....");
		}
		if(length==0) totrim = "......";
		return totrim;
	}



}
