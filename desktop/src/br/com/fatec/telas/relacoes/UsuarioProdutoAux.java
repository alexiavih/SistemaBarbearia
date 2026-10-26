package br.com.fatec.telas.relacoes;

public class UsuarioProdutoAux {
    private int idRelacao;
    private String nomeUsuario;
    private String nomeProduto;
    private String dataHora;
    private String status;

    public UsuarioProdutoAux(int idRelacao, String nomeUsuario, String nomeProduto, String dataHora, String status) {
        this.idRelacao = idRelacao;
        this.nomeUsuario = nomeUsuario;
        this.nomeProduto = nomeProduto;
        this.dataHora = dataHora;
        this.status = status;
    }

    public int getIdRelacao() { return idRelacao; }
    public String getNomeUsuario() { return nomeUsuario; }
    public String getNomeProduto() { return nomeProduto; }
    public String getDataHora() { return dataHora; }
    public String getStatus() { return status; }
}