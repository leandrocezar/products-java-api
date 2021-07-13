package io.github.leandrocezar.productsjavaapi.controller.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.leandrocezar.productsjavaapi.dto.model.product.ProductDTO;
import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.exception.RecordNotFoundException;
import io.github.leandrocezar.productsjavaapi.service.product.ProductService;
import io.github.leandrocezar.productsjavaapi.wrapper.product.ProductWrapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@DisplayName("Product Controller Test")
public class ProductControllerTest {

    private static final String URL = "/products";
    private static final String NO_EXISTS_ID = "XPTO";
    private static final String ID = "0002";
    private static final String NAME = "Product name";
    private static final String DESCRIPTION = "Product description";
    private static final BigDecimal PRICE = new BigDecimal(1);
    
    @InjectMocks
    ProductController controller;
    
    @Mock
    ProductService service;

    
    @BeforeEach
    private void setUp() {
	MockitoAnnotations.initMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("Get all products")
    public void whenGetAllProductsTest() {
	when(service.findAll()).thenReturn(new ArrayList<ProductEntity>());
	
	ResponseEntity<Iterable<ProductWrapper>> response = controller.findAll();
	assertThat(response).isNotNull();
	assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Order(2)
    @DisplayName("Get no exist product by id")
    public void whenGetNotExistsProductTest() {
	
	//when(service.findById(any())).thenReturn(mockProduct());
	ResponseEntity<ProductWrapper> response = controller.findById(NO_EXISTS_ID);
	assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    @Order(3)
    @DisplayName("Get products by criteroa")
    public void whenGetProductsByCriteriaTest() {
	when(service.findByCriteria(any(), any(), any())).thenReturn(new ArrayList<ProductEntity>());
	
	ResponseEntity<Iterable<ProductWrapper>> response = controller.findByCriteria(NAME, PRICE, PRICE);
	assertThat(response).isNotNull();
	assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    @Order(4)
    @DisplayName("Update exist product by id")
    public void whenUpdateExistProductTest() throws RecordNotFoundException {
	
	when(service.save(any(), any())).thenReturn(mockProductEntity());
	ResponseEntity<ProductWrapper> response = null;
	response = controller.update(ID, mockProductDTO());
	assertThat(response).isNotNull();
	assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    @Order(5)
    @DisplayName("Add product")
    public void whenAddProductTest() throws RecordNotFoundException {
	
	when(service.save(any())).thenReturn(mockProductEntity());
	ResponseEntity<ProductWrapper> response = null;
	response = controller.create(mockProductDTO());
	assertThat(response).isNotNull();
	assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
    }
    
    @Test
    @Order(6)
    @DisplayName("Delete product")
    public void whenDeleteProductTest() throws RecordNotFoundException {
	
	doNothing().when(service).delete(any());;
	ResponseEntity<Void> response = controller.delete(ID);
	assertThat(response).isNotNull();
	assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }
    

    @Test
    @Order(7)
    @DisplayName("Get product by id")
    public void whenGetExistProductTest() {
	
	when(service.findById(any())).thenReturn(mockOptionalProductEntity());
	ResponseEntity<ProductWrapper> response = controller.findById(ID);
	
	assertThat(response).isNotNull();
	assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }
    
    private ProductEntity mockProductEntity() {
	return new ProductEntity(ID, NAME, DESCRIPTION, PRICE);
    }

    private ProductDTO mockProductDTO() {
	return new ProductDTO(NAME, DESCRIPTION, PRICE);
    }
    


    private Optional<ProductEntity> mockOptionalProductEntity() {
	return Optional.of(new ProductEntity(ID, NAME, DESCRIPTION, PRICE));
    }

    
}
