package com.sistemapapelaria.api.controller.categoria;

import com.sistemapapelaria.api.model.Categoria;
import com.sistemapapelaria.api.to.input.CategoriaInputTO;
import com.sistemapapelaria.api.to.output.CategoriaOutputTO;
import com.sistemapapelaria.api.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaOutputTO>> listar() {
        List<CategoriaOutputTO> categorias = categoriaService.listarTodos().stream()
                .map(categoria -> new CategoriaOutputTO(
                        categoria.getId(), 
                        categoria.getNome(), 
                        categoria.getDescricao(), 
                        categoria.getProdutos().stream().map(p -> p.getNome()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaOutputTO> buscarPorId(@PathVariable Integer id) {
        return categoriaService.buscarPorId(id)
                .map(categoria -> new CategoriaOutputTO(
                        categoria.getId(), 
                        categoria.getNome(), 
                        categoria.getDescricao(),
                        categoria.getProdutos().stream().map(p -> p.getNome()).collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaOutputTO> salvar(@RequestBody CategoriaInputTO categoriaInputTO) {
        Categoria categoria = new Categoria(
                null,
                categoriaInputTO.getNome(), 
                categoriaInputTO.getDescricao()
        );

        Categoria categoriaSalva = categoriaService.salvar(categoria);

        CategoriaOutputTO categoriaOutputTO = new CategoriaOutputTO(
                categoriaSalva.getId(), 
                categoriaSalva.getNome(), 
                categoriaSalva.getDescricao(),
                categoriaSalva.getProdutos().stream().map(p -> p.getNome()).collect(Collectors.toList())
        );
        return ResponseEntity.ok(categoriaOutputTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaOutputTO> atualizar(@PathVariable Integer id, @RequestBody CategoriaInputTO categoriaInputTO) {
        Categoria categoriaAtualizada = new Categoria(
                id, 
                categoriaInputTO.getNome(), 
                categoriaInputTO.getDescricao()
        );

        return categoriaService.atualizar(id, categoriaAtualizada)
                .map(categoria -> new CategoriaOutputTO(
                        categoria.getId(), 
                        categoria.getNome(), 
                        categoria.getDescricao(),
                        categoria.getProdutos().stream().map(p -> p.getNome()).collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
