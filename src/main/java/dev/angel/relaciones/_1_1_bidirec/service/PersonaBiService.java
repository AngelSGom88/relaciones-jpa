package dev.angel.relaciones._1_1_bidirec.service;

import dev.angel.relaciones._1_1_bidirec.domain.DniBi;
import dev.angel.relaciones._1_1_bidirec.domain.PersonaBi;
import dev.angel.relaciones._1_1_bidirec.repository.DniBiRepository;
import dev.angel.relaciones._1_1_bidirec.repository.PersonaBiRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonaBiService {

    private final PersonaBiRepository personaRepo;
    private final DniBiRepository dniRepo;

    public PersonaBiService(PersonaBiRepository personaRepo, DniBiRepository dniRepo) {
        this.personaRepo = personaRepo;
        this.dniRepo = dniRepo;
    }

    /* ========== READ ========== */

    public List<PersonaBi> listar() {
        return personaRepo.findAll();
    }

    public PersonaBi obtener(Long id) {
        return personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe persona " + id));
    }

    /* ========== CREATE ========== */

    @Transactional
    public PersonaBi crear(PersonaBi p) {
        // Validación de DNI duplicado (mejor UX antes de chocar con la BD)
        if (p.getDni() != null && dniRepo.existsByNumero(p.getDni().getNumero())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI duplicado");
        }

        // Sincroniza lados BI (por si el JSON viene sin enlazar)
        if (p.getDni() != null && p.getDni().getPersona() != p) {
            p.getDni().setPersona(p);
        }

        return personaRepo.save(p);
    }

    /* ========== UPDATE ========== */

    @Transactional
    public PersonaBi actualizar(Long id, PersonaBi body) {
        PersonaBi p = personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe persona " + id));

        // Actualiza nombre
        p.setNombre(body.getNombre());

        // Gestión de DNI
        if (body.getDni() == null) {
            // Si envías dni = null, interpretamos "quitar DNI".
            // En BI el owning side está en DniBI (@JoinColumn persona_id, normalmente nullable=false).
            // Para evitar constraint, borramos el DNI y luego desacoplamos.
            if (p.getDni() != null) {
                DniBi actual = p.getDni();
                actual.setPersona(null);
                p.setDni(null);
                dniRepo.delete(actual); // elimina la fila de dni
            }
        } else {
            DniBi dniBody = body.getDni();

            // ¿Cambia el número? -> valida duplicado (salvo que sea el mismo DNI)
            boolean cambiaNumero = p.getDni() == null
                    || dniBody.getNumero() != null
                    && !dniBody.getNumero().equals(p.getDni().getNumero());

            if (cambiaNumero && dniRepo.existsByNumero(dniBody.getNumero())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI duplicado");
            }

            if (p.getDni() == null) {
                // No tenía DNI: asignamos uno nuevo y sincronizamos el lado inverso
                p.setDni(dniBody);
                dniBody.setPersona(p);
            } else {
                // Ya tenía DNI: actualizamos campos
                p.getDni().setNumero(dniBody.getNumero());
                p.getDni().setFechaExpedicion(dniBody.getFechaExpedicion());
            }
        }

        return personaRepo.save(p);
    }

    /* ========== DELETE ========== */

    @Transactional
    public void eliminar(Long id) {
        PersonaBi p = personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe persona " + id));

        // Si hay DNI asociado, limpia relación y elimina el DNI (para no dejar colgando la FK del owning side)
        if (p.getDni() != null) {
            DniBi actual = p.getDni();
            actual.setPersona(null);
            p.setDni(null);
            dniRepo.delete(actual);
        }

        personaRepo.delete(p);
    }
}
