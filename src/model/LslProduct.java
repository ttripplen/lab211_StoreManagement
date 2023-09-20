/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class LslProduct extends Product {
    //more attributes
   
    public LslProduct() {
    }

    public LslProduct(String productCode, 
                        String productName, 
                        LocalDate manufacturingDate, 
                        LocalDate expirationDate, 
                        int quantity, 
                        String manufacturer) {
        super(productCode, productName, manufacturingDate, expirationDate, quantity, manufacturer);
    }
    
    //more getters
    //more setters

    @Override
    public String toString() {
        return super.toString(); 
    }
    
     @Override
    public String print() {
        return super.print();
    }
    
}
