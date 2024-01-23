package com.enviro.assessment.grad001.KamwanyaMukendi.services;

import com.enviro.assessment.grad001.KamwanyaMukendi.model.Product;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public void withdrawFromProduct(Product product, Double withdrawalAmount) {
        if (product != null) {
            if (withdrawalAmount <= product.getCurrentBalance()) {
                double newBalance = product.getCurrentBalance() - withdrawalAmount;
                product.setCurrentBalance(newBalance);
                productRepository.save(product);
            } else {
                System.out.println("Insufficient funds");
            }
        }
    }

}
