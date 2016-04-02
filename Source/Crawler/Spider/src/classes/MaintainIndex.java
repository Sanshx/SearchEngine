package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaintainIndex {
	
	public static void clearIndexes(int seed_id){
		Connection conn = new Database().returnConnection();
		try {
			System.out.println("Clearing any old indexes if present.");
			PreparedStatement stmt1 = conn.prepareStatement("SELECT page_id FROM allpages_table WHERE seed_id=?");
			PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM words_detail WHERE page_id=?");
			PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM brokenlink_table WHERE seed_id=?");
			stmt3.setInt(1, seed_id);
			stmt3.executeUpdate();
			stmt1.setInt(1, seed_id);
			ResultSet rs = stmt1.executeQuery();
			while(rs.next()){
				stmt2.setInt(1, rs.getInt("page_id"));
				stmt2.execute();
			}
			rs.close();
			stmt1.close();
			stmt2.close();
			stmt3.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while Clearing the old indexes.");
		}
	}
	
	
	public static void insertFetchDate(int seed_id){
		Connection conn = new Database().returnConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO fetchdate_table (seed_id) VALUES(?)");
			stmt.setInt(1, seed_id);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while Clearing the old indexes.");
		}
	}
	
	
	public static boolean existsInFetchDate(int seed_id){
		boolean result = true;
		Connection conn = new Database().returnConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fetchdate_table WHERE seed_id=?");
			stmt.setInt(1, seed_id);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) result = false;
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while Clearing the old indexes.");
		}
		return result;
	}
	
}
