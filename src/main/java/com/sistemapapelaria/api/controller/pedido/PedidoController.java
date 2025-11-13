package com.sistemapapelaria.api.controller.pedido;

import com.sistemapapelaria.api.model.Pedido;
import com.sistemapapelaria.api.service.PedidoService;
import com.sistemapapelaria.api.service.ItemPedidoService;
import com.sistemapapelaria.api.to.input.PedidoInputTO;
import com.sistemapapelaria.api.to.input.ItemPedidoInputTO;
import com.sistemapapelaria.api.to.output.PedidoOutputTO;
import com.sistemapapelaria.api.to.output.ItemPedidoOutputTO;
import com.sistemapapelaria.api.to.output.UsuarioOutputTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private PedidoOutputTO converter(Pedido pedido) {
        return new PedidoOutputTO(
                pedido.getId(),
                new UsuarioOutputTO(
                        pedido.getUsuario().getId(),
                        pedido.getUsuario().getNome()
                ),
                pedido.getItens().stream()
                        .map(item -> new ItemPedidoOutputTO(
                                item.getId(),
                                item.getProduto().getId(),
                                item.getProduto().getNome(),
                                item.getQuantidade(),
                                item.getPreco(),
                                item.getSubtotal()
                        ))
                        .collect(Collectors.toList()),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getFormaPagamento(),
                sdf.format(pedido.getData())
        );
    }

    @GetMapping
    public ResponseEntity<List<PedidoOutputTO>> listarTodos() {
        return ResponseEntity.ok(
                pedidoService.listarTodos().stream()
                        .map(this::converter)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoOutputTO> buscarPorId(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id)
                .map(p -> ResponseEntity.ok(converter(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoOutputTO> salvar(@RequestBody PedidoInputTO pedidoInputTO) {
        Pedido pedidoSalvo = pedidoService.salvar(pedidoInputTO);
        return ResponseEntity.ok(converter(pedidoSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoOutputTO> atualizar(@PathVariable Integer id, @RequestBody PedidoInputTO pedidoInputTO) {
        Pedido pedidoAtualizado = pedidoService.atualizar(id, pedidoInputTO);
        return ResponseEntity.ok(converter(pedidoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemPedidoOutputTO>> listarItensDoPedido(@PathVariable Integer id) {
        List<ItemPedidoOutputTO> itens = itemPedidoService.listarPorPedido(id).stream()
                .map(item -> new ItemPedidoOutputTO(
                        item.getId(),
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPreco(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(itens);
    }

    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<ItemPedidoOutputTO> adicionarItem(
            @PathVariable Integer pedidoId,
            @RequestBody ItemPedidoInputTO itemInput) {

        var itemSalvo = itemPedidoService.adicionarItemAoPedido(pedidoId, itemInput);

        var itemOutput = new ItemPedidoOutputTO(
                itemSalvo.getId(),
                itemSalvo.getProduto().getId(),
                itemSalvo.getProduto().getNome(),
                itemSalvo.getQuantidade(),
                itemSalvo.getPreco(),
                itemSalvo.getSubtotal()
        );

        return ResponseEntity.ok(itemOutput);
    }

    @GetMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<ItemPedidoOutputTO> buscarItemPorId(
            @PathVariable Integer pedidoId,
            @PathVariable Integer itemId) {

        Optional<com.sistemapapelaria.api.model.ItemPedido> itemOpt = itemPedidoService.buscarPorId(itemId);

        if (itemOpt.isEmpty() || !itemOpt.get().getPedido().getId().equals(pedidoId)) {
            return ResponseEntity.notFound().build();
        }

        var item = itemOpt.get();
        var itemOutput = new ItemPedidoOutputTO(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPreco(),
                item.getSubtotal()
        );

        return ResponseEntity.ok(itemOutput);
    }

    @PutMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<ItemPedidoOutputTO> atualizarItem(
            @PathVariable Integer pedidoId,
            @PathVariable Integer itemId,
            @RequestBody ItemPedidoInputTO itemInput) {

        var itemAtualizado = itemPedidoService.atualizarItemDoPedido(pedidoId, itemId, itemInput);

        var itemOutput = new ItemPedidoOutputTO(
                itemAtualizado.getId(),
                itemAtualizado.getProduto().getId(),
                itemAtualizado.getProduto().getNome(),
                itemAtualizado.getQuantidade(),
                itemAtualizado.getPreco(),
                itemAtualizado.getSubtotal()
        );

        return ResponseEntity.ok(itemOutput);
    }

        @GetMapping("/total")
        public ResponseEntity<Double> calcularValorTotalPedidos() {
        double total = pedidoService.listarTodos()
                .stream()
                .mapToDouble(Pedido::getValorTotal)
                .sum();

        return ResponseEntity.ok(total);
        }

        @DeleteMapping("/{pedidoId}/itens/{itemId}")
        public ResponseEntity<Void> removerItemDoPedido(
                @PathVariable Integer pedidoId,
                @PathVariable Integer itemId) {

        Pedido pedido = pedidoService.buscarPorId(pedidoId)
                        .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedidoService.removerItem(pedido, itemId);

        return ResponseEntity.noContent().build();
        }

}
