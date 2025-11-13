package com.sistemapapelaria.api.to.input;

import java.util.List;

public class PedidoInputTO {

    private Long usuarioId;
    private List<ItemPedidoInputTO> itens;
    private String status;
    private Double valorTotal;
    private String formaPagamento;

    public PedidoInputTO() {}

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<ItemPedidoInputTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoInputTO> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @Override
    public String toString() {
        return "PedidoInputTO{" +
                "usuarioId=" + usuarioId +
                ", itens=" + itens +
                ", status='" + status + '\'' +
                ", valorTotal=" + valorTotal +
                ", formaPagamento='" + formaPagamento + '\'' +
                '}';
    }
}
