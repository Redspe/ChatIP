package client;

import javax.swing.JOptionPane;

public class IOGUI {

    /**
     * Shows a question-message dialog requesting a numerical input from the
     * user. Returns `null` if closed or cancelled
     *
     * @param parentComponent Used for <code>JOptionPane</code>'s window
     * alignment
     * @param message Message shown on the window
     * @param title Title of the window
     * @param messageType Message type according to <code>JOptionPane</code>
     * types
     * @return Returns a validated integer or `-1` if closed or cancelled
     */
    public static Integer readIntGUI(JOptionPane parentComponent, String message, String title, int messageType) {
        int num;
        while (true) {
            String str = JOptionPane.showInputDialog(parentComponent,
                    message, title, messageType
            );

            if (str == null) {
                return null;
            }
            if (str.isBlank()) {
                errorMsgGUI("Digite um número!", title);
                continue;
            }

            try {
                num = Integer.parseInt(str);
                return num;
            } catch (NumberFormatException e) {
                errorMsgGUI("Número inválido!", title);
            }

        }
    }

    public static int readIntGUI(String message, String title, int messageType) {
        return readIntGUI(null, message, title, messageType);
    }

    public static int readIntGUI(JOptionPane parentComponent, String message, String title) {
        return readIntGUI(parentComponent, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static int readIntGUI(String message, String title) {
        return readIntGUI(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static String readStrGUI(JOptionPane parentComponent, String message, String title, int messageType) {

        while (true) {
            String str = JOptionPane.showInputDialog(parentComponent,
                    message, title, messageType
            );

            if (str.isBlank()) {
                errorMsgGUI("Digite um Nome!", title);
                continue;
            }

            return str;

        }
    }

    public static String readStrGUI(String message, String title, int messageType) {
        return readStrGUI(null, message, title, messageType);
    }

    public static String readStrGUI(JOptionPane parentComponent, String message, String title) {
        return readStrGUI(parentComponent, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static String readStrGUI(String message, String title) {
        return readStrGUI(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     *
     * @param parentComponent
     * @param message
     * @param title
     * @param messageType
     */
    public static void errorMsgGUI(JOptionPane parentComponent, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }

    public static void errorMsgGUI(JOptionPane parentComponent, String message, String title) {
        JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void errorMsgGUI(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
