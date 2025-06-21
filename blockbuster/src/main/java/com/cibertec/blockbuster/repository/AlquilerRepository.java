package com.cibertec.blockbuster.repository;

import com.cibertec.blockbuster.model.Alquileres;
import com.cibertec.blockbuster.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquileres, Long> {

    // Cuenta los alquileres activos o retrasados por cliente
    long countByClienteAndEstadoIn(Clientes cliente, List<Alquileres.EstadoAlquiler> estados);

    // Obtiene los clientes con al menos un alquiler activo o retrasado
    @Query("SELECT DISTINCT a.cliente FROM Alquileres a WHERE a.estado = com.cibertec.blockbuster.model.Alquileres$EstadoAlquiler.Activo OR a.estado = com.cibertec.blockbuster.model.Alquileres$EstadoAlquiler.Retrasado")
    List<Clientes> findClientesConAlquilerActivoORRetrasado();
}