package allServlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javaClasses.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ListCity")
public class ListCity extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String result="";
		PrintWriter out= response.getWriter();
		result = "<select id='city'><option value='0'>----Select City----</option>";
		try
		{
		Connection con= new Database().returnConnection();
		PreparedStatement ps= con.prepareStatement("select DISTINCT city from maps_list");
		ResultSet rs=ps.executeQuery();
		while(rs.next())  
		{
			result = result.concat("<option value="+rs.getString("city")+">"+rs.getString("city")+"</option>");
			}
		result = result.concat("</select>");
	    out.println(result);
		out.close();
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
      }
	}
