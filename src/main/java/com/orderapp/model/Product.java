package com.orderapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Table(name ="product")
@Entity
public class Product {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String productId;
	private String productName;
	private String productImage;
	private double listPrice;
	private double salesPrice;
	private String productStock;
	private String currency;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getProductStock() {
		return productStock;
	}
	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
