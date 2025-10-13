package dev.angel.relaciones._1_1_UNIDIREC;


import jakarta.persistence.*;

@Entity(name = "PersonaUNI")
@Table(name = "PersonaUNI")
public class PersonaUNI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="dni_id")
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private DniUNI dniUNI;

    public PersonaUNI() {
        super();
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

    public DniUNI getDniUNI() {
        return dniUNI;
    }

    public void setDniUNI(DniUNI dniUNI) {
        this.dniUNI = dniUNI;
    }

    @Override
    public String toString() {
        return "PersonaUNI{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
}
