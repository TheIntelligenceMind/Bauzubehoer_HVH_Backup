package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.QueryManager;
import entity.Adresse;
import entity.Benutzer;
import enums.ENUM_MELDUNG_ART;
import enums.ENUM_RESPONSE_STATUS;
import helper.AdressenHelper;
/**
 * <pre>
 * <h3>Beschreibung:</h3> Die Klasse ist f�r den Themenbereich Bestellungen zust�ndig. 
 * Hier werden alle GET- und POST-Schnittstellenaufrufe verarbeitet und an die View weitergeleitet
 * </pre>
 * @author Tim Hermbecker
 */
@WebServlet("/meineBestellungen")
public class BestellungenController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static QueryManager queryManager = QueryManager.getInstance();
	private String dispatchSite = "index.jsp";

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		RequestDispatcher rd = null;
		resp.setContentType("text/html"); 
		
		// pr�fen ob es eine Session gibt, wenn nicht an die Startseite weiterleiten
		if(req.getSession().getAttribute("benutzer") == null){
			rd = req.getRequestDispatcher("/suchen");	
    		rd.forward(req, resp);
    		return;
		}
		
		
		// Berechtigung f�r die Seite pr�fen
    	if(((Benutzer)req.getSession().getAttribute("benutzer")).getRolle().getSichtBestellungen() != 1){
    		rd = req.getRequestDispatcher("/suchen");	
    		rd.forward(req, resp);
    		return;
    	}
			
		String method = req.getParameter("method");
		
		if(method == null){
			method = "";
		}
			
		switch(method){
			case "bestellungenAnzeigen":				
				resp.addHeader("contentSite", "meineBestellungenPanel");
				break;
			case "bestellungErfassenS1Anzeigen":
				Benutzer benutzer = queryManager.getBenutzerByEMailAdresse(((Benutzer)req.getSession().getAttribute("benutzer")).getEmailadresse());			
				req.setAttribute("benutzer", benutzer);
				dispatchSite = "index.jsp";
				resp.addHeader("contentSite", "bestellungLieferadressePanel");
				break;
			case "bestellungErfassenS2Anzeigen":
				dispatchSite = "index.jsp";
				resp.addHeader("contentSite", "bestellungZahlungsartenPanel");
				break;
			case "bestellungErfassenS3Anzeigen":
				dispatchSite = "index.jsp";
				resp.addHeader("contentSite", "bestellungZusammenfassungPanel");	
				break;
			case "bestellungErfassenS4Anzeigen":
				dispatchSite = "index.jsp";
				resp.addHeader("contentSite", "bestellungAbschlussPanel");	
				break;
			case "bestellungErfassenS1Bestaetigt":
				bestellungS1Validieren(req, resp);
				break;
			case "bestellungErfassenS2Bestaetigt":
				bestellungS2Validieren(req, resp);
				break;
			case "bestellungErfassenS3Bestaetigt":
				bestellungAbschliessen(req, resp);			
				break;
			default:
				resp.addHeader("contentSite", "meineBestellungenPanel");
				break;
		}	
		
		rd = req.getRequestDispatcher(dispatchSite);
		rd.forward(req, resp);	
	}
    
    private void bestellungS1Validieren(HttpServletRequest req, HttpServletResponse resp){
    	Adresse lieferAdresse = new Adresse().init(req.getParameter("strasse"), req.getParameter("hausnummer"), req.getParameter("postleitzahl"), req.getParameter("ort"), "");
    	
    	req.getSession().setAttribute("bestellvorgang_lieferadresse", lieferAdresse);
    	
    	if(!lieferAdresse.getStrasse().isEmpty() && !lieferAdresse.getHausnummer().isEmpty() && !lieferAdresse.getPostleitzahl().isEmpty() && !lieferAdresse.getOrt().isEmpty()){

    		if(AdressenHelper.getInstance().validateAdresse(lieferAdresse)){
    			dispatchSite = "/meineBestellungen?method=bestellungErfassenS2Anzeigen";	
    		}else{
    			Benutzer benutzer = queryManager.getBenutzerByEMailAdresse(((Benutzer)req.getSession().getAttribute("benutzer")).getEmailadresse());			
    			req.setAttribute("benutzer", benutzer);
    			
    			dispatchSite = "index.jsp";
    			resp.addHeader("contentSite", "bestellungLieferadressePanel");
    			
    			resp.addHeader("Status", ENUM_RESPONSE_STATUS.FEHLER.toString());
    			resp.addHeader(ENUM_MELDUNG_ART.FEHLERMELDUNG.toString(), "Die Lieferadresse ist nicht g�ltig.");
    		}		
    	}else{
			Benutzer benutzer = queryManager.getBenutzerByEMailAdresse(((Benutzer)req.getSession().getAttribute("benutzer")).getEmailadresse());			
			req.setAttribute("benutzer", benutzer);
			
			dispatchSite = "index.jsp";
			resp.addHeader("contentSite", "bestellungLieferadressePanel");
			
			resp.addHeader("Status", ENUM_RESPONSE_STATUS.FEHLER.toString());
			resp.addHeader(ENUM_MELDUNG_ART.FEHLERMELDUNG.toString(), "Die Lieferadresse ist nicht vollst�ndig.");
    	}
    }
    
	private boolean bestellungS2Validieren(HttpServletRequest req, HttpServletResponse resp){
		return false; 	
	}
	
	private boolean bestellungAbschliessen(HttpServletRequest req, HttpServletResponse resp){
		
		//Mail Versand
		
		return false;
	}
}
