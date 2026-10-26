package br.com.fatec.controller;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Usuario;
import br.com.fatec.util.ControllerBasico;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerUsuario implements ControllerBasico {

    @Override
    public Object inserir(Object obj) throws SQLException, ClassNotFoundException {
        Usuario u = (Usuario) obj;
        DAOFactory.getUsuarioDAO().inserir(u);
        return u;
    }

    @Override
    public Object alterar(Object obj) throws SQLException, ClassNotFoundException {
        Usuario u = (Usuario) obj;
        return DAOFactory.getUsuarioDAO().alterar(u);
    }

    @Override
    public Object buscar(Object obj) throws SQLException, ClassNotFoundException {
        Usuario u = (Usuario) obj;
        return buscarId(u.getId());
    }

    public Object buscarId(int id) throws SQLException, ClassNotFoundException {
        List<Usuario> lista = DAOFactory.getUsuarioDAO().listarTodos();
        for (Usuario u : lista) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public Object buscarNome(String nome) throws SQLException, ClassNotFoundException {
        List<Usuario> lista = DAOFactory.getUsuarioDAO().listarTodos();
        for (Usuario u : lista) {
            if (u.getNome().equalsIgnoreCase(nome)) return u;
        }
        return null;
    }

    @Override
    public Object excluir(Object obj) throws SQLException, ClassNotFoundException {
        Usuario u = (Usuario) obj;
        return DAOFactory.getUsuarioDAO().excluir(u.getId());
    }

    @Override
    public List<Object> listar(Object obj) throws SQLException, ClassNotFoundException {
        Usuario filtro = (Usuario) obj;
        List<Usuario> listaUsu;
        if (filtro != null && filtro.getNome() != null && !filtro.getNome().trim().isEmpty()) {
            listaUsu = DAOFactory.getUsuarioDAO().listarPorNome(filtro.getNome());
        } else {
            listaUsu = DAOFactory.getUsuarioDAO().listarTodos();
        }
        return new ArrayList<>(listaUsu);
    }
}