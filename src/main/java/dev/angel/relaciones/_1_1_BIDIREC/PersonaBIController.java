package dev.angel.relaciones._1_1_BIDIREC;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // para pruebas desde app.html
@RestController
@RequestMapping("/r11bi/personas")
public class PersonaBIController {

    private final PersonaBIRepository personaRepo;

    public PersonaBIController(PersonaBIRepository personaRepo) {
        this.personaRepo = personaRepo;
    }

    @GetMapping
    public List<PersonaBI> findAll() {
        return personaRepo.findAll();
    }

    @GetMapping("/{id}")
    public PersonaBI findOne(@PathVariable Long id) {
        return personaRepo.findById(id).orElseThrow(() -> new RuntimeException("No existe persona " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaBI create(@RequestBody @Valid PersonaBI persona) {
        // Con tus setters sincronizados bastaría con setDni,
        // pero si quieres ser explícito y robusto:
        DniBI dni = persona.getDni();
        if (dni != null) {
            dni.setPersonaBI(persona);
            persona.setDni(dni);
        }
        return personaRepo.save(persona);
    }

    @PutMapping("/{id}")
    public PersonaBI update(@PathVariable Long id, @RequestBody @Valid PersonaBI body) {
        PersonaBI p = personaRepo.findById(id).orElseThrow(() -> new RuntimeException("No existe persona " + id));
        p.setNombre(body.getNombre());
        if (body.getDni() != null) {
            DniBI dniBody = body.getDni();
            if (p.getDni() == null) {
                p.setDni(dniBody);
            } else {
                p.getDni().setNumero(dniBody.getNumero());
                p.getDni().setFechaExpedicion(dniBody.getFechaExpedicion());
            }
        }
        return personaRepo.save(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        PersonaBI p = personaRepo.findById(id).orElseThrow(() -> new RuntimeException("No existe persona " + id));
        personaRepo.delete(p);
    }
}
