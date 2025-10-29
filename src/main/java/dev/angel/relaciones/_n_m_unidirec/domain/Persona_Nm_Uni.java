package dev.angel.relaciones._n_m_unidirec.domain;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "persona_nm_uni")
public class Persona_Nm_Uni {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "persona_proyecto_nm_uni",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "proyecto_id"),
            uniqueConstraints = @UniqueConstraint(name = "uk_persona_proyecto_uni", columnNames = {"persona_id", "proyecto_id"})
    )
    private Set<Proyecto_Nm_Uni> proyectos = new LinkedHashSet<>();

    // Getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Set<Proyecto_Nm_Uni> getProyectos() { return proyectos; }
    public void setProyectos(Set<Proyecto_Nm_Uni> proyectos) { this.proyectos = proyectos; }
}
