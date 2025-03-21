package com.example.quizapp;

import com.example.quizapp.ui_elements.QuestionCard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StudentViewController {
    public GridPane gpQuestionList;
    public Button btnExit;
    int score = 0;
    int page = 1;
    public Button btnBack;

    public Button btnNext;
    public Label lblQuestion;
    public Button a;
    public Button b;
    public Button c;
    public Button d;
    public Label lblPage;

    public Label lblScore;
    public List<Question> questions;
    public List<QuestionCard> questionCards;
    public Button[] btnChoices;
    private void updateLblPage(){
        lblPage.setText(page + "/" + questions.size());
    }
    private void checkStudentAnswersDatabase(){
        List<Boolean> boolList = SQLQuizUsers.getStudentAnswers(MainApplication.currentUser,questions);
        System.out.println(boolList);
        for(int i = 0;i < boolList.size(); i++){
            if(boolList.get(i) != null){
                questions.get(i).isAnswered = true;
                QuestionCard q = questionCards.get(i);
                q.getStyleClass().clear();
                if(boolList.get(i)){
                    q.getStyleClass().add("questionCardCorrect");
                } else {
                    q.getStyleClass().add("questionCardWrong");
                }
            }
        }
    }
    public void initialize(){
        questions = new ArrayList<>();
        SQLQuestions.retrieveQuestions(questions);
        lblQuestion.setText("Question 1: " + questions.get(0).question);
        lblScore.setText(score + "/" + questions.size());
        questionCards = new ArrayList<>();
        score = SQLQuizUsers.getStudentScore(MainApplication.currentUser);
        lblScore.setText(score + "/" + questions.size());


        updateLblPage();
        for(int i = 0; i < questions.size(); i++){
            QuestionCard q = new QuestionCard(String.valueOf(i+1),i);
            questionCards.add(q);
            int row = i / 5;
            int col = i % 5;
            gpQuestionList.add(q, col, row);
            q.getStyleClass().add("questionCard");
            q.setOnAction(this::onQuestionCardClicked);
        }
        checkStudentAnswersDatabase();
        List<Button> btnClones = new ArrayList<>();
        btnClones.add(a);
        btnClones.add(b);
        btnClones.add(c);
        btnClones.add(d);
        btnChoices = new Button[]{a,b,c,d};
        for(int i = 0;i < questions.get(page-1).choices.length;i++){
            int rand = (int)(Math.random() * btnClones.size());
            Button b = btnClones.remove(rand);
            b.setText(questions.get(page-1).choices[i]);
            if(questions.get(page-1).isAnswered){
                b.setDisable(true);
                String item = b.getText();
                if(Objects.equals(item, questions.get(page - 1).choices[0])){
                    b.getStyleClass().clear();
                    b.getStyleClass().add("correctButton");
                } else {
                    b.getStyleClass().clear();
                    b.getStyleClass().add("wrongButton");
                }
                b.setOpacity(0.5);
            } else {
                b.setDisable(false);
                b.getStyleClass().clear();
                b.getStyleClass().add("questionButton");
                b.setOpacity(1);
            }
        }

        checkNavigationButton();
    }
    private void checkNavigationButton(){
        btnNext.setDisable(page >= questions.size());
        btnBack.setDisable(page <= 1);
    }
    public void onQuestionCardClicked(ActionEvent actionEvent) {
        page = ((QuestionCard)(actionEvent.getSource())).getIndex() + 1;
        checkNavigationButton();
        updateLblPage();
        lblQuestion.setText("Question " + (page) + ": " + questions.get(page-1).question);
        List<Button> btnClones = new ArrayList<>();
        btnClones.add(a);
        btnClones.add(b);
        btnClones.add(c);
        btnClones.add(d);
        for(int i = 0;i < questions.get(page-1).choices.length;i++){
            int rand = (int)(Math.random() * btnClones.size());
            Button b = btnClones.remove(rand);
            b.setText(questions.get(page-1).choices[i]);
            if(questions.get(page-1).isAnswered){
                b.setDisable(true);
                String item = b.getText();
                if(Objects.equals(item, questions.get(page - 1).choices[0])){
                    b.getStyleClass().clear();
                    b.getStyleClass().add("correctButton");
                } else {
                    b.getStyleClass().clear();
                    b.getStyleClass().add("wrongButton");
                }
                b.setOpacity(0.5);
            } else {
                b.setDisable(false);
                b.getStyleClass().clear();
                b.getStyleClass().add("questionButton");
                b.setOpacity(1);
            }
        }
    }
    public void onNavigationCLick(ActionEvent event){
        if(event.getSource() == btnBack){
            page--;
        } else {
            page++;
        }
        checkNavigationButton();
        updateLblPage();


        lblQuestion.setText("Question " + (page) + ": " + questions.get(page-1).question);
        List<Button> btnClones = new ArrayList<>();
        btnClones.add(a);
        btnClones.add(b);
        btnClones.add(c);
        btnClones.add(d);
        for(int i = 0;i < questions.get(page-1).choices.length;i++){
            int rand = (int)(Math.random() * btnClones.size());
            Button b = btnClones.remove(rand);
            b.setText(questions.get(page-1).choices[i]);
            if(questions.get(page-1).isAnswered){
                b.setDisable(true);
                String item = b.getText();
                if(Objects.equals(item, questions.get(page - 1).choices[0])){
                    b.getStyleClass().clear();
                    b.getStyleClass().add("correctButton");
                } else {
                    b.getStyleClass().clear();
                    b.getStyleClass().add("wrongButton");
                }
                b.setOpacity(0.5);
            } else {
                b.setDisable(false);
                b.getStyleClass().clear();
                b.getStyleClass().add("questionButton");
                b.setOpacity(1);
            }
        }
    }

    @FXML
    public void onAnswerClick(ActionEvent event) {
        String ans = ((Button)event.getSource()).getText();
        QuestionCard q = questionCards.get(page-1);
        q.getStyleClass().clear();
        Boolean isCorrect;
        if(Objects.equals(ans, questions.get(page - 1).choices[0])){
            score++;
            lblScore.setText(score + "/" + questions.size());
            q.getStyleClass().add("questionCardCorrect");
            isCorrect = true;
            SQLQuizUsers.updateStudentScore(MainApplication.currentUser, score);
        } else {
            q.getStyleClass().add("questionCardWrong");
            isCorrect = false;
        }
        SQLQuizUsers.studentAnswerQuestion("question_" + questions.get(page - 1).id,isCorrect,MainApplication.currentUser);
        for(Button b : btnChoices){
            String item = b.getText();
            b.getStyleClass().clear();
            if(Objects.equals(item, questions.get(page - 1).choices[0])){
                b.getStyleClass().add("correctButton");
            } else {
                b.getStyleClass().add("wrongButton");
            }
            questions.get(page - 1).isAnswered = true;
        }
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