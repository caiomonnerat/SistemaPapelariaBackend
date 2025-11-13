package com.sistemapapelaria.api.repository;

import com.sistemapapelaria.api.model.Usuario;
import com.sistemapapelaria.api.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByPerfil(Perfil perfil);
}
