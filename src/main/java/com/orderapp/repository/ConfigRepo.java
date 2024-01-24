package com.orderapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderapp.model.CloudConfig;


public interface ConfigRepo extends JpaRepository<CloudConfig,Integer> {
	
	public List<CloudConfig> findByTemplateName(String templateName);

}
