package pc.chatip;

public class Tarefa {

    private final int numero;
    private final String tarefa;

    public Tarefa(int numero, String tarefa) {
        this.numero = numero;
        this.tarefa = tarefa;
    }

    public int getNumero() {
        return numero;
    }

    public String getTarefa() {
        return tarefa;
    }
}
