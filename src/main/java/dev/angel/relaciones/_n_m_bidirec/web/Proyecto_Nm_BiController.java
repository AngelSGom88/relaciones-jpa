package dev.angel.relaciones._n_m_bidirec.web;

import dev.angel.relaciones._n_m_bidirec.domain.Proyecto_Nm_Bi;
import dev.angel.relaciones._n_m_bidirec.service.Proyecto_Nm_BiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rnmbi/proyectos")
public class Proyecto_Nm_BiController {

    private final Proyecto_Nm_BiService service;

    public Proyecto_Nm_BiController(Proyecto_Nm_BiService service) { this.service = service; }

    @GetMapping public List<Proyecto_Nm_Bi> listar(){ return service.listar(); }
    @GetMapping("/{id}") public Proyecto_Nm_Bi obtener(@PathVariable Long id){ return service.obtener(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Proyecto_Nm_Bi crear(@RequestBody Proyecto_Nm_Bi p){ return service.crear(p); }
    @PutMapping("/{id}") public Proyecto_Nm_Bi actualizar(@PathVariable Long id, @RequestBody Proyecto_Nm_Bi p){ return service.actualizar(id, p); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){ service.eliminar(id); }
}
