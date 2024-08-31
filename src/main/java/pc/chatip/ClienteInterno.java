package pc.chatip;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteInterno implements Runnable {

    private final Socket socket;

    public ClienteInterno(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            
            // Cria as variaveis de conexao
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String requisicao, resposta;

            do {
                requisicao = (String) input.readObject(); // Le os dados recebidos
                System.out.println(requisicao); // Imprime os dados recebidos
                resposta = getRespostaTDP(requisicao);
                output.writeObject(resposta);
                
            } while (!requisicao.equals("EXIT"));
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private String getRespostaTDP(String requisicao) { // TDP - ToDo Protocol
        if (requisicao.startsWith("PUT") && requisicao.length() > 4) {

            // String tarefa = requisicao.substring(4);
            // ChatIP.put(tarefa);
            return "OK";

        } else if (requisicao.equals("EXIT")) {
            return "BYE";

        } else if (requisicao.equals("HELP")) {
            return ("""
                    
                    Comandos:
                    - HELP: Mostra essa janela
                    - PUT: Posta uma tarefa para ser salva no servidor
                    - GET: Mostra uma tarefa especifica
                    - LIST: Lista todas as tarefas existentes
                    - EXIT: Fecha o programa
                    """);

        } else {
            return "Erro! Comando não encontrado. Digite HELP para ajuda.";
        }

    }
}
