package dev.angel.relaciones._n_m_bidirec.repository;

import dev.angel.relaciones._n_m_bidirec.domain.Proyecto_Nm_Bi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Proyecto_Nm_BiRepository extends JpaRepository<Proyecto_Nm_Bi, Long> {
    boolean existsByNombre(String nombre);
}
