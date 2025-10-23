package dev.angel.relaciones._1_n_unidirec_b.repository;

import dev.angel.relaciones._1_n_unidirec_b.domain.Direccion_1toN_UniB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Direccion_1toN_UniBRepository extends JpaRepository<Direccion_1toN_UniB,Long> {
    // Listar direcciones de una persona concreta (GET /direcciones?personaId=...)
    List<Direccion_1toN_UniB> findByPersonaId(Long personaId);

    // Pre-check de duplicado global (calle, ciudad, cp, provincia)
    boolean existsByCalleAndCiudadAndCpAndProvincia(String calle, String ciudad, String cp, String provincia);

    // Bulk delete de direcciones por persona (para borrar primero hijos y luego la persona)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Direccion_1toN_UniB d WHERE d.persona.id = :personaId")
    void deleteByPersonaId(@Param("personaId") Long personaId);

}
