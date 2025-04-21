
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen(String username) {
        setTitle("Bem-vindo");
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JLabel welcomeLabel = new JLabel("Bem-vindo, " + username + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //Botõeszinho
        
        JButton btn1 = new JButton("Linkedin");
        JButton btn2 = new JButton("Youtube");
        JButton btn3 = new JButton("Netbeans");
        JButton btn4 = new JButton("VS Code");
        
       btn1.addActionListener((ActionEvent e) -> {
            try{
                String edgePath = "\"C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe\"";
                String url= "https://www.linkedin.com/feed/?trk=sem-ga_campid.12619604099_asid.149519181115_crid.725790844702_kw.linkedin_d.c_tid.kwd-148086543_n.g_mt.e_geo.9100726";
                new ProcessBuilder (edgePath, url).start();
                JOptionPane.showMessageDialog(this, "Abrindo Linkedin...");
            } catch(IOException ex){
                JOptionPane.showMessageDialog(this, "Erro ao abrir o Linkedin:" + ex.getMessage());
            }
       });
            

        btn2.addActionListener((ActionEvent e) -> {
            try{
                String edgePath = "\"C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe\"";
                String url= "https://www.youtube.com/";
                new ProcessBuilder (edgePath, url).start();
                JOptionPane.showMessageDialog(this, "Abrindo Youtube...");
            } catch(IOException ex){
                JOptionPane.showMessageDialog(this, "Erro ao abrir o Youtube:" + ex.getMessage());
            }
       });
        
         btn3.addActionListener((ActionEvent e) -> {
            try{
                String discordPath = "\"C:\\Program Files\\NetBeans-25\\netbeans\\bin\\netbeans64.exe\"";
                Runtime.getRuntime().exec(discordPath);
                JOptionPane.showMessageDialog(this, "Abrindo o Netbeans...");
            } catch(IOException ex){
                JOptionPane.showMessageDialog(this, "Erro ao abrir o Netbeans:" + ex.getMessage());
            }
       });
        
         btn4.addActionListener((ActionEvent e) -> {
            try{
                String discordPath = "\"C:\\Users\\lucas\\AppData\\Local\\Programs\\Microsoft VS Code\\Code.exe\"";
                Runtime.getRuntime().exec(discordPath);
                JOptionPane.showMessageDialog(this, "Abrindo VS Code...");
            } catch(IOException ex){
                JOptionPane.showMessageDialog(this, "Erro ao abrir o VS Code:" + ex.getMessage());
            }
       });

        // Painel com GridLayout 2x2 pros botões
        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        buttonsPanel.add(btn1);
        buttonsPanel.add(btn2);
        buttonsPanel.add(btn3);
        buttonsPanel.add(btn4);
        
ImageIcon icon = new ImageIcon(getClass().getResource("/Assets/engrenagem.png"));
Image img = icon.getImage();
Image newImg = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
ImageIcon newIcon = new ImageIcon(newImg);

JButton settingsButton = new JButton(newIcon);
settingsButton.setBounds(535, 5, 48, 48); // Posição no canto superior direito da tela 600x400
settingsButton.setContentAreaFilled(false);
settingsButton.setBorderPainted(false);
settingsButton.setFocusPainted(false);

// 👉 Abre a tela de configurações (ConfigScreen)
settingsButton.addActionListener(e -> {
    new ConfigScreen(username, this).setVisible(true);
    AppThemeManager.register(Window.getWindows()[Window.getWindows().length - 1]);
});

add(settingsButton);
repaint();
revalidate();
        
        
        

        // Layout principal
        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
    }    
}
