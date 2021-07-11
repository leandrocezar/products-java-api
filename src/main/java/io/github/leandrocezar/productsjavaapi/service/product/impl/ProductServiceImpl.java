package io.github.leandrocezar.productsjavaapi.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import io.github.leandrocezar.productsjavaapi.util.converter.ConverterToWrapper;
import io.github.leandrocezar.productsjavaapi.wrapper.product.ProductWrapper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Override
    public ProductEntity save(ProductEntity product) throws RecordNotFoundException {

	return repository.save(product);
    }

    @Override
    public ProductEntity save(ProductDTO product, String id) throws RecordNotFoundException {
	Optional<ProductEntity> prodDb = findById(id);

	if (!prodDb.isPresent()) {
	    throw new RecordNotFoundException();
	}
	ProductEntity prd = prodDb.get();
	product.mergeToEntity(prd);

	return save(prd);
    }

    @Override
    public Iterable<ProductEntity> findAll() {
	return repository.findAll();
    }

    @Override
    public Optional<ProductEntity> findById(String id) {

	return repository.findById(id);

    }

    @Override
    public void delete(String id) throws RecordNotFoundException {

	if (!findById(id).isPresent()) {
	    throw new RecordNotFoundException();
	}

	repository.deleteById(id);

    }

    @Override
    public Iterable<ProductEntity> findByCriteria(String expression, BigDecimal minPrice, BigDecimal maxPrice) {

	Specification<ProductEntity> spec = getProductSpecification(expression, minPrice, maxPrice);
	return repository.findAll(spec);
    }

    private static Specification<ProductEntity> getProductSpecification(String expression, BigDecimal minPrice,
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
