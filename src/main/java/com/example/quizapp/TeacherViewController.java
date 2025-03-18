package com.example.quizapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class TeacherViewController {
    public ListView<String> lvQuestions;
    public ObservableList<String> QuestionObsList = FXCollections.observableArrayList();
    public TextField tfQuestion;
    public TextField tfA;
    public TextField tfB;
    public TextField tfC;
    public TextField tfD;
    List<Question> questions;

    //HELPER FUNCTIONS
    private boolean allTextFieldsValid(){
        return !(tfQuestion.getText().isEmpty() && tfA.getText().isEmpty() && tfB.getText().isEmpty() && tfD.getText().isEmpty());
    }

    public void initialize(){
        questions = new ArrayList<>();
        SQLQuestions.retrieveQuestions(questions);
        ListView<String> lvQuestions = new ListView<>(QuestionObsList);
        lvQuestions.getItems().clear();
        for(Question q : questions){
            lvQuestions.getItems().add(q.question);
        }
    }

    public void addQuestion(){
        if(!allTextFieldsValid()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required");
            alert.showAndWait();
            return;
        }
        SQLQuestions.addQuestion(tfQuestion.getText(), tfA.getText(), tfB.getText(), tfC.getText(), tfD.getText());
    }
    
}
