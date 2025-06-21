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

import java.util.Collections;
import java.util.List;

@Controller
public class DevolverController {

    @Autowired
    private AlquilerRepository alquilerRepo;
    @Autowired
    private ClientesRepository clientesRepo;
    @Autowired
    private PeliculasRepository peliculasRepo;
    @Autowired
    private DetalleAlquilerRepository detalleRepo;

    // Mostrar formulario de devolución
    @GetMapping("/devoluciones/nueva")
    public String mostrarFormularioDevolucion(
            @RequestParam(value = "cliente", required = false) Long clienteId,
            Model model) {

        List<Clientes> clientesConAlquiler = alquilerRepo.findClientesConAlquilerActivoORRetrasado();
        List<Peliculas> peliculasCliente = (clienteId != null)
                ? detalleRepo.obtenerPeliculasAlquiladasPorCliente(clienteId)
                : Collections.emptyList();

        DevolucionForm form = new DevolucionForm();
        if (clienteId != null) form.setClienteId(clienteId);

        model.addAttribute("clientesConAlquiler", clientesConAlquiler);
        model.addAttribute("peliculasCliente", peliculasCliente);
        model.addAttribute("devolucionForm", form);
        return "devolucion";
    }

    // Procesar devolución (sin cantidad)
    @PostMapping("/devoluciones/nueva")
    public String procesarDevolucion(
            @ModelAttribute("devolucionForm") DevolucionForm form,
            RedirectAttributes redirectAttrs) {
        try {
            Clientes cliente = clientesRepo.findById(form.getClienteId()).orElse(null);
            Peliculas pelicula = peliculasRepo.findById(form.getPeliculaId()).orElse(null);

            if (cliente == null || pelicula == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "Seleccione cliente y película válidos.");
                return "redirect:/devoluciones/nueva";
            }

            Alquileres alquiler = detalleRepo.buscarAlquilerActivoPorClienteYPelicula(
                    cliente.getId_cliente(), pelicula.getId_pelicula());
            if (alquiler == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "No hay alquiler activo/retrasado para ese cliente y película.");
                return "redirect:/devoluciones/nueva?cliente=" + cliente.getId_cliente();
            }

            DetalleAlquiler detalle = detalleRepo.findByAlquilerAndPelicula(alquiler, pelicula);
            if (detalle == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "No se encontró el detalle de alquiler.");
                return "redirect:/devoluciones/nueva?cliente=" + cliente.getId_cliente();
            }

            // Devolver toda la cantidad alquilada
            int cantidadADevolver = detalle.getCantidad();

            // Actualizar stock
            pelicula.setStock(pelicula.getStock() + cantidadADevolver);
            peliculasRepo.save(pelicula);

            // Eliminar el detalle (ya se devolvió todo)
            detalleRepo.delete(detalle);

            // Cambiar estado del alquiler si ya no quedan detalles
            if (detalleRepo.countByAlquiler(alquiler) == 0) {
                alquiler.setEstado(Alquileres.EstadoAlquiler.Devuelto);
                alquilerRepo.save(alquiler);
            }

            redirectAttrs.addFlashAttribute("mensajeExito", "Devolución realizada correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al procesar la devolución.");
        }
        return "redirect:/devoluciones/nueva";
    }

    public static class DevolucionForm {
        private Long clienteId;
        private Long peliculaId;

        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
        public Long getPeliculaId() { return peliculaId; }
        public void setPeliculaId(Long peliculaId) { this.peliculaId = peliculaId; }
    }
}