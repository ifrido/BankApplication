package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class BankController {
	private Bank bank = Bank.getInstance();
	
    Stage prevStage;
    
    /**
     * Methode für die alte Stage zu holen wenn ein neuer Controller geladen wird.
     * @author fridolin.zurlinden
     * @param stage
     */
    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }

	// Login	
	@FXML
	TextField loginUser;
	@FXML
	PasswordField loginPassword;
	@FXML
	Label loginStatus;

	/**
	 * Methode um Login zu machen.
	 * Holt über bank.login Kunde als Objekt "user".
	 * Neuer Controller mit neuem fxml. User wird über Methode initUser übergeben.
	 * @author fridolin.zurlinden
	 */
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
	}
}
