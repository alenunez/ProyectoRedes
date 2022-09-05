package com.redes.springcloud.msvc.cursos.controllers;

import com.redes.springcloud.msvc.cursos.models.Usuario;
import com.redes.springcloud.msvc.cursos.models.entity.Curso;
import com.redes.springcloud.msvc.cursos.services.CursoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping("/")
    public List<Curso> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> cursoDetalle = service.porIdConUsuarios(id); //service.porId(id);
        if(cursoDetalle.isPresent()){
            return ResponseEntity.ok().body(cursoDetalle.get());
        }
        return ResponseEntity.notFound().build();
    }

    //put es para modificar y post para agregar

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody @Valid Curso curso, BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Curso> o = service.porId(id);
        if(o.isPresent()){
            o.get().setNombre(curso.getNombre());
            return  ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(o.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Long id){
        Optional<Curso> o = service.porId(id);
        if(o.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@RequestBody @Valid Curso curso,BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
            Optional<Usuario> o;
            try{
                o = service.asignarUsuario(usuario,cursoId);
            }catch (FeignException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body(Collections.singletonMap("mensaje","No existe el usuario el id o error en la comunicacion " + e.getMessage()));
            }
            if(o.isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
            }
            return ResponseEntity.notFound().build();
    }
    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try{
            o = service.crearUsuario(usuario,cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("mensaje","No se pudo crear  el usuario el id o error en la comunicacion " + e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try{
            o = service.eliminarUsuario(usuario,cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("mensaje","No existe el usuario el id o error en la comunicacion " + e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        service.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldError();
        for (FieldError e: result.getFieldErrors()) {
            errores.put(e.getField(),"El campo " + e.getField() + " " + e.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }




}
