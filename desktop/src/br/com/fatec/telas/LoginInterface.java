package br.com.fatec.telas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginInterface extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;

    // Paleta de Cores (Estilo Barbearia / Dark Elegante)
    private final Color COR_FUNDO = new Color(24, 25, 26);
    private final Color COR_PAINEL = new Color(36, 37, 38);
    private final Color COR_BOTAO = new Color(184, 134, 11); // Dourado / Âmbar
    private final Color COR_BOTAO_HOVER = new Color(212, 175, 55);
    private final Color COR_SAIR = new Color(70, 70, 70);
    private final Color COR_SAIR_HOVER = new Color(100, 100, 100);
    private final Color COR_TEXTO = new Color(241, 241, 241);

    public LoginInterface() {
        setTitle("Acesso ao Sistema - Barbearia");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // --- CABEÇALHO ---
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headerPanel.setOpaque(false);

        JLabel lblTitulo = new JLabel("BARBEARIA & ESTILO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(212, 175, 55)); // Dourado

        JLabel lblSubtitulo = new JLabel("Informe as suas credenciais para aceder", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(170, 170, 170));

        headerPanel.add(lblTitulo);
        headerPanel.add(lblSubtitulo);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- FORMULÁRIO DE LOGIN ---
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 8));
        formPanel.setOpaque(false);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel lblUsuario = new JLabel("Utilizador:");
        lblUsuario.setFont(fontLabel);
        lblUsuario.setForeground(COR_TEXTO);

        txtUsuario = new JTextField();
        txtUsuario.setFont(fontInput);
        txtUsuario.setBackground(COR_PAINEL);
        txtUsuario.setForeground(Color.WHITE);
        txtUsuario.setCaretColor(Color.WHITE);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JLabel lblSenha = new JLabel("Palavra-passe:");
        lblSenha.setFont(fontLabel);
        lblSenha.setForeground(COR_TEXTO);

        txtSenha = new JPasswordField();
        txtSenha.setFont(fontInput);
        txtSenha.setBackground(COR_PAINEL);
        txtSenha.setForeground(Color.WHITE);
        txtSenha.setCaretColor(Color.WHITE);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        formPanel.add(lblUsuario);
        formPanel.add(txtUsuario);
        formPanel.add(lblSenha);
        formPanel.add(txtSenha);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- PAINEL DE BOTÕES ---
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(0, 40));

        btnSair = criarBotao("Sair", COR_SAIR, COR_SAIR_HOVER, Color.WHITE);
        btnEntrar = criarBotao("Entrar", COR_BOTAO, COR_BOTAO_HOVER, Color.BLACK);

        buttonPanel.add(btnSair);
        buttonPanel.add(btnEntrar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // --- EVENTOS ---
        btnEntrar.addActionListener(e -> realizarLogin());
        txtSenha.addActionListener(e -> realizarLogin()); // Permite dar Enter na senha para logar
        btnSair.addActionListener(e -> System.exit(0));
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());

        // Validação simples de login (pode adaptar para o seu banco se necessário)
        if (usuario.equalsIgnoreCase("admin") && senha.equals("1234")) {
            new MenuInterface().setVisible(true);
            dispose();
        } else if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Utilizador ou palavra-passe incorretos!", "Erro de Acesso", JOptionPane.ERROR_MESSAGE);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginInterface().setVisible(true));
    }
}