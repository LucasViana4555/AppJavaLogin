import java.sql.*;
import java.io.File;

public class UserDataManager {

    private static final String DB_URL = "jdbc:sqlite:data/users.db";

    // Criação da tabela, caso não exista
    static {
        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY," +
                    "password TEXT NOT NULL" +
                    ");";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    // Método de conexão com verificação de pasta
    private static Connection connect() throws SQLException {
        try {
            // Cria a pasta se não existir
            File pasta = new File("data");
            if (!pasta.exists()) pasta.mkdirs();

            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    // Salva um novo usuário no banco
    public static void salvarUsuario(String usuario, String senha) throws SQLException {
        String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);
            pstmt.executeUpdate();
        }
    }

    // Valida se o usuário e senha existem no banco
    public static boolean validarLogin(String usuario, String senha) {
        String selectSQL = "SELECT username FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return false;
    }

    // Atualiza os dados do usuário (nome e senha)
    public static void atualizarUsuario(String nomeAtual, String novoNome, String novaSenha) throws SQLException {
        String updateSQL = "UPDATE users SET username = ?, password = ? WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novaSenha);
            pstmt.setString(3, nomeAtual);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Usuário não encontrado para atualização.");
            }
        }
    }
}
