package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MainController")
public class MainController extends HttpServlet{
	
	HttpSession session = null;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		session = req.getSession(false);
		
		RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
		
		if(session != null){
			
			String benutzername = String.valueOf(session.getAttribute("benutzername"));
			
			req.setAttribute("benutzername", benutzername);
			
		}
		
		
		rd.forward(req, resp);
		
		
	}
}
