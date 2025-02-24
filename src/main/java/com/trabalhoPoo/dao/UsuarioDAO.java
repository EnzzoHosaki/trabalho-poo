package com.trabalhoPoo.dao;

import com.trabalhoPoo.model.Usuario;
import com.trabalhoPoo.util.DBConnection;
//import com.trabalhoPoo.util.PasswordUtil; // Não precisa mais importar aqui
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    public Usuario adicionarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha()); // Já vem hashada
            pstmt.setString(4, usuario.getTipo());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                    return usuario;
                } else {
                    throw new SQLException("Falha ao criar usuário, nenhum ID obtido.");
                }
            }
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("tipo")
                    );
                }
            }
        }
        return null;
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuarios SET nome = ?, email = ?, senha = ?, tipo = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setString(4, usuario.getTipo());
            pstmt.setInt(5, usuario.getId());
            pstmt.executeUpdate();
        }
    }
}