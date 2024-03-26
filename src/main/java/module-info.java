module com.example.socketclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.socketclient to javafx.fxml;
    exports com.example.socketclient;
}