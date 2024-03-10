package org.example.m09juego;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label hintLabel;

    @FXML
    private TextField guessField;

    @FXML
    private Button submitButton;

    private SecretNum secretNum;

    public void initialize() {
        secretNum = new SecretNum(40);
        welcomeLabel.setText("¡Bienvenido! Adivina un número entre 1 y 40");
        hintLabel.setVisible(false);
    }

    @FXML
    public void handleGuess(ActionEvent event) {
        String guess = guessField.getText();
        if (guess.isEmpty()) {
            return;
        }

        if (secretNum.check(guess)) {
            welcomeLabel.setText("¡Felicidades! ¡Adivinaste el número!");
            submitButton.setDisable(true);
        } else {
            hintLabel.setVisible(true);
            hintLabel.setText(secretNum.hint(guess));
            guessField.clear();
        }
    }
}

