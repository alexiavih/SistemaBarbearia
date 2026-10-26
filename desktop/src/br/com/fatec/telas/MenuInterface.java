package br.com.fatec.telas;

import br.com.fatec.telas.produtos.ConsultarProdutoInterface;
import br.com.fatec.telas.produtos.InserirProdutoInterface;
import br.com.fatec.telas.relacoes.ConsultarRelacaoInterface;
import br.com.fatec.telas.relacoes.InserirRelacaoInterface;
import br.com.fatec.telas.usuarios.InserirUsuarioInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuInterface extends JFrame {

    // Paleta de Cores (Estilo Barbearia / Dark Elegante)
    private final Color COR_FUNDO = new Color(24, 25, 26);
    private final Color COR_PAINEL = new Color(36, 37, 38);
    private final Color COR_BOTAO = new Color(58, 59, 60);
    private final Color COR_BOTAO_HOVER = new Color(184, 134, 11); // Dourado / Âmbar
    private final Color COR_TEXTO = new Color(241, 241, 241);
    private final Color COR_SAIR = new Color(178, 34, 34); // Vermelho discreto
    private final Color COR_SAIR_HOVER = new Color(220, 20, 60);

    public MenuInterface() {
        setTitle("Sistema de Gestão de Barbearia - Menu Principal");
        setSize(650, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Painel Principal com fundo escuro
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(new EmptyBorder(20, 25, 25, 25));

        // --- CABEÇALHO ---
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setOpaque(false);

        JLabel lblTitulo = new JLabel("BARBEARIA & ESTILO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(212, 175, 55)); // Texto Dourado

        JLabel lblSubtitulo = new JLabel("Painel Geral de Atendimento e Cadastro", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(170, 170, 170));

        headerPanel.add(lblTitulo);
        headerPanel.add(lblSubtitulo);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- PAINEL DE BOTÕES ---
        JPanel gridPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        gridPanel.setOpaque(false);

        JButton btnCadCliente = criarBotaoEstilizado("Cadastrar Cliente", COR_BOTAO, COR_BOTAO_HOVER);
        JButton btnConCliente = criarBotaoEstilizado("Consultar Clientes", COR_BOTAO, COR_BOTAO_HOVER);
        JButton btnCadServico = criarBotaoEstilizado("Cadastrar Serviço", COR_BOTAO, COR_BOTAO_HOVER);
        JButton btnConServico = criarBotaoEstilizado("Consultar Serviços", COR_BOTAO, COR_BOTAO_HOVER);
        JButton btnNovoAgendamento = criarBotaoEstilizado("Novo Agendamento", COR_BOTAO, COR_BOTAO_HOVER);
        JButton btnConAgendamentos = criarBotaoEstilizado("Consultar Agendamentos", COR_BOTAO, COR_BOTAO_HOVER);
        
        JButton btnEspacoVazio = new JButton(); 
        btnEspacoVazio.setVisible(false); // Espaço em branco para alinhar o botão de Sair
        
        JButton btnSair = criarBotaoEstilizado("Sair / Logout", COR_SAIR, COR_SAIR_HOVER);

        // Ações dos Botões
        btnCadCliente.addActionListener(e -> new InserirUsuarioInterface().setVisible(true));
        btnConCliente.addActionListener(e -> new ConsultarUsuarioInterface().setVisible(true));
        btnCadServico.addActionListener(e -> new InserirProdutoInterface().setVisible(true));
        btnConServico.addActionListener(e -> new ConsultarProdutoInterface().setVisible(true));
        btnNovoAgendamento.addActionListener(e -> new InserirRelacaoInterface().setVisible(true));
        btnConAgendamentos.addActionListener(e -> new ConsultarRelacaoInterface().setVisible(true));
        btnSair.addActionListener(e -> {
            new LoginInterface().setVisible(true);
            dispose();
        });

        // Adicionando os botões na grelha
        gridPanel.add(btnCadCliente);
        gridPanel.add(btnConCliente);
        gridPanel.add(btnCadServico);
        gridPanel.add(btnConServico);
        gridPanel.add(btnNovoAgendamento);
        gridPanel.add(btnConAgendamentos);
        gridPanel.add(btnEspacoVazio);
        gridPanel.add(btnSair);

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Método auxiliar para criar e estilizar os botões com efeito Hover
    private JButton criarBotaoEstilizado(String texto, Color corPadrao, Color corHover) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(COR_TEXTO);
        btn.setBackground(corPadrao);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito Hover (muda de cor ao passar o mouse)
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corPadrao);
                btn.setForeground(COR_TEXTO);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInterface().setVisible(true));
    }
}