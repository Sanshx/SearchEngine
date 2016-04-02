package allServlet;

import java.io.IOException;
import java.io.PrintWriter;
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


@WebServlet("/LocationMapping")
public class LocationMapping extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String treatment_id = request.getParameter("treatment_id");
		String location_id = request.getParameter("location_id");
		PrintWriter out = response.getWriter();
		Connection conn = new Database().returnConnection();
		int status = 0;
		try {
			PreparedStatement st = conn.prepareStatement("SELECT * FROM location_mapping where treatment_id=? and location_id=?");
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO location_mapping (treatment_id,location_id) values(?,?)");
			st.setString(1, treatment_id);
			st.setString(2, location_id);
			ResultSet rs = st.executeQuery();
			if(rs.next()) status = 2;
			if(status == 0){
				stmt.setString(1, treatment_id);
				stmt.setString(2, location_id);
				status = stmt.executeUpdate();
			}
			if(status == 0) out.write("Mapping failed....");
			else if(status == 2) out.write("Mapping exists....");
			else if(status == 1) out.write("Mapping successful....");
			else out.write("Mapping successful....");
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while mapping location with treatment.");
		}
	}

}
