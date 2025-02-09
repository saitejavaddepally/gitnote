package com.example.patterns.service.imp;

import com.example.patterns.model.Product;
import com.example.patterns.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FirstTransaction {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void runFirstTransaction() {
        // Fetch the product with version control
        Product product = productRepository.findByIdWithPessimisticLock(1L);
        System.out.println("Thread 1 - Current version: " + product.getVersion());

        // Modify product quantity
        product.setQuantity(150);

        // Save the updated product (will increment version)
        System.out.println("Thread 1 - Saving product with new quantity: " + product.getQuantity());
        productRepository.save(product);

    }
}
