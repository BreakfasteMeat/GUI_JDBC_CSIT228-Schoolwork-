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

    public void updateQuestionList(){
        for(int i = QuestionObsList.size();i < questions.size();i++){
            QuestionObsList.add(questions.get(i).question);
        }
        lvQuestions.setItems(QuestionObsList);
    }
    public void initialize(){
        questions = new ArrayList<>();
        SQLQuestions.retrieveQuestions(questions);
        QuestionObsList.clear();

        lvQuestions.getItems().clear();
        updateQuestionList();

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
        questions.clear();
        SQLQuestions.retrieveQuestions(questions);
        updateQuestionList();
    }
    public void onQuestionSelect(){
        int indexSelected = lvQuestions.getSelectionModel().getSelectedIndex();

    }

}
