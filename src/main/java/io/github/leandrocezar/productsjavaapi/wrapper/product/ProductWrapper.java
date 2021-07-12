package io.github.leandrocezar.productsjavaapi.wrapper.product;

import java.math.BigDecimal;

import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.wrapper.ResponseWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * Represents a product response json
 *
 *
 * @author Leandro Moreira Cezar
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWrapper extends ResponseWrapper<ProductEntity> {

    @ApiModelProperty(notes = "Product ID", name="id",required=true, value="product id")
    private String id;
    
    @ApiModelProperty(notes = "Product name", name="name",required=true, value="product name")
    private String name;
    
    @ApiModelProperty(notes = "Product description", name="description",required=true, value = "product description")
    private String description;

    @ApiModelProperty(notes = "Product price. Required shold be positive number", name="price",required=true,value="product price (should be greater than ZERO)")
    private BigDecimal price;
}
