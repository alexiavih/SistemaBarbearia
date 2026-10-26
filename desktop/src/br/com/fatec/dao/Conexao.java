package br.com.fatec.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
    // Insere o caminho absoluto exato até ao teu ficheiro barbearia.db
  private static final String URL = "jdbc:sqlite:C:/Users/alexi/Documents/NetBeansProjects/ProjetoBarbearia/barbearia.db";
    private static Connection conexao = null;

    private Conexao() {}

    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL);
                criarTabelas();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao SQLite: " + e.getMessage());
        }
        return conexao;
    }

    private static void criarTabelas() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "cpf TEXT UNIQUE NOT NULL, "
                + "telefone TEXT, "
                + "email TEXT"
                + ");";

        String sqlProdutos = "CREATE TABLE IF NOT EXISTS produtos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "descricao TEXT, "
                + "preco REAL NOT NULL, "
                + "duracao INTEGER NOT NULL"
                + ");";

        String sqlRelacoes = "CREATE TABLE IF NOT EXISTS relacoes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "usuario_id INTEGER NOT NULL, "
                + "produto_id INTEGER NOT NULL, "
                + "data_hora TEXT NOT NULL, "
                + "status TEXT DEFAULT 'Agendado', "
                + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id), "
                + "FOREIGN KEY (produto_id) REFERENCES produtos(id)"
                + ");";

        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlProdutos);
            stmt.execute(sqlRelacoes);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}