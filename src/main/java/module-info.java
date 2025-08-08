module org.example.projetopoo {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.projetopoo to javafx.fxml;
    exports org.example.projetopoo;

    opens org.example.projetopoo.controllers to javafx.fxml;
    exports org.example.projetopoo.controllers;
}