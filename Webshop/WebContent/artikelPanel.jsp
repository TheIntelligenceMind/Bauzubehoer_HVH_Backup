<div class="showing" id="artikelPanel">

	<h1>Artikel anlegen</h1>
	
	<form action="artikelAnlegen" method="POST">
		<div id=artikelPanelBeschreibung>
			<input title="Hinweis: Nur eine vierstellige Artikelnummer eingeben." class="inputField" type="text" name="nummer" placeholder="Artikelnummer">
			<input title="Bezeichnung" class="inputField" type="text" name="bezeichnung" placeholder="Bezeichnung">
			<textarea title="Beschreibung" class="inputArea" wrap="soft" name="beschreibung" placeholder="Beschreibung"></textarea>
		</div>
		<div id=artikelPanelZahlen>
			<input title="Hinweis: Der Preis muss mit einem Punkt getrennt sein!" class="inputField" type="text" name="preis" placeholder="Preis">
			<input title="Lagermenge" class="inputField" type="text" name="lagermenge" placeholder="Lagermenge">
		</div>	
			<input id="btnArtikelAnlegen" type="submit" value="anlegen">	
	</form>
	
</div>