<%@page import="entity.WarenkorbArtikel"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>


<!-- 
Autor dieser Datei: Tim Hermbecker, Lukas Vechtel

Diese Datei behandelt den Warenkorb, indem Artikel entfernt, Mengen angepasst oder per Link auf die Artikeldetailansicht verwiesen werden k�nnen.
 -->





<%
	final DecimalFormat formater = new DecimalFormat("#0.00");
%>

<script type="text/javascript">

function deleteRow(element){
	var result = confirm("Sind Sie sicher, dass der Artikel entfernt werden soll?");
	
	if(result){
		$(document).ready(function() {
			var row = element.closest('tr').rowIndex;
	      	               
           window.location.href = "warenkorb?method=artikelAusWarenkorbLoeschen&row="+row;
           element.closest('tr').remove();    
		});
	}
}

function updateQuantity(element, artikelnummer){
	var menge = element.value;

	if(menge != null && artikelnummer != null){
		$(document).ready(function() {	      	               
	          window.location.href = "warenkorb?method=artikelMengeVeraendern&artikelnummer="+artikelnummer+"&menge="+menge;  
		}); 
	}	
}

</script>

<div class="showing" id="warenkorbPanel">
	<h1>Warenkorb</h1>
	<div id="warenkorbTabellen">
	      <table id="artikelTabelle">
	        <colgroup>
		       <col span="1" style="width: 8%;">
		       <col span="1" style="width: 16%;">
		       <col span="1" style="width: 57%;">
		       <col span="1" style="width: 14%;">
		       <col span="1" style="width: 5%;">
		    </colgroup>
	        
	        <thead>
	          <tr>
	            <th>Menge</th>
	            <th>Artikelnummer</th>
	            <th>Produkt</th>
	            <th>Summe</th>
	            <th></th>
	          </tr>
	        </thead>
	        <tbody>
	        	<%
		        	List<?> warenkorbartikelListe = (List<?>)session.getAttribute("warenkorbartikelliste");
		        	double gesamt = 0;
		        	int versandkosten = 20;
		        	double mwst = 0;
	
		        	if(warenkorbartikelListe != null){
			        	for(Object o : warenkorbartikelListe){
			        		WarenkorbArtikel warenkorbartikel = (WarenkorbArtikel)o;
			        		
			        		out.println("<tr>" +
			        		"<td class='tablecell'><input style='font-size:14px; width:20px; border:none;' type='text' onchange='updateQuantity(this," + warenkorbartikel.getArtikel().getNummer() + ")' name='menge' value='" + String.valueOf(warenkorbartikel.getMenge()) + "'></td>" +
			        		"<td class='tablecell'>" + String.format("%04d", warenkorbartikel.getArtikel().getNummer()) + "</td>" +
			        		"<td class='tablecell'>" + warenkorbartikel.getArtikel().getBezeichnung() + "</td>" +
			        		"<td class='tablecell'>" + formater.format(warenkorbartikel.getArtikel().getPreis()*warenkorbartikel.getMenge()) +" &euro;</td>" +
			        		"<td class='rightcell'>" + "<a class='trashSymbol' onclick='deleteRow(this)'><i class='fa fa-trash-o'></i></a>" + "</td>" +
			        		"</tr>");
			        		gesamt = gesamt + (warenkorbartikel.getArtikel().getPreis() * warenkorbartikel.getMenge());
			        		mwst = mwst + (warenkorbartikel.getArtikel().getPreis() * warenkorbartikel.getMenge())*0.19;  	
		        		}	
		        	}
	        	%>
	        </tbody>
	      </table>
      
	      <table id="zusatzTabelle">
	      	<colgroup>
		       <col span="1" style="width: 24%;">
		       <col span="1" style="width: 57%;">
		       <col span="1" style="width: 19%;">
		    </colgroup>
	      	<tbody>
	        	<tr>
	          		<td class="textLeft" colspan="1">Versandkosten:</td>
	          		<td colspan="1"></td>
			        <td><% out.println(formater.format(versandkosten)); %> &euro;</td>
		       	</tr>
				<tr>
				 	<td class="textLeft" colspan="1">MwSt:</td>
					<td colspan="1"></td>
					<td><% out.println(formater.format(mwst)); %> &euro;</td>
				</tr>
				<tr>
					<td class="textLeftBold" colspan="1">Gesamt (Brutto):</td>
				 	<td colspan="1"></td>
				 	<td style="font-weight:bold"><% out.println(formater.format(gesamt + mwst + versandkosten)); %> &euro;<td>
				</tr>
	        </tbody>  
		</table>
  	</div>
	
  	<button id="btnBestellen" type="submit" name="method" value="zahlungsartenAnzeigen()">Bestellen</button>
</div>