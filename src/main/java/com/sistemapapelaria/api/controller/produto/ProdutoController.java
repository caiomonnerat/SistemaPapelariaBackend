package com.sistemapapelaria.api.controller.produto;

import com.sistemapapelaria.api.model.Produto;
import com.sistemapapelaria.api.model.Categoria;
import com.sistemapapelaria.api.model.Fornecedor;
import com.sistemapapelaria.api.service.ProdutoService;
import com.sistemapapelaria.api.to.input.ProdutoInputTO;
import com.sistemapapelaria.api.to.output.ProdutoOutputTO;
import com.sistemapapelaria.api.to.output.CategoriaOutputTO;
import com.sistemapapelaria.api.to.output.FornecedorOutputTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoOutputTO>> listar() {
        List<ProdutoOutputTO> produtos = produtoService.listarTodos().stream()
                .map(produto -> new ProdutoOutputTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getQuantidade(),
                        produto.getMarca(),
                        produto.getTipo(),
                        new CategoriaOutputTO(produto.getCategoria().getId(),
                                produto.getCategoria().getNome(),
                                produto.getCategoria().getDescricao(),
                                produto.getCategoria().getProdutos().stream()
                                        .map(p -> p.getNome())
                                        .collect(Collectors.toList())),
                        new FornecedorOutputTO(produto.getFornecedor().getId(),
                                produto.getFornecedor().getNome(),
                                produto.getFornecedor().getCnpj(),
                                produto.getFornecedor().getTelefone(),
                                produto.getFornecedor().getEndereco())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoOutputTO> buscarPorId(@PathVariable Integer id) {
        return produtoService.buscarPorId(id)
                .map(produto -> new ProdutoOutputTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getQuantidade(),
                        produto.getMarca(),
                        produto.getTipo(),
                        new CategoriaOutputTO(produto.getCategoria().getId(),
                                produto.getCategoria().getNome(),
                                produto.getCategoria().getDescricao(),
                                produto.getCategoria().getProdutos().stream()
                                        .map(p -> p.getNome())
                                        .collect(Collectors.toList())),
                        new FornecedorOutputTO(produto.getFornecedor().getId(),
                                produto.getFornecedor().getNome(),
                                produto.getFornecedor().getCnpj(),
                                produto.getFornecedor().getTelefone(),
                                produto.getFornecedor().getEndereco())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutoOutputTO> salvar(@RequestBody ProdutoInputTO produtoInputTO) {
        Produto produto = new Produto();
        produto.setNome(produtoInputTO.getNome());
        produto.setPreco(produtoInputTO.getPreco());
        produto.setQuantidade(produtoInputTO.getQuantidade());
        produto.setMarca(produtoInputTO.getMarca());
        produto.setTipo(produtoInputTO.getTipo());

        produto.setCategoria(new Categoria(produtoInputTO.getCategoriaId()));
        produto.setFornecedor(new Fornecedor(produtoInputTO.getFornecedorId()));

        Produto produtoSalvo = produtoService.salvar(produto);

        ProdutoOutputTO produtoOutputTO = new ProdutoOutputTO(
                produtoSalvo.getId(),
                produtoSalvo.getNome(),
                produtoSalvo.getPreco(),
                produtoSalvo.getQuantidade(),
                produtoSalvo.getMarca(),
                produtoSalvo.getTipo(),
                new CategoriaOutputTO(produtoSalvo.getCategoria().getId(),
                        produtoSalvo.getCategoria().getNome(),
                        produtoSalvo.getCategoria().getDescricao(),
                        produtoSalvo.getCategoria().getProdutos().stream()
                                .map(p -> p.getNome())
                                .collect(Collectors.toList())),
                new FornecedorOutputTO(produtoSalvo.getFornecedor().getId(),
                        produtoSalvo.getFornecedor().getNome(),
                        produtoSalvo.getFornecedor().getCnpj(),
                        produtoSalvo.getFornecedor().getTelefone(),
                        produtoSalvo.getFornecedor().getEndereco())
        );

        return ResponseEntity.ok(produtoOutputTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoOutputTO> atualizar(@PathVariable Integer id, @RequestBody ProdutoInputTO produtoInputTO) {
        Produto produtoAtualizado = produtoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoAtualizado.setNome(produtoInputTO.getNome());
        produtoAtualizado.setPreco(produtoInputTO.getPreco());
        produtoAtualizado.setQuantidade(produtoInputTO.getQuantidade());
        produtoAtualizado.setMarca(produtoInputTO.getMarca());
        produtoAtualizado.setTipo(produtoInputTO.getTipo());

        if (produtoInputTO.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(produtoInputTO.getCategoriaId());
            produtoAtualizado.setCategoria(categoria);
        }

        if (produtoInputTO.getFornecedorId() != null) {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(produtoInputTO.getFornecedorId());
            produtoAtualizado.setFornecedor(fornecedor);
        }

        produtoAtualizado = produtoService.atualizar(id, produtoAtualizado);

        ProdutoOutputTO produtoOutputTO = new ProdutoOutputTO(
                produtoAtualizado.getId(),
                produtoAtualizado.getNome(),
                produtoAtualizado.getPreco(),
                produtoAtualizado.getQuantidade(),
                produtoAtualizado.getMarca(),
                produtoAtualizado.getTipo(),
                new CategoriaOutputTO(
                        produtoAtualizado.getCategoria().getId(),
                        produtoAtualizado.getCategoria().getNome(),
                        produtoAtualizado.getCategoria().getDescricao(),
                        produtoAtualizado.getCategoria().getProdutos().stream()
                                .map(p -> p.getNome())
                                .collect(Collectors.toList())
                ),
                new FornecedorOutputTO(
                        produtoAtualizado.getFornecedor().getId(),
                        produtoAtualizado.getFornecedor().getNome(),
                        produtoAtualizado.getFornecedor().getCnpj(),
                        produtoAtualizado.getFornecedor().getTelefone(),
                        produtoAtualizado.getFornecedor().getEndereco()
                )
        );

        return ResponseEntity.ok(produtoOutputTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias")
    public ResponseEntity<Map<String, List<ProdutoOutputTO>>> listarPorCategoria() {
        Map<String, List<ProdutoOutputTO>> resultado = new HashMap<>();

        List<Produto> produtos = produtoService.listarTodos();
        for (Produto produto : produtos) {
            String categoria = produto.getCategoria() != null
                    ? produto.getCategoria().getNome()
                    : "Sem Categoria";

            resultado.computeIfAbsent(categoria, k -> new ArrayList<>())
                    .add(new ProdutoOutputTO(
                            produto.getId(),
                            produto.getNome(),
                            produto.getPreco(),
                            produto.getQuantidade(),
                            produto.getMarca(),
                            produto.getTipo(),
                            null,
                            null
                    ));
        }

        return ResponseEntity.ok(resultado);
    }
}
