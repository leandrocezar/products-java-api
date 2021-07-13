package io.github.leandrocezar.productsjavaapi.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.leandrocezar.productsjavaapi.dto.model.product.ProductDTO;
import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.exception.RecordNotFoundException;
import io.github.leandrocezar.productsjavaapi.repository.product.ProductRepository;
import io.github.leandrocezar.productsjavaapi.service.product.impl.ProductServiceImpl;
import io.github.leandrocezar.productsjavaapi.util.mapper.GenericMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@DisplayName("Product Service Test")
public class ProductServiceTest {

    
    private static final String ID = "0002";
    private static final String NAME = "Product name";
    private static final String DESCRIPTION = "Product description";
    private static final BigDecimal PRICE = new BigDecimal(1);
    
    @InjectMocks
    private ProductServiceImpl service;
    
    @Mock
    private ProductRepository repository;
    
    @Spy
    private GenericMapper modelMapper;

    @BeforeEach
    private void setUp() {
	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    @Order(1)
    @DisplayName("Add a product")
    public void whenSaveProductTest() {
	ProductDTO dto = new ProductDTO(NAME, DESCRIPTION, PRICE);
	ProductEntity entity = new ProductEntity(ID, NAME, DESCRIPTION, PRICE);
	
	when(repository.save(any())).thenReturn(entity);
	doReturn(entity).when(modelMapper).map(dto, ProductEntity.class);
	
	ProductEntity response = service.save(dto);
	
	assertThat(response).isNotNull();
    }
    
    @Test
    @Order(2)
    @DisplayName("Update a product")
    public void whenUpdateProductTest() throws RecordNotFoundException {
	ProductDTO dto = new ProductDTO(NAME, DESCRIPTION, PRICE);
	ProductEntity entity = new ProductEntity(ID, NAME, DESCRIPTION, PRICE);
	
	when(repository.save(any())).thenReturn(entity);
	when(repository.findById(any())).thenReturn(Optional.of(entity));
	doNothing().when(modelMapper).map(dto, entity);
	
	ProductEntity response = service.save(dto, ID);
	
	assertThat(response).isNotNull();
    }
    

    @Test
    @Order(3)
    @DisplayName("Delete a product")
    public void whenDeleteProductTest() throws RecordNotFoundException {
	ProductDTO dto = new ProductDTO(NAME, DESCRIPTION, PRICE);
	ProductEntity entity = new ProductEntity(ID, NAME, DESCRIPTION, PRICE);
	
	doNothing().when(repository).delete(any());
	when(repository.findById(any())).thenReturn(Optional.of(entity));
	doNothing().when(modelMapper).map(dto, entity);
	
	service.delete(ID);
    }
    
    @Test
    @Order(4)
    @DisplayName("Get all products")
    public void whenGetAllProductsTest() throws RecordNotFoundException {
	
	when(repository.findAll()).thenReturn(new ArrayList<>());
	
	
	Iterable<ProductEntity> response = service.findAll();
	
	assertThat(response).isNotNull();
    }
    @Test
    @Order(5)
    @DisplayName("Get products by criteria")
    public void whenGetProductsByCriteriaTest() throws RecordNotFoundException {
	
	when(repository.findAll(any())).thenReturn(new ArrayList<>());
	
	Iterable<ProductEntity> response = service.findByCriteria(DESCRIPTION, PRICE, PRICE);
	
	assertThat(response).isNotNull();
    }
    
    @Test
    @Order(5)
    @DisplayName("Try to update no exist product")
    public void whenTryUpdateNoExistProdictTest()  {
	
	Assertions.assertThrows(RecordNotFoundException.class, () -> {
	    ProductDTO dto = new ProductDTO(NAME, DESCRIPTION, PRICE);
	    ProductEntity entity = new ProductEntity(ID, NAME, DESCRIPTION, PRICE);

	    when(repository.save(any())).thenReturn(entity);
	    when(repository.findById(any())).thenReturn(Optional.empty());
	    doNothing().when(modelMapper).map(dto, entity);

	    service.save(dto, ID);
	});
	
    }

}
