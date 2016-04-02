package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlPages {
	
	static Connection conn = null;
	static int maxdepth = 0;
	static String match = null;
	static String output = null;
	static int type_id = 0;
	
	
	//This method will read seeds form the database.
	public static void readSeed(int maxdepthpassed){
		String page_url = null;
		try{
			maxdepth = maxdepthpassed;
			conn = new Database().returnConnection();
			disableUpdated();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT seed_id, seed_url,type_id FROM seeds_table");
			while(rs.next()){
				int seed_id = rs.getInt("seed_id");
				if(!MaintainIndex.existsInFetchDate(seed_id)){
					//MaintainIndex.clearIndexes(seed_id);
					page_url = rs.getString("seed_url");
					type_id = rs.getInt("type_id");
					match = page_url.replace("http://www.", "");
					System.out.println("\n\n     Starting with --> "+match);
					processController(seed_id, page_url ,0);
					MaintainIndex.insertFetchDate(seed_id);
				}
			}
			rs.close();
			st.close();
			conn = null;
			st = null;
			rs = null;
		}catch(SQLException e){
			System.out.println("Error: SQLException occured while reading seeds to create Fetchlist.");
		}
		finally{
			disableUpdated();
		}
	}
	
	
	//This method will insert the seed and will recursively find all its pages at different depth.
	public static void processController(int seed_id, String page_url ,int depth){		
		if(depth<=maxdepth){
			if(page_url.length()<200){
				boolean isUnique = false;
				boolean isfetched = false;
				isUnique = isUnique(page_url);
				if(!isUnique) {
					isfetched = fetched(returnPageId(page_url));
				}
				if(!isfetched){
					PageInfoBean page_object = pageInfo(seed_id, page_url, depth,isUnique);
					if(page_object.isReadStatus()){
						for(String url: page_object.getLinks()) {
							if(!isBroken(url)) processController(seed_id, url, depth+1);
						}
					}
					page_object = null;
				}
				else increaseLink(returnPageId(page_url), depth);
			}
		}
	}
	
	
	//This method return Information about Hyperlinks.
	public static PageInfoBean pageInfo(int seed_id, String page_url, int depth,boolean isUnique){
		System.out.print("\nProcessing page --> "+page_url);
		PageInfoBean page_object = new PageInfoBean();
		Document doc=null;
		Set<String> setlinks = new HashSet<String>();
		boolean readStatus = true;
		try {
			doc = Jsoup.connect(page_url).timeout(10*1000).get();
			}catch(Exception e){
				readStatus = false;
			}
		if(readStatus) {
			Elements links = doc.select("a[href]");
			for(Element link: links){
				if(link.attr("abs:href").contains(match)&&(!link.attr("abs:href").contains("#"))) {
					setlinks.add(link.attr("abs:href"));
				}
			}
		}
		
		page_object.setLinks(setlinks);
		page_object.setNumber(setlinks.size());
		page_object.setReadStatus(readStatus);
		
		setlinks = null;
		
		if(page_object.isReadStatus()){
			if(isUnique) {
				String info = "";
				for(Element meta : doc.select("meta")) if(meta.attr("name").equals("description")) info = meta.attr("content").replaceAll("\\[.*?\\]", "");
				if(info.length()>100) {
					info = info.substring(0,100);
					info = info.concat(".....");
				}
				addToTable(seed_id, page_url, depth, page_object.getNumber(), doc.title(), info);
				}
			else updateflag(returnPageId(page_url));
			if(!isUnique) updateflag(returnPageId(page_url));
			
	
			//Here the pages are sent for crawling and indexing.
			if(isUnique) new PreIndexer().writeToFile(returnPageId(page_url), page_url, type_id, doc);
			doc = null;
		}
		
		if(readStatus) System.out.print("   .....Success");
		else {
			System.out.print("   .....Fail");
			addBrokenLink(seed_id, page_url);
		}
		return page_object;
	}
	
	
	
	//This method will put links of all the pages associated with the seed into the fetchList.
	public static void addToTable(int seed_id, String page_url, int depth, int outgoing_link, String title, String info){
		try{
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO allpages_table (seed_id, page_url, depth, incoming_link, outgoing_link, added, fetched, page_title, page_info) VALUES (?,?,?,0,?,1,1,?,?)");
			stmt.setInt(1, seed_id);
			stmt.setString(2, page_url);
			stmt.setInt(3, depth);
			stmt.setInt(4, outgoing_link);
			stmt.setString(5, title);
			stmt.setString(6, info);
			stmt.execute();
			stmt.close();
		}catch(SQLException e){
			System.out.println("Error: SQLException occurred while adding Link to Allpages table.");
		}
	}	
	
	
	//This method will check if the URL already exists in fetchList or not.
	public static boolean isUnique(String page_url){
		boolean result = false;
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT page_url from allpages_table WHERE page_url=?");
			stmt.setString(1, page_url);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) result = false;
			else result = true;
			stmt.close();
			rs.close();
		}catch(SQLException e){
			System.out.println("Error: SQLException occured while adding Link to FetchList.");
		}
		return result;
	}	

	
	//This function will increase the incoming_link.
	public static void increaseLink(int page_id, int depth){
		try{
			PreparedStatement stmt1 = conn.prepareStatement("SELECT added, depth FROM allpages_table WHERE page_id=?");
			PreparedStatement stmt2 = conn.prepareStatement("UPDATE allpages_table SET incoming_link=incoming_link+1, depth=? WHERE page_id=?");
			PreparedStatement stmt3 = conn.prepareStatement("UPDATE allpages_table SET incoming_link=incoming_link+1 WHERE page_id=?");
			stmt1.setInt(1, page_id);
			ResultSet rs = stmt1.executeQuery();
			rs.next();
			boolean updateDepth = false;
			if(rs.getInt("depth")>depth) updateDepth  = true;
			if(rs.getInt("added")==1) {
				if(updateDepth) {
					stmt2.setInt(1, depth);
					stmt2.setInt(2, page_id);
					stmt2.executeUpdate();
				}
				else {
					stmt3.setInt(1, page_id);
					stmt3.executeUpdate();
				} 
			}
			stmt1.close();
			stmt2.close();
			stmt3.close();
			rs.close();
		}catch(SQLException e){
			System.out.println("Error: SQLException occurred while incrementing incoming links.");
		}
	}
	
			
	//This method will return the page id of existing page.
	public static int returnPageId(String page_url){
		int result = 0;
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT page_id FROM allpages_table WHERE page_url=?");
			stmt.setString(1, page_url);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
			rs.close();
			stmt.close();
		}catch(SQLException e){
			System.out.println("Error: SQLException occurred while returning page id of an existing page.");
		}
		return result;
	}
	
		
	//This method disables added and fetched flag of allpages_table
	public static void disableUpdated(){
		try{
			Connection conn = new Database().returnConnection();
			PreparedStatement stmt = conn.prepareStatement("UPDATE allpages_table SET added=0,fetched=0");
			stmt.execute();
			stmt.close();
			conn.close();
			conn = null;
		}catch(SQLException e){
			System.out.println("Error: SQLException occurred while disabling the added flag.");
		}
	}
	
	
	//This method return whether the page is fetched at this crawl or not
	public static boolean fetched(int page_id){
		boolean result = true;
		try{
			PreparedStatement stmt = conn.prepareStatement("SELECT fetched FROM allpages_table WHERE page_id=?");
			stmt.setInt(1, page_id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			if(rs.getInt("fetched")==0) result = false;
			rs.close();
			stmt.close();
		}catch(SQLException e){
			System.out.println("Error: SQLException occurred while finding whether the page is fetched during this crawl or not.");
		}
		return result;
	}
	
	
	//This method will update fetched flag.
	public static void updateflag(int page_id) {
		try{
			PreparedStatement stmt = conn.prepareStatement("UPDATE allpages_table SET fetched=1 WHERE page_id=?");
			stmt.setInt(1, page_id);
			stmt.execute();
			stmt.close();
		}catch(SQLException e) {
			System.out.println("Error: SQLException occurred while updating fetched flag.");
		}
	}
	
	
	//This method adds broken links to table.
	public static void addBrokenLink(int seed_id, String page_url){
		try {
			PreparedStatement st = conn.prepareStatement("INSERT INTO brokenlink_table (seed_id, page_url) VALUES (?,?)");
			st.setInt(1, seed_id);
			st.setString(2, page_url);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while adding broken link to db.");
		}
	}
	
	//This method checks if a link is broken.
	public static boolean isBroken(String page_url){
		boolean result = false;
		PreparedStatement st;
		try {
			st = conn.prepareStatement("SELECT * FROM brokenlink_table WHERE page_url=?");
			st.setString(1, page_url);
			ResultSet rs = st.executeQuery();
			if(rs.next()) result = true;
			st.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while checking for broken link.");
		}
		return result;
	}
	
}
