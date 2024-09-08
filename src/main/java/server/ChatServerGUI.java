// File: server/ChatServerGUI.java
package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServerGUI extends JFrame {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Set<ClientHandler> clientHandlers = new HashSet<>();
    private JTextArea logArea;
    private JButton startButton;
    private JButton stopButton;
    private boolean isRunning;

    public ChatServerGUI() {
        setTitle("Chat Servidor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Iniciar Servidor");
        stopButton = new JButton("Parar Servidor");
        stopButton.setEnabled(false);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(this::startServer);
        stopButton.addActionListener(this::stopServer);

        setVisible(true);
    }

    private void startServer(ActionEvent event) {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            logArea.append("Servidor iniciado na porta " + PORT + "\n");

            // Thread to accept incoming connections
            new Thread(() -> {
                while (isRunning) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        logArea.append("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress() + "\n");
                        ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);
                        clientHandlers.add(clientHandler);
                        new Thread(clientHandler).start();
                    } catch (IOException e) {
                        logArea.append("Erro ao aceitar conexão: " + e.getMessage() + "\n");
                    }
                }
            }).start();

        } catch (IOException e) {
            logArea.append("Não foi possível iniciar o servidor: " + e.getMessage() + "\n");
        }
    }

    private void stopServer(ActionEvent event) {
        try {
            isRunning = false;
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.closeConnection();
            }
            clientHandlers.clear();
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            logArea.append("Servidor parado.\n");
        } catch (IOException e) {
            logArea.append("Erro ao parar o servidor: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatServerGUI::new);
    }
}
