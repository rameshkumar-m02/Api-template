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

@Table(name ="cloudconfig")
@Entity
public class CloudConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer configId;
	private String templateName;
		
	private String repo_url;
	private String repo_user_name;
	private String repo_user_email;
	private String repo_user_pwd;
	private String repo_token;
	private String awsAccessKey; 
	private String awsSecretKey; 
	private String landingZone;
	private String createdBy;
	private String regione;
	private String accessKey;
	private String secretKey;
	private String serverType;
	private String cloudUname;
	private String masterCount;
	private String zone;
	private String nodeCount;
	private String machineType;
	private String nodeType;
	private String bucketName;
	private String clusterName;
	
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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
	public String getRepo_token() {
		return repo_token;
	}
	public void setRepo_token(String repo_token) {
		this.repo_token = repo_token;
	}
	public String getAwsAccessKey() {
		return awsAccessKey;
	}
	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}
	public String getAwsSecretKey() {
		return awsSecretKey;
	}
	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}
	public String getLandingZone() {
		return landingZone;
	}
	public void setLandingZone(String landingZone) {
		this.landingZone = landingZone;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public String getCloudUname() {
		return cloudUname;
	}
	public void setCloudUname(String cloudUname) {
		this.cloudUname = cloudUname;
	}
	public String getMasterCount() {
		return masterCount;
	}
	public void setMasterCount(String masterCount) {
		this.masterCount = masterCount;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getNodeCount() {
		return nodeCount;
	}
	public void setNodeCount(String nodeCount) {
		this.nodeCount = nodeCount;
	}
	public String getMachineType() {
		return machineType;
	}
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	
    public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

}
