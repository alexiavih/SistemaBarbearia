<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.fatec.model.Usuario"%>
<%@page import="br.com.fatec.controller.ControllerUsuario"%>
<%@page import="java.util.List"%>

<%
    ControllerUsuario uc = new ControllerUsuario();
    
    // 1. Processar Ações (Salvar, Excluir, Alterar)
    String acao = request.getParameter("acao");
    if (acao != null) {
        if (acao.equals("salvar")) {
            String idStr = request.getParameter("id");
            String nome = request.getParameter("nome");
            String cpf = request.getParameter("cpf");
            String telefone = request.getParameter("telefone");
            String email = request.getParameter("email");
            
            Usuario u = new Usuario();
            u.setNome(nome);
            u.setCpf(cpf);
            u.setTelefone(telefone);
            u.setEmail(email);
            
            if (idStr == null || idStr.isEmpty()) {
                uc.inserir(u);
            } else {
                u.setId(Integer.parseInt(idStr));
                uc.alterar(u);
            }
            response.sendRedirect("gerenciarUsuario.jsp");
            return;
        } else if (acao.equals("excluir")) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                Usuario u = new Usuario();
                u.setId(Integer.parseInt(idStr));
                uc.excluir(u);
            }
            response.sendRedirect("gerenciarUsuario.jsp");
            return;
        }
    }
    
    // 2. Carregar dados para Edição
    Usuario usuarioEdicao = new Usuario();
    String idEditar = request.getParameter("editarId");
    if (idEditar != null && !idEditar.isEmpty()) {
        List<Object> todos = uc.listar(new Usuario());
        for(Object obj : todos) {
            Usuario u = (Usuario) obj;
            if(u.getId() == Integer.parseInt(idEditar)) {
                usuarioEdicao = u;
                break;
            }
        }
    }
    
    // 3. Filtrar / Listar Usuários (Tratamento robusto para capturar a busca)
    String busca = request.getParameter("busca");
    if (busca == null || busca.trim().isEmpty()) {
        busca = request.getParameter("nome");
    }
    if (busca == null) {
        busca = "";
    }
    
    Usuario filtro = new Usuario();
    filtro.setNome(busca.trim());
    List<Object> listaUsuarios = uc.listar(filtro);
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <%@include file="../../inc/materalizeWeb.inc" %>
        <title>Gestão de Usuários</title>
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
            .input-field input[type=text], .input-field input[type=email] {
                color: #ffffff !important;
                border-bottom: 1px solid #d4af37 !important;
                box-shadow: 0 1px 0 0 #d4af37 !important;
            }
            .input-field input[type=text]:focus, .input-field input[type=email]:focus {
                border-bottom: 1px solid #e6c547 !important;
                box-shadow: 0 1px 0 0 #e6c547 !important;
            }
            .input-field label {
                color: #b0b0b0 !important;
            }
            .input-field input[type=text]:focus + label, .input-field input[type=email]:focus + label {
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
                    <li><a href="../../index.jsp" style="color: #d4af37;">Home</a></li>
                </ul>
            </div>
        </nav>

        <div class="container" style="margin-top: 30px;">
            <h2 class="center-align" style="font-weight: 300;">Gestão de Usuários</h2>
            
            <!-- FORMULÁRIO DE CADASTRO / EDIÇÃO -->
            <div class="card-panel">
                <h4><%= (usuarioEdicao.getId() > 0) ? "Editar Usuário (ID: " + usuarioEdicao.getId() + ")" : "Novo Usuário" %></h4>
                <form action="gerenciarUsuario.jsp" method="post">
                    <input type="hidden" name="acao" value="salvar">
                    <input type="hidden" name="id" value="<%= (usuarioEdicao.getId() > 0) ? usuarioEdicao.getId() : "" %>">
                    
                    <div class="row">
                        <div class="input-field col s12 m6">
                            <input type="text" name="nome" id="nome" value="<%= usuarioEdicao.getNome() != null ? usuarioEdicao.getNome() : "" %>" required>
                            <label class="active" for="nome">Nome</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <input type="text" name="cpf" id="cpf" value="<%= usuarioEdicao.getCpf() != null ? usuarioEdicao.getCpf() : "" %>" required>
                            <label class="active" for="cpf">CPF</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 m6">
                            <input type="text" name="telefone" id="telefone" value="<%= usuarioEdicao.getTelefone() != null ? usuarioEdicao.getTelefone() : "" %>">
                            <label class="active" for="telefone">Telefone</label>
                        </div>
                        <div class="input-field col s12 m6">
                            <input type="email" name="email" id="email" value="<%= usuarioEdicao.getEmail() != null ? usuarioEdicao.getEmail() : "" %>">
                            <label class="active" for="email">E-mail</label>
                        </div>
                    </div>
                    <button type="submit" class="waves-effect waves-light btn btn-gold">Salvar</button>
                    <% if(usuarioEdicao.getId() > 0) { %>
                        <a href="gerenciarUsuario.jsp" class="waves-effect waves-light btn btn-cancel">Cancelar</a>
                    <% } %>
                </form>
            </div>

            <!-- BARRA DE BUSCA -->
            <div class="card-panel">
                <h4>Consultar / Filtrar</h4>
                <form action="gerenciarUsuario.jsp" method="get">
                    <div class="row" style="margin-bottom: 0;">
                        <div class="input-field col s10">
                            <input type="text" name="busca" id="busca" value="<%= busca %>" placeholder="Digite o nome para buscar...">
                            <label class="active" for="busca">Pesquisar por Nome</label>
                        </div>
                        <div class="input-field col s2 center-align">
                            <button type="submit" class="waves-effect waves-light btn btn-gold" style="width: 100%;">Buscar</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- TABELA DE RESULTADOS -->
            <div class="card-panel">
                <h4>Usuários Cadastrados</h4>
                <table class="striped responsive-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>CPF</th>
                            <th>Telefone</th>
                            <th>E-mail</th>
                            <th class="center-align">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(listaUsuarios != null && !listaUsuarios.isEmpty()) {
                            for(Object obj : listaUsuarios) {
                                Usuario u = (Usuario) obj;
                        %>
                        <tr>
                            <td><%= u.getId() %></td>
                            <td><%= u.getNome() %></td>
                            <td><%= u.getCpf() %></td>
                            <td><%= u.getTelefone() %></td>
                            <td><%= u.getEmail() %></td>
                            <td class="center-align">
                                <a href="gerenciarUsuario.jsp?editarId=<%= u.getId() %>&busca=<%= busca %>" class="action-edit">Alterar</a> | 
                                <a href="gerenciarUsuario.jsp?acao=excluir&id=<%= u.getId() %>" class="action-delete" onclick="return confirm('Tem certeza que deseja excluir?');">Excluir</a>
                            </td>
                        </tr>
                        <%  } 
                           } else { %>
                        <tr>
                            <td colspan="6" class="center-align" style="color: #9e9e9e;">Nenhum usuário encontrado.</td>
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