/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ADMIN
 */
public interface FileInteraction { //quy định 2 hành vi, bất kì 1 class kế thừa 2 hành vi này
    void loadFromFile(String fileName);
    void saveToFile(String fileName);
}
