package com.cibertec.blockbuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.blockbuster.model.Clientes;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    // No se requieren métodos adicionales para las operaciones básicas
}