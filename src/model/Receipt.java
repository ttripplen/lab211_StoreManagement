/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Receipt {
    
    private String code;
    
    private LocalDateTime createAt;
    
    private List<ProductItem> productItems;
    
    public static class ProductItem {
        String productCode;
        
        int quantity;
        
        public ProductItem() {
            
        }
        
        public ProductItem(String productCode, int quantity) {
            this.productCode = productCode;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "{productCode=" + productCode + ", quantity=" + quantity + '}';
        }
        
        
    }

    public Receipt() {
        createAt = LocalDateTime.now();
        productItems = new ArrayList<>();
    }

    public Receipt(String code, LocalDateTime createAt, List<ProductItem> productItems) {
        this.code = code;
        this.createAt = createAt;
        this.productItems = productItems;
    }

    
    public String getCode() {
        return code;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public List<ProductItem> getProducts() {
        return productItems;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Receipt{" + "code=" + code + ", createAt=" + createAt + ", productItems=" + productItems + '}';
    }

    

    
    
    
}
