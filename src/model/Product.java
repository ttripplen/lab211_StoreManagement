/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import util.Constant;

/**
 *
 * @author ADMIN
 */
public abstract class Product {
    protected String productCode;
    protected String productName;
    protected LocalDate manufacturingDate;
    protected LocalDate expirationDate;
    protected int quantity;
    protected String manufacturer;
    
    protected Product() {}

    protected Product(String productCode, String productName, LocalDate manufacturingDate, LocalDate expirationDate, int quantity, String manufacturer) {
        this.productCode = productCode;
        this.productName = productName;
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "ProductCode=" + productCode + ", ProductName=" + productName + ", ManufacturingDate=" + manufacturingDate + ", ExpirationDate=" + expirationDate + ", Quantity=" + quantity + ", Manufacturer=" + manufacturer + '}';
    }
    
    public String print() {
        return productCode + "|" + productName + "|" 
                + manufacturingDate.format(Constant.DATE_TIME_FORMAT).toString() + "|"
                + expirationDate.format(Constant.DATE_TIME_FORMAT).toString() + "|"
                + String.valueOf(quantity) + "|"
                + manufacturer;
    }
    
    
}
