// File: client/ChatClient.java
package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1"; // Change to the server's IP address
    private static final int SERVER_PORT = 12345; // Must match the server's port

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {

            System.out.println("Connectado ao servidor do chat");
            
            

            // Thread for reading messages from the server
            new Thread(() -> {
                Message message;
                try {
                    while ((message = (Message) in.readObject()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread for sending messages to the server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                Message messageToSend = new Message("User Teste", scanner.nextLine());
                out.writeObject(messageToSend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
