package com.orderapp.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name ="template")
@Entity
public class Template {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer templateId;
	private String templateName;
	private String description;
	private String templateUrl;
	private int num_microservices;
	
	private String repo_url;
	private String repo_user_name;
	private String repo_user_email;
	private String repo_user_pwd;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "templateId") 
    private List<ServiceDetails> serviceDetails;
	//private ServiceDetails serviceDetails;
	/*private String technology;
	private String db_type;
	private String repo_url;
	private String ci_cd_required;
	private String graphql_required;
	private String repo_user_name;
	private String repo_user_pwd;
	private String project_name;
	private String dependencyList;
	private String db_url;
	private String db_username;
	private String db_pwd;*/
	
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTemplateUrl() {
		return templateUrl;
	}
	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}
	public int getNum_microservices() {
		return num_microservices;
	}
	public void setNum_microservices(int num_microservices) {
		this.num_microservices = num_microservices;
	}
	public List<ServiceDetails> getServiceDetails() {
		return serviceDetails;
	}
	public void setServiceDetails(List<ServiceDetails> serviceDetails) {
		this.serviceDetails = serviceDetails;
	}
	public String getRepo_url() {
		return repo_url;
	}
	public void setRepo_url(String repo_url) {
		this.repo_url = repo_url;
	}
	public String getRepo_user_name() {
		return repo_user_name;
	}
	public void setRepo_user_name(String repo_user_name) {
		this.repo_user_name = repo_user_name;
	}
	public String getRepo_user_email() {
		return repo_user_email;
	}
	public void setRepo_user_email(String repo_user_email) {
		this.repo_user_email = repo_user_email;
	}
	public String getRepo_user_pwd() {
		return repo_user_pwd;
	}
	public void setRepo_user_pwd(String repo_user_pwd) {
		this.repo_user_pwd = repo_user_pwd;
	}

	/*
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getDb_type() {
		return db_type;
	}
	public void setDb_type(String db_type) {
		this.db_type = db_type;
	}
	public String getRepo_url() {
		return repo_url;
	}
	public void setRepo_url(String repo_url) {
		this.repo_url = repo_url;
	}
	public String getCi_cd_required() {
		return ci_cd_required;
	}
	public void setCi_cd_required(String ci_cd_required) {
		this.ci_cd_required = ci_cd_required;
	}
	public String getGraphql_required() {
		return graphql_required;
	}
	public void setGraphql_required(String graphql_required) {
		this.graphql_required = graphql_required;
	}
	public String getRepo_user_name() {
		return repo_user_name;
	}
	public void setRepo_user_name(String repo_user_name) {
		this.repo_user_name = repo_user_name;
	}
	public String getRepo_user_pwd() {
		return repo_user_pwd;
	}
	public void setRepo_user_pwd(String repo_user_pwd) {
		this.repo_user_pwd = repo_user_pwd;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getDependencyList() {
		return dependencyList;
	}
	public void setDependencyList(String dependencyList) {
		this.dependencyList = dependencyList;
	}
	public String getDb_url() {
		return db_url;
	}
	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}
	public String getDb_username() {
		return db_username;
	}
	public void setDb_username(String db_username) {
		this.db_username = db_username;
	}
	public String getDb_pwd() {
		return db_pwd;
	}
	public void setDb_pwd(String db_pwd) {
		this.db_pwd = db_pwd;
	}*/
	
	
	

}
