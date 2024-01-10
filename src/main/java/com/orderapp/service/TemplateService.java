package com.orderapp.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.orderapp.bo.TemplateRequest;
import com.orderapp.model.Template;

import jakarta.servlet.http.HttpServletRequest;

public interface TemplateService {

	public Template createTemplate(Template template);
	public List<Template> getTemplateList();
	public Template getTemplateDetail(Integer templateId);
	public String deleteOldCodebase();
	public ResponseEntity<Resource> generateDynamicTemplate(TemplateRequest template,HttpServletRequest request);
}
