<%@page import="entity.Artikel"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>

<!-- 
Autor dieser Datei: Tim Hermbecker, Lukas Vechtel

Diese Datei behandelt die Anzeige von Artikeln für den Kunden.
Hier wird auch eine gefilterte Suche mit ausgegeben.
 -->


<%
	final DecimalFormat preisFormatter = new DecimalFormat("#0.00");	
%>
<div class="showing" id="artikelListeKunden">
	<div id="artikelKundenTabellen">
       	<%
		     	List<?> artikelListe = null;
		     	
		     	artikelListe = ((List<?>)request.getAttribute("artikelliste"));
		     
		     	if(artikelListe != null){
		     		for(Object o : artikelListe){
			     		Artikel a = (Artikel)o;
			     		
			     		out.println("<div id='artikelListeKundenDesign'>" + 
			    		"<div id='artikelListeKundenBild'></div>" +
			     		"<div id='artikelListeKundenBezeichnung'>" + a.getBezeichnung() + "</div>" +
			       		"<div id='artikelListeKundenPreis'>" + "&euro; " +  preisFormatter.format(a.getPreis()) + "</div>" +
			     	    "<div id='artikelListeKundenBeschreibung'>" + a.getBeschreibung() + "</div>" +
			     	    "<form action='warenkorb'><input type='hidden' name='method' value='artikelInDenWarenkorb'><input type='hidden' name='artikelnummer' value='" + a.getNummer() + "'><button id='artikelListeKundenWarenkorbButton'>In den Warenkorb</button></form>" +
			     		"</div>");
			     	}
		     	}
		     	
       	%>
	</div>
</div>