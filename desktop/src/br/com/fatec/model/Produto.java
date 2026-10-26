package br.com.fatec.model;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int duracao; // em minutos

    public Produto() {}

    public Produto(int id, String nome, String descricao, double preco, int duracao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracao = duracao;
    }

    public Produto(String nome, String descricao, double preco, int duracao) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracao = duracao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}