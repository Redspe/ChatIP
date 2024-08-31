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
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to chat server");

            // Thread for reading messages from the server
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread for sending messages to the server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String messageToSend = scanner.nextLine();
                out.println(messageToSend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}