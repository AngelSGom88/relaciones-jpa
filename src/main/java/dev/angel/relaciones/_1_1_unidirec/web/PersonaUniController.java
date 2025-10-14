package dev.angel.relaciones._1_1_unidirec.web;

import dev.angel.relaciones._1_1_unidirec.domain.PersonaUni;
import dev.angel.relaciones._1_1_unidirec.service.PersonaUniService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin // quítalo si sirves app.html desde el mismo host/puerto
@RestController
@RequestMapping("/r11uni/personas")
public class PersonaUniController {

    private final PersonaUniService service;

    public PersonaUniController(PersonaUniService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonaUni> all() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PersonaUni byId(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public ResponseEntity<PersonaUni> create(@RequestBody @Valid PersonaUni body) {
        PersonaUni saved = service.crear(body);
        return ResponseEntity
                .created(URI.create("/r11uni/personas/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public PersonaUni update(@PathVariable Long id, @RequestBody @Valid PersonaUni body) {
        return service.actualizar(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.eliminar(id);
    }
}
