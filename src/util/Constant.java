/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ADMIN
 */
public class Constant {
    
    public static final String STORAGE_FOLDER = "./storage/";
    
    public static final String PRODUCT_FILE_NAME = "product.txt";
    
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static boolean testCodeDaily(String code) {
        String regex = "^(DL)\\d{6}$"; 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches(); 
    }
    
    public static boolean testCodeLsl(String code) {
        String regex = "^(LSL)\\d{6}$"; 
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches(); 
    }
}
