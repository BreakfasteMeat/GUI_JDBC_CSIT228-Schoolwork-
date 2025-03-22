package com.example.quizapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherViewController {
    public ListView<String> lvQuestions;
    public ObservableList<String> QuestionObsList = FXCollections.observableArrayList();
    public TextField tfQuestion;
    public TextField tfA;
    public TextField tfB;
    public TextField tfC;
    public TextField tfD;
    List<Question> questions;
    int indexSelected = -1;
    public Button btnUpdate;
    public Button btnDelete;

    //HELPER FUNCTIONS
    private boolean allTextFieldsValid(){
        return !(tfQuestion.getText().isEmpty() && tfA.getText().isEmpty() && tfB.getText().isEmpty() && tfD.getText().isEmpty());
    }

    private void updateQuestionList(){
        QuestionObsList.clear();
        for(int i = 0; i < questions.size(); i++){
            QuestionObsList.add(questions.get(i).toString());
        }
        lvQuestions.setItems(QuestionObsList);
    }
    private void clearTextFields(){
        tfQuestion.clear();
        tfA.clear();
        tfB.clear();
        tfC.clear();
        tfD.clear();
    }
    public void initialize(){
        questions = new ArrayList<>();
        SQLQuestions.retrieveQuestions(questions);
        QuestionObsList.clear();

        lvQuestions.getItems().clear();
        updateQuestionList();

        lvQuestions.setOnMouseClicked(event -> {
            onQuestionSelect();
        });

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

    }

    public void addQuestion(){
        if(!allTextFieldsValid()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required");
            alert.showAndWait();
            return;
        } else if(SQLQuestions.checkDuplicateQuestion(tfQuestion.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Question already exists");
            alert.showAndWait();
            return;
        }

        SQLQuestions.addQuestion(tfQuestion.getText(), tfA.getText(), tfB.getText(), tfC.getText(), tfD.getText());
        questions.clear();
        SQLQuestions.retrieveQuestions(questions);
        updateQuestionList();
        clearTextFields();
    }

    public void updateQuestion(){
        Question q = questions.get(indexSelected);
        q.question = tfQuestion.getText();
        q.choices[0] = tfA.getText();
        q.choices[1] = tfB.getText();
        q.choices[2] = tfC.getText();
        q.choices[3] = tfD.getText();
        SQLQuestions.editQuestion(q.id, q.question, q.choices);
        updateQuestionList();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Question Updated");
        alert.setHeaderText(null);
        alert.setContentText("Question updated successfully");

        alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");
        alert.showAndWait();


    }
    public void deleteQuestion(){
        Question q = questions.get(indexSelected);
        SQLQuestions.removeQuestion(q.id);
        questions.remove(q);
        updateQuestionList();
        indexSelected = -1;
        lvQuestions.getSelectionModel().clearSelection();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearTextFields();
    }
    public void onQuestionSelect(){
        indexSelected = lvQuestions.getSelectionModel().getSelectedIndex();
        tfQuestion.setText(questions.get(indexSelected).question);
        tfA.setText(questions.get(indexSelected).choices[0]);
        tfB.setText(questions.get(indexSelected).choices[1]);
        tfC.setText(questions.get(indexSelected).choices[2]);
        tfD.setText(questions.get(indexSelected).choices[3]);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }
    @FXML
    public void onExitClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");

        alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            try {
                MainApplication.changeScene("login-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
