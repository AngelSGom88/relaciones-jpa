package dev.angel.relaciones._1_n_unidirec_a.service;

import dev.angel.relaciones._1_n_unidirec_a.domain.Direccion_1toN_UniA;
import dev.angel.relaciones._1_n_unidirec_a.domain.Persona_1toN_UniA;
import dev.angel.relaciones._1_n_unidirec_a.repository.Direccion_1toN_UniARepository;
import dev.angel.relaciones._1_n_unidirec_a.repository.Persona_1toN_UniARepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class Persona_1toN_UniAService {

    private final Persona_1toN_UniARepository personaRepo;
    private final Direccion_1toN_UniARepository dirRepo;

    public Persona_1toN_UniAService(Persona_1toN_UniARepository personaRepo,
                                    Direccion_1toN_UniARepository dirRepo) {
        this.personaRepo = personaRepo;
        this.dirRepo = dirRepo;
    }

    public List<Persona_1toN_UniA> listar() { return personaRepo.findAll(); }

    public Persona_1toN_UniA obtener(Long id) {
        return personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }

    public Persona_1toN_UniA crear(Persona_1toN_UniA p) {
        if (p.getId()!=null) p.setId(null);
        if (isBlank(p.getNombre())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre obligatorio");
        // si vienen direcciones en el body, las validamos/normalizamos
        if (p.getDirecciones()!=null) {
            for (Direccion_1toN_UniA d : p.getDirecciones()) validarDireccion(d);
            // pre-check duplicado global por cada dirección
            for (Direccion_1toN_UniA d : p.getDirecciones()) {
                if (dirRepo.existsByCalleAndCiudadAndCpAndProvincia(d.getCalle(), d.getCiudad(), d.getCp(), d.getProvincia())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
                }
            }
        }
        try {
            return personaRepo.save(p);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
        }
    }

    public Persona_1toN_UniA actualizar(Long id, Persona_1toN_UniA in) {
        Persona_1toN_UniA p = obtener(id);
        if (!isBlank(in.getNombre())) p.setNombre(in.getNombre());
        return personaRepo.save(p);
    }

    public void eliminar(Long id) {
        if (!personaRepo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada");
        // orphanRemoval + REMOVE se encargan de las direcciones
        personaRepo.deleteById(id);
    }

    // === hijos (direcciones) ===

    public Persona_1toN_UniA addDireccion(Long personaId, Direccion_1toN_UniA d) {
        Persona_1toN_UniA p = obtener(personaId);
        validarDireccion(d);
        if (dirRepo.existsByCalleAndCiudadAndCpAndProvincia(d.getCalle(), d.getCiudad(), d.getCp(), d.getProvincia())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
        }
        p.getDirecciones().add(d); // cascade PERSIST
        try {
            return personaRepo.save(p);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
        }
    }

    public void removeDireccion(Long personaId, Long dirId) {
        Persona_1toN_UniA p = obtener(personaId);
        boolean removed = false;
        Iterator<Direccion_1toN_UniA> it = p.getDirecciones().iterator();
        while (it.hasNext()) {
            Direccion_1toN_UniA d = it.next();
            if (dirId.equals(d.getId())) { it.remove(); removed = true; break; }
        }
        if (!removed) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dirección no encontrada en la persona");
        // orphanRemoval eliminará el registro
        personaRepo.save(p);
    }

    private void validarDireccion(Direccion_1toN_UniA d) {
        if (d==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dirección requerida");
        if (isBlank(d.getCalle()) || isBlank(d.getCiudad()) || isBlank(d.getCp()) || isBlank(d.getProvincia())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Calle/ciudad/cp/provincia obligatorios");
        }
        d.setId(null); // asegurar alta
    }

    private boolean isBlank(String s){ return s==null || s.isBlank(); }
}
