package javaClasses;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.DriverManager;

public class Database {
	
	Connection conn = null;
	
	//This method will return a connection to the database
	public Connection returnConnection(){

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Context env = (Context)new InitialContext().lookup("java:comp/env");
			String db_host = (String)env.lookup("host_db");
			String db_name = (String)env.lookup("name_db");
			String db_user = (String)env.lookup("user_db");
			String db_pass = (String)env.lookup("pass_db");
			conn=DriverManager.getConnection("jdbc:mysql://"+db_host+"/"+db_name,db_user,db_pass);
			
		/*	Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/Spider";
			conn = DriverManager.getConnection(url, "root", "password");*/
		} catch (Exception e) {
			System.out.println("Error: Exception while taking database connection. ");
		} 
		return conn;
	}

}
