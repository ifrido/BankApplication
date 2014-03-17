package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Login");
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
		Pane myPane = (Pane)myLoader.load();
		BankController controller = (BankController) myLoader.getController();
		controller.setPrevStage(primaryStage);

		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
