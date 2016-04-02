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


@WebServlet("/GetTreatments")
public class GetTreatments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "<option value='0'>----Select Treatment----</option>";
		Connection conn = new Database().returnConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM treatment_table");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) result = result.concat("<option value='"+rs.getInt("treatment_id")+"'>"+rs.getString("treatment_name")+"</option>");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while fetching treatment_name in maps page");
		}
		response.getWriter().write(result);
	}

}
