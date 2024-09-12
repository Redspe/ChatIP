// File: server/ClientHandler.java
package server;

import java.io.*;
import java.net.*;
import java.util.Set;
import client.Message;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Set<ClientHandler> clientHandlers;

    public ClientHandler(Socket socket, Set<ClientHandler> clientHandlers) {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Message message;
            while ((message = (Message) in.readObject()) != null) {
                System.out.println(message.getUsername() + ": " + message.getMsg());
                broadcastMessage(message);
            }
        } catch (IOException e) {
            closeConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(Message message) throws IOException {
        for (ClientHandler client : clientHandlers) {
            if (client != this) {
                client.out.writeObject(message);
            }
        }
    }

    void closeConnection() {
        try {
            clientHandlers.remove(this);
            socket.close();
            System.out.println("Cliente desconectou: " + socket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
