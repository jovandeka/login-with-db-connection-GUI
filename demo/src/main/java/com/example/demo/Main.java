package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static double xOffset = 0;
    private static double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        Scene initialScene = new Scene(fxmlLoader.load(), 540, 400);
        mouseDragWindow(initialScene);

        primaryStage.setScene(initialScene);
        primaryStage.show();
        //scene.getRoot().requestFocus();
    }
    public static Stage getPrimaryStage(){
        return primaryStage;
    }
    public static void switchToDecoratedScene(String fxmlFile) throws IOException {
        Stage decoratedStage = new Stage();
        decoratedStage.initStyle(StageStyle.DECORATED);
        decoratedStage.setMaximized(true);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);

        decoratedStage.setScene(scene);
        decoratedStage.setMaximized(true);
        decoratedStage.show();
        primaryStage = decoratedStage;
    }
    public static void switchToUndecoratedScene(String fxmlFile) throws IOException {
        Stage undecoratedStage = new Stage(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene loginScene = new Scene(fxmlLoader.load(), 540, 400);
        mouseDragWindow(loginScene);

        undecoratedStage.setScene(loginScene);
        undecoratedStage.show();
        primaryStage = undecoratedStage;
    }
    public static void mouseDragWindow(Scene scene){
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double newX = event.getScreenX() - xOffset;
                double newY = event.getScreenY() - yOffset;

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

                newX = Math.min(Math.max(newX, screenBounds.getMinX()), screenBounds.getMaxX() - primaryStage.getWidth());
                newY = Math.min(Math.max(newY, screenBounds.getMinY()), screenBounds.getMaxY() - primaryStage.getHeight());

                primaryStage.setX(newX);
                primaryStage.setY(newY);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}