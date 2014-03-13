package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * Start / Loginpage
 *
 * @author Frido Zurlinden / Christian Kiss
 * @version 1.0 / 27.01.2014
 */


public class Main_verbastelt extends Application {
	public final static String ADMIN = "ADMIN";
	public final static String USER = "USER";
	Scene scene;
	AnchorPane root;
	/*AnchorPane admin;
	AnchorPane user;*/
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			/*user = (AnchorPane)FXMLLoader.load(getClass().getResource("InterfaceUser.fxml"));
			admin = (AnchorPane)FXMLLoader.load(getClass().getResource("InterfaceAdmin.fxml"));*/
			scene = new Scene(root,900,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			/*BankController bankController = new BankController();
			fxmlLoader.setController(bankController);
			bankController.setApp(this);*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}  
	
	/*public void switchScene(String sceneName) {
		switch (sceneName) {	
		case ADMIN:
			scene.setRoot(admin);
			break;
		case USER:
			scene.setRoot(user);
		default: 
			scene.setRoot(root);
			break;
		}
	}*/
	
	public static void main(String[] args) {
		launch(args);
	}
}
