package application;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class InterfaceUserController {
	private Bank bank = Bank.getInstance();
	private Kunde user;
	
	Stage prevStage;

    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }

	
	// Userauswahl anlegen
	// Nach Usereinloggen Session von User, diese Variable nur Temp
	Kunde tempKunde = bank.holeKunde("1000000");
	
	
	
	  /**
	   * ----------- Christian Kiss -----------
	   * InterfaceAdmin
	   * alle Funktionen des User-Interfaces
	   */
	
		// Variablen Menüpunkt Benutzer verwalten
		@FXML
		Pane userZahlungen, userKonten, userEdit;

		// Variablen Menüpunkt Konten verwalten
		@FXML
		ListView<String> kontenBuchungen1; 
		@FXML
		ChoiceBox<String> kontoWahl;
		@FXML
		Text kontenSaldo;

		// Variablen Menüpunkt Zahlungen
		@FXML
		ChoiceBox<String> zahlungKonto;
		@FXML
		TextField zahlungEmpfänger, zahlungBetrag, zahlungMitteilung;
		@FXML
		Text zahlungFehler;
		
		// Variablen Menüpunkt Zahlungen
		@FXML
		TextField userKundennummer, userName;
		@FXML
		PasswordField userPasswort;
		@FXML
		Text userStatus;
		
		//Variable Logout
		@FXML
		TitledPane logoutPane;
		
		//Variable angemeldeter User
		@FXML
		Text userNameStatus;

		
		
		
	
	  /**
	   * ----------- Christian Kiss -----------
	   * Pane visibility
	   */
		public void disableAllPanes(){
			userZahlungen.setVisible(false);
			userKonten.setVisible(false);
			userEdit.setVisible(false);
		}

		
		// Pane Konten anzeigen
		public void PaneUserKonten(){
			disableAllPanes();
			userKonten.setVisible(true);
			
			// Buchungstabelle leeren
			kontenBuchungen1.getItems().clear();
			
	
			//kontowahl leeren, dann befüllen
			kontoWahl.getItems().clear();
		    for (Konto q : tempKunde.getKonten()) {
		    	kontoWahl.getItems().add(q.getNummer());
		    }
			
		}
	
		
		
		// Pane Zahlungen anzeigen
		public void PaneUserZahlungen(){
			disableAllPanes();
			userZahlungen.setVisible(true);
			
			//zahlungKonto leeren, dann befüllen
			zahlungKonto.getItems().clear();
		    for (Konto q : tempKunde.getKonten()) {
		    	zahlungKonto.getItems().add(q.getNummer());
		    }
		}

		// Pane Zahlungen anzeigen
		public void PaneUserEdit(){
			
			disableAllPanes();
			userEdit.setVisible(true);

			// Felder vorfüllen, aus aktuellen Userdaten
			userKundennummer.setText(tempKunde.getKundennummer());
			userName.setText(tempKunde.getName());
			userPasswort.setText(tempKunde.getPasswort());
		}
		
		
		public void PaneLogout(){
			bank.logout();
		}


		
		
		  /**
		   * ----------- Christian Kiss -----------
		   * Pane PaneUserKonten
		   * Kontoauswahl + Anzeige Buchungen
		   */
		public void kontenDetail(){
			
			// Liste anlegen (MultiColum wäre zwar schöner, aber nix gefunden)
			ObservableList<String> observableList1 = FXCollections.observableList(new ArrayList<String>());

			// Initalwert für Saldoberechnung
			double saldo = 0;
			
			// Buchungen auslesen
			for (Buchung q : bank.holeKonto(kontoWahl.getValue()).getBuchungen()) {
				
				
				// Werte für Buchungen in Liste hinzufügen
				observableList1.add(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(q.getAusfuehrung()) + "\t\t" + q.getBetrag() + "\t\t" + q.getText());
				
				// Saldo berechnen
				saldo = saldo + q.getBetrag();
				
		    }
			
			// zurückschreiben Buchungen in GUI
	        kontenBuchungen1.setItems(observableList1);
	        kontenSaldo.setText(Double.toString(saldo));
		}

		
		  /**
		   * ----------- Christian Kiss -----------
		   * PaneUserZahlungen
		   * Überweisungen Tätigen + Fehlermeldungen anzeigen
		   */
		public void zahlungen(){
			
			boolean eingabefehler = false;
			String fehlerAusgabe = "";
			
			// Prüfen ob Konto angegeben
			if(zahlungKonto.getValue() == null){
				fehlerAusgabe += "kein Konto angegeben\n";
				eingabefehler = true;
			}
			
			// Prüfen ob Empfänger angegeben
			if(zahlungEmpfänger.getText().isEmpty()){
				fehlerAusgabe += "kein Empfänger angegeben\n";
				eingabefehler = true;
			}
			
			// Prüfen ob Mitteilung angegeben
			if(zahlungMitteilung.getText().isEmpty()){
				fehlerAusgabe += "keine Mitteilung angegeben\n";
				eingabefehler = true;
			}
			

			// Prüfen, ob Betrag in double wandelbar ist
			// und auf 2 Nachkommastellen kürzen
			double betragKorrekt = 0;
			 try {
				 	// Prüfung, ob Double möglich
				 	double betrag = Double.parseDouble(zahlungBetrag.getText());
				 	
				 	// Runden auf 2 Nachkommastellen
				 	// !!! NOCHMALS Überdenken, vielleicht throw Exeption wenn mehr als 2 Nachkommastellen
				 	String betragFormat = ((new DecimalFormat( "0.00" )).format(betrag));
				 	
				 	// Umwandeln in Double
				 	betragKorrekt = Double.parseDouble(betragFormat);
				 	
				 }
				 catch(NumberFormatException e) {
						fehlerAusgabe += "Fehler im Feld Betrag";
						eingabefehler = true;
				 }
			
			if(eingabefehler == false){
				bank.ueberweisung(zahlungKonto.getValue(), betragKorrekt, zahlungEmpfänger.getText(), zahlungMitteilung.getText());
				
				zahlungBetrag.clear();
				zahlungEmpfänger.clear();
				zahlungMitteilung.clear();
				zahlungFehler.setText("überweisung erfolgreich");
				
				
			
			}
			else{
				zahlungFehler.setText(fehlerAusgabe);
			}
	
			

		}
		

		  /**
		   * ----------- Christian Kiss -----------
		   * PaneUserEdit
		   * Überweisungen Tätigen + Fehlermeldungen anzeigen
		   */
		
	public void userEdit() {

		bank.holeKunde(userKundennummer.getText()).setName(userName.getText());
		bank.holeKunde(userKundennummer.getText()).setPasswort(userPasswort.getText());
		bank.speichern();
		userStatus.setText("Erfolgreich geändert!");
		

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
		userNameStatus.setText("User: "+ user.getName());
	}
		
		
	
}
