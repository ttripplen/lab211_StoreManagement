/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ADMIN
 */
public class Validate {

    public static boolean testCodeDaily(String code) {
        String regex = "^(DP)\\d{6}$";
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

    public static boolean testCode(String code) {
        String regex;
        if (code.matches("^(LSL)\\d{6}$") || code.matches("^(DP)\\d{6}$")) {
            return true;
        } else {
            return false;
        }
    }
}
