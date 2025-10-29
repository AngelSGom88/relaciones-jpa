package dev.angel.relaciones._n_m_unidirec.service;

import dev.angel.relaciones._n_m_unidirec.domain.Persona_Nm_Uni;
import dev.angel.relaciones._n_m_unidirec.domain.Proyecto_Nm_Uni;
import dev.angel.relaciones._n_m_unidirec.repository.Persona_Nm_UniRepository;
import dev.angel.relaciones._n_m_unidirec.repository.Proyecto_Nm_UniRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class Persona_Nm_UniService {

    private final Persona_Nm_UniRepository personaRepo;
    private final Proyecto_Nm_UniRepository proyectoRepo;

    public Persona_Nm_UniService(Persona_Nm_UniRepository personaRepo, Proyecto_Nm_UniRepository proyectoRepo) {
        this.personaRepo = personaRepo;
        this.proyectoRepo = proyectoRepo;
    }

    public List<Persona_Nm_Uni> listar() { return personaRepo.findAll(); }

    public Persona_Nm_Uni obtener(Long id) {
        return personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }

    public Persona_Nm_Uni crear(Persona_Nm_Uni p) {
        if (p.getId() != null) p.setId(null);
        if (p.getNombre() == null || p.getNombre().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre obligatorio");
        try {
            return personaRepo.save(p);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicado");
        }
    }

    public Persona_Nm_Uni actualizar(Long id, Persona_Nm_Uni in) {
        Persona_Nm_Uni p = obtener(id);
        if (in.getNombre() != null && !in.getNombre().isBlank())
            p.setNombre(in.getNombre());
        return personaRepo.save(p);
    }

    public void eliminar(Long id) {
        if (!personaRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada");
        personaRepo.deleteById(id);
    }

    // === relaciones ===

    public Persona_Nm_Uni vincularProyecto(Long personaId, Long proyectoId) {
        Persona_Nm_Uni p = obtener(personaId);
        Proyecto_Nm_Uni pr = proyectoRepo.findById(proyectoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        if (p.getProyectos().contains(pr))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya vinculado");

        p.getProyectos().add(pr);
        return personaRepo.save(p);
    }

    public void desvincularProyecto(Long personaId, Long proyectoId) {
        Persona_Nm_Uni p = obtener(personaId);
        boolean removed = p.getProyectos().removeIf(pr -> pr.getId().equals(proyectoId));
        if (!removed)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Relación no existe");
        personaRepo.save(p);
    }
}
