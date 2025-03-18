package com.example.quizapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {


    public TextField tfUsername;

    private String getUsername(){
        return tfUsername.getText();
    }

    private void loginAsTeacher(String username) throws IOException {
        MainApplication.changeScene("teacher-view.fxml");
    }
    private void loginAsStudent(String username) throws IOException {
        MainApplication.changeScene("student-view.fxml");
    }

    public void loginUser(ActionEvent event) throws IOException {
        if(SQLQuizUsers.retrieveUserExists(getUsername(),SQLQuizUsers.tablename_teacher)){
            loginAsTeacher(getUsername());
        } else if(SQLQuizUsers.retrieveUserExists(getUsername(),SQLQuizUsers.tablename_student)){
            loginAsStudent(getUsername());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Username does not exist!");
            alert.showAndWait();
            return;
        }
    }
    public void createAccount() throws IOException{
        MainApplication.changeScene("create-account-view.fxml");
    }


}
