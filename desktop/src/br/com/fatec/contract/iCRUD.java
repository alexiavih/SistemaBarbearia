package br.com.fatec.contract;

import java.sql.SQLException;
import java.util.List;

public interface iCRUD {
    public Object inserir(Object obj) throws SQLException, ClassNotFoundException;
    public Object alterar(Object obj) throws SQLException, ClassNotFoundException;
    public Object excluir(Object obj) throws SQLException, ClassNotFoundException;
    public Object buscarId(int id) throws SQLException, ClassNotFoundException;
    public Object buscarNome(String nome) throws SQLException, ClassNotFoundException;
    public List<Object> listarTudo(Object obj) throws SQLException, ClassNotFoundException;
}