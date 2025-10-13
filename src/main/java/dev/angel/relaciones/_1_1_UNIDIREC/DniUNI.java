package dev.angel.relaciones._1_1_UNIDIREC;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "DniUNI")
@Table(name = "DniUNI")
public class DniUNI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private LocalDate fechaExpedicion;

    public DniUNI() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    @Override
    public String toString() {
        return "DniUNI{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", fechaExpedicion=" + fechaExpedicion +
                '}';
    }
}
