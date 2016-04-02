package allServlet;


import java.io.IOException;
import java.io.PrintWriter;
import javaClasses.Validate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        
	        if(Validate.checkUser(username, password))
	        {
	        	HttpSession sess=request.getSession(true);
	        	sess.setAttribute("username", username);
	            RequestDispatcher rs = request.getRequestDispatcher("AdminPanel.jsp");
	            rs.forward(request, response);
	        }
	        else
	        {
	           out.println("Wrong username and password combination...!!!");
	           RequestDispatcher rs = request.getRequestDispatcher("AdminHome.html");
	           rs.include(request, response);
	        }
	}

}
