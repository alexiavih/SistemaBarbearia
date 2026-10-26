package br.com.fatec.telas.produtos;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InserirProdutoInterface extends JFrame {

    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtPreco;
    private JTextField txtTempo;
    private JButton btnSalvar;
    private JButton btnCancelar;

    // Paleta de Cores (Dark Mode Barbearia)
    private final Color COR_FUNDO = new Color(24, 25, 26);
    private final Color COR_PAINEL = new Color(36, 37, 38);
    private final Color COR_TEXTO = new Color(241, 241, 241);
    private final Color COR_DOURADO = new Color(212, 175, 55);
    private final Color COR_BOTAO = new Color(184, 134, 11);
    private final Color COR_BOTAO_HOVER = new Color(212, 175, 55);
    private final Color COR_CANCELAR = new Color(70, 70, 70);
    private final Color COR_CANCELAR_HOVER = new Color(100, 100, 100);

    public InserirProdutoInterface() {
        setTitle("Cadastrar Novo Serviço");
        setSize(480, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // --- CABEÇALHO ---
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headerPanel.setOpaque(false);

        JLabel lblTitulo = new JLabel("CADASTRAR SERVIÇO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(COR_DOURADO);

        JLabel lblSubtitulo = new JLabel("Preencha os dados do novo serviço da barbearia", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(170, 170, 170));

        headerPanel.add(lblTitulo);
        headerPanel.add(lblSubtitulo);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- FORMULÁRIO ---
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setOpaque(false);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);

        JLabel lblNome = new JLabel("Nome do Serviço:");
        lblNome.setFont(fontLabel);
        lblNome.setForeground(COR_TEXTO);
        txtNome = criarCampoTexto();

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setFont(fontLabel);
        lblDescricao.setForeground(COR_TEXTO);
        txtDescricao = criarCampoTexto();

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setFont(fontLabel);
        lblPreco.setForeground(COR_TEXTO);
        txtPreco = criarCampoTexto();

        JLabel lblTempo = new JLabel("Duração (min):");
        lblTempo.setFont(fontLabel);
        lblTempo.setForeground(COR_TEXTO);
        txtTempo = criarCampoTexto();

        formPanel.add(lblNome);
        formPanel.add(txtNome);
        formPanel.add(lblDescricao);
        formPanel.add(txtDescricao);
        formPanel.add(lblPreco);
        formPanel.add(txtPreco);
        formPanel.add(lblTempo);
        formPanel.add(txtTempo);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- PAINEL DE BOTÕES ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        btnCancelar = criarBotao("Cancelar", COR_CANCELAR, COR_CANCELAR_HOVER, Color.WHITE);
        btnSalvar = criarBotao("Salvar Serviço", COR_BOTAO, COR_BOTAO_HOVER, Color.BLACK);

        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // --- AÇÕES ---
        btnSalvar.addActionListener(e -> salvarServico());
        btnCancelar.addActionListener(e -> dispose());
    }

    private JTextField criarCampoTexto() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBackground(COR_PAINEL);
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private JButton criarBotao(String texto, Color corPadrao, Color corHover, Color corTexto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(corTexto);
        btn.setBackground(corPadrao);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corPadrao);
            }
        });

        return btn;
    }

    private void salvarServico() {
        String nome = txtNome.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String strPreco = txtPreco.getText().trim();
        String strTempo = txtTempo.getText().trim();

        if (nome.isEmpty() || strPreco.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(strPreco.replace(",", "."));
            int tempo = Integer.parseInt(strTempo);

            Produto p = new Produto();
            p.setNome(nome);
            p.setDescricao(descricao);
            p.setPreco(preco);
            p.setDuracao(tempo);

            if (DAOFactory.getProdutoDAO().inserir(p)) {
                JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar serviço no banco.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique os valores numéricos de preço e duração.", "Erro de Digitação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InserirProdutoInterface().setVisible(true));
    }
}