package com.sistemapapelaria.api.service;

import com.sistemapapelaria.api.model.Usuario;
import com.sistemapapelaria.api.model.Perfil;
import com.sistemapapelaria.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Integer id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNome(usuarioAtualizado.getNome());
                usuario.setEmail(usuarioAtualizado.getEmail());
                usuario.setSenha(usuarioAtualizado.getSenha());
                usuario.setPerfil(usuarioAtualizado.getPerfil());
                return usuarioRepository.save(usuario);
            }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void excluir(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> listarPorPerfil(Perfil perfil) {
        return usuarioRepository.findByPerfil(perfil);
    }
}
