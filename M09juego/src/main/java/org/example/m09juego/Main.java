package org.example.m09juego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Server server;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Juego de adivinar un número");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        server = new Server();
        new Thread(server).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        server.stopServer();
        super.stop();
    }
}