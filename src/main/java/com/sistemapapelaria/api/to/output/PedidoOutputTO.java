package com.sistemapapelaria.api.to.output;

import java.util.List;

public class PedidoOutputTO {

    private Integer id;
    private UsuarioOutputTO usuario;
    private List<ItemPedidoOutputTO> itens;
    private String status;
    private Double valorTotal;
    private String formaPagamento;
    private String data;

    public PedidoOutputTO() {}

    public PedidoOutputTO(Integer id,
                          UsuarioOutputTO usuario,
                          List<ItemPedidoOutputTO> itens,
                          String status,
                          Double valorTotal,
                          String formaPagamento,
                          String data) {
        this.id = id;
        this.usuario = usuario;
        this.itens = itens;
        this.status = status;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.data = data;
    }

    public Integer getId() { return id; }
    public UsuarioOutputTO getUsuario() { return usuario; }
    public List<ItemPedidoOutputTO> getItens() { return itens; }
    public String getStatus() { return status; }
    public Double getValorTotal() { return valorTotal; }
    public String getFormaPagamento() { return formaPagamento; }
    public String getData() { return data; }
}
