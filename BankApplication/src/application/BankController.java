package application;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BankController {
	private Bank bank = Bank.getInstance();
	
/*	Main app;
	

	public void setApp(Main app) {
		this.app = app;
	}*/

	// Login	
	@FXML
	TextField loginUser;
	@FXML
	PasswordField loginPassword;
	public void login(){		
		Kunde user = bank.login(loginUser.getText(), loginPassword.getText());
		System.out.println(loginUser.getText());
		//app.switchScene(Main.USER);
	}
	
	
	
	
	
	
	
	@FXML
	Pane userKonten, userZahlungen, userEdit;
	@FXML
	Accordion menue;

	

	
	
	
	// Menü Userinterface Christian Kiss

	
	public void menueAction(){
//		
//		
//		userKonten.setVisible(false);
//		userZahlungen.setVisible(false);
//		userEdit.setVisible(false);
		userEdit.setVisible(true);
		
//		
//		System.out.println(menue.get);
//
//		
////		if (menu.equals( "benutzerverwaltung" )){
////			benutzerverwaltung.setVisible(true);	
////		}
//		

	}
	
	
	
	
	
	
}
