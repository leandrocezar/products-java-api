package io.github.leandrocezar.productsjavaapi.dto.model.product;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.github.leandrocezar.productsjavaapi.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/***
 * Class to represent the body request to manage products
 *
 *
 * @author Leandro Moreira Cezar
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductDTO extends BaseDTO{
    
    private static final long serialVersionUID = -3636540370641171305L;

    @NotBlank
    @ApiModelProperty(notes = "Product name", name="name",required=true, value="product name")
    private String name;
    
    @NotBlank
    @ApiModelProperty(notes = "Product description", name="description",required=true, value = "product description")
    private String description;
    
    @Positive
    @ApiModelProperty(notes = "Product price. Required shold be positive number", name="price",required=true,value="product price (should be greater than ZERO)")
    private BigDecimal price;
    
}
