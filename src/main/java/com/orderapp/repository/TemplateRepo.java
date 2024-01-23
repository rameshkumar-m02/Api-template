package com.orderapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.orderapp.model.Template;

public interface TemplateRepo extends JpaRepository<Template,Integer> {

public List<Template> findByCreatedBy(String createdBy);
}
