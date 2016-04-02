package allServlet;


import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javaClasses.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Location
 */
@WebServlet("/Location")
public class Location extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
		PrintWriter out= response.getWriter();
		 String city = request.getParameter("city");
		 String treatment = request.getParameter("treatment");
		 String result="";
		 String type = request.getParameter("type");
		 if(treatment.equals("0")) treatment = "treatment_id";
		 if(city.equals("0")) city = "B.city";
		 else {
			 city = "'".concat(city);
			 city = city.concat("'");
		 }
		 try
			{
			Connection con= new Database().returnConnection();
			Statement stmt = con.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT DISTINCT B.address,B.type_id,B.location_id FROM maps_list AS B INNER JOIN (SELECT location_id FROM location_mapping where treatment_id="+treatment+") AS A ON A.location_id=B.location_id WHERE B.city="+city+" and B.type_id="+type+"");                    //ps.executeQuery();
			while(rs.next())  
			{
				result=result.concat("<div style='cursor:pointer;' id='"+rs.getInt("B.location_id")+"' onclick='UpdateMap(this)'>"+rs.getString("B.address")+"</div><br>");
			}
			if(result.equals("")) result = result.concat("NO RESULTS FOUND !!!");
		    out.println(result);
			}catch(Exception e)
			{
				System.out.println(e);
			}
		
	}

	
}
