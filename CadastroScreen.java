import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CadastroScreen extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;

    public CadastroScreen() {
        AppThemeManager.register(this); // registra para futuras mudanças
        SwingUtilities.updateComponentTreeUI(this); // aplica o tema atual

        setTitle("Cadastro de Usuário");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(UIManager.getColor("Panel.background"));

        JLabel lblTitle = new JLabel("Cadastro");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(UIManager.getColor("Label.foreground"));
        lblTitle.setBounds(140, 20, 150, 30);
        panel.add(lblTitle);

        JLabel lblUsername = new JLabel("Usuário:");
        lblUsername.setForeground(UIManager.getColor("Label.foreground"));
        lblUsername.setBounds(50, 80, 100, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 80, 180, 25);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Senha:");
        lblPassword.setForeground(UIManager.getColor("Label.foreground"));
        lblPassword.setBounds(50, 120, 100, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 120, 180, 25);
        panel.add(txtPassword);

        JLabel lblConfirmPassword = new JLabel("Confirmar Senha:");
        lblConfirmPassword.setForeground(UIManager.getColor("Label.foreground"));
        lblConfirmPassword.setBounds(20, 160, 120, 25);
        panel.add(lblConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(150, 160, 180, 25);
        panel.add(txtConfirmPassword);

        JButton btnRegister = new JButton("Cadastrar");
        btnRegister.setBounds(140, 220, 120, 30);
        panel.add(btnRegister);

        btnRegister.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirmPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            } else if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            } else {
                try {
                    // Aqui chamamos o método para salvar o usuário no banco de dados (SQLite)
                    UserDataManager.salvarUsuario(user, pass);
                    JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + ex.getMessage());
                }
            }
        });

        add(panel);
    }
}
