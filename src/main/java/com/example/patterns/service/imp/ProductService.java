package com.example.patterns.service.imp;

import com.example.patterns.model.Product;
import com.example.patterns.repository.ProductRepository;
import com.example.patterns.service.imp.FirstTransaction;
import com.example.patterns.service.imp.SecondTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FirstTransaction firstTransaction;

    @Autowired
    private SecondTransaction secondTransaction;


    @Transactional
    public Product updateProductWithOptimisticLock(Long id, int quantity) {
        Product product = productRepository.findByIdWithPessimisticLock(id);
        product.setQuantity(quantity);
        return productRepository.save(product); // Optimistic lock uses @Version
    }

    @Transactional
    public Product updateProductWithPessimisticLock(Long id, int quantity) {
        Product product = productRepository.findByIdWithPessimisticLock(id);
        product.setQuantity(quantity);
        return productRepository.save(product);
    }

    public void simulateOptimisticLocking() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> firstTransaction.runFirstTransaction());
        executorService.submit(() -> secondTransaction.runSecondTransaction());

        executorService.shutdown();
    }
}