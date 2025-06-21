package com.cibertec.blockbuster.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;

@Entity
@IdClass(DetalleAlquiler.DetalleAlquilerId.class)
public class DetalleAlquiler {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_alquiler", nullable = false)
    private Alquileres alquiler;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Peliculas pelicula;

    private Integer cantidad;

    public DetalleAlquiler() {}

    public DetalleAlquiler(Alquileres alquiler, Peliculas pelicula, Integer cantidad) {
        this.alquiler = alquiler;
        this.pelicula = pelicula;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Alquileres getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquileres alquiler) {
        this.alquiler = alquiler;
    }

    public Peliculas getPelicula() {
        return pelicula;
    }

    public void setPelicula(Peliculas pelicula) {
        this.pelicula = pelicula;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Detalle_alquiler [alquiler=" + (alquiler != null ? alquiler.getId_alquiler() : null) +
               ", pelicula=" + (pelicula != null ? pelicula.getId_pelicula() : null) +
               ", cantidad=" + cantidad + "]";
    }

    // Clase estática para la clave compuesta
    public static class DetalleAlquilerId implements Serializable {
        private Long alquiler;
        private Long pelicula;

        public DetalleAlquilerId() {}

        public DetalleAlquilerId(Long alquiler, Long pelicula) {
            this.alquiler = alquiler;
            this.pelicula = pelicula;
        }

        // Getters y Setters
        public Long getAlquiler() {
            return alquiler;
        }

        public void setAlquiler(Long alquiler) {
            this.alquiler = alquiler;
        }

        public Long getPelicula() {
            return pelicula;
        }

        public void setPelicula(Long pelicula) {
            this.pelicula = pelicula;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DetalleAlquilerId that = (DetalleAlquilerId) o;
            return java.util.Objects.equals(alquiler, that.alquiler) &&
                   java.util.Objects.equals(pelicula, that.pelicula);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(alquiler, pelicula);
        }
    }
}