<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <title>Sistema Web Barbearia</title>
        <!-- Importação do Materialize CSS e Google Icons -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
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
            .card {
                background-color: #1e1e1e !important;
                border: 1px solid #333;
                border-radius: 10px;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 20px rgba(212, 175, 55, 0.2) !important;
            }
            .card-title {
                color: #d4af37 !important;
            }
            .card-content p {
                color: #b0b0b0 !important;
            }
            .btn-gold {
                background-color: #d4af37 !important;
                color: #000000 !important;
                font-weight: bold;
                width: 100%;
                border-radius: 4px;
            }
            .btn-gold:hover {
                background-color: #e6c547 !important;
            }
            .card-action {
                background-color: #181818 !important;
                border-top: 1px solid #333 !important;
                border-bottom-left-radius: 10px;
                border-bottom-right-radius: 10px;
            }
        </style>
    </head>
    <body>

        <!-- Barra de Navegação Superior -->
        <nav>
            <div class="nav-wrapper container">
                <a href="#" class="brand-logo"><i class="material-icons">content_cut</i>Sistema Barbearia</a>
            </div>
        </nav>

        <!-- Conteúdo Principal -->
        <div class="container" style="margin-top: 50px;">
            <div class="center-align" style="margin-bottom: 50px;">
                <h3 style="color: #d4af37; font-weight: 300;">Painel de Gestão</h3>
                <p class="flow-text" style="color: #9e9e9e;">Selecione abaixo o módulo que deseja gerenciar</p>
            </div>

            <div class="row">
                <!-- Módulo de Produtos -->
                <div class="col s12 m4">
                    <div class="card hoverable">
                        <div class="card-content center-align">
                            <i class="material-icons large" style="color: #d4af37;">store</i>
                            <span class="card-title" style="font-weight: bold; margin-top: 15px;">Produtos</span>
                            <p>Gerencie o catálogo de serviços, preços, durações e descrições dos produtos.</p>
                        </div>
                        <div class="card-action center-align">
                            <a href="jsp/produto/gerenciarProduto.jsp" class="waves-effect waves-light btn btn-gold">Gerenciar Produtos</a>
                        </div>
                    </div>
                </div>

                <!-- Módulo de Usuários -->
                <div class="col s12 m4">
                    <div class="card hoverable">
                        <div class="card-content center-align">
                            <i class="material-icons large" style="color: #d4af37;">people</i>
                            <span class="card-title" style="font-weight: bold; margin-top: 15px;">Usuários</span>
                            <p>Controle o cadastro de clientes e utilizadores, dados de contacto e CPFs.</p>
                        </div>
                        <div class="card-action center-align">
                            <a href="jsp/usuario/gerenciarUsuario.jsp" class="waves-effect waves-light btn btn-gold">Gerenciar Usuários</a>
                        </div>
                    </div>
                </div>

                <!-- Módulo de Relações -->
                <div class="col s12 m4">
                    <div class="card hoverable">
                        <div class="card-content center-align">
                            <i class="material-icons large" style="color: #d4af37;">event_note</i>
                            <span class="card-title" style="font-weight: bold; margin-top: 15px;">Relações</span>
                            <p>Gerencie os agendamentos e os vínculos entre utilizadores e produtos.</p>
                        </div>
                        <div class="card-action center-align">
                            <a href="jsp/usuarioProduto/gerenciarUsuarioProduto.jsp" class="waves-effect waves-light btn btn-gold">Gerenciar Relação</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Importação do JavaScript do Materialize -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    </body>
</html>