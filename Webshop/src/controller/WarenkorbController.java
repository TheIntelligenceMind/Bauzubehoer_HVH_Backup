package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.QueryManager;
import entity.WarenkorbArtikel;
import enums.RESPONSE_STATUS;

/**
 * Servlet implementation class WarenkorbController
 */
@WebServlet("/warenkorb")
public class WarenkorbController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final QueryManager queryManager = QueryManager.getInstance();
	
    public WarenkorbController() {
        super();

    }

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
    	RequestDispatcher rq = null; 
    	resp.setContentType("text/html");
    		  	
		String method = req.getParameter("method");
		
		if(method == null){
			method = "";
		}
    	
    	switch(method){
	    	case "artikelInDenWarenkorb": 		
	    		if(artikelHinzufuegen(req)){
	    			updateWarenkorb(req);
	    			
	    			resp.addHeader("status", RESPONSE_STATUS.HINWEIS.toString());
    				resp.addHeader("hinweismeldung", "Der Artikel wurde dem Warenkorb hinzugefügt.");
	    		}else{
	    			resp.addHeader("status", RESPONSE_STATUS.FEHLER.toString());
    				resp.addHeader("fehlermeldung", "Der Artikel konnte nicht hinzugefügt werden.");	
	    		}
	    		
	    		rq = req.getRequestDispatcher("/suchen");
	    		break;
	    	case "artikelAusWarenkorbLoeschen":
	    		if(req.getParameter("row") != null){
	    			int row = Integer.valueOf(req.getParameter("row") );
	    			boolean hasDeleted = false;  
	    			
	    			hasDeleted = queryManager.deleteArtikelFromWarenkorb(row, req.getSession().getAttribute("emailadresse").toString());
	
	    			if(hasDeleted){
	    				updateWarenkorb(req);
	    				
	    				resp.addHeader("status", RESPONSE_STATUS.HINWEIS.toString());
	    				resp.addHeader("hinweismeldung", "Der Artikel wurde aus dem Warenkorb entfernt.");
	    			}else{
	    				resp.addHeader("status", RESPONSE_STATUS.FEHLER.toString());
	    				resp.addHeader("fehlermeldung", "Es ist ein Problem beim Löschen aufgetreten.");	
	    			}
	    		}
	    		
	    		rq = req.getRequestDispatcher("index.jsp");
	    		resp.addHeader("contentSite", "warenkorbPanel");		
	    		break;
	    	default:
	    		rq = req.getRequestDispatcher("index.jsp");
	    		resp.addHeader("contentSite", "warenkorbPanel");	
	    		break;   	
    	}		
		
		
		
		rq.forward(req, resp);
	}
    
    private void updateWarenkorb(HttpServletRequest req){
    	String benutzerEmailadresse = req.getSession().getAttribute("emailadresse").toString();
    	
    	List<WarenkorbArtikel> warenkorbartikelListe = queryManager.selectAllWarenkorbartikelByBenutzeremailadresse(benutzerEmailadresse);	

    	req.getSession().setAttribute("warenkorbartikelliste", warenkorbartikelListe);   	
    }

    private boolean artikelHinzufuegen(HttpServletRequest req){  	
    	int artikelnummer = 0;
    	boolean added = false;
    	String emailadresseBenutzer = String.valueOf(req.getSession().getAttribute("emailadresse"));
    	
    	try{
    		artikelnummer = Integer.valueOf(req.getParameter("artikelnummer"));
    	}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	
    	added = queryManager.addArtikelToWarenkorb(emailadresseBenutzer, artikelnummer);
     	
    	return added;
    }
}
