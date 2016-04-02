package allServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javaClasses.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GetFrequency")
public class GetFrequency extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn  = new Database().returnConnection();
		try {
			String recommend = "";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT sum(frequency-1) from words_table");
			rs.next();
			int a = rs.getInt(1);
			if(a<1000) recommend = "Update and Clearing not recommended..!!";
			else if(a>1000 && a<10000) recommend = "Update recommended. Clearing not recommended..!!";
			else if(a>10000) recommend = "Update recommended and Clearing recommended..!!";
			response.getWriter().write(a+" Searches yet... "+recommend);
		} catch (SQLException e) {
			System.out.println("Error: Exception occurred while Clearing frequency.");
		}
	}

}
