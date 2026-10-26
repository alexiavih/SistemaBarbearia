package br.com.fatec.dao;

import br.com.fatec.model.Relacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelacaoDAO {

    public boolean inserir(Relacao relacao) {
        String sql = "INSERT INTO relacoes (usuario_id, produto_id, data_hora, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, relacao.getUsuarioId());
            stmt.setInt(2, relacao.getProdutoId());
            stmt.setString(3, relacao.getDataHora());
            stmt.setString(4, relacao.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir relação: " + e.getMessage());
            return false;
        }
    }

    public List<Relacao> listarTodas() {
        List<Relacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM relacoes";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Relacao r = new Relacao(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("produto_id"),
                    rs.getString("data_hora"),
                    rs.getString("status")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar relações: " + e.getMessage());
        }
        return lista;
    }

    public List<Relacao> listarPorFiltro(String termo) {
        List<Relacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM relacoes WHERE status LIKE ? OR data_hora LIKE ? OR CAST(usuario_id AS TEXT) LIKE ? OR CAST(produto_id AS TEXT) LIKE ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String wildcard = "%" + termo + "%";
            stmt.setString(1, wildcard);
            stmt.setString(2, wildcard);
            stmt.setString(3, wildcard);
            stmt.setString(4, wildcard);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Relacao r = new Relacao(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("produto_id"),
                        rs.getString("data_hora"),
                        rs.getString("status")
                    );
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao filtrar relações: " + e.getMessage());
        }
        return lista;
    }

    public Relacao buscarPorId(int id) {
        String sql = "SELECT * FROM relacoes WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Relacao(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("produto_id"),
                        rs.getString("data_hora"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar relação: " + e.getMessage());
        }
        return null;
    }

    public boolean alterar(Relacao relacao) {
        String sql = "UPDATE relacoes SET usuario_id = ?, produto_id = ?, data_hora = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, relacao.getUsuarioId());
            stmt.setInt(2, relacao.getProdutoId());
            stmt.setString(3, relacao.getDataHora());
            stmt.setString(4, relacao.getStatus());
            stmt.setInt(5, relacao.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao alterar relação: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM relacoes WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir relação: " + e.getMessage());
            return false;
        }
    }
}