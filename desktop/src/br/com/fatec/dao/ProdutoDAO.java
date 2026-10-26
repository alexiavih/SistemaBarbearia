package br.com.fatec.dao;

import br.com.fatec.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco, duracao) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getDuracao());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("preco"),
                    rs.getInt("duracao")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }

    public List<Produto> listarPorNome(String nome) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE nome LIKE ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("duracao")
                    );
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos por nome: " + e.getMessage());
        }
        return lista;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("duracao")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    public boolean alterar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, duracao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getDuracao());
            stmt.setInt(5, produto.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao alterar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }
}