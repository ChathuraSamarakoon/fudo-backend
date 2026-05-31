package fudo_backend.service;

import fudo_backend.model.Product;
import fudo_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product addProduct(Product product) {
        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
