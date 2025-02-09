package com.example.patterns.service.imp;

import com.example.patterns.model.Product;
import com.example.patterns.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecondTransaction {

    @Autowired
    private ProductRepository productRepository1;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void runSecondTransaction() {
        // Fetch the product with version control
        Product product = productRepository1.findByIdWithPessimisticLock(1L);

        System.out.println("Thread 2 - Current version before update: " + product.getVersion());

        // Modify product quantity
        product.setQuantity(200);

        // Try to save the updated product (should fail if version mismatch occurs)
        try {
            System.out.println("Thread 2 - Saving product with new quantity: " + product.getQuantity());
            productRepository1.save(product);  // This should throw an OptimisticLockException if version mismatch occurs
        } catch (OptimisticLockException e) {
            System.out.println("Thread 2 - Optimistic lock conflict: " + e.getMessage());
        } catch (StaleObjectStateException e) {
            System.out.println("Thread 2 - Optimistic lock conflict (StaleObjectStateException): " + e.getMessage());
        }
    }
}
