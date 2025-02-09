package com.example.patterns.controlller;

import com.example.patterns.model.Product;
import com.example.patterns.service.imp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PutMapping("/{id}/optimistic")
    public Product updateProductOptimistic(@PathVariable Long id, @RequestParam int quantity) {
        try {
            return productService.updateProductWithOptimisticLock(id, quantity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product optimistically: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/pessimistic")
    public Product updateProductPessimistic(@PathVariable Long id, @RequestParam int quantity) {
        return productService.updateProductWithPessimisticLock(id, quantity);
    }

    @GetMapping("/simulate")
    public String simulateOptimisticLocking() {
        productService.simulateOptimisticLocking();
        return "Simulating Optimistic Locking...";
    }
}