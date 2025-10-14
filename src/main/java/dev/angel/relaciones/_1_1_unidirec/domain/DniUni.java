package dev.angel.relaciones._1_1_unidirec.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "DniUni")
@Table(name = "DniUni")
public class DniUni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private LocalDate fechaExpedicion;

    public DniUni() {
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
        return "Dni{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", fechaExpedicion=" + fechaExpedicion +
                '}';
    }
}
