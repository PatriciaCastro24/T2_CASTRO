package com.cibertec.blockbuster.controller;

import com.cibertec.blockbuster.model.DetalleAlquiler;
import com.cibertec.blockbuster.repository.DetalleAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DetalleAlquilerController {

    @Autowired
    private DetalleAlquilerRepository detalleRepo;

    // Visualizar listado de detalles de alquiler
    @GetMapping("/detalle-alquiler")
    public String mostrarDetalleAlquiler(Model model) {
        List<DetalleAlquiler> listaDetalles = detalleRepo.findAll();
        model.addAttribute("listaDetalles", listaDetalles);
        return "detalleAlquiler"; // nombre de la vista Thymeleaf
    }
}