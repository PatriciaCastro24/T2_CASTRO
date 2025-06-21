package com.cibertec.blockbuster.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.time.LocalDate;

@Entity
public class Alquileres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_alquiler;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    private Double total;

    @Enumerated(EnumType.STRING)
    private EstadoAlquiler estado;

    public enum EstadoAlquiler {
        Activo, Devuelto, Retrasado
    }

    public Alquileres() {}

    public Alquileres(LocalDate fecha, Clientes cliente, Double total, EstadoAlquiler estado) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.total = total;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId_alquiler() {
        return id_alquiler;
    }

    public void setId_alquiler(Long id_alquiler) {
        this.id_alquiler = id_alquiler;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Alquiler de " + cliente.getNombre() + " el " + fecha;
    }
}