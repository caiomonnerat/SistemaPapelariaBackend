package com.sistemapapelaria.api.controller.perfil;

import com.sistemapapelaria.api.model.Perfil;
import com.sistemapapelaria.api.to.PerfilTO;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfis")
public class PerfilController {

    @GetMapping
    public List<PerfilTO> listarPerfis() {
        return Arrays.stream(Perfil.values())
                .map(perfil -> new PerfilTO(perfil.name()))
                .collect(Collectors.toList());
    }
}
