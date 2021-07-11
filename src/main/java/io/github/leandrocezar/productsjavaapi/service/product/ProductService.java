package io.github.leandrocezar.productsjavaapi.service.product;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.leandrocezar.productsjavaapi.dto.model.product.ProductDTO;
import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.exception.RecordNotFoundException;

public interface ProductService {
    
    
    Iterable<ProductEntity> findAll();
    Optional<ProductEntity> findById(String id);
    Iterable<ProductEntity> findByCriteria(String textToSearch, BigDecimal minPrice, BigDecimal maxPrice);

    ProductEntity save(ProductEntity product) throws RecordNotFoundException;
    ProductEntity save(ProductDTO product, String id) throws RecordNotFoundException;
    
    void delete(String id) throws RecordNotFoundException;
    
    
    
}
