package io.github.leandrocezar.productsjavaapi.service.product;

import java.math.BigDecimal;
import java.util.Optional;

import io.github.leandrocezar.productsjavaapi.dto.model.product.ProductDTO;
import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.exception.RecordNotFoundException;

/***
 * Product service interface
 *
 *
 * @author Leandro Moreira Cezar
 *
 */
public interface ProductService {
    
    
    /**
     * Find all products
     * 
     * @author Leandro Moreira Cezar
     *
     * @return Iterable<ProductEntity>
     */
    Iterable<ProductEntity> findAll();
    
    /**
     * Find product by id 
     * 
     * @author Leandro Moreira Cezar
     *
     * @param id Product id
     * @return Iterable<ProductEntity>
     */
    Optional<ProductEntity> findById(String id);
    
    /**
     * Find products by criteria
     * 
     * @author Leandro Moreira Cezar
     *
     * @param expression product name or description
     * @param minPrice product min price
     * @param maxPrice product max price
     * @return Iterable<ProductEntity> 
     */
    Iterable<ProductEntity> findByCriteria(String expression, BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Add a product
     * 
     * @author Leandro Moreira Cezar
     *
     * @param dto product data 
     * @return ProductEntity saved
     */
    ProductEntity save(ProductDTO dto);
    
    /***
     * Update a product 
     * 
     * @author Leandro Moreira Cezar
     *
     * @param product product data
     * @param id product id
     * @return ProductEntity saved
     * @throws RecordNotFoundException Product not found
     */
    ProductEntity save(ProductDTO product, String id) throws RecordNotFoundException;
    
    /**
     * Delete a product
     * 
     * @author Leandro Moreira Cezar
     *
     * @param id product id
     * @throws RecordNotFoundException Product not found
     */
    void delete(String id) throws RecordNotFoundException;
    
    
    
}
