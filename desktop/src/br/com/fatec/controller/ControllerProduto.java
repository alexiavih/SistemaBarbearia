package br.com.fatec.controller;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Produto;
import br.com.fatec.util.ControllerBasico;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerProduto implements ControllerBasico {

    @Override
    public Object inserir(Object obj) throws SQLException, ClassNotFoundException {
        Produto p = (Produto) obj;
        DAOFactory.getProdutoDAO().inserir(p);
        return p;
    }

    @Override
    public Object alterar(Object obj) throws SQLException, ClassNotFoundException {
        Produto p = (Produto) obj;
        return DAOFactory.getProdutoDAO().alterar(p);
    }

    @Override
    public Object buscar(Object obj) throws SQLException, ClassNotFoundException {
        Produto p = (Produto) obj;
        return buscarId(p.getId());
    }

    public Object buscarId(int id) throws SQLException, ClassNotFoundException {
        List<Produto> lista = DAOFactory.getProdutoDAO().listarTodos();
        for (Produto p : lista) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public Object buscarNome(String nome) throws SQLException, ClassNotFoundException {
        List<Produto> lista = DAOFactory.getProdutoDAO().listarTodos();
        for (Produto p : lista) {
            if (p.getNome().equalsIgnoreCase(nome)) return p;
        }
        return null;
    }

    @Override
    public Object excluir(Object obj) throws SQLException, ClassNotFoundException {
        Produto p = (Produto) obj;
        return DAOFactory.getProdutoDAO().excluir(p.getId());
    }

    @Override
    public List<Object> listar(Object obj) throws SQLException, ClassNotFoundException {
        Produto filtro = (Produto) obj;
        List<Produto> listaProd;
        if (filtro != null && filtro.getNome() != null && !filtro.getNome().trim().isEmpty()) {
            listaProd = DAOFactory.getProdutoDAO().listarPorNome(filtro.getNome());
        } else {
            listaProd = DAOFactory.getProdutoDAO().listarTodos();
        }
        return new ArrayList<>(listaProd);
    }
}