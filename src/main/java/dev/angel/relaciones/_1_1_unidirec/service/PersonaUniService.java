package dev.angel.relaciones._1_1_unidirec.service;

import dev.angel.relaciones._1_1_unidirec.domain.DniUni;
import dev.angel.relaciones._1_1_unidirec.domain.PersonaUni;
import dev.angel.relaciones._1_1_unidirec.repository.DniUniRepository;
import dev.angel.relaciones._1_1_unidirec.repository.PersonaUniRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonaUniService {

    private final PersonaUniRepository personaRepo;
    private final DniUniRepository dniRepo;

    public PersonaUniService(PersonaUniRepository personaRepo, DniUniRepository dniRepo) {
        this.personaRepo = personaRepo;
        this.dniRepo = dniRepo;
    }

    public List<PersonaUni> listar() {
        return personaRepo.findAll();
    }

    public PersonaUni obtener(Long id) {
        return personaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe persona " + id));
    }

    @Transactional
    public PersonaUni crear(PersonaUni p) {
        // Pre-chequeo UX: evita choque tarde en BD
        if (p.getDni() != null && dniRepo.existsByNumero(p.getDni().getNumero())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI duplicado");
        }
        // En UNI, cascade PERSIST/MERGE + orphanRemoval en PersonaUni.dni se encarga del resto
        return personaRepo.save(p);
    }

    @Transactional
    public PersonaUni actualizar(Long id, PersonaUni body) {
        PersonaUni p = obtener(id);
        p.setNombre(body.getNombre());

        if (body.getDni() == null) {
            // Quitar DNI: orphanRemoval = true lo elimina
            p.setDni(null);
        } else {
            DniUni dniBody = body.getDni();

            boolean cambiaNumero = p.getDni() == null
                    || (dniBody.getNumero() != null && !dniBody.getNumero().equals(p.getDni().getNumero()));

            if (cambiaNumero && dniRepo.existsByNumero(dniBody.getNumero())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "DNI duplicado");
            }

            if (p.getDni() == null) {
                p.setDni(dniBody);
            } else {
                p.getDni().setNumero(dniBody.getNumero());
                p.getDni().setFechaExpedicion(dniBody.getFechaExpedicion());
            }
        }
        return personaRepo.save(p);
    }

    @Transactional
    public void eliminar(Long id) {
        PersonaUni p = obtener(id);
        // Al borrar persona, si tienes orphanRemoval en la relación, el DNI se elimina solo
        personaRepo.delete(p);
    }
}