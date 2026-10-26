package br.com.fatec.util;

import java.sql.SQLException;
import java.util.List;

public interface ControllerBasico {
    public Object inserir(Object obj) throws SQLException, ClassNotFoundException;
    public Object alterar(Object obj) throws SQLException, ClassNotFoundException;
    public Object excluir(Object obj) throws SQLException, ClassNotFoundException;
    public Object buscar(Object obj) throws SQLException, ClassNotFoundException;
    public List<Object> listar(Object obj) throws SQLException, ClassNotFoundException;
}