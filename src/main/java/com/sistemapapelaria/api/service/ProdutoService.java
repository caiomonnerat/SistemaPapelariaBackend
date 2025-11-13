package com.sistemapapelaria.api.service;

import com.sistemapapelaria.api.model.Produto;
import com.sistemapapelaria.api.repository.ProdutoRepository;
import com.sistemapapelaria.api.model.Categoria;
import com.sistemapapelaria.api.model.Fornecedor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Integer id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setQuantidade(produtoAtualizado.getQuantidade());
                    produto.setMarca(produtoAtualizado.getMarca());
                    produto.setTipo(produtoAtualizado.getTipo());

                    Categoria categoria = new Categoria(produtoAtualizado.getCategoria().getId());
                    Fornecedor fornecedor = new Fornecedor(produtoAtualizado.getFornecedor().getId());
                    produto.setCategoria(categoria);
                    produto.setFornecedor(fornecedor);

                    return produtoRepository.save(produto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void excluir(Integer id) {
        produtoRepository.deleteById(id);
    }
}
