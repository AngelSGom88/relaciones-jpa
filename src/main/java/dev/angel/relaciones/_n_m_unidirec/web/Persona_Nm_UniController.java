package dev.angel.relaciones._n_m_unidirec.web;

import dev.angel.relaciones._n_m_unidirec.domain.Persona_Nm_Uni;
import dev.angel.relaciones._n_m_unidirec.service.Persona_Nm_UniService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rnmuni/personas")
public class Persona_Nm_UniController {

    private final Persona_Nm_UniService service;

    public Persona_Nm_UniController(Persona_Nm_UniService service) {
        this.service = service;
    }

    @GetMapping public List<Persona_Nm_Uni> listar() { return service.listar(); }
    @GetMapping("/{id}") public Persona_Nm_Uni obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Persona_Nm_Uni crear(@RequestBody Persona_Nm_Uni p) { return service.crear(p); }
    @PutMapping("/{id}") public Persona_Nm_Uni actualizar(@PathVariable Long id, @RequestBody Persona_Nm_Uni p) { return service.actualizar(id, p); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id) { service.eliminar(id); }

    @PostMapping("/{personaId}/proyectos/{proyectoId}") @ResponseStatus(HttpStatus.CREATED)
    public Persona_Nm_Uni vincular(@PathVariable Long personaId, @PathVariable Long proyectoId) {
        return service.vincularProyecto(personaId, proyectoId);
    }

    @DeleteMapping("/{personaId}/proyectos/{proyectoId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincular(@PathVariable Long personaId, @PathVariable Long proyectoId) {
        service.desvincularProyecto(personaId, proyectoId);
    }
}
