package dev.angel.relaciones._1_n_bidirec.web;

import dev.angel.relaciones._1_n_bidirec.domain.Direccion_1toN_Bi;
import dev.angel.relaciones._1_n_bidirec.domain.Persona_1toN_Bi;
import dev.angel.relaciones._1_n_bidirec.service.Persona_1toN_BiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1nbi/personas")
public class Persona_1toN_BiController {

    private final Persona_1toN_BiService service;

    public Persona_1toN_BiController(Persona_1toN_BiService service) {
        this.service = service;
    }

    @GetMapping
    public List<Persona_1toN_Bi> listar(){ return service.listar(); }

    @GetMapping("/{id}")
    public Persona_1toN_Bi obtener(@PathVariable Long id){ return service.obtener(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Persona_1toN_Bi crear(@RequestBody Persona_1toN_Bi p){ return service.crear(p); }

    @PutMapping("/{id}")
    public Persona_1toN_Bi actualizar(@PathVariable Long id, @RequestBody Persona_1toN_Bi p){
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){ service.eliminar(id); }

    // --- anidados opcionales ---
    @PostMapping("/{id}/direcciones")
    @ResponseStatus(HttpStatus.CREATED)
    public Direccion_1toN_Bi addDireccion(@PathVariable Long id, @RequestBody Direccion_1toN_Bi d){
        return service.agregarDireccion(id, d);
    }

    @DeleteMapping("/{id}/direcciones/{dirId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delDireccion(@PathVariable Long id, @PathVariable Long dirId){
        service.eliminarDireccion(id, dirId);
    }
}
