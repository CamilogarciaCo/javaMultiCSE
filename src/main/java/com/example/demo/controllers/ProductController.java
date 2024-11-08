package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.entities.ProductEntity;
import com.example.demo.services.ProductService;
import java.util.UUID;
import com.example.demo.entities.UserEntity;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "dashboard";  // Renderiza dashboard.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new ProductEntity());
        return "createProduct";  // Renderiza createProduct
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute ProductEntity product,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.createProduct(product);
            redirectAttributes.addFlashAttribute("success",
                    "Producto creado exitosamente!");
            return "redirect:/products";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al crear el producto: " + e.getMessage());
            return "redirect:/products/new";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            ProductEntity product = productService.getProductById(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            model.addAttribute("product", product);
            return "editProduct";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable UUID id,
                                @ModelAttribute ProductEntity product,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.updateProduct(id, product)
                    .orElseThrow(() -> new RuntimeException("Error al actualizar el producto"));
            redirectAttributes.addFlashAttribute("success",
                    "Producto actualizado exitosamente!");
            return "redirect:/products";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable UUID id,
                                RedirectAttributes redirectAttributes) {
        try {
            if (productService.deleteProduct(id)) {
                redirectAttributes.addFlashAttribute("success",
                        "Producto eliminado exitosamente!");
            } else {
                throw new RuntimeException("No se pudo eliminar el producto");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al eliminar el producto: " + e.getMessage());
        }
        return "redirect:/products";
    }
}
