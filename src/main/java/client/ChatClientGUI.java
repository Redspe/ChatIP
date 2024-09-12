// File: client/ChatClientGUI.java
package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatClientGUI extends JFrame {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton connectButton;
    private JButton disconnectButton;
    private boolean isConnected;
    private JButton emojiButton;

    public  ChatClientGUI() {
        setTitle("Chat Cliente");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        messageField = new JTextField();
        messageField.setEnabled(false);
        messageField.setPreferredSize(new Dimension(350, 30));
        messageField.setMinimumSize(new Dimension(350, 30));
        bottomPanel.add(messageField);

        sendButton = new JButton("Enviar");
        sendButton.setEnabled(false);
        sendButton.addActionListener(this::sendMessage);
        sendButton.setPreferredSize(new Dimension(80, 30));
        sendButton.setMinimumSize(new Dimension(80, 30));
        bottomPanel.add(sendButton);
        add(bottomPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(sendButton);
        sendButton.requestFocus();

        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 14); //
        messageField.setFont(emojiFont);
        chatArea.setFont(emojiFont);


        emojiButton = new JButton("\uD83D\uDE0A");
        emojiButton.setEnabled(false);
        emojiButton.setPreferredSize(new Dimension(50, 30));
        emojiButton.setMinimumSize(new Dimension(50, 30));
        bottomPanel.add(emojiButton);

        String[] emojis = {
                "\uD83D\uDE04", // 😄
                "\uD83E\uDD23", // 🤣
                "\uD83E\uDEE0", // 🫠
                "\uD83D\uDE07", // 😇
                "\uD83E\uDD70", // 🥰
                "\uD83D\uDE0D", // 😍
                "\uD83D\uDE1C", // 😜
                "\uD83E\uDD2A", // 🤪
                "\uD83D\uDE1D", // 😝
                "\uD83E\uDD11", // 🤑
                "\uD83E\uDD17", // 🤗
                "\uD83E\uDD10", // 🤐
                "\uD83D\uDE10", // 😐
                "\uD83D\uDE0F", // 😏
                "\uD83D\uDE12", // 😒
                "\uD83D\uDE14"  // 😔
        };

        JPopupMenu menuEmojis = new JPopupMenu();
        for (String emoji : emojis) {
            JMenuItem emojiItem = new JMenuItem(emoji);
            emojiItem.setPreferredSize(new Dimension(50, 30));
            emojiItem.setFont(emojiFont);
            emojiItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    messageField.setText(messageField.getText() + emoji);
                }
            });
            menuEmojis.add(emojiItem);
        }

        emojiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuEmojis.show(emojiButton, 0, emojiButton.getHeight());
            }
        });


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
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            chatArea.append("Conectado ao servidor.\n");

            isConnected = true;
            messageField.setEnabled(true);
            sendButton.setEnabled(true);
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);
            emojiButton.setEnabled(true);

            // Thread to listen for messages from the server
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        chatArea.append("Servidor: " + message + "\n");
                    }
                } catch (IOException e) {
                    chatArea.append("Erro de conexão: " + e.getMessage() + "\n");
                }
            }).start();

        } catch (IOException e) {
            chatArea.append("Não foi possível conectar ao servidor: " + e.getMessage() + "\n");
        }
    }

    private void sendMessage(ActionEvent event) {
        if (isConnected && !messageField.getText().trim().isEmpty()) {
            out.println(messageField.getText().trim());
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
