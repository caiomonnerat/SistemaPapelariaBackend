package com.sistemapapelaria.api.to.input;

public class ItemPedidoInputTO {

    private Integer produtoId;

    private ProdutoRef produto;

    private Integer quantidade;
    private Double preco;

    public ItemPedidoInputTO() {}

    public ItemPedidoInputTO(Integer produtoId, Integer quantidade, Double preco) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public Integer getProdutoId() {
        if (produtoId != null) return produtoId;
        if (produto != null) return produto.getId();
        return null;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public ProdutoRef getProduto() {
        return produto;
    }

    public void setProduto(ProdutoRef produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ItemPedidoInputTO{" +
                "produtoId=" + getProdutoId() +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }

    public static class ProdutoRef {
        private Integer id;

        public ProdutoRef() {}

        public ProdutoRef(Integer id) { this.id = id; }

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
    }
}
