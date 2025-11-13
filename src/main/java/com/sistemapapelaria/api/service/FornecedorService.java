package com.sistemapapelaria.api.service;

import com.sistemapapelaria.api.model.Fornecedor;
import com.sistemapapelaria.api.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Integer id) {
        return fornecedorRepository.findById(id);
    }

    public Fornecedor salvar(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> atualizar(Integer id, Fornecedor fornecedorAtualizado) {
        return fornecedorRepository.findById(id)
                .map(fornecedor -> {
                    fornecedor.setNome(fornecedorAtualizado.getNome());
                    fornecedor.setCnpj(fornecedorAtualizado.getCnpj());
                    fornecedor.setTelefone(fornecedorAtualizado.getTelefone());
                    fornecedor.setEndereco(fornecedorAtualizado.getEndereco());
                    return fornecedorRepository.save(fornecedor);
                });
    }

    public void excluir(Integer id) {
        fornecedorRepository.deleteById(id);
    }
}
