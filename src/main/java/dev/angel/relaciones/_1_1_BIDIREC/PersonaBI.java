package dev.angel.relaciones._1_1_BIDIREC;

import jakarta.persistence.*;

@Entity(name = "PersonaBI")
@Table(name = "PersonaBI")
public class PersonaBI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @OneToOne(mappedBy = "personaBI", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private DniBI dniBI;

    public PersonaBI() {
        super();
    }

    public DniBI getDni() {
        return dniBI;
    }

    public void setDni(DniBI dni) {
        this.dniBI = dni;
        // Evita recursión infinita y mantiene ambos lados sincronizados
        if (dni != null && dni.getPersonaBI() != this) {
            dni.setPersonaBI(this);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
