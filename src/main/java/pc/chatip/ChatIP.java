package pc.chatip;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatIP {

    private static int contador = 0;
    private static final ArrayList<Tarefa> tarefas = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Servidor Afazeres!");

        try {
            ServerSocket servidor = new ServerSocket(50000);
            while (true) {
                Socket socket = servidor.accept();
                Thread t = new Thread(new ClienteInterno(socket));
                t.start();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public synchronized static void put(String tarefa) {
        tarefas.add(new Tarefa(++contador, tarefa));
    }

    public synchronized static String get(int numero) {
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getNumero() == numero) {
                String afazer = tarefa.getTarefa();
                tarefas.remove(tarefa);
                return afazer;
            }
        }
        return null;
    }
    
    public synchronized static String list() {
        String afazeres = "";
        for (int i = 0; i < tarefas.size(); i++) {
            Tarefa tarefa = tarefas.get(i);
            afazeres += tarefa.getNumero() + ". " + tarefa.getTarefa();
            if(i < tarefas.size() -1) afazeres += "\n";
        }
        return afazeres.isEmpty() ? null : afazeres;
    }
}
