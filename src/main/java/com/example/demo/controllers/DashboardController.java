package com.example.demo.controllers;

import com.example.demo.entities.UserEntity;
import com.example.demo.entities.ProductEntity;
import com.example.demo.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ProductService productService; // Declarar el servicio de productos

    public DashboardController(ProductService productService) {
        this.productService = productService; // Inyectar el servicio a través del constructor
    }

    @GetMapping
    public String dashboard(HttpSession session, Model model) {

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }


        boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
        model.addAttribute("isAdmin", isAdmin);

        // Obtener todos los productos
        List<ProductEntity> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "dashboard";
    }
}
