package com.orderapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.model.CloudConfig;


public interface ConfigRepo extends JpaRepository<CloudConfig,Integer> {

}
