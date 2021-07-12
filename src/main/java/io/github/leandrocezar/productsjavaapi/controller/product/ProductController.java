package io.github.leandrocezar.productsjavaapi.controller.product;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.leandrocezar.productsjavaapi.dto.model.product.ProductDTO;
import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.exception.RecordNotFoundException;
import io.github.leandrocezar.productsjavaapi.service.product.ProductService;
import io.github.leandrocezar.productsjavaapi.util.converter.ConverterToWrapper;
import io.github.leandrocezar.productsjavaapi.wrapper.product.ProductWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/***
 * Controller to manage products
 *
 *
 * @author Leandro Moreira Cezar
 *
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    private ConverterToWrapper<ProductWrapper, ProductEntity> wrapperConverter;

    public ProductController() {
	wrapperConverter = new ConverterToWrapper<>(ProductWrapper::new);
    }
    
    /***
     * Return all products in the database
     * 
     * @author Leandro Moreira Cezar
     *
     * @return ResponseEntity with a Iterable<ProductWrapper> object and the http status
     * 
     * HTTP Status:
     * 200 - OK: Everything worked as expected
     * 500: Something wrong on API
     */
    @GetMapping
    @ApiOperation(value = "Get list of Products", response = Iterable.class, produces="application/json", tags = "products")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Return the list of products"),
	    @ApiResponse(code = 500, message = "An exception ocurred"), })
    public ResponseEntity<Iterable<ProductWrapper>> allProducts() {

	Iterable<ProductEntity> response = service.findAll();

	return ResponseEntity.status(HttpStatus.OK).body(wrapperConverter.toList(response));
    }

    /***
     * Return a product searching by ID
     * 
     * @author Leandro Moreira Cezar
     *
     * @param id Product id
     * @return ResponseEntity with a ProductWrapper object and the http status
     * 
     * HTTP Status:
     * 200 - OK: Everything worked as expected. Product found.
     * 404 - Not found: Product not found in the database
     * 500: Something wrong on API
     */
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find product by id", response = ProductWrapper.class, produces="application/json", tags = "products")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Return the product"),
	    @ApiResponse(code = 404, message = "Product not exist in the database"),
	    @ApiResponse(code = 500, message = "An exception ocurred"), })
    public ResponseEntity<ProductWrapper> findById(
	    @ApiParam(name = "id", type = "String", value = "product id", example = "0001", required = true) @PathVariable(value = "id", required = true) String id) {
	return service.findById(id).map(record -> ResponseEntity.ok().body(wrapperConverter.convert(record)))
		.orElse(ResponseEntity.notFound().build());
    }

    /**
     * Return a list of products searching by criteria
     * 
     * @author Leandro Moreira Cezar
     *
     * @param expression value to match on product name or description attributes
     * @param minPrice Product min price
     * @param maxPrice Product max price
     * @return ResponseEntity with a Iterable<ProductWrapper> object and the http status
     * 
     * HTTP Status:
     * 200 - OK: Everything worked as expected. 
     * 500: Something wrong on API
     */
    @GetMapping(path = "/search")
    @ApiOperation(value = "Get list of products using a criteria", response = Iterable.class, produces="application/json", tags = "products")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Return the list of products"),
	    @ApiResponse(code = 500, message = "An exception ocurred"), })
    public ResponseEntity<Iterable<ProductWrapper>> findByCriteria(
	    @ApiParam(name = "q", type = "String", value = "part of name or description", example = "item", required = false) @RequestParam(value = "q", defaultValue = "", required = false) String expression,
	    @ApiParam(name = "min_price", type = "BigDecimal", value = "product min price", example = "10.0", required = false) @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
	    @ApiParam(name = "max_price", type = "BigDecimal", value = "product max price", example = "200.0", required = false) @RequestParam(value = "max_price", required = false) BigDecimal maxPrice) {
	Iterable<ProductEntity> response = service.findByCriteria(expression, minPrice, maxPrice);
	return ResponseEntity.status(HttpStatus.OK).body(wrapperConverter.toList(response));
    }

    /**
     * Add a new product on the database
     * 
     * @author Leandro Moreira Cezar
     *
     * @param request ProductDTO object containing the data to save
     * @return ResponseEntity with a ProductWrapper object and the http status
     * 
     * HTTP Status:
     * 201 - Created: Everything worked as expected. 
     * 400 - Bad Request: Required fields are emtpy
     * 500: Something wrong on API
     */
    @PostMapping
    @ApiOperation(value = "Add a new product", response = ProductWrapper.class, produces="application/json", tags = "products")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Return the product with the id"),
	    @ApiResponse(code = 400, message = "Exist one or more required fields empty"),
	    @ApiResponse(code = 500, message = "An exception ocurred"), })
    public ResponseEntity<ProductWrapper> create(
	    @ApiParam(name = "request", type = "ProductDTO", value = "product data to save", required = true) @Valid @RequestBody ProductDTO request)
	    {
	ProductEntity product = service.save(request);
	return ResponseEntity.status(HttpStatus.CREATED).body(wrapperConverter.convert(product));
    }

    /**
     * Update product on the database
     * 
     * @author Leandro Moreira Cezar
     *
     * @param id Product id
     * @param request ProductDTO object containing the data to save
     * @return ResponseEntity with a ProductWrapper object and the http status
     * 
     * HTTP Status:
     * 200 - OK: Everything worked as expected.
     * 404 - Not found: Product not found on database
     * 500: Something wrong on API
     */
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update product. Need to pass the product id to check if exists before update", response = ProductWrapper.class, produces="application/json", tags = "products")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Return the updated product "),
	    @ApiResponse(code = 404, message = "Product not exist in the database"),
	    @ApiResponse(code = 500, message = "An exception ocurred"), })
    public ResponseEntity<ProductWrapper> update(
	    @ApiParam(name = "id", type = "String", value = "product id", example = "0001", required = true) @PathVariable(value = "id", required = true) String id,
	    @ApiParam(name = "request", type = "ProductDTO", value = "product data to save", required = true) @Valid @RequestBody ProductDTO request)
	    throws RecordNotFoundException {

	ProductEntity response = service.save(request, id);

	return ResponseEntity.ok().body(wrapperConverter.convert(response));
    }

    /**
     * Delete a product from the database
     * 
     * @author Leandro Moreira Cezar
     *
     * @param id Product id
     * @return 
     * 
     * HTTP Status:
     * 200 - OK: Everything worked as expected.
     * 404 - Not found: Product not found on database
     * 500: Something wrong on API
     * 
     * @throws RecordNotFoundException Product not found
     */
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete product", response = Void.class, tags = "products")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Product deleted"),
	    @ApiResponse(code = 404, message = "Product not exist in the database"),
	    @ApiResponse(code = 500, message = "An exception ocurred") })
    public ResponseEntity<Void> delete(
	    @ApiParam(name = "id", type = "String", value = "product id", example = "0001", required = true) @PathVariable(value = "id", required = true) String id)
	    throws RecordNotFoundException {

	service.delete(id);

	return ResponseEntity.ok().build();
    }

}
