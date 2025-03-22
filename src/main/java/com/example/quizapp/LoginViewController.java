package com.example.quizapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginViewController {


    public TextField tfUsername;
    public ImageView logo;

    private String getUsername(){
        return tfUsername.getText();
    }


    public void initialize(){
        Image image = new Image(Objects.requireNonNull(getClass().getResource("logo.png")).toExternalForm());
        logo.setImage(image);

    }
    private void loginAsTeacher(String username) throws IOException {
        MainApplication.currentUser = username;
        MainApplication.changeScene("teacher-view.fxml");
    }
    private void loginAsStudent(String username) throws IOException {
        if(SQLQuestions.getNumQuestions() <= 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Quiz");
            alert.setHeaderText(null);
            alert.setContentText("Quiz has no questions, please check back later");
            alert.showAndWait();
        } else {
            MainApplication.currentUser = username;
            MainApplication.changeScene("student-view.fxml");
        }
    }

    public void loginUser(ActionEvent event) throws IOException {
        if(SQLQuizUsers.retrieveUserExists(getUsername(),SQLQuizUsers.tablename_teacher)){
            loginAsTeacher(getUsername());
        } else if(SQLQuizUsers.retrieveUserExists(getUsername(),SQLQuizUsers.tablename_student)){
            loginAsStudent(getUsername());
        } else if(tfUsername.getText().isEmpty()){
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
            alert.setContentText("Username does not exist!");

            alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            alert.getDialogPane().getStyleClass().add("custom-alert");

            alert.showAndWait();
            return;
        }
    }
    public void createAccount() throws IOException{
        MainApplication.changeScene("create-account-view.fxml");
    }


}
