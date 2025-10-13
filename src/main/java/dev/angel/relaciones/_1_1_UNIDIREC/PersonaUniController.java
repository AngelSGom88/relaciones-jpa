package dev.angel.relaciones._1_1_UNIDIREC;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // para pruebas desde app.html
@RestController
@RequestMapping("/r11uni/personas")
public class PersonaUniController {

    private final PersonaUNIRepository personaRepo;

    public PersonaUniController(PersonaUNIRepository personaRepo) {
        this.personaRepo = personaRepo;
    }

    @GetMapping
    public List<PersonaUNI> findAll() {
        return personaRepo.findAll();
    }

    @GetMapping("/{id}")
    public PersonaUNI findOne(@PathVariable Long id) {
        return personaRepo.findById(id).orElseThrow(() -> new RuntimeException("No existe persona " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaUNI create(@RequestBody @Valid PersonaUNI persona) {
        return personaRepo.save(persona);
    }

    @PutMapping("/{id}")
    public PersonaUNI update(@PathVariable Long id, @RequestBody @Valid PersonaUNI body) {
        PersonaUNI p = personaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe persona con id " + id));

        // Actualiza el nombre
        p.setNombre(body.getNombre());

        // Si se envía un DNI en el body
        if (body.getDniUNI() != null) {
            DniUNI dniBody = body.getDniUNI();

            // Si no existía DNI, lo asignamos completo
            if (p.getDniUNI() == null) {
                p.setDniUNI(dniBody);
            } else {
                // Si ya existía, actualizamos sus campos
                p.getDniUNI().setNumero(dniBody.getNumero());
                p.getDniUNI().setFechaExpedicion(dniBody.getFechaExpedicion());
            }
        } else {
            // Si se envía DNI = null, eliminamos el existente (por orphanRemoval)
            p.setDniUNI(null);
        }

        // Guarda y devuelve la entidad actualizada
        return personaRepo.save(p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        PersonaUNI p = personaRepo.findById(id).orElseThrow(() -> new RuntimeException("No existe persona " + id));
        personaRepo.delete(p);
    }

}
