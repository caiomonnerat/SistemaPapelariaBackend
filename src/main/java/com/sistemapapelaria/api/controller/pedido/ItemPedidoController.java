package com.sistemapapelaria.api.controller.pedido;

import com.sistemapapelaria.api.model.ItemPedido;
import com.sistemapapelaria.api.model.Produto;
import com.sistemapapelaria.api.service.ItemPedidoService;
import com.sistemapapelaria.api.service.ProdutoService;
import com.sistemapapelaria.api.to.input.ItemPedidoInputTO;
import com.sistemapapelaria.api.to.output.ItemPedidoOutputTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/itensPedido")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;
    private final ProdutoService produtoService;

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedidoOutputTO>> listarPorPedido(@PathVariable Integer pedidoId) {
        List<ItemPedidoOutputTO> itensPedido = itemPedidoService.listarPorPedido(pedidoId).stream()
                .map(item -> new ItemPedidoOutputTO(
                        item.getId(),
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPreco(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(itensPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoOutputTO> buscarPorId(@PathVariable Integer id) {
        return itemPedidoService.buscarPorId(id)
                .map(item -> new ItemPedidoOutputTO(
                        item.getId(),
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPreco(),
                        item.getSubtotal()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemPedidoOutputTO> salvar(@RequestBody ItemPedidoInputTO itemInputTO) {
        Produto produto = produtoService.buscarPorId(itemInputTO.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(itemInputTO.getQuantidade());
        itemPedido.setPreco(itemInputTO.getPreco());

        ItemPedido itemSalvo = itemPedidoService.salvar(itemPedido);

        ItemPedidoOutputTO itemOutputTO = new ItemPedidoOutputTO(
                itemSalvo.getId(),
                itemSalvo.getProduto().getId(),
                itemSalvo.getProduto().getNome(),
                itemSalvo.getQuantidade(),
                itemSalvo.getPreco(),
                itemSalvo.getSubtotal()
        );

        return ResponseEntity.ok(itemOutputTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        itemPedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pedido/{pedidoId}/total")
    public ResponseEntity<Double> somarTotalPorPedido(@PathVariable Integer pedidoId) {
        return ResponseEntity.ok(itemPedidoService.somarTotalPorPedido(pedidoId));
    }
}
