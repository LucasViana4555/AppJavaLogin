import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkShowPassword;

    public LoginScreen() {
        AppThemeManager.register(this); // Registra para atualizar o tema depois
        SwingUtilities.updateComponentTreeUI(this); // Aplica o tema atual visualmente

        setTitle("Tela de Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(UIManager.getColor("Panel.background"));

        JLabel lblTitle = new JLabel("Login");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(UIManager.getColor("Label.foreground"));
        lblTitle.setBounds(160, 20, 100, 30);
        panel.add(lblTitle);

        JLabel lblUsername = new JLabel("Usuário:");
        lblUsername.setForeground(UIManager.getColor("Label.foreground"));
        lblUsername.setBounds(50, 70, 100, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(130, 70, 200, 25);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Senha:");
        lblPassword.setForeground(UIManager.getColor("Label.foreground"));
        lblPassword.setBounds(50, 110, 100, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(130, 110, 200, 25);
        panel.add(txtPassword);

        chkShowPassword = new JCheckBox("Mostrar senha");
        chkShowPassword.setBounds(130, 140, 200, 25);
        chkShowPassword.setForeground(UIManager.getColor("Label.foreground"));
        chkShowPassword.setBackground(UIManager.getColor("Panel.background"));
        panel.add(chkShowPassword);

        chkShowPassword.addActionListener(e -> {
            txtPassword.setEchoChar(chkShowPassword.isSelected() ? (char) 0 : '•');
        });

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(150, 180, 100, 30);
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            if (UserDataManager.validarLogin(user, pass)) {
                new WelcomeScreen(user).setVisible(true);
                AppThemeManager.register(Window.getWindows()[Window.getWindows().length - 1]);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos");
            }
        });

        JButton btnRegister = new JButton("Cadastrar");
        btnRegister.setBounds(150, 220, 100, 30);
        panel.add(btnRegister);

        btnRegister.addActionListener(e -> {
            new CadastroScreen().setVisible(true);
            AppThemeManager.register(Window.getWindows()[Window.getWindows().length - 1]);
            dispose(); // Fecha essa tela se quiser
        });

        add(panel);
    }
}