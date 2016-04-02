package classes;

import java.sql.Connection;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.sql.DriverManager;

public class Database {
	
	Connection conn = null;
	
	//This method will return a connection to the database
	public Connection returnConnection(){

		try {
			String location = "";
			String name = "";
			String user = "";
			String password = "";
			File xmlf=new File("bin/db_config/database.xml");
			DocumentBuilderFactory docFactory =  DocumentBuilderFactory.newInstance();
	        	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlf);
			NodeList first = doc.getDocumentElement().getChildNodes();
			location = first.item(1).getTextContent();
			name = first.item(3).getTextContent();
			user = first.item(5).getTextContent();
			password = first.item(7).getTextContent();
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://"+location+"/"+name+"";
			conn = DriverManager.getConnection(url, user, password);
			location = null;
			name = null;
			user = null;
			password = null;
		} catch (Exception e) {
			System.out.println("Error: Exception while taking database connection."+e);
		}
		return conn;
	}

}
