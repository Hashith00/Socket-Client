package com.example.socketclient;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket sk) {
        try {
            this.socket = sk;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in creating Client");
            close(socket, bufferedWriter, bufferedReader);
        }

    }

    public void close(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            if(bufferedReader != null && bufferedWriter != null){
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void receiveMessageFromServer(VBox vBox_messages) {
        System.out.println("hee");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String messages = bufferedReader.readLine();
                        System.out.println(messages);
                        ClientController.addLabel(messages, vBox_messages);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error in getting messages from client");
                        close(socket, bufferedWriter, bufferedReader);
                        break;
                    }
                }
            }
        }).start();
    }

    public void sendMassageToServer(String messagesToSend) {
        try{
            bufferedWriter.write(messagesToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error in messages to client");
            close(socket, bufferedWriter, bufferedReader);
        }
    }
}
