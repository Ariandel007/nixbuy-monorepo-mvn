package com.mvnnixbuyapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {

        if (error != null) {
            switch (error) {
                case "true", "credentials":
                    model.addAttribute("errorMessage", "Hubo un error. Por favor, verifica tus credenciales.");
                    break;
                default:
                    model.addAttribute("errorMessage", "Hubo un error. Por favor, intente más tarde.");
                    break;
            }
        }

        return "login"; // Return the login.html in templates/
    }
}
