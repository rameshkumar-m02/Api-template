package com.orderapp.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.orderapp.bo.TemplateRequest;
import com.orderapp.model.Template;
import com.orderapp.model.CloudConfig;

import jakarta.servlet.http.HttpServletRequest;

public interface TemplateService {

	public Template createTemplate(Template template);
	public List<Template> getTemplateList();
	public Template getTemplateDetail(Integer templateId);
	public String deleteOldCodebase();
	public ResponseEntity<Resource> generateDynamicTemplate(TemplateRequest template,HttpServletRequest request);
	public CloudConfig createCloudConfig(CloudConfig cloudConfig);
	public List<Template> findByCreatedBy(String createdBy);
	public List<CloudConfig> getCloudConfigList();
	public List<CloudConfig> getTemplateCloudConfig(String templateName); 
	
}
