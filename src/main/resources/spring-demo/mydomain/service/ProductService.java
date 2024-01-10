package com.mydomain.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.mydomain.bo.ProductResponse;
import com.mydomain.model.Product;

public interface ProductService {

	public ProductResponse createProduct(Product product);
	public List<Product> getAllProduct();
	public Product getProductById(String productId);
}
