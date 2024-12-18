package edu.westga.cs3230.healthcare_system;

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
    public static final String TITLE = "Healthcare System";
    public static final String LOGIN_PAGE_FXML = "view/LoginPage.fxml";
	public static final String HOME_PAGE = "view/HomePage.fxml";
	public static final String REGISTER_PATIENT_PAGE = "view/RegisterPatient.fxml";
	public static final String CREATE_APPOINTMENT_PAGE = "view/CreateAppointmentPage.fxml";
	public static final String EDIT_PATIENT_INFO_PAGE = "view/EditPatientInfoPage.fxml";
	public static final String VIEW_PATIENT_INFO_PAGE = "view/ViewPatientInfoPage.fxml";
	public static final String ADD_ROUTINE_CHECKUP_PAGE = "view/AddRoutineCheckupPage.fxml";
	public static final String ORDER_TESTS_PAGE = "view/OrderTestsPage.fxml";
	public static final String VIEW_VISIT_DETAILS_PAGE = "view/ViewVisitDetailsPage.fxml";
	
	/**
	 * Entry point of the application.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Main.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		DBLogin login = new DBLogin();
//		String hashedPassword = login.hashPassword("newPassword");
//		System.out.println(hashedPassword);
		try {
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource(Main.LOGIN_PAGE_FXML)));
			primaryStage.setScene(scene);
			primaryStage.setTitle("Healthcare System");
			primaryStage.show();
		} catch (Exception abc) {
			abc.printStackTrace();
		}
	}

}
