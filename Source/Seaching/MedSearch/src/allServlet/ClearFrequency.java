package allServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javaClasses.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/ClearFrequency")
public class ClearFrequency extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn  = new Database().returnConnection();
		try {
			Statement stmt = conn.createStatement();
			int a = stmt.executeUpdate("UPDATE words_table SET frequency=1");
			if(a>0) response.getWriter().write("Frequencies set to 1..!!");
			else response.getWriter().write("No row updated..!!");
		} catch (SQLException e) {
			System.out.println("Error: Exception occurred while Clearing frequency.");
		}
	}

}
