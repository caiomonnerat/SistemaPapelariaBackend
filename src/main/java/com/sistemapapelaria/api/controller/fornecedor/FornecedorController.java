package com.sistemapapelaria.api.controller.fornecedor;

import com.sistemapapelaria.api.model.Fornecedor;
import com.sistemapapelaria.api.service.FornecedorService;
import com.sistemapapelaria.api.to.input.FornecedorInputTO;
import com.sistemapapelaria.api.to.output.FornecedorOutputTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<FornecedorOutputTO>> listarTodos() {
        List<FornecedorOutputTO> fornecedores = fornecedorService.listarTodos().stream()
                .map(fornecedor -> new FornecedorOutputTO(
                        fornecedor.getId(),
                        fornecedor.getNome(),
                        fornecedor.getCnpj(),
                        fornecedor.getTelefone(),
                        fornecedor.getEndereco()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorOutputTO> buscarPorId(@PathVariable Integer id) {
        return fornecedorService.buscarPorId(id)
                .map(fornecedor -> new FornecedorOutputTO(
                        fornecedor.getId(),
                        fornecedor.getNome(),
                        fornecedor.getCnpj(),
                        fornecedor.getTelefone(),
                        fornecedor.getEndereco()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FornecedorOutputTO> salvar(@RequestBody FornecedorInputTO fornecedorInputTO) {
        Fornecedor fornecedor = new Fornecedor(
                null,
                fornecedorInputTO.getNome(),
                fornecedorInputTO.getCnpj(),
                fornecedorInputTO.getTelefone(),
                fornecedorInputTO.getEndereco()
        );

        Fornecedor fornecedorSalvo = fornecedorService.salvar(fornecedor);

        FornecedorOutputTO fornecedorOutputTO = new FornecedorOutputTO(
                fornecedorSalvo.getId(),
                fornecedorSalvo.getNome(),
                fornecedorSalvo.getCnpj(),
                fornecedorSalvo.getTelefone(),
                fornecedorSalvo.getEndereco()
        );
        return ResponseEntity.ok(fornecedorOutputTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorOutputTO> atualizar(@PathVariable Integer id, @RequestBody FornecedorInputTO fornecedorInputTO) {
        Fornecedor fornecedorAtualizado = new Fornecedor(
                id,
                fornecedorInputTO.getNome(),
                fornecedorInputTO.getCnpj(),
                fornecedorInputTO.getTelefone(),
                fornecedorInputTO.getEndereco()
        );

        return fornecedorService.atualizar(id, fornecedorAtualizado)
                .map(fornecedor -> new FornecedorOutputTO(
                        fornecedor.getId(),
                        fornecedor.getNome(),
                        fornecedor.getCnpj(),
                        fornecedor.getTelefone(),
                        fornecedor.getEndereco()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        fornecedorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
