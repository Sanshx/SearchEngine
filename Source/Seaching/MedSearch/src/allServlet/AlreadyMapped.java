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


@WebServlet("/AlreadyMapped")
public class AlreadyMapped extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String location_id = request.getParameter("location_id");
		String result = "<option>----Already mapped treatments----</option>";
		Connection conn = new Database().returnConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT A.treatment_name, A.treatment_id FROM treatment_table AS A INNER JOIN (SELECT treatment_id FROM location_mapping WHERE location_id=?) AS B ON A.treatment_id=B.treatment_id");
			stmt.setString(1, location_id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) result = result.concat("<option value='"+rs.getString("treatment_id")+"'>"+rs.getString("treatment_name")+"</option>");
			conn.close();
			response.getWriter().write(result);
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while fetching already mapped treatments for admin panel.");
		}
	}

}
