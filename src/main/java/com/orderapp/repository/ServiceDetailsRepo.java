package com.orderapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.model.ServiceDetails;

public interface ServiceDetailsRepo extends JpaRepository<ServiceDetails,Integer> {

}
