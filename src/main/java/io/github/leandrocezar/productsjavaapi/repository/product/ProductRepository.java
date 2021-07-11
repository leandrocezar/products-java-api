package io.github.leandrocezar.productsjavaapi.repository.product;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, String>, JpaSpecificationExecutor<ProductEntity>{

}
