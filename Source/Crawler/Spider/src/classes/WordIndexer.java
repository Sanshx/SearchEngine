package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WordIndexer {
	
	static Connection conn = null;
	
	public static void wordIndexer(String word_name, int page_id, int degree, int type_id){
		conn = new Database().returnConnection();
		int word_id = 0;
		word_id = getPageId(word_name);
		if(word_id == 0) insertWord(word_name, page_id, degree, type_id);
		else updatePageEntry(word_id, page_id, degree, type_id);
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException while close connection after each word.");
		}
		conn = null;
	}
	
	
	//This method returns word_id if word exists, else it returns zero.
	public static int getPageId(String word_name){
		int id = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT word_id FROM words_table WHERE word_name=?");
			stmt.setString(1, word_name);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) id = rs.getInt("word_id");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while returning word_id give word_name.");
		}
		return id;
	}
	
	
	//This method insert an initial entry corresponding to a word which doesn't exists in database yet.
	public static boolean insertWord(String word_name, int page_id, int degree, int type_id){
		boolean insert = false;
		try {
			PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO words_table (word_name) VALUES (?)");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT word_id FROM words_table WHERE word_name=?");
			stmt1.setString(1, word_name);
			stmt2.setString(1, word_name);
			int status1 = stmt1.executeUpdate();
			if(status1 != 0 ) {
				ResultSet rs = stmt2.executeQuery();
				if(rs.next()) {
					insert = addPageEntry(rs.getInt("word_id"), page_id, degree, type_id);
				}
				rs.close();
			}
			stmt1.close();
			stmt2.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while inserting inital word entry.");
		}
		return insert;
	}
	
	
	//This method adds a page entry for a particular word.
	public static boolean addPageEntry(int word_id, int page_id, int degree, int type_id){
		boolean add = false;
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO words_detail (word_id, page_id, frequency, degree, type_id) VALUES (?,?,?,?,?)");
			stmt.setInt(1, word_id);
			stmt.setInt(2, page_id);
			stmt.setInt(3, 1);
			stmt.setInt(4, degree);
			stmt.setInt(5, type_id);
			int status = stmt.executeUpdate();
			if(status != 0 ) add = true;
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while adding an page entry to database.");
		}
		return add;
	}
	
	
	//This method updates a page entry in database if exists, if doesn't exists, it call addPageEntry function.
	public static void updatePageEntry(int word_id, int page_id, int degree, int type_id){
		try {
			PreparedStatement stmt1 = conn.prepareStatement("SELECT degree, frequency FROM words_detail WHERE word_id=? and page_id=?");
			PreparedStatement stmt2 = conn.prepareStatement("UPDATE words_detail SET frequency=frequency+1, degree=? WHERE word_id=? and page_id=?");
			stmt1.setInt(1, word_id);
			stmt1.setInt(2, page_id);
			ResultSet rs = stmt1.executeQuery();
			if(rs.next()){
				if(degree>rs.getInt("degree")) degree = rs.getInt("degree");
				stmt2.setInt(1, degree);
				stmt2.setInt(2, word_id);
				stmt2.setInt(3, page_id);
				stmt2.executeUpdate();
			}
			else{
				addPageEntry(word_id, page_id, degree, type_id);
			}
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while updating page entry in database.");
		}
		
	}
	
}