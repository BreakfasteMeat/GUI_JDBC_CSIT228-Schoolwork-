package com.example.quizapp;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateAccountViewController {
    public TextField tfusername;
    public RadioButton rbteacher;

    public void createAccount(){
        String username = tfusername.getText();
        boolean isTeacher = rbteacher.isSelected();
        String table;
        if(isTeacher) table = SQLQuizUsers.tablename_teacher;
        else table = SQLQuizUsers.tablename_student;

        if(!SQLQuizUsers.retrieveUserExists(username,SQLQuizUsers.tablename_teacher) && !SQLQuizUsers.retrieveUserExists(username,SQLQuizUsers.tablename_student)){
            SQLQuizUsers.addUser(username,table);
            System.out.println("Added Account");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Account already exists");
            alert.showAndWait();
        }
    }
    public void backButtonClicked() throws IOException {
        MainApplication.changeScene("login-view.fxml");
    }
}
