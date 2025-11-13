package com.sistemapapelaria.api.to.input;

import java.util.List;

public class CategoriaInputTO {

    private String nome;
    private String descricao;
    private List<String> produtos;

    public CategoriaInputTO() {}

    public CategoriaInputTO(String nome, String descricao, List<String> produtos) {
        this.nome = nome;
        this.descricao = descricao;
        this.produtos = produtos;
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
        return "CategoriaInputTO{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", produtos=" + produtos +
                '}';
    }
}
