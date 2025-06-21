package com.cibertec.blockbuster.controller;

import com.cibertec.blockbuster.model.Alquileres;
import com.cibertec.blockbuster.model.Clientes;
import com.cibertec.blockbuster.model.Peliculas;
import com.cibertec.blockbuster.model.DetalleAlquiler;
import com.cibertec.blockbuster.repository.AlquilerRepository;
import com.cibertec.blockbuster.repository.ClientesRepository;
import com.cibertec.blockbuster.repository.PeliculasRepository;
import com.cibertec.blockbuster.repository.DetalleAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AlquilerController {

    @Autowired
    private AlquilerRepository alquileresRepo;
    @Autowired
    private ClientesRepository clientesRepo;
    @Autowired
    private PeliculasRepository peliculasRepo;
    @Autowired
    private DetalleAlquilerRepository detalleRepo;

    // Mostrar formulario para registrar un nuevo alquiler
    @GetMapping("/alquileres/registrar")
    public String registrarAlquilerForm(Model model) {
        model.addAttribute("alquilerForm", new AlquilerForm());
        model.addAttribute("clientes", clientesRepo.findAll());
        model.addAttribute("peliculas", peliculasRepo.findAll());
        return "nuevoAlquiler"; // Cambiado para coincidir con la plantilla
    }

    // Procesar el registro del alquiler (sin validación de stock ni máximo de alquileres)
    @PostMapping("/alquileres/registrar")
    public String guardarAlquiler(
            @ModelAttribute("alquilerForm") AlquilerForm form,
            RedirectAttributes redirectAttrs) {
        try {
            Clientes cliente = clientesRepo.findById(form.getClienteId()).orElse(null);
            Peliculas pelicula = peliculasRepo.findById(form.getPeliculaId()).orElse(null);

            if (cliente == null || pelicula == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "Debe seleccionar un cliente y una película válidos.");
                return "redirect:/alquileres/registrar";
            }
            if (form.getCantidad() == null || form.getCantidad() < 1) {
                redirectAttrs.addFlashAttribute("mensajeError", "La cantidad debe ser mayor a cero.");
                return "redirect:/alquileres/registrar";
            }

            // Crear y guardar el alquiler
            Alquileres nuevoAlquiler = new Alquileres();
            nuevoAlquiler.setCliente(cliente);
            nuevoAlquiler.setFecha(LocalDate.now());
            nuevoAlquiler.setTotal(pelicula.getPrecio() * form.getCantidad());
            nuevoAlquiler.setEstado(Alquileres.EstadoAlquiler.Activo);
            alquileresRepo.save(nuevoAlquiler);

            // Crear y guardar el detalle del alquiler
            DetalleAlquiler detalle = new DetalleAlquiler(nuevoAlquiler, pelicula, form.getCantidad());
            detalleRepo.save(detalle);

            redirectAttrs.addFlashAttribute("mensajeExito", "Alquiler registrado correctamente.");
        } catch (Exception ex) {
            redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al registrar el alquiler.");
        }
        return "redirect:/alquileres/registrar";
    }

    // Listar todos los alquileres (con o sin filtro)
    @GetMapping("/alquileres/listado")
    public String verAlquileres(
            @RequestParam(value = "filtro", required = false) String filtro,
            Model model) {
        List<DetalleAlquiler> detalles;
        if (filtro != null && !filtro.trim().isEmpty()) {
            detalles = detalleRepo.buscarPorClientePeliculaOEstado(filtro.toLowerCase());
        } else {
            detalles = detalleRepo.findAll();
        }
        model.addAttribute("detalles", detalles);
        return "listado_alquileres";
    }

    // Eliminar un alquiler y sus detalles
    @PostMapping("/alquileres/eliminar")
    public String eliminarAlquiler(
            @RequestParam("idAlquiler") Long idAlquiler,
            RedirectAttributes redirectAttrs) {
        try {
            List<DetalleAlquiler> detalles = detalleRepo.findByAlquilerIdAlquiler(idAlquiler);
            detalleRepo.deleteAll(detalles);
            alquileresRepo.deleteById(idAlquiler);
            redirectAttrs.addFlashAttribute("mensajeExito", "Alquiler eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "No se pudo eliminar el alquiler.");
        }
        return "redirect:/alquileres/listado";
    }

    // Clase auxiliar para el formulario de alquiler
    public static class AlquilerForm {
        private Long clienteId;
        private Long peliculaId;
        private Integer cantidad;

        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
        public Long getPeliculaId() { return peliculaId; }
        public void setPeliculaId(Long peliculaId) { this.peliculaId = peliculaId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}