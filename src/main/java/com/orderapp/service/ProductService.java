package com.orderapp.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.orderapp.bo.ProductResponse;
import com.orderapp.model.Product;

public interface ProductService {

	public ProductResponse createProduct(Product product);
	public List<Product> getAllProduct();
	public Product getProductById(String productId);
}
