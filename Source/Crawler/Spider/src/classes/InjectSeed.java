package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class InjectSeed {
	
	
	//Inserting seed into seeds_table in database.
	public static void injectSeed(String seed_url, String type_name) {
		seed_url = "http://".concat(seed_url);
		if(!seed_url.endsWith("/")) seed_url = seed_url.concat("/");
		int type_id = new InjectSeed().typeIdReturn(type_name);
		System.out.println("Injecting to Database --> "+seed_url);
		new InjectSeed().insertSeed(seed_url, type_id);
	}
	
	
	//Cleaning the removed seeds from the seeds.xml (Must be called directly after insertion is done)
	public static void cleanSeeds(){
		Connection conn = null;
		ResultSet rs = null;
		try{
			conn = new Database().returnConnection();
			PreparedStatement stmt1 = conn.prepareStatement("SELECT seed_id FROM seeds_table WHERE seed_updated=0");
			PreparedStatement stmt2 = conn.prepareStatement("DELETE from allpages_table WHERE seed_id=?");
			PreparedStatement stmt3 = conn.prepareStatement("DELETE from seeds_table WHERE seed_updated=0");
			PreparedStatement stmt4 = conn.prepareStatement("UPDATE seeds_table SET seed_updated=0");
			PreparedStatement stmt5 = conn.prepareStatement("DELETE from brokenlink_table WHERE seed_id=?");
			PreparedStatement stmt6 = conn.prepareStatement("DELETE from fetchdate_table WHERE seed_id=?");
			
			System.out.println("\nDeleting outdated seeds...");
			rs = stmt1.executeQuery();
			while(rs.next()){
				stmt2.setInt(1, rs.getInt(1));
				stmt2.execute();
				stmt5.setInt(1, rs.getInt(1));
				stmt5.execute();
				stmt6.setInt(1, rs.getInt(1));
				stmt6.execute();
			}
			stmt3.execute();
			stmt4.execute();
			rs.close();
			stmt1.close();
			stmt2.close();
			stmt3.close();
			stmt4.close();
			conn.close();
			conn = null;
			stmt1 = null;
			stmt2 = null;
			stmt3 = null;
			stmt4 = null;
			rs = null;
		}catch(SQLException e){
			System.out.println("SQLException occured while cleaning seeds_table for outdated entries.");
			e.printStackTrace();
		}
	}
	
	
	//dbExists will return true of the seed exists in seeds_table, else will return false.
	public boolean insertSeed(String seed_url, int type_id){
		boolean status=false;
		int update_status=0;
		Connection conn = null;
		try{
			conn = new Database().returnConnection();
			PreparedStatement stmt1 = conn.prepareStatement("SELECT * from seeds_table WHERE seed_url=?");
			PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO seeds_table (seed_url, type_id, seed_updated) VALUES (?,?,1)");
			PreparedStatement stmt3 = conn.prepareStatement("UPDATE seeds_table SET seed_updated=1, type_id=? WHERE seed_url=?");
			stmt1.setString(1, seed_url);
			ResultSet rs = stmt1.executeQuery();
			if(!rs.next()) {
				stmt2.setString(1, seed_url);
				stmt2.setInt(2, type_id);
				status = stmt2.execute();
			}
			else {
				stmt3.setInt(1, type_id);
				stmt3.setString(2, seed_url);
				update_status = stmt3.executeUpdate();
				if(update_status!=0) status = true;
			}
			stmt1.close();
			stmt2.close();
			stmt3.close();
			rs.close();
			conn.close();
			conn = null;
			stmt1 = null;
			stmt2 = null;
			stmt3 = null;
			rs = null;
		}catch(SQLException e){
			System.out.println("Error: SQLException while searching and inserting seed.");
		}
		return status;
	}

	
	//This method returns the type_id when type_name is passed in it.
	public int typeIdReturn(String type_name){
		Connection conn = null;
		int type_id = 0;
		try{
			conn = new Database().returnConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT type_id FROM type_table WHERE type_name=?");
			stmt.setString(1, type_name);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			type_id = rs.getInt(1);
			stmt.close();
			rs.close();
			conn.close();
			conn = null;
			stmt = null;
			rs = null;
		}catch(SQLException e){
			System.out.println("Error: SQLException while fetching type_id for type_name.");
		}
		return type_id;
	}
		
}
