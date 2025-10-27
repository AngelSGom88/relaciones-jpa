package dev.angel.relaciones._n_m_bidirec.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Proyecto_Nm_Bi {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // opcional: nombre único para ejemplo
    private String nombre;

    @ManyToMany(mappedBy = "proyectos")
    @JsonIgnore
    private Set<Persona_Nm_Bi> personas = new LinkedHashSet<>();

    // --- getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Set<Persona_Nm_Bi> getPersonas() { return personas; }
    public void setPersonas(Set<Persona_Nm_Bi> personas) { this.personas = personas; }
}
