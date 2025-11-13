package com.sistemapapelaria.api.to.input;

public class ProdutoInputTO {

    private String nome;
    private Double preco;
    private Integer quantidade;
    private String marca;
    private String tipo;
    private Integer categoriaId;
    private Integer fornecedorId;

    public ProdutoInputTO() {}

    public ProdutoInputTO(String nome, Double preco, Integer quantidade, String marca, String tipo, Integer categoriaId, Integer fornecedorId) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.marca = marca;
        this.tipo = tipo;
        this.categoriaId = categoriaId;
        this.fornecedorId = fornecedorId;
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

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    @Override
    public String toString() {
        return "ProdutoInputTO{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", marca='" + marca + '\'' +
                ", tipo='" + tipo + '\'' +
                ", categoriaId=" + categoriaId +
                ", fornecedorId=" + fornecedorId +
                '}';
    }
}
