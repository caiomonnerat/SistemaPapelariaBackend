package com.sistemapapelaria.api.controller.usuario;

import com.sistemapapelaria.api.model.Usuario;
import com.sistemapapelaria.api.model.Perfil;
import com.sistemapapelaria.api.service.UsuarioService;
import com.sistemapapelaria.api.to.input.UsuarioInputTO;
import com.sistemapapelaria.api.to.output.UsuarioOutputTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioOutputTO>> listar(@RequestParam(required = false) String perfil) {
        List<Usuario> usuarios;

        if (perfil != null && !perfil.isBlank()) {
            try {
                Perfil perfilEnum = Perfil.valueOf(perfil.toUpperCase());
                usuarios = usuarioService.listarPorPerfil(perfilEnum);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            usuarios = usuarioService.listarTodos();
        }

        List<UsuarioOutputTO> usuariosTO = usuarios.stream()
                .map(usuario -> new UsuarioOutputTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getPerfil() != null ? usuario.getPerfil().name() : null
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutputTO> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> new UsuarioOutputTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getPerfil() != null ? usuario.getPerfil().name() : null
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody UsuarioInputTO usuarioInputTO) {
        if (usuarioInputTO.getPerfil() == null || usuarioInputTO.getPerfil().isBlank()) {
            return ResponseEntity.badRequest().body("O perfil do usuário deve ser informado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioInputTO.getNome());
        usuario.setEmail(usuarioInputTO.getEmail());
        usuario.setSenha(usuarioInputTO.getSenha());

        try {
            usuario.setPerfil(Perfil.valueOf(usuarioInputTO.getPerfil().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Perfil inválido: " + usuarioInputTO.getPerfil());
        }

        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        return ResponseEntity.ok(new UsuarioOutputTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getPerfil().name()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id,
                                       @RequestBody UsuarioInputTO usuarioInputTO) {

        if (usuarioInputTO.getPerfil() == null || usuarioInputTO.getPerfil().isBlank()) {
            return ResponseEntity.badRequest().body("O perfil do usuário deve ser informado.");
        }

        Usuario usuarioAtualizado = usuarioService.buscarPorId(id)
                .map(usuario -> {
                    usuario.setNome(usuarioInputTO.getNome());
                    usuario.setEmail(usuarioInputTO.getEmail());
                    usuario.setSenha(usuarioInputTO.getSenha());
                    try {
                        usuario.setPerfil(Perfil.valueOf(usuarioInputTO.getPerfil().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Perfil inválido: " + usuarioInputTO.getPerfil());
                    }
                    return usuarioService.salvar(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ResponseEntity.ok(new UsuarioOutputTO(
                usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getPerfil().name()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
