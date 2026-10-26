package br.com.fatec.telas.produtos;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlterarProdutoInterface extends JFrame {

    private JTextField txtId, txtNome, txtDescricao, txtPreco, txtDuracao;
    private JButton btnSalvar, btnCancelar;
    private Produto produtoAtual;

    // Paleta de Cores (Dark Mode Barbearia)
    private final Color COR_FUNDO = new Color(24, 25, 26);
    private final Color COR_PAINEL = new Color(36, 37, 38);
    private final Color COR_TEXTO = new Color(241, 241, 241);
    private final Color COR_DOURADO = new Color(212, 175, 55);
    private final Color COR_BOTAO = new Color(58, 59, 60);
    private final Color COR_BOTAO_HOVER = new Color(184, 134, 11);

    public AlterarProdutoInterface() {
        this(null);
    }

    public AlterarProdutoInterface(Produto produto) {
        this.produtoAtual = produto;

        setTitle("Alterar Serviço / Produto");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- PAINEL SUPERIOR (TÍTULO) ---
        JPanel panelTopo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTopo.setOpaque(false);

        JLabel lblTitulo = new JLabel("ALTERAR SERVIÇO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(COR_DOURADO);

        JLabel lblSubtitulo = new JLabel("Atualize os dados do serviço selecionado", SwingConstants.CENTER);
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

        // Descrição
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Descrição:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtDescricao = criarTextField();
        panelForm.add(txtDescricao, gbc);

        // Preço (R$)
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Preço (R$):"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtPreco = criarTextField();
        panelForm.add(txtPreco, gbc);

        // Duração (min)
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        panelForm.add(criarLabel("Duração (min):"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtDuracao = criarTextField();
        panelForm.add(txtDuracao, gbc);

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

        // Preencher dados se o produto for fornecido
        if (produtoAtual != null) {
            txtId.setText(String.valueOf(produtoAtual.getId()));
            txtNome.setText(produtoAtual.getNome());
            txtDescricao.setText(produtoAtual.getDescricao());
            txtPreco.setText(String.valueOf(produtoAtual.getPreco()));
            txtDuracao.setText(String.valueOf(produtoAtual.getDuracao()));
        }

        // --- EVENTOS ---
        btnSalvar.addActionListener(e -> {
            if (produtoAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum serviço selecionado para alteração!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                produtoAtual.setNome(txtNome.getText().trim());
                produtoAtual.setDescricao(txtDescricao.getText().trim());
                produtoAtual.setPreco(Double.parseDouble(txtPreco.getText().trim().replace(",", ".")));
                produtoAtual.setDuracao(Integer.parseInt(txtDuracao.getText().trim()));

                boolean sucesso = DAOFactory.getProdutoDAO().alterar(produtoAtual);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Serviço atualizado com sucesso!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço e Duração devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
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