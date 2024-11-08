package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.entities.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private List<ProductEntity> products = new ArrayList<>();

    public ProductService() {
        products.add(new ProductEntity("Producto 1", "Categoría A", 10.0, 100));
        products.add(new ProductEntity("Producto 2", "Categoría B", 15.5, 50));
        products.add(new ProductEntity("Producto 3", "Categoría A", 7.25, 200));
        products.add(new ProductEntity("Producto 4", "Categoría C", 22.99, 20));
        products.add(new ProductEntity("Producto 5", "Categoría B", 5.75, 150));
    }

    public List<ProductEntity> getAllProducts() {
        return products;
    }

    public Optional<ProductEntity> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public ProductEntity createProduct(ProductEntity product) {
        products.add(product);
        return product;
    }

    public Optional<ProductEntity> updateProduct(UUID id, ProductEntity product) {
        return getProductById(id).map(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            return existingProduct;
        });
    }



    public boolean deleteProduct(UUID id) {
        return products.removeIf(product -> product.getId().equals(id));
    }
}
