package dev.angel.relaciones._1_n_bidirec.service;

import dev.angel.relaciones._1_n_bidirec.domain.Direccion_1toN_Bi;
import dev.angel.relaciones._1_n_bidirec.domain.Persona_1toN_Bi;
import dev.angel.relaciones._1_n_bidirec.repository.Direccion_1toN_BiRepository;
import dev.angel.relaciones._1_n_bidirec.repository.Persona_1toN_BiRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class Persona_1toN_BiService {

    private final Persona_1toN_BiRepository personaRepo;
    private final Direccion_1toN_BiRepository dirRepo;

    public Persona_1toN_BiService(Persona_1toN_BiRepository personaRepo,
                                  Direccion_1toN_BiRepository dirRepo) {
        this.personaRepo = personaRepo;
        this.dirRepo = dirRepo;
    }

    // ---- Persona ----
    @Transactional(readOnly = true)
    public List<Persona_1toN_Bi> listar() { return personaRepo.findAll(); }

    @Transactional(readOnly = true)
    public Persona_1toN_Bi obtener(Long id) {
        return personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
    }

    public Persona_1toN_Bi crear(Persona_1toN_Bi in) {
        // sincroniza lados y valida mínimos
        in.getDirecciones().forEach(d -> {
            validarDireccionCampos(d);
            d.setPersona(in);
            // pre-check (unicidad global)
            if (dirRepo.existsByCalleAndCiudadAndCpAndProvincia(
                    d.getCalle(), d.getCiudad(), d.getCp(), d.getProvincia())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La dirección ya existe en el sistema");
            }
        });

        try {
            return personaRepo.save(in);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La dirección ya existe en el sistema");
        }
    }

    public Persona_1toN_Bi actualizar(Long id, Persona_1toN_Bi in) {
        Persona_1toN_Bi db = obtener(id);
        db.setNombre(in.getNombre());

        // Reemplazo total (PUT) — activa orphanRemoval
        db.clearAndSetDirecciones(in.getDirecciones());

        // validar + pre-check global
        db.getDirecciones().forEach(this::validarDireccionCampos);
        db.getDirecciones().forEach(d -> {
            if (dirRepo.existsByCalleAndCiudadAndCpAndProvincia(
                    d.getCalle(), d.getCiudad(), d.getCp(), d.getProvincia())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La dirección ya existe en el sistema");
            }
        });

        try {
            return personaRepo.save(db);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La dirección ya existe en el sistema");
        }
    }

    public void eliminar(Long id) {
        Persona_1toN_Bi db = obtener(id);
        personaRepo.delete(db); // orphanRemoval borra sus direcciones primero
    }

    // ---- Direcciones anidadas ----
    public Direccion_1toN_Bi agregarDireccion(Long personaId, Direccion_1toN_Bi d) {
        Persona_1toN_Bi p = obtener(personaId);
        validarDireccionCampos(d);

        if (dirRepo.existsByCalleAndCiudadAndCpAndProvincia(
                d.getCalle(), d.getCiudad(), d.getCp(), d.getProvincia())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La dirección ya existe en el sistema");
        }

        p.addDireccion(d);
        try {
            personaRepo.save(p);
            return d;
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La dirección ya existe en el sistema");
        }
    }

    public void eliminarDireccion(Long personaId, Long dirId) {
        Persona_1toN_Bi p = obtener(personaId);
        p.removeDireccionById(dirId);
        personaRepo.save(p);
    }

    private void validarDireccionCampos(Direccion_1toN_Bi d) {
        if (isBlank(d.getCalle()) || isBlank(d.getCiudad()) || isBlank(d.getCp()) || isBlank(d.getProvincia())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Calle/ciudad/cp/provincia obligatorios");
        }
    }

    private boolean isBlank(String s){ return s == null || s.isBlank(); }
}
