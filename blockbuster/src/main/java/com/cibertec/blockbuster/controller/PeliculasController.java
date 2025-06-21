package com.cibertec.blockbuster.controller;

import com.cibertec.blockbuster.model.Peliculas;
import com.cibertec.blockbuster.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PeliculasController {

    @Autowired
    private PeliculasRepository peliculasRepo;

    // Mostrar todas las películas
    @GetMapping("/peliculas")
    public String mostrarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculasRepo.findAll());
        model.addAttribute("pelicula", new Peliculas());
        return "peliculas";
    }

    // Procesar acciones: agregar, eliminar, editar, guardar
    @PostMapping("/peliculas/guardar")
    public String procesarPelicula(
            @ModelAttribute("pelicula") Peliculas pelicula,
            @RequestParam("accion") String accion,
            RedirectAttributes redirectAttrs) {

        switch (accion) {
            case "agregar":
                if (pelicula.getId_pelicula() == null) {
                    try {
                        peliculasRepo.save(pelicula);
                        redirectAttrs.addFlashAttribute("mensajeExito", "Película agregada correctamente.");
                    } catch (Exception e) {
                        redirectAttrs.addFlashAttribute("mensajeError", "Error al agregar la película.");
                    }
                }
                break;
            case "eliminar":
                if (pelicula.getId_pelicula() != null) {
                    try {
                        peliculasRepo.deleteById(pelicula.getId_pelicula());
                        redirectAttrs.addFlashAttribute("mensajeExito", "Película eliminada correctamente.");
                    } catch (Exception e) {
                        redirectAttrs.addFlashAttribute("mensajeError", "Error. Película alquilada.");
                    }
                }
                break;
            case "editar":
            case "guardar":
                if (pelicula.getId_pelicula() != null) {
                    try {
                        peliculasRepo.save(pelicula);
                        redirectAttrs.addFlashAttribute("mensajeExito", "Película actualizada correctamente.");
                    } catch (Exception e) {
                        redirectAttrs.addFlashAttribute("mensajeError", "Error al actualizar la película.");
                    }
                }
                break;
        }
        return "redirect:/peliculas";
    }

    // Cargar datos de una película para editar
    @GetMapping("/peliculas/editar/{id}")
    public String editarPelicula(@PathVariable("id") Long id, Model model) {
        Peliculas pelicula = peliculasRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de película inválido: " + id));
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("peliculas", peliculasRepo.findAll());
        return "peliculas";
    }
}