package com.mydomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mydomain.model.Product;




public interface ProductRepo extends JpaRepository<Product,String> {

}
