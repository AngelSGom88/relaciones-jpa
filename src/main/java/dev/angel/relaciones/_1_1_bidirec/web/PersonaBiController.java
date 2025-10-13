package dev.angel.relaciones._1_1_bidirec.web;

import dev.angel.relaciones._1_1_bidirec.domain.PersonaBi;
import dev.angel.relaciones._1_1_bidirec.service.PersonaBiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/r11bi/personas")
public class PersonaBiController {

    private final PersonaBiService service;

    public PersonaBiController(PersonaBiService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonaBi> all() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PersonaBi byId(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public ResponseEntity<PersonaBi> create(@RequestBody @Valid PersonaBi body) {
        PersonaBi saved = service.crear(body);
        return ResponseEntity
                .created(URI.create("/r11bi/personas/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public PersonaBi update(@PathVariable Long id, @RequestBody @Valid PersonaBi body) {
        return service.actualizar(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}