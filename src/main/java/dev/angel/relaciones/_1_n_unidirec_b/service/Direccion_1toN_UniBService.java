package dev.angel.relaciones._1_n_unidirec_b.service;

import dev.angel.relaciones._1_n_unidirec_b.domain.Direccion_1toN_UniB;
import dev.angel.relaciones._1_n_unidirec_b.domain.Persona_1toN_UniB;
import dev.angel.relaciones._1_n_unidirec_b.repository.Direccion_1toN_UniBRepository;
import dev.angel.relaciones._1_n_unidirec_b.repository.Persona_1toN_UniBRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class Direccion_1toN_UniBService {

    private final Direccion_1toN_UniBRepository dirRepo;
    private final Persona_1toN_UniBRepository personaRepo;

    public Direccion_1toN_UniBService(Direccion_1toN_UniBRepository dirRepo,
                                      Persona_1toN_UniBRepository personaRepo) {
        this.dirRepo = dirRepo;
        this.personaRepo = personaRepo;
    }

    public List<Direccion_1toN_UniB> listarPorPersona(Long personaId) {
        if (personaId == null) return dirRepo.findAll();
        return dirRepo.findByPersonaId(personaId);
    }

    public Direccion_1toN_UniB obtener(Long id) {
        return dirRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dirección no encontrada"));
    }

    public Direccion_1toN_UniB crear(Direccion_1toN_UniB d) {
        validarCampos(d);
        Long personaId = (d.getPersona() != null) ? d.getPersona().getId() : null;
        if (personaId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "persona.id obligatorio");

        Persona_1toN_UniB persona = personaRepo.findById(personaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));

        // Pre-check duplicado global (calle,ciudad,cp,provincia)
        if (dirRepo.existsByCalleAndCiudadAndCpAndProvincia(d.getCalle(), d.getCiudad(), d.getCp(), d.getProvincia())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
        }

        d.setId(null);
        d.setPersona(persona);
        try {
            return dirRepo.save(d);
        } catch (DataIntegrityViolationException ex) {
            // refuerzo por UNIQUE en BD
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
        }
    }

    public Direccion_1toN_UniB actualizar(Long id, Direccion_1toN_UniB in) {
        Direccion_1toN_UniB actual = obtener(id);

        if (in.getPersona() != null && in.getPersona().getId() != null) {
            Persona_1toN_UniB nueva = personaRepo.findById(in.getPersona().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
            actual.setPersona(nueva);
        }

        if (!isBlank(in.getCalle())) actual.setCalle(in.getCalle());
        if (!isBlank(in.getCiudad())) actual.setCiudad(in.getCiudad());
        if (!isBlank(in.getCp())) actual.setCp(in.getCp());
        if (!isBlank(in.getProvincia())) actual.setProvincia(in.getProvincia());
        // etiqueta es opcional (puede ser null)
        if (in.getEtiqueta() != null) actual.setEtiqueta(in.getEtiqueta());

        validarCampos(actual);

        // Pre-check si cambió a uno existente
        if (dirRepo.existsByCalleAndCiudadAndCpAndProvincia(
                actual.getCalle(), actual.getCiudad(), actual.getCp(), actual.getProvincia())) {
            // permitir si es la misma fila
            boolean mismo = dirRepo.findById(actual.getId())
                    .map(d -> d.getCalle().equals(actual.getCalle())
                            && d.getCiudad().equals(actual.getCiudad())
                            && d.getCp().equals(actual.getCp())
                            && d.getProvincia().equals(actual.getProvincia()))
                    .orElse(false);
            if (!mismo) throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
        }

        try {
            return dirRepo.save(actual);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dirección duplicada");
        }
    }

    public void eliminar(Long id) {
        if (!dirRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dirección no encontrada");
        }
        dirRepo.deleteById(id);
    }

    private void validarCampos(Direccion_1toN_UniB d) {
        if (isBlank(d.getCalle()) || isBlank(d.getCiudad()) || isBlank(d.getCp()) || isBlank(d.getProvincia())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Calle/ciudad/cp/provincia obligatorios");
        }
    }
    private boolean isBlank(String s){ return s == null || s.isBlank(); }
}
