package br.com.fatec.dao;

import br.com.fatec.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getEmail());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return lista;
    }

    public List<Usuario> listarPorNome(String nome) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE nome LIKE ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email")
                    );
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários por nome: " + e.getMessage());
        }
        return lista;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public boolean alterar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getEmail());
            stmt.setInt(5, usuario.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao alterar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }
}