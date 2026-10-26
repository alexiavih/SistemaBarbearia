package br.com.fatec.telas.relacoes;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Produto;
import br.com.fatec.model.Relacao;
import br.com.fatec.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class InserirRelacaoInterface extends JFrame {

    private JComboBox<Usuario> cbUsuario;
    private JComboBox<Produto> cbProduto;
    private JTextField txtDataHora;
    private JComboBox<String> cbStatus;
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

    public InserirRelacaoInterface() {
        setTitle("Novo Agendamento");
        setSize(480, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(new EmptyBorder(25, 30, 25, 30));

        // --- CABEÇALHO ---
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        headerPanel.setOpaque(false);

        JLabel lblTitulo = new JLabel("AGENDAR SERVIÇO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(COR_DOURADO);

        JLabel lblSubtitulo = new JLabel("Vincule o cliente, serviço, data e status", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(170, 170, 170));

        headerPanel.add(lblTitulo);
        headerPanel.add(lblSubtitulo);
        painelPrincipal.add(headerPanel, BorderLayout.NORTH);

        // --- FORMULÁRIO ---
        JPanel painelForm = new JPanel(new GridLayout(4, 2, 10, 15));
        painelForm.setOpaque(false);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);

        JLabel lblUsuario = new JLabel("Cliente:");
        lblUsuario.setFont(fontLabel);
        lblUsuario.setForeground(COR_TEXTO);
        cbUsuario = new JComboBox<>();
        estilizarCombo(cbUsuario);

        JLabel lblProduto = new JLabel("Serviço:");
        lblProduto.setFont(fontLabel);
        lblProduto.setForeground(COR_TEXTO);
        cbProduto = new JComboBox<>();
        estilizarCombo(cbProduto);

        JLabel lblData = new JLabel("Data/Hora:");
        lblData.setFont(fontLabel);
        lblData.setForeground(COR_TEXTO);
        txtDataHora = criarCampoTexto("22/09/2026 14:00");

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(fontLabel);
        lblStatus.setForeground(COR_TEXTO);
        cbStatus = new JComboBox<>(new String[]{"Agendado", "Concluído", "Cancelado"});
        estilizarCombo(cbStatus);

        painelForm.add(lblUsuario);
        painelForm.add(cbUsuario);
        painelForm.add(lblProduto);
        painelForm.add(cbProduto);
        painelForm.add(lblData);
        painelForm.add(txtDataHora);
        painelForm.add(lblStatus);
        painelForm.add(cbStatus);

        painelPrincipal.add(painelForm, BorderLayout.CENTER);

        carregarCombos();

        // --- PAINEL DE BOTÕES ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setOpaque(false);

        btnCancelar = criarBotao("Cancelar", COR_CANCELAR, COR_CANCELAR_HOVER, Color.WHITE);
        btnSalvar = criarBotao("Confirmar Agendamento", COR_BOTAO, COR_BOTAO_HOVER, Color.BLACK);

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        // --- AÇÕES ---
        btnSalvar.addActionListener(e -> {
            Usuario u = (Usuario) cbUsuario.getSelectedItem();
            Produto p = (Produto) cbProduto.getSelectedItem();
            String data = txtDataHora.getText().trim();
            String status = (String) cbStatus.getSelectedItem();

            if (u == null || p == null || data.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione o Cliente, Serviço e insira a Data!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Relacao r = new Relacao();
            r.setUsuarioId(u.getId());
            r.setProdutoId(p.getId());
            r.setDataHora(data);
            r.setStatus(status);

            DAOFactory.getRelacaoDAO().inserir(r);

            JOptionPane.showMessageDialog(this, "Agendamento salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private <T> void estilizarCombo(JComboBox<T> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(COR_PAINEL);
        combo.setForeground(Color.WHITE);
    }

    private JTextField criarCampoTexto(String textoPadrao) {
        JTextField tf = new JTextField(textoPadrao);
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

    private void carregarCombos() {
        List<Usuario> listaU = DAOFactory.getUsuarioDAO().listarTodos();
        for (Usuario u : listaU) {
            cbUsuario.addItem(u);
        }

        List<Produto> listaP = DAOFactory.getProdutoDAO().listarTodos();
        for (Produto p : listaP) {
            cbProduto.addItem(p);
        }
    }
}