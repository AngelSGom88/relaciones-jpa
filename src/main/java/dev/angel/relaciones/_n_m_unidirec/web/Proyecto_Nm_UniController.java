package dev.angel.relaciones._n_m_unidirec.web;

import dev.angel.relaciones._n_m_unidirec.domain.Proyecto_Nm_Uni;
import dev.angel.relaciones._n_m_unidirec.service.Proyecto_Nm_UniService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rnmuni/proyectos")
public class Proyecto_Nm_UniController {

    private final Proyecto_Nm_UniService service;

    public Proyecto_Nm_UniController(Proyecto_Nm_UniService service) { this.service = service; }

    @GetMapping public List<Proyecto_Nm_Uni> listar() { return service.listar(); }
    @GetMapping("/{id}") public Proyecto_Nm_Uni obtener(@PathVariable Long id) { return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Proyecto_Nm_Uni crear(@RequestBody Proyecto_Nm_Uni p) { return service.crear(p); }
    @PutMapping("/{id}") public Proyecto_Nm_Uni actualizar(@PathVariable Long id, @RequestBody Proyecto_Nm_Uni p) { return service.actualizar(id, p); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
