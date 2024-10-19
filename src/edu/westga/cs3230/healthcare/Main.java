package edu.westga.cs3230.healthcare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class, in which program execution starts and ends.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public class Main extends Application {

	/**
	 * Entry point of the application.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		//TODO: add basic pages
		//launch(args);
		System.out.println("test");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("view/WelcomePage.fxml")));
			primaryStage.setScene(scene);
			primaryStage.setTitle("Healthcare System");
			primaryStage.show();
		} catch (Exception abc) {
			abc.printStackTrace();
		}
	}

}
