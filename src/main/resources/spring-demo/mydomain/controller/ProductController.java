package com.mydomain.controller;

import java.util.List;
																																																																													
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mydomain.bo.ProductResponse;
import com.mydomain.model.Product;
import com.mydomain.service.ProductService;





@RestController
@RequestMapping("/product")
public class ProductController {
	
		    
	    @Autowired
	    ProductService productService;

	    @GetMapping("/findAll")
	   public List<Product> getAllProduct(){
	        return productService.getAllProduct();
	    }

	    @PostMapping("/findByProductId")
		public Product getProductById(@RequestBody Product product){
		     return productService.getProductById(product.getProductId());
		}
	    
	    @PostMapping("/create")
	    public ProductResponse create(@RequestBody Product product){
	    	
	        return productService.createProduct(product);
	    }
	    
	    
}
