package dev.angel.relaciones._n_m_bidirec.service;

import dev.angel.relaciones._n_m_bidirec.domain.Proyecto_Nm_Bi;
import dev.angel.relaciones._n_m_bidirec.repository.Proyecto_Nm_BiRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class Proyecto_Nm_BiService {

    private final Proyecto_Nm_BiRepository repo;

    public Proyecto_Nm_BiService(Proyecto_Nm_BiRepository repo) { this.repo = repo; }

    public List<Proyecto_Nm_Bi> listar(){ return repo.findAll(); }

    public Proyecto_Nm_Bi obtener(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));
    }

    public Proyecto_Nm_Bi crear(Proyecto_Nm_Bi p){
        if (p.getId()!=null) p.setId(null);
        if (isBlank(p.getNombre())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre obligatorio");
        try { return repo.save(p); }
        catch (DataIntegrityViolationException e){ throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre duplicado"); }
    }

    public Proyecto_Nm_Bi actualizar(Long id, Proyecto_Nm_Bi in){
        Proyecto_Nm_Bi p = obtener(id);
        if (!isBlank(in.getNombre())) p.setNombre(in.getNombre());
        try { return repo.save(p); }
        catch (DataIntegrityViolationException e){ throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre duplicado"); }
    }

    public void eliminar(Long id){
        if (!repo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado");
        repo.deleteById(id); // borra join rows por FK
    }

    private boolean isBlank(String s){ return s==null || s.isBlank(); }
}
