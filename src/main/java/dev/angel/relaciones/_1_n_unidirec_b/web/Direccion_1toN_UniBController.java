package dev.angel.relaciones._1_n_unidirec_b.web;

import dev.angel.relaciones._1_n_unidirec_b.domain.Direccion_1toN_UniB;
import dev.angel.relaciones._1_n_unidirec_b.service.Direccion_1toN_UniBService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/r1nuni-b/direcciones")
public class Direccion_1toN_UniBController {

    private final Direccion_1toN_UniBService service;

    public Direccion_1toN_UniBController(Direccion_1toN_UniBService service) {
        this.service = service;
    }

    // En UNI-B exigimos personaId. Si no viene → 400 (lo hace el service).
    @GetMapping
    public List<Direccion_1toN_UniB> listar(@RequestParam("personaId") Long personaId) {
        return service.listarPorPersona(personaId);
    }

    @GetMapping("/{id}")
    public Direccion_1toN_UniB obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Direccion_1toN_UniB crear(@RequestBody Direccion_1toN_UniB d) {
        return service.crear(d);
    }

    @PutMapping("/{id}")
    public Direccion_1toN_UniB actualizar(@PathVariable Long id, @RequestBody Direccion_1toN_UniB d) {
        return service.actualizar(id, d);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
