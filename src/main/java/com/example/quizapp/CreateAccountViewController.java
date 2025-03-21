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
