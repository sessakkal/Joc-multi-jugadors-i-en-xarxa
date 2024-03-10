module org.example.m09juego {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.m09juego to javafx.fxml;
    exports org.example.m09juego;
}