package com.cibertec.blockbuster.repository;

import com.cibertec.blockbuster.model.Peliculas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculasRepository extends JpaRepository<Peliculas, Long> {
    // No se requieren métodos adicionales para las operaciones básicas
}