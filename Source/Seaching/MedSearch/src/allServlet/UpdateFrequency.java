package allServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javaClasses.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UpdateFrequency")
public class UpdateFrequency extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		Connection conn = new Database().returnConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE words_table SET frequency=frequency+1 where word_name=?");
			stmt.setString(1, query);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {}
	}

}
