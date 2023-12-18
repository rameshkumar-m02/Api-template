package com.orderapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.model.Product;




public interface ProductRepo extends JpaRepository<Product,String> {

}
