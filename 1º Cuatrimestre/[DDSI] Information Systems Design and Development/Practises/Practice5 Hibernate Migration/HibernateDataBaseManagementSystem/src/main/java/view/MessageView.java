package view;

import javax.swing.JOptionPane;

/**
 *
 * @author Alberto Fernández
 */
public class MessageView {

    /**
     *
     * @param type
     * @param text
     */
    public void Message(String type, String text) {
        switch (type) {
            case "info":
                JOptionPane.showMessageDialog(null, text, null, JOptionPane.INFORMATION_MESSAGE);
                break;
            case "error":
                JOptionPane.showMessageDialog(null, text, null, JOptionPane.ERROR_MESSAGE);
                break;
            default:

        }
    }

}
