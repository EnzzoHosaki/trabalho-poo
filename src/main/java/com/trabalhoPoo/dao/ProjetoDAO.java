package com.trabalhoPoo.dao;

import com.trabalhoPoo.model.Projeto;
import com.trabalhoPoo.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    public void adicionarProjeto(Projeto projeto) throws SQLException {
        String sql = "INSERT INTO Projetos (nome, descricao, data_inicio, data_termino) VALUES (?, ?, ?, ?)"; //Removido o status
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, projeto.getNome());
            pstmt.setString(2, projeto.getDescricao());
            pstmt.setDate(3, java.sql.Date.valueOf(projeto.getDataInicio()));
            pstmt.setDate(4, java.sql.Date.valueOf(projeto.getDataTermino()));
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projeto.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao criar projeto, nenhum ID obtido.");
                }
            }
        }
    }

    public List<Projeto> listarProjetos() throws SQLException {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT * FROM Projetos";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                projeto.setDataTermino(rs.getDate("data_termino").toLocalDate());
                projetos.add(projeto);
            }
        }
        return projetos;
    }


    public void atualizarProjeto(Projeto projeto) throws SQLException {
        String sql = "UPDATE Projetos SET nome = ?, descricao = ?, data_inicio = ?, data_termino = ? WHERE id = ?"; //Removido o status
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projeto.getNome());
            pstmt.setString(2, projeto.getDescricao());
            pstmt.setDate(3, java.sql.Date.valueOf(projeto.getDataInicio()));
            pstmt.setDate(4, java.sql.Date.valueOf(projeto.getDataTermino()));
            pstmt.setInt(5, projeto.getId());
            pstmt.executeUpdate();
        }
    }


    public void excluirProjeto(int id) throws SQLException {
        String sql = "DELETE FROM Projetos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    //buscar projeto por ID
    public Projeto buscarProjetoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Projetos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Projeto projeto = new Projeto();
                    projeto.setId(rs.getInt("id"));
                    projeto.setNome(rs.getString("nome"));
                    projeto.setDescricao(rs.getString("descricao"));
                    projeto.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                    projeto.setDataTermino(rs.getDate("data_termino").toLocalDate());
                    //projeto.setStatus(rs.getString("status"));
                    return projeto;
                }
            }
        }
        return null;
    }
}