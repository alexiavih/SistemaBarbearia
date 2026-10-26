package br.com.fatec.telas;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlterarUsuarioInterface extends JFrame {

    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEmail;
    private JButton btnSalvar, btnCancelar;
    private Usuario usuarioAtual;

    // Paleta de Cores (Dark Mode Barbearia)
    private final Color COR_FUNDO = new Color(24, 25, 26);
    private final Color COR_PAINEL = new Color(36, 37, 38);
    private final Color COR_TEXTO = new Color(241, 241, 241);
    private final Color COR_DOURADO = new Color(212, 175, 55);
    private final Color COR_BOTAO = new Color(58, 59, 60);
    private final Color COR_BOTAO_HOVER = new Color(184, 134, 11);

    public AlterarUsuarioInterface() {
        this(null);
    }

    public AlterarUsuarioInterface(Usuario usuario) {
        this.usuarioAtual = usuario;

        setTitle("Alterar Cliente");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- PAINEL SUPERIOR (TÍTULO) ---
        JPanel panelTopo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTopo.setOpaque(false);

        JLabel lblTitulo = new JLabel("ALTERAR CLIENTE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(COR_DOURADO);

        JLabel lblSubtitulo = new JLabel("Atualize os dados do cliente selecionado", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(Color.GRAY);

        panelTopo.add(lblTitulo);
        panelTopo.add(lblSubtitulo);
        mainPanel.add(panelTopo, BorderLayout.NORTH);

        // --- PAINEL CENTRAL (FORMULÁRIO) ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID (Bloqueado)
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panelForm.add(criarLabel("ID:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtId = criarTextField();
        txtId.setEditable(false);
        txtId.setForeground(Color.LIGHT_GRAY);
        panelForm.add(txtId, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Nome:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtNome = criarTextField();
        panelForm.add(txtNome, gbc);

        // CPF
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panelForm.add(criarLabel("CPF:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtCpf = criarTextField();
        panelForm.add(txtCpf, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Telefone:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtTelefone = criarTextField();
        panelForm.add(txtTelefone, gbc);

        // E-mail
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        panelForm.add(criarLabel("E-mail:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtEmail = criarTextField();
        panelForm.add(txtEmail, gbc);

        mainPanel.add(panelForm, BorderLayout.CENTER);

        // --- PAINEL INFERIOR (BOTÕES) ---
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelBotoes.setOpaque(false);

        btnCancelar = criarBotao("Cancelar", COR_BOTAO, COR_BOTAO_HOVER, Color.WHITE);
        btnSalvar = criarBotao("Atualizar", COR_DOURADO, COR_BOTAO_HOVER, Color.BLACK);

        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnSalvar);

        mainPanel.add(panelBotoes, BorderLayout.SOUTH);

        add(mainPanel);

        // Preencher dados se o utilizador for fornecido
        if (usuarioAtual != null) {
            txtId.setText(String.valueOf(usuarioAtual.getId()));
            txtNome.setText(usuarioAtual.getNome());
            txtCpf.setText(usuarioAtual.getCpf());
            txtTelefone.setText(usuarioAtual.getTelefone());
            txtEmail.setText(usuarioAtual.getEmail());
        }

        // --- EVENTOS ---
        btnSalvar.addActionListener(e -> {
            if (usuarioAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente selecionado para alteração!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Nome e CPF)!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            usuarioAtual.setNome(txtNome.getText().trim());
            usuarioAtual.setCpf(txtCpf.getText().trim());
            usuarioAtual.setTelefone(txtTelefone.getText().trim());
            usuarioAtual.setEmail(txtEmail.getText().trim());

            boolean sucesso = DAOFactory.getUsuarioDAO().alterar(usuarioAtual);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(COR_TEXTO);
        return label;
    }

    private JTextField criarTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBackground(COR_PAINEL);
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return tf;
    }

    private JButton criarBotao(String texto, Color corPadrao, Color corHover, Color corTexto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(corTexto);
        btn.setBackground(corPadrao);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
                if (corPadrao.equals(COR_DOURADO)) {
                    btn.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corPadrao);
                btn.setForeground(corTexto);
            }
        });

        return btn;
    }
}