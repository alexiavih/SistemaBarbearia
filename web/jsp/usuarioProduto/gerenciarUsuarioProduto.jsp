<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.fatec.model.UsuarioProduto"%>
<%@page import="br.com.fatec.controller.ControllerUsuarioProduto"%>
<%@page import="java.util.List"%>

<%
    ControllerUsuarioProduto rpc = new ControllerUsuarioProduto();
    
    // 1. Processar Ações (Salvar / Excluir)
    String acao = request.getParameter("acao");
    if (acao != null) {
        if (acao.equals("salvar")) {
            String idStr = request.getParameter("id");
            String idUStr = request.getParameter("idU");
            String idPStr = request.getParameter("idP");
            String obs = request.getParameter("obs"); // Data/Hora ou observação
            
            UsuarioProduto up = new UsuarioProduto();
            if (idUStr != null && !idUStr.isEmpty()) up.setIdU(Integer.parseInt(idUStr));
            if (idPStr != null && !idPStr.isEmpty()) up.setIdP(Integer.parseInt(idPStr));
            up.setObs(obs);
            
            if (idStr == null || idStr.trim().isEmpty()) {
                rpc.inserir(up);
            } else {
                up.setId(Integer.parseInt(idStr));
                rpc.alterar(up);
            }
            response.sendRedirect("gerenciarUsuarioProduto.jsp");
            return;
        } else if (acao.equals("excluir")) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                UsuarioProduto up = new UsuarioProduto();
                up.setId(Integer.parseInt(idStr));
                rpc.excluir(up);
            }
            response.sendRedirect("gerenciarUsuarioProduto.jsp");
            return;
        }
    }
    
    // 2. Carregar dados para Edição
    UsuarioProduto relacaoEdicao = new UsuarioProduto();
    String idEditar = request.getParameter("editarId");
    if (idEditar != null && !idEditar.isEmpty()) {
        List<Object> todos = rpc.listar(new UsuarioProduto());
        for(Object obj : todos) {
            UsuarioProduto up = (UsuarioProduto) obj;
            if(up.getId() == Integer.parseInt(idEditar)) {
                relacaoEdicao = up;
                break;
            }
        }
    }
    
    // 3. Filtrar / Listar Relações
    String busca = request.getParameter("busca");
    if (busca == null) {
        busca = "";
    }
    
    UsuarioProduto filtro = new UsuarioProduto();
    filtro.setObs(busca.trim());
    List<Object> listaRelacoes = rpc.listar(filtro);
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <%@include file="../../inc/materalizeWeb.inc" %>
        <title>Gestão de Relações</title>
        <style>
            body {
                background-color: #121212;
                color: #e0e0e0;
            }
            nav {
                background-color: #000000 !important;
                border-bottom: 2px solid #d4af37;
            }
            .brand-logo, nav i {
                color: #d4af37 !important;
            }
            .card-panel {
                background-color: #1e1e1e !important;
                border: 1px solid #333;
                border-radius: 10px;
            }
            h2, h4 {
                color: #d4af37 !important;
            }
            /* Ajuste para inputs do Materialize no tema escuro */
            .input-field input[type=text] {
                color: #ffffff !important;
                border-bottom: 1px solid #d4af37 !important;
                box-shadow: 0 1px 0 0 #d4af37 !important;
            }
            .input-field input[type=text]:focus {
                border-bottom: 1px solid #e6c547 !important;
                box-shadow: 0 1px 0 0 #e6c547 !important;
            }
            .input-field label {
                color: #b0b0b0 !important;
            }
            .input-field input[type=text]:focus + label {
                color: #d4af37 !important;
            }
            /* Botões Dourados e Neutros */
            .btn-gold {
                background-color: #d4af37 !important;
                color: #000000 !important;
                font-weight: bold;
            }
            .btn-gold:hover {
                background-color: #e6c547 !important;
            }
            .btn-cancel {
                background-color: #333333 !important;
                color: #ffffff !important;
            }
            .btn-cancel:hover {
                background-color: #444444 !important;
            }
            /* Tabela */
            table.striped > tbody > tr:nth-child(odd) {
                background-color: rgba(255, 255, 255, 0.02);
            }
            table.striped > tbody > tr {
                border-bottom: 1px solid #333;
                color: #e0e0e0;
            }
            th {
                color: #d4af37;
            }
            /* Links de Ação */
            .action-edit {
                color: #d4af37 !important;
                font-weight: 500;
            }
            .action-edit:hover {
                color: #e6c547 !important;
                text-decoration: underline;
            }
            .action-delete {
                color: #ff5252 !important;
                font-weight: 500;
            }
            .action-delete:hover {
                color: #ff8a80 !important;
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        
        <!-- Barra de Navegação -->
        <nav>
            <div class="nav-wrapper container">
                <a href="../../index.html" class="brand-logo"><i class="material-icons">content_cut</i>Sistema Barbearia</a>
                <ul class="right">
                    <li><a href="../../index.html" style="color: #d4af37;">Home</a></li>
                </ul>
            </div>
        </nav>

        <div class="container" style="margin-top: 30px;">
            <h2 class="center-align" style="font-weight: 300;">Gestão de Relações</h2>
            
            <!-- FORMULÁRIO DE CADASTRO / EDIÇÃO -->
            <div class="card-panel">
                <h4><%= (relacaoEdicao.getId() > 0) ? "Editar Relação (ID: " + relacaoEdicao.getId() + ")" : "Nova Relação" %></h4>
                <form action="gerenciarUsuarioProduto.jsp" method="post">
                    <input type="hidden" name="acao" value="salvar">
                    <input type="hidden" name="id" value="<%= (relacaoEdicao.getId() > 0) ? relacaoEdicao.getId() : "" %>">
                    
                    <div class="row">
                        <div class="input-field col s12 m4">
                            <input type="text" name="idU" id="idU" value="<%= (relacaoEdicao.getIdU() > 0) ? relacaoEdicao.getIdU() : "" %>" required>
                            <label class="active" for="idU">ID do Usuário</label>
                        </div>
                        <div class="input-field col s12 m4">
                            <input type="text" name="idP" id="idP" value="<%= (relacaoEdicao.getIdP() > 0) ? relacaoEdicao.getIdP() : "" %>" required>
                            <label class="active" for="idP">ID do Produto</label>
                        </div>
                        <div class="input-field col s12 m4">
                            <input type="text" name="obs" id="obs" value="<%= relacaoEdicao.getObs() != null ? relacaoEdicao.getObs() : "" %>" required>
                            <label class="active" for="obs">Data / Hora / Detalhes</label>
                        </div>
                    </div>
                    <button type="submit" class="waves-effect waves-light btn btn-gold">Salvar</button>
                    <% if(relacaoEdicao.getId() > 0) { %>
                        <a href="gerenciarUsuarioProduto.jsp" class="waves-effect waves-light btn btn-cancel">Cancelar</a>
                    <% } %>
                </form>
            </div>

            <!-- BARRA DE BUSCA -->
            <div class="card-panel">
                <h4>Consultar / Filtrar</h4>
                <form action="gerenciarUsuarioProduto.jsp" method="get">
                    <div class="row" style="margin-bottom: 0;">
                        <div class="input-field col s10">
                            <input type="text" name="busca" id="busca" value="<%= busca %>" placeholder="Digite para filtrar por data ou status...">
                            <label class="active" for="busca">Pesquisar Relação</label>
                        </div>
                        <div class="input-field col s2 center-align">
                            <button type="submit" class="waves-effect waves-light btn btn-gold" style="width: 100%;">Buscar</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- TABELA DE RESULTADOS -->
            <div class="card-panel">
                <h4>Relações Cadastradas</h4>
                <table class="striped responsive-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>ID Usuário</th>
                            <th>ID Produto</th>
                            <th>Data / Detalhes</th>
                            <th class="center-align">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(listaRelacoes != null && !listaRelacoes.isEmpty()) {
                            for(Object obj : listaRelacoes) {
                                UsuarioProduto up = (UsuarioProduto) obj;
                        %>
                        <tr>
                            <td><%= up.getId() %></td>
                            <td><%= up.getIdU() %></td>
                            <td><%= up.getIdP() %></td>
                            <td><%= up.getObs() %></td>
                            <td class="center-align">
                                <a href="gerenciarUsuarioProduto.jsp?editarId=<%= up.getId() %>&busca=<%= busca %>" class="action-edit">Alterar</a> | 
                                <a href="gerenciarUsuarioProduto.jsp?acao=excluir&id=<%= up.getId() %>" class="action-delete" onclick="return confirm('Tem certeza que deseja excluir?');">Excluir</a>
                            </td>
                        </tr>
                        <%  } 
                           } else { %>
                        <tr>
                            <td colspan="5" class="center-align" style="color: #9e9e9e;">Nenhuma relação encontrada.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            
            <div class="center-align" style="margin-top: 30px; margin-bottom: 40px;">
                <a href="../../index.jsp" class="waves-effect waves-light btn btn-cancel">Voltar ao Menu Principal</a>
            </div>
        </div>
    </body>
</html>