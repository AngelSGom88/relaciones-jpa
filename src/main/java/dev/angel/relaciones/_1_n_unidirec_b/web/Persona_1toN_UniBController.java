package dev.angel.relaciones._1_n_unidirec_b.web;

import dev.angel.relaciones._1_n_unidirec_b.domain.Persona_1toN_UniB;
import dev.angel.relaciones._1_n_unidirec_b.service.Persona_1toN_UniBService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1nuni-b/personas")
public class Persona_1toN_UniBController {

    private final Persona_1toN_UniBService service;

    public Persona_1toN_UniBController(Persona_1toN_UniBService service) {
        this.service = service;
    }

    @GetMapping
    public List<Persona_1toN_UniB> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Persona_1toN_UniB obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Persona_1toN_UniB crear(@RequestBody Persona_1toN_UniB p) {
        return service.crear(p);
    }

    @PutMapping("/{id}")
    public Persona_1toN_UniB actualizar(@PathVariable Long id, @RequestBody Persona_1toN_UniB p) {
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id); // borra hijos en Direccion y luego la Persona (según tu service)
    }
}
