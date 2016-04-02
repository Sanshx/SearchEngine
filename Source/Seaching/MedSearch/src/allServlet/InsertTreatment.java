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


@WebServlet("/InsertTreatment")
public class InsertTreatment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String treatment_name = request.getParameter("treat");
		Connection conn = new Database().returnConnection();
		int status = 0;
		try {
			PreparedStatement st = conn.prepareStatement("SELECT * FROM treatment_table WHERE treatment_name=?");
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO treatment_table (treatment_name) values (?)");
			st.setString(1, treatment_name);
			ResultSet rs = st.executeQuery();
			if(rs.next()) status = 2;
			if(status != 2){
				stmt.setString(1, treatment_name);
				status = stmt.executeUpdate();
			}
			if(status == 0) out.write("Insertion failed...!!!");
			else if(status == 2) out.write("Treatment exists...!!!");
			else out.write("Treatment name inserted ...!!!");
		} catch (SQLException e) {
			System.out.println("Error: SQLException occurred while inserting treatment_name.");
		}
		
	}

}
