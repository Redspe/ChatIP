// File: client/ChatClientGUI.java
package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class ChatClientGUI extends JFrame {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private String user;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton connectButton;
    private JButton disconnectButton;
    private boolean isConnected;

    public ChatClientGUI() {
        setTitle("Chat Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.setEnabled(false);
        add(messageField, BorderLayout.SOUTH);

        sendButton = new JButton("Enviar");
        sendButton.setEnabled(false);
        sendButton.addActionListener(event -> {
            try {
                this.sendMessage(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        add(sendButton, BorderLayout.EAST);

        getRootPane().setDefaultButton(sendButton);
        sendButton.requestFocus();

        JPanel topPanel = new JPanel();
        connectButton = new JButton("Conectar");
        disconnectButton = new JButton("Desconectar");
        disconnectButton.setEnabled(false);
        topPanel.add(connectButton);
        topPanel.add(disconnectButton);
        add(topPanel, BorderLayout.NORTH);

        connectButton.addActionListener(this::connectToServer);
        disconnectButton.addActionListener(this::disconnectFromServer);

        setVisible(true);
    }

    private void connectToServer(ActionEvent event) {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            chatArea.append("Conectado ao servidor.\n");

            if (user == null) {
                user = IOGUI.readStrGUI("Nome de Usuário", "Nome de usuário");
            }

            isConnected = true;
            messageField.setEnabled(true);
            sendButton.setEnabled(true);
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);

            // Thread to listen for messages from the server
            new Thread(() -> {
                Message msg;

                try {
                    while ((msg = (Message) in.readObject()) != null) {
                        chatArea.append(msg.getUsername() + ": " + msg.getMsg() + "\n");
                    }
                } catch (IOException e) {
                    chatArea.append("Erro de conexão: " + e.getMessage() + "\n");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            chatArea.append("Não foi possível conectar ao servidor: " + e.getMessage() + "\n");
        }
    }

    private void sendMessage(ActionEvent event) throws IOException {
        if (isConnected && !messageField.getText().trim().isEmpty()) {
            Message msg = new Message(user, messageField.getText().trim());
            out.writeObject(msg);
            chatArea.append("Você: " + messageField.getText().trim() + "\n");
            messageField.setText("");
        }
    }

    private void disconnectFromServer(ActionEvent event) {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            isConnected = false;
            messageField.setEnabled(false);
            sendButton.setEnabled(false);
            connectButton.setEnabled(true);
            disconnectButton.setEnabled(false);
            chatArea.append("Desconectado do servidor.\n");
        } catch (IOException e) {
            chatArea.append("Erro ao desconectar: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientGUI::new);
    }
}
