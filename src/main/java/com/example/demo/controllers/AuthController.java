package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.AuthService;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public String home() {
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login"; // Redirigir a inicio de sesión
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam(required = false) String role,
                        HttpSession session,
                        Model model) {
        Optional<UserEntity> user;

        if (role != null) {
            user = authService.loginWithRole(email, password, role);
        } else {
            user = authService.login(email, password);
        }

        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            return "redirect:/products"; // Redirigir a la lista de productos
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login"; // Volver a mostrar el formulario de inicio de sesión
        }
    }
}
