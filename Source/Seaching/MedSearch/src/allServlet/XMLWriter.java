package allServlet;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javaClasses.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/XMLWriter")
public class XMLWriter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File xmlfile = new File(getServletContext().getRealPath("autocomplete/transfer.xml"));
		xmlfile.delete();
		Connection conn = null;
		Statement stmt=null;
		ResultSet rs= null;
		FileWriter fw = null;
		
		try {
		fw = new FileWriter(xmlfile);
		fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		fw.write("<words>");
		conn = new Database().returnConnection();
		stmt= conn.createStatement();
		rs = stmt.executeQuery("SELECT word_name, frequency FROM words_table ORDER BY frequency DESC LIMIT 100");
	
		while(rs.next()) fw.write("<word freq=\""+rs.getInt("frequency")+"\">"+rs.getString("word_name").toLowerCase()+"</word>");
		fw.write("</words>");
		rs.close();
		stmt.close();
		fw.close();
		conn.close();
		response.getWriter().write("Updation done..!!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
