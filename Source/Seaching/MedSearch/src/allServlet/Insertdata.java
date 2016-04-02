package allServlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaClasses.Database;


@WebServlet("/Insertdata")
public class Insertdata extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
			int n = returnTypeId(request.getParameter("type"));
			String p = request.getParameter("city");
			String e = request.getParameter("address");
			try
			{
			Connection con= new Database().returnConnection();
			PreparedStatement ps= con.prepareStatement("insert into maps_list(type_id,city,address) values (?,?,?)");
			ps.setInt(1,n);
			ps.setString(2,p);
			ps.setString(3, e);
			ps.execute();
			out.close();
			System.out.println("Inserted");
			}
			catch(Exception e1)
			{
				System.out.println(e1);
			}
	}
	
	
	private int returnTypeId(String type_name){
		int type_id=0;
		try {
			PreparedStatement stmt = new Database().returnConnection().prepareStatement("SELECT type_id FROM type_table where type_name=?");
			stmt.setString(1, type_name);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) type_id = rs.getInt("type_id");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while returning type_id for type_name");
		}
		return type_id;
	}
}