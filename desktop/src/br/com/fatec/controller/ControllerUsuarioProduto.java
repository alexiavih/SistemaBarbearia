package br.com.fatec.controller;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Relacao;
import br.com.fatec.model.UsuarioProduto;
import br.com.fatec.util.ControllerBasico;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerUsuarioProduto implements ControllerBasico {

    @Override
    public Object inserir(Object obj) throws SQLException, ClassNotFoundException {
        UsuarioProduto up = (UsuarioProduto) obj;
        Relacao r = converterParaRelacao(up);
        DAOFactory.getRelacaoDAO().inserir(r);
        return up;
    }

    @Override
    public Object alterar(Object obj) throws SQLException, ClassNotFoundException {
        UsuarioProduto up = (UsuarioProduto) obj;
        Relacao r = converterParaRelacao(up);
        return DAOFactory.getRelacaoDAO().alterar(r);
    }

    @Override
    public Object buscar(Object obj) throws SQLException, ClassNotFoundException {
        UsuarioProduto up = (UsuarioProduto) obj;
        return buscarId(up.getId());
    }

    public Object buscarId(int id) throws SQLException, ClassNotFoundException {
        List<Relacao> lista = DAOFactory.getRelacaoDAO().listarTodas();
        for (Relacao r : lista) {
            if (r.getId() == id) {
                String detalhe = (r.getStatus() != null && !r.getStatus().isEmpty()) ? r.getStatus() : r.getDataHora();
                return new UsuarioProduto(r.getId(), r.getUsuarioId(), r.getProdutoId(), detalhe, null, null);
            }
        }
        return null;
    }

    public Object buscarUsuario(int id) throws SQLException, ClassNotFoundException {
        ControllerUsuario cu = new ControllerUsuario();
        return cu.buscarId(id);
    }

    public Object buscarProduto(int id) throws SQLException, ClassNotFoundException {
        ControllerProduto cp = new ControllerProduto();
        return cp.buscarId(id);
    }

    @Override
    public Object excluir(Object obj) throws SQLException, ClassNotFoundException {
        UsuarioProduto up = (UsuarioProduto) obj;
        return excluirId(up.getId());
    }

    public Object excluirId(int id) throws SQLException, ClassNotFoundException {
        return DAOFactory.getRelacaoDAO().excluir(id);
    }

    @Override
    public List<Object> listar(Object obj) throws SQLException, ClassNotFoundException {
        UsuarioProduto filtro = (UsuarioProduto) obj;
        List<Relacao> listaRel;
        
        // Verifica se há termo de busca para filtrar por ID, status ou data/hora
        if (filtro != null && filtro.getObs() != null && !filtro.getObs().trim().isEmpty()) {
            listaRel = DAOFactory.getRelacaoDAO().listarPorFiltro(filtro.getObs());
        } else {
            listaRel = DAOFactory.getRelacaoDAO().listarTodas();
        }

        List<Object> lista = new ArrayList<>();
        for (Relacao r : listaRel) {
            String detalhe = (r.getStatus() != null && !r.getStatus().isEmpty()) ? r.getStatus() : r.getDataHora();
            UsuarioProduto up = new UsuarioProduto(r.getId(), r.getUsuarioId(), r.getProdutoId(), detalhe, null, null);
            lista.add(up);
        }
        return lista;
    }

    private Relacao converterParaRelacao(UsuarioProduto up) {
        Relacao r = new Relacao();
        r.setId(up.getId());
        r.setUsuarioId(up.getIdU());
        r.setProdutoId(up.getIdP());
        r.setDataHora(up.getObs());
        r.setStatus(up.getObs() != null && !up.getObs().trim().isEmpty() ? up.getObs() : "Agendado");
        return r;
    }
}