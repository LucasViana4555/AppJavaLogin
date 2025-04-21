import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Tema escuro
        } catch (Exception e) {
            System.out.println("Falha ao aplicar tema.");
        }

        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
