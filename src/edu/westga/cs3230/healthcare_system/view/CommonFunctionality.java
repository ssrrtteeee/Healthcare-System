package edu.westga.cs3230.healthcare_system.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_system.Main;
import edu.westga.cs3230.healthcare_system.dal.DBAccessor;
import edu.westga.cs3230.healthcare_system.model.Admin;
import edu.westga.cs3230.healthcare_system.model.UserLogin;
import edu.westga.cs3230.healthcare_system.view.dialogs.ShowVisitsInRange;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Provides functionality that needs to be accessible across most pages.
 * 
 * @author Jacob Wilson
 * @version Fall 2024
 */
public abstract class CommonFunctionality {
	@FXML private Pane mainPane;
	
	private Button adminQueryButton;
	private Button adminRangeButton;
		
	protected CommonFunctionality() {
		this.adminQueryButton = new Button("Free query");
		this.adminRangeButton = new Button("View range");
	}
	
	protected void initCommon() {
		if (UserLogin.getSessionUser() instanceof Admin) {
			this.adminQueryButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					CommonFunctionality.this.showFreeQueryDialog();
				}			
			});
			this.adminRangeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					CommonFunctionality.this.showAppointmentsInRangeDialog();
				}
			});
	
			this.mainPane.getChildren().add(this.adminQueryButton);
			this.adminRangeButton.setLayoutX(75);
			this.mainPane.getChildren().add(this.adminRangeButton);
		}
	}
	
	private void showAppointmentsInRangeDialog() {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Enter date range");
	    dialog.setHeaderText("Enter a range of dates below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    DatePicker startDate = new DatePicker();
	    DatePicker endDate = new DatePicker();
	    GridPane.setColumnSpan(errorLabel, 2);
	    
	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
	    
        grid.addRow(1, new Label("Start date:"), startDate);
        grid.addRow(2, new Label("End date:"), endDate);
		grid.addRow(3, confirmButton, cancelButton);
		grid.addRow(4, errorLabel);
	    
	    dialog.getDialogPane().setContent(grid);

	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					CommonFunctionality.this.showAppointmentsInRangeResults(startDate.getValue(), endDate.getValue());
					cancelButton.fire();
				} catch (IOException ex) {
					ex.getMessage();
				} catch (Exception ex) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText(ex.getMessage());
					alert.showAndWait();
				}
			}
		});
		
	    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
				dialog.resultProperty().set(true);
			}
		});
	    
	    dialog.showAndWait();
	}

	protected void showAppointmentsInRangeResults(LocalDate startDate, LocalDate endDate) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(this.getClass().getResource("dialogs/ShowVisitsInRange.fxml"));
    	loader.load();
    	ShowVisitsInRange page = loader.getController();
    	page.setDates(startDate, endDate);
    	Parent parent = loader.getRoot();
    	Scene scene = new Scene(parent);
    	Stage addTodoStage = new Stage();
    	addTodoStage.setTitle(Main.TITLE);
    	addTodoStage.setScene(scene);
    	addTodoStage.initModality(Modality.APPLICATION_MODAL);
    	        	
    	addTodoStage.show();
	}

	private void showFreeQueryDialog() {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Enter query");
	    dialog.setHeaderText("Enter a query below.");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    Label errorLabel = new Label();
	    errorLabel.setTextFill(Color.RED);
	    TextArea queryTextArea = new TextArea();
	    GridPane.setColumnSpan(errorLabel, 2);
	    GridPane.setColumnSpan(queryTextArea, 2);
	    
	    Button confirmButton = new Button("Confirm");
	    Button cancelButton = new Button("Cancel");
	    confirmButton.setPrefSize(80, 30);
	    cancelButton.setPrefSize(80, 30);
	    
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
	    
        grid.addRow(1, queryTextArea);
		grid.addRow(2, confirmButton, cancelButton);
		grid.addRow(3, errorLabel);
	    
	    dialog.getDialogPane().setContent(grid);

	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					cancelButton.fire();
					CommonFunctionality.this.showFreeQueryResultsDialog(queryTextArea.getText());
				} catch (SQLException ex) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText(ex.getMessage());
					alert.showAndWait();
				}
			}
		});
		
	    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
				dialog.resultProperty().set(true);
			}
		});
	    
	    dialog.showAndWait();
    }

	private void showFreeQueryResultsDialog(String query) throws SQLException {
		Dialog<Boolean> dialog = new Dialog<Boolean>();
	    dialog.setTitle("Query results");
	    dialog.setResizable(false);

	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(0, 10, 0, 10));
	    
	    TableView<ObservableList<String>> resultTableView = new TableView<ObservableList<String>>();
	    GridPane.setColumnSpan(resultTableView, 2);
	    try (Connection connection = DriverManager.getConnection(DBAccessor.getConnectionString());
				PreparedStatement stmt = connection.prepareStatement(query)
	    ) {
			ResultSet results = stmt.executeQuery();
			
			for (int i = 0; i < results.getMetaData().getColumnCount(); i++) {
				TableColumn<ObservableList<String>, String> column = new TableColumn<ObservableList<String>, String>(results.getMetaData().getColumnName(i + 1));
				final int j = i;
				column.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
	                public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
						return new SimpleStringProperty(param.getValue().get(j));
	                }
	            });
				resultTableView.getColumns().add(column);
			}
			
			ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
			while (results.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= results.getMetaData().getColumnCount(); i++) {
					row.add(results.getString(i));
				}
				data.add(row);
			}
			resultTableView.setItems(data);
		}
	    
	    Button confirmButton = new Button("Close");
	    confirmButton.setPrefSize(80, 30);
	    
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
	    
        grid.addRow(1, resultTableView);
		grid.addRow(2, confirmButton);
	    
	    dialog.getDialogPane().setContent(grid);

	    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
				stage.close();
				dialog.resultProperty().set(true);
			}
		});
	    
	    dialog.showAndWait();
	}
}
