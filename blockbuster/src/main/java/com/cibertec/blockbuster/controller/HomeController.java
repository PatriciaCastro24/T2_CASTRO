package com.cibertec.blockbuster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/inicio"})
    public String inicio() {
        return "index"; // Busca index.html en templates
    }
}