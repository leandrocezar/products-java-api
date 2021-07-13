package io.github.leandrocezar.productsjavaapi.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.github.leandrocezar.productsjavaapi.dto.model.product.ProductDTO;
import io.github.leandrocezar.productsjavaapi.entity.product.ProductEntity;
import io.github.leandrocezar.productsjavaapi.exception.RecordNotFoundException;
import io.github.leandrocezar.productsjavaapi.repository.product.ProductRepository;
import io.github.leandrocezar.productsjavaapi.service.product.ProductService;
import io.github.leandrocezar.productsjavaapi.util.mapper.GenericMapper;

/***
 * Product service implementation. This class has all business rules applied to do any operation,
 *
 *
 * @author Leandro Moreira Cezar
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;
    
    @Autowired
    GenericMapper modelMapper;

    /**
    * @see ProductService#save(ProductDTO)
     */
    @Override
    public ProductEntity save(ProductDTO dto) {

	ProductEntity product= modelMapper.map(dto, ProductEntity.class);
	return repository.save(product);
    }

    /**
     * @see ProductService#save(ProductDTO, String)
      */
    @Override
    public ProductEntity save(ProductDTO product, String id) throws RecordNotFoundException {
	Optional<ProductEntity> prodDb = findById(id);
	ProductEntity prd  = null;
	
	if (!prodDb.isPresent()) {
	    throw new RecordNotFoundException();
	}
	prd = prodDb.get();
	modelMapper.map(product, prd);
	
	// product.mergeToEntity(prd);

	return repository.save(prd);
    }

    /**
     * @see ProductService#findAll()
      */
    @Override
    public Iterable<ProductEntity> findAll() {
	return repository.findAll();
    }

    /**
     * @see ProductService#findById(String)
      */
    @Override
    public Optional<ProductEntity> findById(String id) {

	return repository.findById(id);

    }

    /**
     * @see ProductService#delete(String)
      */
    @Override
    public void delete(String id) throws RecordNotFoundException {

	if (!findById(id).isPresent()) {
	    throw new RecordNotFoundException();
	}

	repository.deleteById(id);

    }

    /**
     * @see ProductService#findByCriteria(String, BigDecimal, BigDecimal)
      */
    @Override
    public Iterable<ProductEntity> findByCriteria(String expression, BigDecimal minPrice, BigDecimal maxPrice) {

	Specification<ProductEntity> spec = getProductSpecification(expression, minPrice, maxPrice);
	return repository.findAll(spec);
    }

    /***
     * Return the specification to dinamyc query
     * 
     * @author Leandro Moreira Cezar
     *
     * @param expression product name or description
     * @param minPrice product min price
     * @param maxPrice product max price
     * @return Specification<ProductEntity> dinamyc specification
     */
    private Specification<ProductEntity> getProductSpecification(String expression, BigDecimal minPrice,
	    BigDecimal maxPrice) {

	return (Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

	    List<Predicate> predicates = new ArrayList<>();

	    if (StringUtils.isNotBlank(expression)) {

		Predicate predicatesByExpresion = builder.or(
			builder.like(builder.lower(root.get("name")), "%" + expression.toLowerCase() + "%"),
			builder.like(builder.lower(root.get("description")), "%" + expression.toLowerCase() + "%"));
		predicates.add(predicatesByExpresion);
	    }

	    if (null != minPrice) {
		predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
	    }

	    if (null != maxPrice) {
		predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
	    }

	    return builder.and(predicates.toArray(new Predicate[0]));
	};
    }
    
}
