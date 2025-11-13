package com.sistemapapelaria.api.service;

import com.sistemapapelaria.api.model.ItemPedido;
import com.sistemapapelaria.api.model.Pedido;
import com.sistemapapelaria.api.model.Produto;
import com.sistemapapelaria.api.model.Usuario;
import com.sistemapapelaria.api.repository.PedidoRepository;
import com.sistemapapelaria.api.to.input.PedidoInputTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoService itemPedidoService;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    public Pedido salvar(PedidoInputTO pedidoInputTO) {

        Pedido pedido = new Pedido();

        Usuario usuario = usuarioService.buscarPorId(pedidoInputTO.getUsuarioId().intValue())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        pedido.setUsuario(usuario);
        pedido.setStatus(pedidoInputTO.getStatus());
        pedido.setFormaPagamento(pedidoInputTO.getFormaPagamento());
        pedido.setData(new Date());
        pedido.setItens(new ArrayList<>());
        pedido.setValorTotal(0.0);

        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Integer id, PedidoInputTO pedidoInputTO) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Usuario usuario = usuarioService.buscarPorId(pedidoInputTO.getUsuarioId().intValue())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        pedido.setUsuario(usuario);
        pedido.setStatus(pedidoInputTO.getStatus());
        pedido.setFormaPagamento(pedidoInputTO.getFormaPagamento());
        pedido.setValorTotal(calcularTotal(pedido));

        return pedidoRepository.save(pedido);
    }

    public void excluir(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public double calcularTotal(Pedido pedido) {
        return pedido.getItens().stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    public void adicionarItem(Pedido pedido, ItemPedido item) {

        Produto produto = produtoService.buscarPorId(item.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidade() < item.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente para " + produto.getNome());
        }

        produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
        produtoService.atualizar(produto.getId(), produto);

        item.setPreco(produto.getPreco());
        item.setPedido(pedido);
        item.setProduto(produto);
        itemPedidoService.salvar(item);

        pedido.getItens().add(item);
        pedido.setValorTotal(calcularTotal(pedido));
        pedidoRepository.save(pedido);
    }

    public void atualizarItem(Pedido pedido, ItemPedido itemAtualizado) {

        ItemPedido itemExistente = itemPedidoService.buscarPorId(itemAtualizado.getId())
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado"));

        Produto produto = produtoService.buscarPorId(itemExistente.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        int diferenca = itemAtualizado.getQuantidade() - itemExistente.getQuantidade();

        if (diferenca > 0 && produto.getQuantidade() < diferenca) {
            throw new RuntimeException("Estoque insuficiente para " + produto.getNome());
        }

        produto.setQuantidade(produto.getQuantidade() - diferenca);
        produtoService.atualizar(produto.getId(), produto);

        itemExistente.setQuantidade(itemAtualizado.getQuantidade());
        itemExistente.setPreco(produto.getPreco());
        itemPedidoService.atualizar(itemExistente.getId(), itemExistente);

        pedido.setValorTotal(calcularTotal(pedido));
        pedidoRepository.save(pedido);
    }

    public void removerItem(Pedido pedido, Integer itemId) {

        ItemPedido item = itemPedidoService.buscarPorId(itemId)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado"));

        Produto produto = produtoService.buscarPorId(item.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
        produtoService.atualizar(produto.getId(), produto);

        pedido.getItens().remove(item);
        itemPedidoService.excluir(itemId);

        pedido.setValorTotal(calcularTotal(pedido));
        pedidoRepository.save(pedido);
    }
}
