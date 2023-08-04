package com.example.demo;

import javafx.event.ActionEvent;

import java.io.IOException;

public class Admin {
    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        Controller.changeSceneAndStyle("login.fxml", false);
}

}
