package com.example.quizapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage stage;
    static int width = 820;
    static int height = 440;
    static String currentUser = "";
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Quiz App");
        stage.setScene(scene);
        stage.show();
    }
    public static void changeScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(),width, height);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        SQLSchemaUtils.initDB();
        SQLSchemaUtils.createTables();
        launch();
    }
}