package dev.angel.relaciones._n_m_unidirec.service;

import dev.angel.relaciones._n_m_unidirec.domain.Proyecto_Nm_Uni;
import dev.angel.relaciones._n_m_unidirec.repository.Proyecto_Nm_UniRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class Proyecto_Nm_UniService {

    private final Proyecto_Nm_UniRepository repo;

    public Proyecto_Nm_UniService(Proyecto_Nm_UniRepository repo) {
        this.repo = repo;
    }

    public List<Proyecto_Nm_Uni> listar() { return repo.findAll(); }

    public Proyecto_Nm_Uni obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));
    }

    public Proyecto_Nm_Uni crear(Proyecto_Nm_Uni p) {
        if (p.getId() != null) p.setId(null);
        if (p.getNombre() == null || p.getNombre().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nombre obligatorio");
        try {
            return repo.save(p);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre duplicado");
        }
    }

    public Proyecto_Nm_Uni actualizar(Long id, Proyecto_Nm_Uni in) {
        Proyecto_Nm_Uni p = obtener(id);
        if (in.getNombre() != null && !in.getNombre().isBlank())
            p.setNombre(in.getNombre());
        try {
            return repo.save(p);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre duplicado");
        }
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado");
        repo.deleteById(id);
    }
}
