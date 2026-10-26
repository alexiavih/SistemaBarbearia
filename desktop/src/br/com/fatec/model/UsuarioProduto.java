package br.com.fatec.model;

public class UsuarioProduto {

    private int id;
    private int idU;
    private int idP;
    private String obs;
    private Usuario usuario;
    private Produto produto;

    public UsuarioProduto() {}

    public UsuarioProduto(int id, int idU, int idP, String obs, Usuario usuario, Produto produto) {
        this.id = id;
        this.idU = idU;
        this.idP = idP;
        this.obs = obs;
        this.usuario = usuario;
        this.produto = produto;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdU() { return idU; }
    public void setIdU(int idU) { this.idU = idU; }

    public int getIdP() { return idP; }
    public void setIdP(int idP) { this.idP = idP; }

    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}