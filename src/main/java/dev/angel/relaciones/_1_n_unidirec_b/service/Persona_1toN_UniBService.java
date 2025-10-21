package dev.angel.relaciones._1_n_unidirec_b.service;

import dev.angel.relaciones._1_n_unidirec_b.domain.Persona_1toN_UniB;
import dev.angel.relaciones._1_n_unidirec_b.repository.Persona_1toN_UniBRepository;
import dev.angel.relaciones._1_n_unidirec_b.repository.Direccion_1toN_UniBRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class Persona_1toN_UniBService {

    private final Persona_1toN_UniBRepository personaRepo;
    private final Direccion_1toN_UniBRepository dirRepo;

    public Persona_1toN_UniBService(Persona_1toN_UniBRepository personaRepo,
                                    Direccion_1toN_UniBRepository dirRepo) {
        this.personaRepo = personaRepo;
        this.dirRepo = dirRepo;
    }

    public List<Persona_1toN_UniB> listar() {
        return personaRepo.findAll();
    }

    public Persona_1toN_UniB obtener(Long id) {
        return personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }

    public Persona_1toN_UniB crear(Persona_1toN_UniB p) {
        if (p.getId() != null) p.setId(null);
        if (isBlank(p.getNombre())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre obligatorio");
        }
        return personaRepo.save(p);
    }

    public Persona_1toN_UniB actualizar(Long id, Persona_1toN_UniB p) {
        Persona_1toN_UniB actual = obtener(id);
        if (!isBlank(p.getNombre())) actual.setNombre(p.getNombre());
        return personaRepo.save(actual);
    }

    public void eliminar(Long id) {
        // En UNI-B hay FK en Direccion -> Persona. Limpiamos primero.
        if (!personaRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada");
        }
        dirRepo.deleteByPersonaId(id);
        personaRepo.deleteById(id);
    }

    private boolean isBlank(String s){ return s == null || s.isBlank(); }
}
