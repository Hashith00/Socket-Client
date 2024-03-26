package com.example.socketclient;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    public AnchorPane mp_main;
    @FXML
    public Button button_send;
    @FXML
    public TextField massage;
    @FXML
    public ScrollPane plain;
    @FXML
    public VBox VBox_messages;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            client = new Client(new Socket("localHost", 1234));
            System.out.println("Connected to the Server");

        }catch (IOException e){
            e.printStackTrace();
        }

        VBox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                plain.setVvalue((double) t1);
            }
        });

        client.receiveMessageFromServer(VBox_messages);

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String MessagesToSend = massage.getText();
                if(!MessagesToSend.isEmpty()){
                    HBox hBox= new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));
                    Text text = new Text(MessagesToSend);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(239, 242, 255);" +
                            "-fx-background-color: rgb(15, 125, 246);" +
                            "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.944, 0.945, 0.956));

                    hBox.getChildren().add(textFlow);
                    VBox_messages.getChildren().add(hBox);
                    client.sendMassageToServer(MessagesToSend);
                    massage.clear();
                }
            }
        });


    }

    public static void addLabel(String messageFromTheServer, VBox vbox){
        System.out.println(messageFromTheServer + "massage");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromTheServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(235, 233, 255);" +
                "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }
}