package com.orderapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderapp.bo.TemplateRequest;
import com.orderapp.model.Template;
import com.orderapp.service.TemplateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/template")
public class TemplateController {

	 @Autowired
	 TemplateService templateService;
	 
	 @GetMapping("/findAll")
	  public List<Template> getAllProduct(){
	        return templateService.getTemplateList();
	  }
	
	 
	 @PostMapping("/create")
	  public Template create(@RequestBody Template template){
	        return templateService.createTemplate(template);
	  }
	 
	 
	 @PostMapping("/dynamicTemplate")
	  public ResponseEntity<Resource> dynamicTemplate(@RequestBody TemplateRequest templateRequest,HttpServletRequest request){
	        return templateService.generateDynamicTemplate(templateRequest, request);
	  }
	 
	 @GetMapping("/oldCodeClean")
	  public String deleteOldCodes(){
	        return templateService.deleteOldCodebase();
	  }
}
