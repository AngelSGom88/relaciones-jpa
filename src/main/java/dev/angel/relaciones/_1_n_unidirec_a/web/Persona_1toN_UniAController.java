package dev.angel.relaciones._1_n_unidirec_a.web;

import dev.angel.relaciones._1_n_unidirec_a.domain.Direccion_1toN_UniA;
import dev.angel.relaciones._1_n_unidirec_a.domain.Persona_1toN_UniA;
import dev.angel.relaciones._1_n_unidirec_a.service.Persona_1toN_UniAService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1nuni-a/personas")
public class Persona_1toN_UniAController {

    private final Persona_1toN_UniAService service;

    public Persona_1toN_UniAController(Persona_1toN_UniAService service) { this.service = service; }

    // CRUD persona
    @GetMapping public List<Persona_1toN_UniA> listar(){ return service.listar(); }
    @GetMapping("/{id}") public Persona_1toN_UniA obtener(@PathVariable Long id){ return service.obtener(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Persona_1toN_UniA crear(@RequestBody Persona_1toN_UniA p){ return service.crear(p); }

    @PutMapping("/{id}")
    public Persona_1toN_UniA actualizar(@PathVariable Long id, @RequestBody Persona_1toN_UniA p){
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){ service.eliminar(id); }

    // hijos (direcciones)
    @PostMapping("/{id}/direcciones") @ResponseStatus(HttpStatus.CREATED)
    public Persona_1toN_UniA addDireccion(@PathVariable Long id, @RequestBody Direccion_1toN_UniA d){
        return service.addDireccion(id, d);
    }

    @DeleteMapping("/{id}/direcciones/{dirId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDireccion(@PathVariable Long id, @PathVariable Long dirId){
        service.removeDireccion(id, dirId);
    }
}
