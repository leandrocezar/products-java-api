package io.github.leandrocezar.productsjavaapi.dto.model.product;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.modelmapper.ModelMapper;

import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import lombok.Data;


@Data
public class ProductDTO {
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String description;
    
    @Positive
    private BigDecimal price;
    
    public void mergeToEntity(ProductEntity p) {
	
	new ModelMapper().map(this, p);
	
    }
    public ProductEntity convertToEntity() {
	return new ModelMapper().map(this, ProductEntity.class);
    }
}
