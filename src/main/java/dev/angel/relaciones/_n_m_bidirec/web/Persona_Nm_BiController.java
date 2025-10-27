package dev.angel.relaciones._n_m_bidirec.web;

import dev.angel.relaciones._n_m_bidirec.domain.Persona_Nm_Bi;
import dev.angel.relaciones._n_m_bidirec.service.Persona_Nm_BiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rnmbi/personas")
public class Persona_Nm_BiController {

    private final Persona_Nm_BiService service;

    public Persona_Nm_BiController(Persona_Nm_BiService service) { this.service = service; }

    // CRUD
    @GetMapping public List<Persona_Nm_Bi> listar(){ return service.listar(); }
    @GetMapping("/{id}") public Persona_Nm_Bi obtener(@PathVariable Long id){ return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Persona_Nm_Bi crear(@RequestBody Persona_Nm_Bi p){ return service.crear(p); }
    @PutMapping("/{id}") public Persona_Nm_Bi actualizar(@PathVariable Long id, @RequestBody Persona_Nm_Bi p){ return service.actualizar(id, p); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){ service.eliminar(id); }

    // Vincular / Desvincular proyecto
    @PostMapping("/{personaId}/proyectos/{proyectoId}") @ResponseStatus(HttpStatus.CREATED)
    public Persona_Nm_Bi vincular(@PathVariable Long personaId, @PathVariable Long proyectoId){
        return service.vincularProyecto(personaId, proyectoId);
    }

    @DeleteMapping("/{personaId}/proyectos/{proyectoId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincular(@PathVariable Long personaId, @PathVariable Long proyectoId){
        service.desvincularProyecto(personaId, proyectoId);
    }
}
