package br.com.fatec.telas;

import br.com.fatec.dao.DAOFactory;
import br.com.fatec.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ConsultarUsuarioInterface extends JFrame {

    private JTable tabela;
    private DefaultTableModel model;
    private JTextField txtBusca;
    private JButton btnBuscar, btnAtualizar, btnAlterar, btnExcluir;

    // Paleta de Cores (Dark Mode Barbearia)
    private final Color COR_FUNDO = new Color(24, 25, 26);
    private final Color COR_PAINEL = new Color(36, 37, 38);
    private final Color COR_TEXTO = new Color(241, 241, 241);
    private final Color COR_DOURADO = new Color(212, 175, 55);
    private final Color COR_BOTAO = new Color(58, 59, 60);
    private final Color COR_BOTAO_HOVER = new Color(184, 134, 11);
    private final Color COR_EXCLUIR = new Color(178, 34, 34);
    private final Color COR_EXCLUIR_HOVER = new Color(220, 20, 60);

    public ConsultarUsuarioInterface() {
        setTitle("Consultar / Gerenciar Clientes");
        setSize(720, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- PAINEL SUPERIOR (BUSCA) ---
        JPanel panelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelTopo.setOpaque(false);

        JLabel lblBusca = new JLabel("Buscar por Nome/CPF:");
        lblBusca.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBusca.setForeground(COR_TEXTO);

        txtBusca = new JTextField(20);
        txtBusca.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBusca.setBackground(COR_PAINEL);
        txtBusca.setForeground(Color.WHITE);
        txtBusca.setCaretColor(Color.WHITE);
        txtBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        btnBuscar = criarBotao("Buscar", COR_BOTAO, COR_BOTAO_HOVER, Color.WHITE);
        btnAtualizar = criarBotao("Listar Todos", COR_BOTAO, COR_BOTAO_HOVER, Color.WHITE);

        panelTopo.add(lblBusca);
        panelTopo.add(txtBusca);
        panelTopo.add(btnBuscar);
        panelTopo.add(btnAtualizar);

        mainPanel.add(panelTopo, BorderLayout.NORTH);

        // --- TABELA CENTRAL ---
        model = new DefaultTableModel(new String[]{"ID", "Nome", "CPF", "Telefone", "E-mail"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(model);
        tabela.setRowHeight(30);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setBackground(COR_PAINEL);
        tabela.setForeground(COR_TEXTO);
        tabela.setSelectionBackground(COR_BOTAO_HOVER);
        tabela.setSelectionForeground(Color.WHITE);
        tabela.setGridColor(new Color(50, 50, 50));

        // Cabeçalho Estilizado
        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(18, 18, 18));
        header.setForeground(COR_DOURADO);
        header.setPreferredSize(new Dimension(0, 35));

        // Centralização das colunas ID, CPF e Telefone
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabela.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabela.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.getViewport().setBackground(COR_PAINEL);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- PAINEL INFERIOR (AÇÕES) ---
        JPanel panelBaixo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelBaixo.setOpaque(false);

        btnAlterar = criarBotao("Alterar Selecionado", COR_BOTAO, COR_BOTAO_HOVER, Color.WHITE);
        btnExcluir = criarBotao("Excluir Selecionado", COR_EXCLUIR, COR_EXCLUIR_HOVER, Color.WHITE);

        panelBaixo.add(btnAlterar);
        panelBaixo.add(btnExcluir);

        mainPanel.add(panelBaixo, BorderLayout.SOUTH);

        add(mainPanel);

        // --- EVENTOS ---
        btnAtualizar.addActionListener(e -> carregarTabela(""));
        btnBuscar.addActionListener(e -> carregarTabela(txtBusca.getText().trim()));

        btnAlterar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = Integer.parseInt(model.getValueAt(linha, 0).toString());
                Usuario u = DAOFactory.getUsuarioDAO().buscarPorId(id);
                if (u != null) {
                    new AlterarUsuarioInterface(u).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = Integer.parseInt(model.getValueAt(linha, 0).toString());
                int resp = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este cliente?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    if (DAOFactory.getUsuarioDAO().excluir(id)) {
                        JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                        carregarTabela("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        carregarTabela("");
    }

    private void carregarTabela(String filtro) {
        model.setRowCount(0);
        List<Usuario> lista = DAOFactory.getUsuarioDAO().listarTodos();
        for (Usuario u : lista) {
            String busca = filtro.toLowerCase();
            boolean atendeFiltro = filtro.isEmpty() || 
                                   (u.getNome() != null && u.getNome().toLowerCase().contains(busca)) || 
                                   (u.getCpf() != null && u.getCpf().contains(filtro));
            
            if (atendeFiltro) {
                model.addRow(new Object[]{
                        u.getId(),
                        u.getNome(),
                        u.getCpf(),
                        u.getTelefone(),
                        u.getEmail()
                });
            }
        }
    }

    private JButton criarBotao(String texto, Color corPadrao, Color corHover, Color corTexto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(corTexto);
        btn.setBackground(corPadrao);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70), 1),
                BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));
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
}