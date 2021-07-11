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

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;
    
    private ConverterToWrapper<ProductWrapper, ProductEntity> converter;

    public ProductController() {
	converter = new ConverterToWrapper<>(ProductWrapper::new);
    }
    
    @GetMapping
    @ApiOperation(value = "Find all products in the API")
    public ResponseEntity<Iterable<ProductWrapper>> allProducts() {
	
	Iterable<ProductEntity> response = service.findAll();
	
	return ResponseEntity.status(HttpStatus.OK).body(converter.toList(response));
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find product by id ")
    public ResponseEntity<ProductWrapper> findById(@PathVariable(value = "id", required = true) String id) {
	return service.findById(id)
		.map(record -> ResponseEntity.ok().body(converter.convert(record)))
		.orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping(path = "/search")
    @ApiOperation(value = "Search product by parameters")
    public ResponseEntity<Iterable<ProductWrapper>> findByCriteria(
	    @RequestParam(value = "q", defaultValue = "", required = false)String expression,
	    @RequestParam(value ="min_price", required=false) BigDecimal minPrice,
	    @RequestParam(value ="max_price", required=false) BigDecimal maxPrice
	    ) {
	Iterable<ProductEntity> response =service.findByCriteria(expression, minPrice, maxPrice);
	return ResponseEntity.status(HttpStatus.OK).body(converter.toList(response));
    }

    @PostMapping
    @ApiOperation(value = "Add a new product")
    public ResponseEntity<ProductWrapper> create(@Valid @RequestBody ProductDTO request) throws RecordNotFoundException {
	ProductEntity product = service.save(request.convertToEntity());
	return ResponseEntity.status(HttpStatus.CREATED).body(converter.convert(product));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update product")
    public ResponseEntity<ProductWrapper> update(@PathVariable(value = "id", required = true) String id,
	    @Valid @RequestBody ProductDTO request) throws RecordNotFoundException {

	ProductEntity response = service.save(request, id);

	return ResponseEntity.ok().body(converter.convert(response));
    }
    
    
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Remove product from the database")
    public ResponseEntity<Void> delete(@PathVariable(value = "id", required = true) String id) throws RecordNotFoundException {

	service.delete(id);

	return ResponseEntity.ok().build();
    }

}
