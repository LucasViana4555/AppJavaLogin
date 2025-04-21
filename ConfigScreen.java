import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConfigScreen extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private boolean isDark = true;
    private String username;
    private JFrame previousWindow;

    public ConfigScreen(String username, JFrame previousWindow) {
        this.username = username;
        this.previousWindow = previousWindow;
        AppThemeManager.register(this);

        setTitle("Configurações");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(45, 45, 45));
        sidebar.setPreferredSize(new Dimension(150, getHeight()));

        JButton temaBtn = new JButton("Tema");
        JButton perfilBtn = new JButton("Perfil");
        JButton loginBtn = new JButton("Login/Cadastro");
        JButton voltarInicioBtn = new JButton("Voltar Início");
        JButton sairBtn = new JButton("Sair do App");

        temaBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        perfilBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        voltarInicioBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sairBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebar.add(temaBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(perfilBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(loginBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(voltarInicioBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(sairBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        add(sidebar, BorderLayout.WEST);

        // Painel principal com CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        JPanel temaPanel = criarPainelTema();
        JPanel perfilPanel = criarPainelPerfil();
        JPanel loginPanel = criarPainelLogin();

        mainPanel.add(temaPanel, "tema");
        mainPanel.add(perfilPanel, "perfil");
        mainPanel.add(loginPanel, "login");

        add(mainPanel, BorderLayout.CENTER);

        // Listeners
        temaBtn.addActionListener(e -> cardLayout.show(mainPanel, "tema"));
        perfilBtn.addActionListener(e -> cardLayout.show(mainPanel, "perfil"));
        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        voltarInicioBtn.addActionListener(e -> {
            new WelcomeScreen(username).setVisible(true);
            AppThemeManager.register(Window.getWindows()[Window.getWindows().length - 1]);
            dispose();
        });
        sairBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private JPanel criarPainelTema() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UIManager.getColor("Panel.background"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Tema atual:");
        JButton alternarBtn = new JButton("Alternar Tema");

        alternarBtn.addActionListener(e -> {
            isDark = !isDark;
            AppThemeManager.setDarkMode(isDark);
        });

        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(alternarBtn, gbc);

        return panel;
    }

    private JPanel criarPainelPerfil() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIManager.getColor("Panel.background"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Editar Perfil");
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(20);
        nomeField.setText(username);

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField(20);

        JButton salvarBtn = new JButton("Salvar Alterações");

        salvarBtn.addActionListener(e -> {
            String novoNome = nomeField.getText();
            String novaSenha = new String(senhaField.getPassword());

            if (!novoNome.isEmpty() && !novaSenha.isEmpty()) {
                try {
                    UserDataManager.atualizarUsuario(username, novoNome, novaSenha);
                    JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!");
                    username = novoNome;
                    new WelcomeScreen(username).setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar perfil: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            }
        });

        formPanel.add(nomeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(senhaLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(senhaField, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(salvarBtn, gbc);

        panel.add(header, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel criarPainelLogin() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(UIManager.getColor("Panel.background"));

        JButton voltarLogin = new JButton("Voltar ao Login");
        JButton irCadastro = new JButton("Ir para Cadastro");

        voltarLogin.addActionListener(e -> {
            JFrame login = new LoginScreen();
            login.setVisible(true);
            AppThemeManager.register(login);
            if (previousWindow != null) previousWindow.dispose();
            dispose();
        });

        irCadastro.addActionListener(e -> {
            JFrame cadastro = new CadastroScreen();
            cadastro.setVisible(true);
            AppThemeManager.register(cadastro);
            if (previousWindow != null) previousWindow.dispose();
            dispose();
        });

        panel.add(voltarLogin);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(irCadastro);

        return panel;
    }

    public static void main(String[] args) {
        AppThemeManager.setDarkMode(true);
        new ConfigScreen("Usuário", null);
    }
}

class AppThemeManager {
    private static final java.util.List<Window> registeredWindows = new ArrayList<>();
    private static boolean isDark = true;

    public static void register(Window window) {
        if (!registeredWindows.contains(window)) {
            registeredWindows.add(window);
        }
    }

    public static void setDarkMode(boolean dark) {
        try {
            UIManager.setLookAndFeel(dark ? new FlatDarkLaf() : new FlatLightLaf());
            isDark = dark;
            for (Window window : registeredWindows) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDarkMode() {
        return isDark;
    }
}
