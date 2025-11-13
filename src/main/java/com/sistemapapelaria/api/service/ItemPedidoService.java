package com.sistemapapelaria.api.service;

import com.sistemapapelaria.api.model.ItemPedido;
import com.sistemapapelaria.api.model.Pedido;
import com.sistemapapelaria.api.model.Produto;
import com.sistemapapelaria.api.repository.ItemPedidoRepository;
import com.sistemapapelaria.api.repository.PedidoRepository;
import com.sistemapapelaria.api.repository.ProdutoRepository;
import com.sistemapapelaria.api.to.input.ItemPedidoInputTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public List<ItemPedido> listarPorPedido(Integer pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }

    public Optional<ItemPedido> buscarPorId(Integer id) {
        return itemPedidoRepository.findById(id);
    }

    public ItemPedido salvar(ItemPedido item) {
        ItemPedido itemSalvo = itemPedidoRepository.save(item);
        atualizarTotalPedido(item.getPedido());
        return itemSalvo;
    }

    public ItemPedido atualizar(Integer id, ItemPedido itemAtualizado) {
        ItemPedido itemSalvo = itemPedidoRepository.findById(id)
                .map(item -> {
                    item.setProduto(itemAtualizado.getProduto());
                    item.setQuantidade(itemAtualizado.getQuantidade());
                    item.setPreco(itemAtualizado.getPreco());
                    return itemPedidoRepository.save(item);
                })
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado"));
        atualizarTotalPedido(itemSalvo.getPedido());
        return itemSalvo;
    }

    public void excluir(Integer id) {
        ItemPedido item = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado"));
        Pedido pedido = item.getPedido();
        itemPedidoRepository.deleteById(id);
        atualizarTotalPedido(pedido);
    }

    public double somarTotalPorPedido(Integer pedidoId) {
        return itemPedidoRepository.somarTotalPorPedido(pedidoId);
    }

    public ItemPedido adicionarItemAoPedido(Integer pedidoId, ItemPedidoInputTO itemPedidoInputTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Integer produtoId = itemPedidoInputTO.getProdutoId();
        if (produtoId == null) {
            throw new RuntimeException("produtoId não informado");
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);

        Integer qtd = itemPedidoInputTO.getQuantidade();
        if (qtd == null) qtd = 1;
        item.setQuantidade(qtd);

        Double precoInput = itemPedidoInputTO.getPreco();
        item.setPreco(precoInput != null ? precoInput : produto.getPreco());

        return salvar(item);
    }

    public ItemPedido atualizarItemDoPedido(Integer pedidoId, Integer itemId, ItemPedidoInputTO input) {
        ItemPedido item = buscarPorId(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (!item.getPedido().getId().equals(pedidoId)) {
            throw new RuntimeException("Item não pertence a este pedido");
        }

        Produto produto = produtoRepository.findById(input.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        item.setProduto(produto);
        item.setQuantidade(input.getQuantidade());
        item.setPreco(produto.getPreco());

        return salvar(item);
    }

    private void atualizarTotalPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(pedido.getId());
        double total = itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
        pedido.setValorTotal(total);
        pedidoRepository.save(pedido);
    }
}
