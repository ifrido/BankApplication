package application;

import javax.swing.MenuElement;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class BankController {
	
	
	// Login
	@FXML
	TextField loginUser;
	PasswordField loginPassword;
	public void login(){
		loginUser.getText();
		System.out.println(loginUser.getText());
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
