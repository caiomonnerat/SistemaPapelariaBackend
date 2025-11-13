package com.sistemapapelaria.api.to.output;

public class ItemPedidoOutputTO {

    private Integer id;
    private ProdutoResumo produto;
    private Integer quantidade;
    private Double preco;
    private Double subtotal;

    public static class ProdutoResumo {
        private Integer id;
        private String nome;

        public ProdutoResumo(Integer id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Integer getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }
    }

    public ItemPedidoOutputTO() {}

    public ItemPedidoOutputTO(Integer id, Integer produtoId, String produtoNome,
                              Integer quantidade, Double preco, Double subtotal) {
        this.id = id;
        this.produto = new ProdutoResumo(produtoId, produtoNome);
        this.quantidade = quantidade;
        this.preco = preco;
        this.subtotal = subtotal;
    }

    public Integer getId() {
        return id;
    }

    public ProdutoResumo getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public Double getSubtotal() {
        return subtotal;
    }
}
