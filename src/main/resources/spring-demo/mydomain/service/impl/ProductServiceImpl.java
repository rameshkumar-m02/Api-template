package com.mydomain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mydomain.bo.ProductResponse;
import com.mydomain.model.Product;
import com.mydomain.repository.ProductRepo;
import com.mydomain.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
    private ProductRepo productRep;
	
	@Override
	public ProductResponse createProduct(Product product ) {
		ProductResponse response = new ProductResponse();
		Product prod = productRep.save(product);
		response.setProductId(prod.getProductId());
		response.setProductName(prod.getProductName());
		response.setProductImage(prod.getProductImage());
		response.setListPrice(prod.getListPrice());
		response.setSalesPrice(prod.getSalesPrice());
		response.setCurrency(prod.getCurrency());
		response.setProductStock(prod.getProductStock());
		return response;
	}
	
	@Override
	public List<Product> getAllProduct() {
		
		List<Product> prods = productRep.findAll();
		
		return prods;
	}

	public Product getProductById(String productId){
	     return productRep.findById(productId).orElse(new Product());
	}
}
