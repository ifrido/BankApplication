package application;

import java.text.DecimalFormat;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * ----------- Christian Kiss ----------- 
 * InterfaceAdmin alle Funktionen des
 * Admin-Interfaces
 */

public class InterfaceAdminController {
	private Bank bank = Bank.getInstance();
	private Kunde user;

    Stage prevStage;

    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }
	
	/**
	 * ----------- Christian Kiss ----------- 
	 * alle FXML-Variablen
	 */

	// Variablen Pane
	@FXML
	Pane kundenAnlegen, kontoAnlegen, kontoZahlungen;

	// Variablen Kunde anlegen
	@FXML
	TextField kundeKundennummer, kundeName;
	@FXML
	PasswordField kundePassword;
	@FXML
	CheckBox kundeRolle;
	@FXML
	Text kundeStatus;	
	
	
	// Variablen Konto anlegen
	@FXML
	ComboBox<String> kontoKundenliste;
	@FXML
	Text kontoStatus;

	// Variablen Zahlungen
	@FXML
	ComboBox<String> zahlungenKonto, zahlungenUebKonto, zahlungenEinAusBox;
	@FXML
	TextField zahlungenBetrag, zahlungenUebEmpfaenger, zahlungenUebBetrag,
			zahlungenUebMitteilung;
	@FXML
	Text zahlungenStatusEinAus, zahlungenStatusUeb;
	
	//Variablen Logout
	@FXML
	TitledPane logoutPane;
	
	//Variable angemeldeter User
	@FXML
	Text userNameStatus;
	
	
	
	

	/**
	 * ----------- Christian Kiss ----------- 
	 * Pane visibility
	 */
	
	// Methode um alle Panes zu disablen
	public void disableAllPanes() {
		kundenAnlegen.setVisible(false);
		kontoAnlegen.setVisible(false);
		kontoZahlungen.setVisible(false);
	}

	// Pane kundenAnlegen anzeigen
	public void PaneKundenAnlegen() {
		disableAllPanes();
		kundenAnlegen.setVisible(true);

		// nochmal überdenken, eventuell mit Counter und Globaler Variable
		kundeKundennummer.setText("10"
				+ String.format("%05d", bank.holeKunden().size()));

	}

	// Pane kontoAnlegen anzeigen
	public void PaneKontoAnlegen() {
		disableAllPanes();
		kontoAnlegen.setVisible(true);

		// Kundenliste erst leeren und dann anzeigen
		kontoKundenliste.getItems().clear();
		for (Kunde q : bank.holeKunden()) {
			kontoKundenliste.getItems().add(q.getKundennummer() + " | " + q.getName());
		}
	}

	// Pane kontoZahlungen anzeigen
	public void PaneZahlungen() {
		disableAllPanes();
		kontoZahlungen.setVisible(true);

		// Mit vorgegebenen Funktionen können die Konten
		// leider nur über den Kunden geholt werden 

		// DropDown-Felder leeren und befüllen
		zahlungenKonto.getItems().clear();
		zahlungenUebKonto.getItems().clear();

		// Kunden holen
		for (Kunde kunde : bank.holeKunden()) {

			// Konten des Kunden holen
			for (Konto konto : bank.holeKonten(kunde)) {

				// Felder befüllen
				zahlungenKonto.getItems().add(konto.getNummer());
				zahlungenUebKonto.getItems().add(konto.getNummer());
			}
		}
	}


		// ------ Funktion ok - ck ------
		// Funtion Kunde anlegen
		public void kundenAnlegen(){
			
			// Neuen Kunden anlegen
			bank.neuerKunde(kundeKundennummer.getText(), kundePassword.getText(), kundeName.getText(), kundeRolle.isSelected());		
				

			// Felder zurücksetzen
			kundeKundennummer.clear();
			kundeName.clear();
			kundePassword.clear();
			kundeRolle.setSelected(false);
			
			kundeStatus.setText("Kunde erfolgreich angelegt");
			
			// Nochmal überdenken, mit Counter in BANK !!!!
			kundeKundennummer.setText("10" + String.format("%05d", bank.holeKunden().size()));
			
		}
	
	
	// ------ Funktion ok - ck ------
	// Neues Konto anlegen
	public void kontoAnlegen() {

		// Variable kürzen, da Name noch mit angezeigt wird für einfachere Bedienung
		Kunde kundeKonto = bank.holeKunde(kontoKundenliste.getValue().substring(0, 7));
		Konto neuesKonto = bank.neuesKonto(0);
		kundeKonto.addKonto(neuesKonto);

		// Status schreiben
		kontoStatus.setText("Konto " + neuesKonto.getNummer() + "\nfür "
				+ kundeKonto.getName() + "\nwurde angelegt");
	}


	/**
	 * ----------- Christian Kiss ----------- 
	 * Einzahlung oder Auszahlung
	 */
	public void zahlungenEinAus() {

		boolean eingabefehler = false;
		String fehlerAusgabe = "";

		// Prüfen ob Konto angegeben
		if (zahlungenKonto.getValue() == null) {
			fehlerAusgabe += "kein Konto angegeben\n";
			eingabefehler = true;
		}

		// Prüfen ob Ein- oder Auszahlung angegeben
		if (zahlungenEinAusBox.getValue() == null) {
			fehlerAusgabe += "Bitte angeben, ob Ein- oder Auszahlung\n";
			eingabefehler = true;
		}

		// Prüfen, ob Betrag in double wandelbar ist
		// und auf 2 Nachkommastellen runden 
		double betragKorrekt = 0;
		try {
			// Prüfung, ob Double möglich
			double betrag = Double.parseDouble(zahlungenBetrag.getText());

			// Runden auf 2 Nachkommastellen
			String betragFormat = ((new DecimalFormat("0.00")).format(betrag));

			// Umwandeln in Double
			betragKorrekt = Double.parseDouble(betragFormat);

		} catch (NumberFormatException e) {
			fehlerAusgabe += "Fehler im Feld Betrag";
			eingabefehler = true;
		}

		// Wenn keine eingabefehler Funktion ausführen, sonst Status-Text angeben
		if (eingabefehler == false) {

			String statusText = "";

			// Geld einzahlen
			if ("Einzahlen".equals(zahlungenEinAusBox.getValue())) {
				bank.einzahlen(zahlungenKonto.getValue(), betragKorrekt);
				statusText = "Einzahlung";
			}
			
			// Geld einzahlen
			else {
				bank.abheben(zahlungenKonto.getValue(), betragKorrekt);
				statusText = "Auszahlung";
			}

			// Felder zurücksetzen
			zahlungenBetrag.clear();
			zahlungenStatusEinAus.setText(statusText + " erfolgreich");
			
		} 
		
		else {
			zahlungenStatusEinAus.setText(fehlerAusgabe);
		}

	}


	
	/**
	 * ----------- Christian Kiss ----------- 
	 * Überweisung von Konto
	 */
	public void zahlungenUeberweisung() {

		boolean eingabefehler = false;
		String fehlerAusgabe = "";

		// Prüfen ob Konto angegeben
		if (zahlungenUebKonto.getValue() == null) {
			fehlerAusgabe += "kein Konto angegeben\n";
			eingabefehler = true;
		}

		// Prüfen ob Empfänger angegeben
		if (zahlungenUebEmpfaenger.getText().isEmpty()) {
			fehlerAusgabe += "kein Empfänger angegeben\n";
			eingabefehler = true;
		}

		// Prüfen ob Mitteilung angegeben
		if (zahlungenUebMitteilung.getText().isEmpty()) {
			fehlerAusgabe += "keine Mitteilung angegeben\n";
			eingabefehler = true;
		}

		// Prüfen, ob Betrag in double wandelbar ist
		// und auf 2 Nachkommastellen runden
		double betragKorrekt = 0;
		try {
			// Prüfung, ob Double möglich
			double betrag = Double.parseDouble(zahlungenUebBetrag.getText());

			// Runden auf 2 Nachkommastellen
			String betragFormat = ((new DecimalFormat("0.00")).format(betrag));

			// Umwandeln in Double
			betragKorrekt = Double.parseDouble(betragFormat);

		} catch (NumberFormatException e) {
			fehlerAusgabe += "Fehler im Feld Betrag";
			eingabefehler = true;
		}

		if (eingabefehler == false) {
			bank.ueberweisung(zahlungenUebKonto.getValue(), betragKorrekt,
					zahlungenUebEmpfaenger.getText(),
					zahlungenUebMitteilung.getText());

			zahlungenUebBetrag.clear();
			zahlungenUebEmpfaenger.clear();
			zahlungenUebMitteilung.clear();
			zahlungenStatusUeb.setText("überweisung erfolgreich");

		} else {
			zahlungenStatusUeb.setText(fehlerAusgabe);
		}

	}




	/**
	 * ----------- Christian Kiss ----------- 
	 * Generate SampleData vorher
	 * bank.dat löschen, sonst doppelt !!!
	 */

	public void generateSampleData() {

		// 10 neuen Kunden inkl. erstes Bankkonto anlegen
		for (int i = 0; i < 10; i++) {
			bank.neuerKunde("10" + String.format("%05d", i), "passwort", "Kunde-" + i, true);
		}

		// Konto anlegen
		Kunde kundeKonto = bank.holeKunde("1000000");
		Konto neuesKonto = bank.neuesKonto(0);
		
		kundeKonto.addKonto(neuesKonto);

		// Geld einzahlen und abheben
		String einzahlenKontonummer = "262-651117801";

		// note a single Random object is reused here
		Random betrag = new Random();
		for (int i = 1; i <= 10; ++i) {
			bank.einzahlen(einzahlenKontonummer, betrag.nextInt(1000));
			bank.abheben(einzahlenKontonummer, betrag.nextInt(1000));
		}

		// alle Kunden auflisten zur Kontrolle
		for (Kunde q : bank.holeKunden()) {
			System.out.println("Name: " + q.getName() + "\nKundennummer: "
					+ q.getKundennummer());
			System.out.println(bank.holeKonten(q));
			System.out.println();
		}

	}
	
	public void logout() {
		try {
			Stage primaryStage = (Stage) logoutPane.getScene().getWindow();
			primaryStage.setTitle("Login");
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
			Pane myPane = (Pane)myLoader.load();
			BankController controller = (BankController) myLoader.getController();
			controller.setPrevStage(primaryStage);
			Scene myScene = new Scene(myPane);        
			primaryStage.setScene(myScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initUser(Kunde user) {
		this.user = user;
	}




	
	
	

}
