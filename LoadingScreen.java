
import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JDialog {
    public LoadingScreen(JFrame parent) {
        super(parent, "Carregando...", true);
        setSize(200, 100);
        setLocationRelativeTo(parent);
        JLabel label = new JLabel("Entrando...", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
