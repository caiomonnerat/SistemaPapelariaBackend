package com.sistemapapelaria.api.to.output;

public class ProdutoOutputTO {

    private Integer id;
    private String nome;
    private Double preco;
    private Integer quantidade;
    private String marca;
    private String tipo;
    private CategoriaOutputTO categoria;
    private FornecedorOutputTO fornecedor;

    public ProdutoOutputTO() {}

    public ProdutoOutputTO(Integer id, String nome, Double preco, Integer quantidade, String marca, String tipo, CategoriaOutputTO categoria, FornecedorOutputTO fornecedor) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.marca = marca;
        this.tipo = tipo;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CategoriaOutputTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaOutputTO categoria) {
        this.categoria = categoria;
    }

    public FornecedorOutputTO getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorOutputTO fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "ProdutoOutputTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", marca='" + marca + '\'' +
                ", tipo='" + tipo + '\'' +
                ", categoria=" + categoria +
                ", fornecedor=" + fornecedor +
                '}';
    }
}
