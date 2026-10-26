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

public class AlterarRelacaoInterface extends JFrame {

    private JTextField txtId, txtDataHora;
    private JComboBox<Usuario> cbUsuario;
    private JComboBox<Produto> cbProduto;
    private JComboBox<String> cbStatus;
    private JButton btnSalvar, btnCancelar;
    private Relacao relacaoAtual;

    // Paleta de Cores (Dark Mode Barbearia)
    private final Color COR_FUNDO = new Color(24, 25, 26);
    private final Color COR_PAINEL = new Color(36, 37, 38);
    private final Color COR_TEXTO = new Color(241, 241, 241);
    private final Color COR_DOURADO = new Color(212, 175, 55);
    private final Color COR_BOTAO = new Color(58, 59, 60);
    private final Color COR_BOTAO_HOVER = new Color(184, 134, 11);

    public AlterarRelacaoInterface() {
        this(null);
    }

    public AlterarRelacaoInterface(Relacao relacao) {
        this.relacaoAtual = relacao;

        setTitle("Alterar Agendamento");
        setSize(480, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- PAINEL SUPERIOR (TÍTULO) ---
        JPanel panelTopo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTopo.setOpaque(false);

        JLabel lblTitulo = new JLabel("ALTERAR AGENDAMENTO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(COR_DOURADO);

        JLabel lblSubtitulo = new JLabel("Atualize os dados do agendamento selecionado", SwingConstants.CENTER);
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

        // Cliente
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Cliente:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        cbUsuario = new JComboBox<>();
        estilizarComboBox(cbUsuario);
        panelForm.add(cbUsuario, gbc);

        // Serviço
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Serviço:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        cbProduto = new JComboBox<>();
        estilizarComboBox(cbProduto);
        panelForm.add(cbProduto, gbc);

        // Data e Hora
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Data e Hora:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtDataHora = criarTextField();
        panelForm.add(txtDataHora, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Status:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        cbStatus = new JComboBox<>(new String[]{"Agendado", "Concluído", "Cancelado"});
        estilizarComboBox(cbStatus);
        panelForm.add(cbStatus, gbc);

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

        // Carrega opções e preenche dados
        carregarCombos();

        if (relacaoAtual != null) {
            txtId.setText(String.valueOf(relacaoAtual.getId()));
            txtDataHora.setText(relacaoAtual.getDataHora());
            cbStatus.setSelectedItem(relacaoAtual.getStatus());
            selecionarNosCombos();
        }

        // --- EVENTOS ---
        btnSalvar.addActionListener(e -> {
            if (relacaoAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum agendamento selecionado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario uSel = (Usuario) cbUsuario.getSelectedItem();
            Produto pSel = (Produto) cbProduto.getSelectedItem();

            if (uSel == null || pSel == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente e um serviço válidos!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (txtDataHora.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe a Data e Hora!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            relacaoAtual.setUsuarioId(uSel.getId());
            relacaoAtual.setProdutoId(pSel.getId());
            relacaoAtual.setDataHora(txtDataHora.getText().trim());
            relacaoAtual.setStatus((String) cbStatus.getSelectedItem());

            boolean sucesso = DAOFactory.getRelacaoDAO().alterar(relacaoAtual);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Agendamento atualizado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar agendamento.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private void carregarCombos() {
        cbUsuario.removeAllItems();
        List<Usuario> usuarios = DAOFactory.getUsuarioDAO().listarTodos();
        for (Usuario u : usuarios) {
            cbUsuario.addItem(u);
        }

        cbProduto.removeAllItems();
        List<Produto> produtos = DAOFactory.getProdutoDAO().listarTodos();
        for (Produto p : produtos) {
            cbProduto.addItem(p);
        }
    }

    private void selecionarNosCombos() {
        for (int i = 0; i < cbUsuario.getItemCount(); i++) {
            if (cbUsuario.getItemAt(i).getId() == relacaoAtual.getUsuarioId()) {
                cbUsuario.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < cbProduto.getItemCount(); i++) {
            if (cbProduto.getItemAt(i).getId() == relacaoAtual.getProdutoId()) {
                cbProduto.setSelectedIndex(i);
                break;
            }
        }
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

    private <T> void estilizarComboBox(JComboBox<T> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(COR_PAINEL);
        combo.setForeground(Color.WHITE);
        combo.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70), 1));
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