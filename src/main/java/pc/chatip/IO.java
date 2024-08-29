package pc.chatip;

import java.util.Scanner;

public class IO {

    /**
     * Imprime no console sem pular linha depois
     *
     * @param obj aceita qualquer tipo de objeto
     */
    public static void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Imprime no console pulando uma linha ao final do texto
     *
     * @param obj aceita qualquer tipo de objeto
     */
    public static void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Imprime no console pulando uma linha ao final do texto
     *
     * @param format texto
     * @param args aceita qualquer tipo de objeto
     */
    public static void printlnf(String format, Object args) {
        System.out.printf(format + "\n", args);
    }

    /**
     * Pede que o usuario clique ENTER para continuar
     */
    public static void aperteContinuar() {
        print("\nAperte ENTER para continuar...");
        Scanner read = new Scanner(System.in);
        read.nextLine();
    }

    /**
     * """"Limpa"""" o console fazendo tudo sumir
     */
    public static void clearConsole() {
        println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * @param msg Text shown when asking for the string
     * @return Returns a string
     */
    public static String readStr(String msg) {
        String text;

        while (true) {
            try {
                print(msg);
                Scanner read = new Scanner(System.in);
                text = read.nextLine();
                break;

            } catch (Exception e) {
                print("Um erro inesperado aconteceu: ");
                System.out.println(e);
            }
        }

        return text;
    }

    /**
     * @param msg Text shown when asking for the integer
     * @return Returns a verified integer
     */
    public static int readInt(String msg) {
        int number;

        while (true) {
            try {
                print(msg);
                Scanner read = new Scanner(System.in);
                number = Integer.parseInt(read.nextLine());
                break;

            } catch (NumberFormatException e) {
                println("Erro! Digite um número inteiro.");
            }
        }

        return number;
    }

    /**
     * @param msg Text shown when asking for the double
     * @return Returns a verified double
     */
    public static double readDouble(String msg) {
        double number;

        while (true) {
            try {
                print(msg);
                Scanner read = new Scanner(System.in);
                number = Double.parseDouble(read.nextLine());
                break;

            } catch (NumberFormatException e) {
                print("Erro! Digite um número.");
            }
        }

        return number;
    }

    /**
     *
     * @param min Minimum acceptable number (included)
     * @param max Maximum acceptable number (included)
     * @param msg Text shown when asking for the number
     * @param errorMsg Text shown when a wrong input is typed
     *
     * @return
     */
    public static int chooseInRange(int min, int max, String msg, String errorMsg) {
        int option;
        while (true) {
            option = readInt(msg);
            if (option >= min && option <= max) {
                return option;
            } else {
                IO.println(errorMsg);
            }

        }
    }

    /**
     *
     * @param min Minimum acceptable number (included)
     * @param max Maximum acceptable number (included)
     * @param msg Text shown when asking for the number
     * @param errorMsg Text shown when a wrong input is typed
     *
     * @return
     */
    public static double chooseInRange(double min, double max, String msg, String errorMsg) {
        double option;
        while (true) {
            option = readDouble(msg);
            if (option >= min && option <= max) {
                return option;
            } else {
                IO.println(errorMsg);
            }

        }
    }

}
