package io.github.leandrocezar.productsjavaapi.dto.model.product;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.modelmapper.ModelMapper;

import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProductDTO {
    
    @NotBlank
    @ApiModelProperty(notes = "Product name", name="name",required=true, value="product name")
    private String name;
    
    @NotBlank
    @ApiModelProperty(notes = "Product description", name="description",required=true, value = "product description")
    private String description;
    
    @Positive
    @ApiModelProperty(notes = "Product price. Required shold be positive number", name="price",required=true,value="product price (should be greater than ZERO)")
    private BigDecimal price;
    
    public void mergeToEntity(ProductEntity p) {
	
	new ModelMapper().map(this, p);
	
    }
    public ProductEntity convertToEntity() {
	return new ModelMapper().map(this, ProductEntity.class);
    }
}
