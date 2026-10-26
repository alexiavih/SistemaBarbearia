package br.com.fatec.dao;

public class DAOFactory {

    public static UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO();
    }

    public static ProdutoDAO getProdutoDAO() {
        return new ProdutoDAO();
    }

    public static RelacaoDAO getRelacaoDAO() {
        return new RelacaoDAO();
    }
}