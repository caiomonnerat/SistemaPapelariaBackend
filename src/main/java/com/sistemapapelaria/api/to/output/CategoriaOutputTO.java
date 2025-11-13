package com.sistemapapelaria.api.to.output;

import java.util.List;

public class CategoriaOutputTO {

    private Integer id;
    private String nome;
    private String descricao;
    private List<String> produtos;

    public CategoriaOutputTO() {}

    public CategoriaOutputTO(Integer id, String nome, String descricao, List<String> produtos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.produtos = produtos;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<String> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "CategoriaOutputTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", produtos=" + produtos +
                '}';
    }
}
