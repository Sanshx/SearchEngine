package allServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javaClasses.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GetDropDown")
public class GetDropDown extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "<option>----Select location from here----</option>";
		Connection conn = new Database().returnConnection();
		try {
			PreparedStatement stmt1 = conn.prepareStatement("SELECT address,location_id FROM maps_list");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT treatment_name,treatment_id FROM treatment_table");
			ResultSet rs1 = stmt1.executeQuery();
			while(rs1.next()) result = result.concat("<option value='"+rs1.getInt("location_id")+"'>"+rs1.getString("address")+"</option>");
			result = result.concat("###");
			result = result.concat("<option>----Select treatment for mapping----</option>");
			ResultSet rs2 = stmt2.executeQuery();
			while(rs2.next()) result = result.concat("<option value='"+rs2.getInt("treatment_id")+"'>"+rs2.getString("treatment_name")+"</option>");
			rs2.clearWarnings();
			rs1.close();
			stmt1.close();
			stmt2.close();
			conn.close();
			response.getWriter().write(result);
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while fetch drop downs for admin panel.");
		}
		
	}

}
