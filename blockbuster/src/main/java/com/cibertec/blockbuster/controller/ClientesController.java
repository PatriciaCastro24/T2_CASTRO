package com.cibertec.blockbuster.controller;

import com.cibertec.blockbuster.model.Clientes;
import com.cibertec.blockbuster.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientesController {

    @Autowired
    private ClientesRepository clientesRepo;

    // --- READ: Mostrar lista de clientes ---
    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clientesRepo.findAll());
        model.addAttribute("cliente", new Clientes());
        return "clientes";
    }

    // --- CREATE/UPDATE/DELETE según acción del botón ---
    @PostMapping("/clientes/guardar")
    public String procesarCliente(
            @ModelAttribute("cliente") Clientes cliente,
            @RequestParam("accion") String accion,
            RedirectAttributes redirectAttrs) {

        if ("agregar".equals(accion)) {
            try {
                clientesRepo.save(cliente);
                redirectAttrs.addFlashAttribute("mensajeExito", "Cliente agregado correctamente.");
            } catch (Exception e) {
                redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al agregar el cliente.");
            }
        } else if ("eliminar".equals(accion)) {
            if (cliente.getId_cliente() != null) {
                try {
                    clientesRepo.deleteById(cliente.getId_cliente());
                    redirectAttrs.addFlashAttribute("mensajeExito", "Cliente eliminado correctamente.");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "Error. Alquileres asociados.");
                }
            }
        } else if ("editar".equals(accion) || "guardar".equals(accion)) {
            if (cliente.getId_cliente() != null) {
                try {
                    clientesRepo.save(cliente);
                    redirectAttrs.addFlashAttribute("mensajeExito", "Cliente actualizado correctamente.");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al actualizar el cliente.");
                }
            }
        }
        return "redirect:/clientes";
    }

    // --- UPDATE: Mostrar formulario para editar cliente (opcional) ---
    @GetMapping("/clientes/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Clientes cliente = clientesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de cliente inválido: " + id));
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clientesRepo.findAll());
        return "clientes";
    }
}