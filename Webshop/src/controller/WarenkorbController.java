package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WarenkorbController
 */
@WebServlet("/warenkorb")
public class WarenkorbController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public WarenkorbController() {
        super();

    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
		
		response.addHeader("contentSite", "warenkorb");
		
		rq.forward(request, response);
		
		
	}

}