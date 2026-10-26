package br.com.fatec.model;

public class Relacao {
    private int id;
    private int usuarioId;
    private int produtoId;
    private String dataHora;
    private String status;

    public Relacao() {}

    public Relacao(int id, int usuarioId, int produtoId, String dataHora, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.produtoId = produtoId;
        this.dataHora = dataHora;
        this.status = status;
    }

    public Relacao(int usuarioId, int produtoId, String dataHora, String status) {
        this.usuarioId = usuarioId;
        this.produtoId = produtoId;
        this.dataHora = dataHora;
        this.status = status;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}