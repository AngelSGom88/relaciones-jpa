package dev.angel.relaciones._n_m_unidirec.repository;

import dev.angel.relaciones._n_m_unidirec.domain.Proyecto_Nm_Uni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Proyecto_Nm_UniRepository extends JpaRepository<Proyecto_Nm_Uni, Long> {
    boolean existsByNombre(String nombre);
}
