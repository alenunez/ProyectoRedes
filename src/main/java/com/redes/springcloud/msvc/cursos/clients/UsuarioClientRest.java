package com.redes.springcloud.msvc.cursos.clients;

import com.redes.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios",url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/usuarios/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/usuarios/")
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
