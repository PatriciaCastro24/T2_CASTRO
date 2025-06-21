package com.cibertec.blockbuster.repository;

import com.cibertec.blockbuster.model.DetalleAlquiler;
import com.cibertec.blockbuster.model.DetalleAlquiler.DetalleAlquilerId;
import com.cibertec.blockbuster.model.Alquileres;
import com.cibertec.blockbuster.model.Peliculas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetalleAlquilerRepository extends JpaRepository<DetalleAlquiler, DetalleAlquilerId> {

    // Búsqueda flexible por cliente, película o estado
    @Query("SELECT d FROM DetalleAlquiler d WHERE " +
           "LOWER(d.alquiler.cliente.nombre) LIKE %:filtro% OR " +
           "LOWER(d.pelicula.titulo) LIKE %:filtro% OR " +
           "LOWER(d.alquiler.estado) LIKE %:filtro%")
    List<DetalleAlquiler> buscarPorClientePeliculaOEstado(@Param("filtro") String filtro);

    // Buscar detalles por id de alquiler (usa el nombre real del campo en Alquileres)
    @Query("SELECT d FROM DetalleAlquiler d WHERE d.alquiler.id_alquiler = :idAlquiler")
    List<DetalleAlquiler> findByAlquilerIdAlquiler(@Param("idAlquiler") Long idAlquiler);

    // Películas alquiladas actualmente por un cliente
    @Query("SELECT DISTINCT d.pelicula FROM DetalleAlquiler d WHERE d.alquiler.cliente.id_cliente = :clienteId AND (d.alquiler.estado = com.cibertec.blockbuster.model.Alquileres$EstadoAlquiler.Activo OR d.alquiler.estado = com.cibertec.blockbuster.model.Alquileres$EstadoAlquiler.Retrasado)")
    List<Peliculas> obtenerPeliculasAlquiladasPorCliente(@Param("clienteId") Long clienteId);

    // Buscar alquiler activo o retrasado por cliente y película
    @Query("SELECT d.alquiler FROM DetalleAlquiler d WHERE d.alquiler.cliente.id_cliente = :clienteId AND d.pelicula.id_pelicula = :peliculaId AND (d.alquiler.estado = com.cibertec.blockbuster.model.Alquileres$EstadoAlquiler.Activo OR d.alquiler.estado = com.cibertec.blockbuster.model.Alquileres$EstadoAlquiler.Retrasado)")
    Alquileres buscarAlquilerActivoPorClienteYPelicula(@Param("clienteId") Long clienteId, @Param("peliculaId") Long peliculaId);

    // Buscar detalle por alquiler y película
    DetalleAlquiler findByAlquilerAndPelicula(Alquileres alquiler, Peliculas pelicula);

    // Contar detalles de un alquiler
    long countByAlquiler(Alquileres alquiler);
}