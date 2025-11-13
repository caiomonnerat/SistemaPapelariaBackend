package com.sistemapapelaria.api.controller.relatorios;

import com.sistemapapelaria.api.model.Categoria;
import com.sistemapapelaria.api.model.Produto;
import com.sistemapapelaria.api.model.Usuario;
import com.sistemapapelaria.api.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/produtos-estoque")
    public List<Produto> produtosEmEstoque() {
        return relatorioService.listarProdutosEmEstoque();
    }

    @GetMapping("/valor-pedidos")
    public double valorTotalPedidos() {
        return relatorioService.valorTotalPedidos();
    }

    @GetMapping("/clientes")
    public List<Usuario> listarClientes() {
        return relatorioService.listarClientes();
    }

    @GetMapping("/produtos-por-categoria")
    public Map<Categoria, List<Produto>> produtosPorCategoria() {
        return relatorioService.produtosPorCategoria();
    }
}

