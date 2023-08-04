package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;


public class Controller {
    @FXML
    private Button izlazButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;

    public static void changeSceneAndStyle(String fxmlFile, boolean useDecoratedStage) throws IOException {
        Stage primaryStage = Main.getPrimaryStage();
        if (primaryStage != null) {
            if (useDecoratedStage) {
                primaryStage.close();
                Main.switchToDecoratedScene(fxmlFile);
            } else {
                primaryStage.close();
                Main.switchToUndecoratedScene(fxmlFile);
            }
        }
    }

    public void onEnter(ActionEvent ae) throws IOException {
        loginButtonOnAction(ae);
    }
    public void loginButtonOnAction(ActionEvent e) throws IOException {
        if(!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()){
            // loginMessageLabel.setText("Ulogovan!");
            validateLogin();
        }
        else{
            loginMessageLabel.setText("Unesite korisničko ime i lozinku.");
        }
    }
    public void izlazButtonOnAction(ActionEvent e){
        Stage stage = (Stage) izlazButton.getScene().getWindow();
        stage.close();
    }
    public void validateLogin() throws IOException {
        if(Objects.equals(usernameTextField.getText(), "admin") && Objects.equals(passwordPasswordField.getText(), "admin")) {
            //loginMessageLabel.setText("ADMIN!");
            changeSceneAndStyle("admin.fxml", true);
        }
        else{
            DatabaseConnection connect = new DatabaseConnection();
            Connection connectDB = connect.getConnection();

            String validateLogin = "SELECT count(1) FROM nalozi WHERE username = '" + usernameTextField.getText() + "' AND password = '" + passwordPasswordField.getText() +"'";
            try{
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(validateLogin);
                while(queryResult.next()){
                    if(queryResult.getInt(1) == 1){
                        loginMessageLabel.setText("Dobro došli!");
                    } else{
                        loginMessageLabel.setText("Pogrešan unos, pokušajte ponovo.");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}