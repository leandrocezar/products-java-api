package io.github.leandrocezar.productsjavaapi.wrapper.product;

import java.math.BigDecimal;

import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.wrapper.ResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWrapper extends ResponseWrapper<ProductEntity> {

    private String id;
    private String name;
    private String description;

    private BigDecimal price;
}
