package dev.angel.relaciones._n_m_bidirec.service;

import dev.angel.relaciones._n_m_bidirec.domain.Persona_Nm_Bi;
import dev.angel.relaciones._n_m_bidirec.domain.Proyecto_Nm_Bi;
import dev.angel.relaciones._n_m_bidirec.repository.Persona_Nm_BiRepository;
import dev.angel.relaciones._n_m_bidirec.repository.Proyecto_Nm_BiRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class Persona_Nm_BiService {

    private final Persona_Nm_BiRepository personaRepo;
    private final Proyecto_Nm_BiRepository proyectoRepo;

    public Persona_Nm_BiService(Persona_Nm_BiRepository personaRepo, Proyecto_Nm_BiRepository proyectoRepo) {
        this.personaRepo = personaRepo;
        this.proyectoRepo = proyectoRepo;
    }

    public List<Persona_Nm_Bi> listar(){ return personaRepo.findAll(); }

    public Persona_Nm_Bi obtener(Long id){
        return personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }

    public Persona_Nm_Bi crear(Persona_Nm_Bi p){
        if (p.getId()!=null) p.setId(null);
        if (isBlank(p.getNombre())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre obligatorio");
        try { return personaRepo.save(p); }
        catch (DataIntegrityViolationException e){ throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicado"); }
    }

    public Persona_Nm_Bi actualizar(Long id, Persona_Nm_Bi in){
        Persona_Nm_Bi p = obtener(id);
        if (!isBlank(in.getNombre())) p.setNombre(in.getNombre());
        return personaRepo.save(p);
    }

    public void eliminar(Long id){
        if (!personaRepo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada");
        // La join table se limpia sola (FK ON DELETE CASCADE o por JPA)
        personaRepo.deleteById(id);
    }

    // === relaciones ===

    public Persona_Nm_Bi vincularProyecto(Long personaId, Long proyectoId){
        Persona_Nm_Bi p = obtener(personaId);
        Proyecto_Nm_Bi pr = proyectoRepo.findById(proyectoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));
        if (p.getProyectos().contains(pr))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya vinculado");
        p.getProyectos().add(pr);
        pr.getPersonas().add(p); // mantener ambos lados
        try { return personaRepo.save(p); }
        catch (DataIntegrityViolationException e){ throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicado"); }
    }

    public void desvincularProyecto(Long personaId, Long proyectoId){
        Persona_Nm_Bi p = obtener(personaId);
        Proyecto_Nm_Bi pr = proyectoRepo.findById(proyectoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        boolean removed = p.getProyectos().remove(pr);
        pr.getPersonas().remove(p);
        if (!removed) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Relación no existe");

        personaRepo.save(p);
    }

    private boolean isBlank(String s){ return s==null || s.isBlank(); }
}
