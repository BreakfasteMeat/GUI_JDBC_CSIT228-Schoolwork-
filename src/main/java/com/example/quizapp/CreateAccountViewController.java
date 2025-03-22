package com.example.quizapp;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountViewController {
    public TextField tfusername;
    public RadioButton rbteacher;
    public ImageView logo;

    public void initialize() {
        Image image = new Image(Objects.requireNonNull(getClass().getResource("logo.png")).toExternalForm());
        logo.setImage(image);
    }
    public void createAccount(){
        String username = tfusername.getText();
        boolean isTeacher = rbteacher.isSelected();
        String table;
        if(isTeacher) table = SQLQuizUsers.tablename_teacher;
        else table = SQLQuizUsers.tablename_student;

        if(!SQLQuizUsers.retrieveUserExists(username,SQLQuizUsers.tablename_teacher) && !SQLQuizUsers.retrieveUserExists(username,SQLQuizUsers.tablename_student)){
            SQLQuizUsers.addUser(username,table);
            System.out.println("Added Account");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Created");
            alert.setHeaderText(null);
            alert.setContentText("Account Created");

            alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            alert.getDialogPane().getStyleClass().add("custom-alert");

            alert.showAndWait();
        } else if(tfusername.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Username");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid username");

            alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            alert.getDialogPane().getStyleClass().add("custom-alert");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Account already exists");

            alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            alert.getDialogPane().getStyleClass().add("custom-alert");

            alert.showAndWait();
        }
    }
    public void backButtonClicked() throws IOException {
        MainApplication.changeScene("login-view.fxml");
    }
}
