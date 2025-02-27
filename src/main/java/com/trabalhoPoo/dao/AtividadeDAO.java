package com.trabalhoPoo.dao;

import com.trabalhoPoo.model.Atividade;
import com.trabalhoPoo.model.Projeto;
import com.trabalhoPoo.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class AtividadeDAO {

    public void adicionarAtividade(Atividade atividade) throws SQLException {
        String sql = "INSERT INTO Atividades (nome, descricao, data_inicio, data_termino, status, porcentagem_conclusao, projeto_id, justificativa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, atividade.getNome());
            pstmt.setString(2, atividade.getDescricao());
            pstmt.setDate(3, java.sql.Date.valueOf(atividade.getDataInicio()));
            pstmt.setDate(4, java.sql.Date.valueOf(atividade.getDataTermino()));
            pstmt.setString(5, atividade.getStatus()); //O status vai continuar sendo salvo no banco, pois o usuário pode querer mudar o status
            pstmt.setInt(6, atividade.getPorcentagemConclusao());
            pstmt.setInt(7, atividade.getProjetoId());
            pstmt.setString(8, atividade.getJustificativa());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    atividade.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao criar atividade, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao adicionar atividade: " + e.getMessage(), e);
        }
    }
    public List<Atividade> listarAtividadesPorProjeto(int projetoId) throws SQLException {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM Atividades WHERE projeto_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, projetoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Atividade atividade = new Atividade();
                    atividade.setId(rs.getInt("id"));
                    atividade.setNome(rs.getString("nome"));
                    atividade.setDescricao(rs.getString("descricao"));
                    atividade.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                    atividade.setDataTermino(rs.getDate("data_termino").toLocalDate());
                    atividade.setStatus(rs.getString("status")); //Aqui, o status que foi definido é salvo
                    atividade.setPorcentagemConclusao(rs.getInt("porcentagem_conclusao"));
                    atividade.setProjetoId(rs.getInt("projeto_id"));
                    atividade.setJustificativa(rs.getString("justificativa"));
                    atividades.add(atividade);
                }
            }
        } catch (SQLException e){
            throw new SQLException("Erro ao listar as atividades: "+ e.getMessage());
        }
        return atividades;
    }

    public Atividade buscarAtividadePorId(int id) throws SQLException {
        String sql = "SELECT * FROM Atividades WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Atividade atividade = new Atividade();
                    atividade.setId(rs.getInt("id"));
                    atividade.setNome(rs.getString("nome"));
                    atividade.setDescricao(rs.getString("descricao"));
                    atividade.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                    atividade.setDataTermino(rs.getDate("data_termino").toLocalDate());
                    atividade.setStatus(rs.getString("status"));
                    atividade.setPorcentagemConclusao(rs.getInt("porcentagem_conclusao"));
                    atividade.setProjetoId(rs.getInt("projeto_id"));
                    atividade.setJustificativa(rs.getString("justificativa"));
                    return atividade;
                }
            }
        }
        return null;
    }

    public void atualizarAtividade(Atividade atividade) throws SQLException {
        String sql = "UPDATE Atividades SET nome = ?, descricao = ?, data_inicio = ?, data_termino = ?, status = ?, porcentagem_conclusao = ?, projeto_id = ?, justificativa = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, atividade.getNome());
            pstmt.setString(2, atividade.getDescricao());
            pstmt.setDate(3, java.sql.Date.valueOf(atividade.getDataInicio()));
            pstmt.setDate(4, java.sql.Date.valueOf(atividade.getDataTermino()));
            pstmt.setString(5, atividade.getStatus());
            pstmt.setInt(6, atividade.getPorcentagemConclusao());
            pstmt.setInt(7, atividade.getProjetoId());
            pstmt.setString(8, atividade.getJustificativa());
            pstmt.setInt(9, atividade.getId());
            pstmt.executeUpdate();
        }
    }


    public void excluirAtividade(int id) throws SQLException {
        String sql = "DELETE FROM Atividades WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}