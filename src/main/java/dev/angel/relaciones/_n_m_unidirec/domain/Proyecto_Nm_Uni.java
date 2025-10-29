package dev.angel.relaciones._n_m_unidirec.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "proyecto_nm_uni")
public class Proyecto_Nm_Uni {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // No referencia inversa (unidireccional)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
