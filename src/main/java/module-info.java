module org.example.projetopoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.projetopoo to javafx.fxml;
    exports org.example.projetopoo;
}