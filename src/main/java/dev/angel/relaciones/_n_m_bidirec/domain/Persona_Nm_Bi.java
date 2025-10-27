package dev.angel.relaciones._n_m_bidirec.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Persona_Nm_Bi {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    // Dueño de la relación (define la join table)
    @ManyToMany
    @JoinTable(
            name = "persona_proyecto_nm_bi",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "proyecto_id"),
            uniqueConstraints = @UniqueConstraint(name = "uk_persona_proyecto", columnNames = {"persona_id","proyecto_id"})
    )
    private Set<Proyecto_Nm_Bi> proyectos = new LinkedHashSet<>();

    // --- getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Set<Proyecto_Nm_Bi> getProyectos() { return proyectos; }
    public void setProyectos(Set<Proyecto_Nm_Bi> proyectos) { this.proyectos = proyectos; }
}
