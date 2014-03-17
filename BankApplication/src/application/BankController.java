package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class BankController implements Initializable {
	private Bank bank = Bank.getInstance();
	
    Stage prevStage;

    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void gotoCreateCategory(ActionEvent event) throws IOException {
       Stage stage = new Stage();
       stage.setTitle("Login");
       Pane myPane = null;
       myPane = FXMLLoader.load(getClass().getResource("Login.fxml"));
       Scene scene = new Scene(myPane);
       stage.setScene(scene);

       prevStage.close();

       stage.show();

    }
	
/*	Main app;
	

	public void setApp(Main app) {
		this.app = app;
	}*/

	// Login	
	@FXML
	TextField loginUser;
	@FXML
	PasswordField loginPassword;
	@FXML
	Label loginStatus;

	
	public void login(){
		Kunde user = bank.login(loginUser.getText(), loginPassword.getText());
		if(user != null) {
			try {
				Stage primaryStage = (Stage) loginStatus.getScene().getWindow();
			if (user.getAdmin()) {
				primaryStage.setTitle("Bank Administration");
				FXMLLoader myLoader = new FXMLLoader(getClass().getResource("InterfaceAdmin.fxml"));
				Pane myPane = (Pane)myLoader.load();
				InterfaceAdminController controller = (InterfaceAdminController) myLoader.getController();
				controller.setPrevStage(primaryStage);
				controller.initUser(user);
				Scene myScene = new Scene(myPane);        
				primaryStage.setScene(myScene);
				primaryStage.show();
			} else {
				primaryStage.setTitle("Bank");
				FXMLLoader myLoader = new FXMLLoader(getClass().getResource("InterfaceUser.fxml"));
				Pane myPane = (Pane)myLoader.load();
				InterfaceUserController controller = (InterfaceUserController) myLoader.getController();
				controller.setPrevStage(primaryStage);
				controller.initUser(user);
				Scene myScene = new Scene(myPane);        
				primaryStage.setScene(myScene);
				primaryStage.show();
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
		loginStatus.setText("Kundennummer oder Passwort nicht korrekt");
		}
		
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
