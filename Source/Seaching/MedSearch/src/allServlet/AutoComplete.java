package allServlet;

import java.io.File;
import java.io.IOException;

import javaClasses.Matcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@WebServlet("/AutoComplete")
public class AutoComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		File xmlfile = new File(getServletContext().getRealPath("autocomplete/transfer.xml"));
        String string= request.getParameter("string");
        String result = "";
		try {
			result = new Matcher().match(string,xmlfile);
		} catch (ParserConfigurationException | SAXException e) {
			System.out.println("Error: Exeption occurred while ");
		}
        response.getWriter().write(result);
	}

}
