package com.sistemapapelaria.api.service;

import com.sistemapapelaria.api.model.Categoria;
import com.sistemapapelaria.api.model.Produto;
import com.sistemapapelaria.api.model.Usuario;
import com.sistemapapelaria.api.model.Perfil;
import com.sistemapapelaria.api.repository.PedidoRepository;
import com.sistemapapelaria.api.repository.ProdutoRepository;
import com.sistemapapelaria.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Produto> listarProdutosEmEstoque() {
        return produtoRepository.findAll();
    }

    public double valorTotalPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getValorTotal())
                .sum();
    }

    public List<Usuario> listarClientes() {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getPerfil() == Perfil.CLIENTE)
                .toList();
    }

    public Map<Categoria, List<Produto>> produtosPorCategoria() {
        return produtoRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Produto::getCategoria));
    }
}
