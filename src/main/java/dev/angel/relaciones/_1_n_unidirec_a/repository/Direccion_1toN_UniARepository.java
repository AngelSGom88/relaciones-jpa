package dev.angel.relaciones._1_n_unidirec_a.repository;

import dev.angel.relaciones._1_n_unidirec_a.domain.Direccion_1toN_UniA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Direccion_1toN_UniARepository extends JpaRepository<Direccion_1toN_UniA, Long> {
    boolean existsByCalleAndCiudadAndCpAndProvincia(String calle, String ciudad, String cp, String provincia);
}
