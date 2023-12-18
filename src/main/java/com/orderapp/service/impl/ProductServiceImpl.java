package com.orderapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.orderapp.bo.ProductResponse;
import com.orderapp.model.Product;
import com.orderapp.repository.ProductRepo;
import com.orderapp.service.ProductService;

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
