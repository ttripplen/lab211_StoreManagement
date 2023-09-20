/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab211_storemanagement;

import java.time.LocalDate;
import java.util.Scanner;
import model.DailyProduct;
import model.LslProduct;
import model.Product;
import model.ProductList;
import util.Constant;

/**
 *
 * @author ADMIN
 */
public class Lab211_StoreManagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

//        DailyProduct dailyProduct = new DailyProduct(
//                "DP000001",
//                "bread",
//                LocalDate.parse("13/09/2023", Constant.DATE_TIME_FORMAT),
//                LocalDate.parse("24/09/2023", Constant.DATE_TIME_FORMAT),
//                300,
//                "VietNam"
//        );
//        
//       
//        LslProduct lslProduct = new LslProduct(
//                "LSL000002",
//                "cookie",
//                LocalDate.parse("13/09/2023", Constant.DATE_TIME_FORMAT),
//                LocalDate.parse("24/09/2023", Constant.DATE_TIME_FORMAT),
//                100,
//                "VietNam"
//        );
//        
//         LslProduct lslProduct1 = new LslProduct(
//                "LSL000006",
//                "cookie",
//                LocalDate.parse("13/09/2023", Constant.DATE_TIME_FORMAT),
//                LocalDate.parse("24/09/2023", Constant.DATE_TIME_FORMAT),
//                100,
//                "VietNam"
//        );
//        
//        ProductList list = new ProductList();
//        list.loadFromFile(Constant.PRODUCT_FILE_NAME);
//         list.displayAllProducts();
//        
//        
//        list.getDailyProducts().add(dailyProduct);
//        list.getLslProducts().add(lslProduct);
//        list.getLslProducts().add(lslProduct1);
//        list.displayAllProducts();
//        list.saveToFile(Constant.PRODUCT_FILE_NAME); //clean all -> add all
        int choice;
        int operation;
        char productType;
        Scanner scanner = new Scanner(System.in);
        ProductList list = new ProductList();
        list.loadFromFile(Constant.PRODUCT_FILE_NAME);

        do {
            printMenu();
            System.out.println("----------------------------------------------");

            while (true) {
                try {
                    System.out.print("\nInput your choice from 1 to 5: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please input your choice must be integer from 1 to 5. Try again!");
                }
            }

            switch (choice) {
                case 1:
                    while (true) {
                        try {
                            System.out.println("1. Add a product.");
                            System.out.println("2. Update product information.");
                            System.out.println("3. Delete product.");
                            System.out.println("4. Show all product.");
                            System.out.print("Please select the operation: ");
                            operation = Integer.parseInt(scanner.nextLine());

                            if (operation == 1 || operation == 2 || operation == 3 || operation == 4) {
                                break;
                            } else {
                                System.out.println("\nPlease from 1 to 4!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nPlease input your choice as an integer (from 1 to 4). Try again!");
                        }
                    }

                    switch (operation) {
                        case 1:
                            do {
                                System.out.println("a. Daily use product.");
                                System.out.println("b. Long shelf life product.");
                                System.out.println("c. Go back to the main menu!");
                                System.out.print("Please select type of product: ");
                                while (true) {
                                    try {
                                        productType = Character.toLowerCase(scanner.nextLine().charAt(0));

                                        if (productType == 'a' || productType == 'b' || productType == 'c') {
                                            break;
                                        } else {
                                            System.out.println("\nPlease input a, b or c!");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("\nPlease input your choice as a character (a of b). Try again!");
                                    }
                                }
                                switch (productType) {
                                    case 'a':
                                        list.addDailyProducts();
                                        break;

                                    case 'b':
                                        list.addLslProducts();
                                        break;

                                    case 'c':
                                        break;
                                }
                            } while (choice == 'c');

                            break;
                        case 2:
                            list.updateAProduct();
                            break;
                        case 3:
                            list.removeAProduct();
                            break;
                        case 4:
                            list.displayAllProducts();
                            break;
                    }
                    break;
                case 2:
                    //
                    break;
                case 3:
                    while (true) {
                        try {
                            System.out.println("1. Expired products.");
                            System.out.println("2. The store's current products.");
                            System.out.println("3. Out-of-stock products.");
                            System.out.println("4. Import/export receipt of a product.");
                            System.out.print("Please select the operation: ");
                            operation = Integer.parseInt(scanner.nextLine());

                            if (operation == 1 || operation == 2 || operation == 3 || operation == 4) {
                                break;
                            } else {
                                System.out.println("\nPlease choose from 1 to 4!");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nPlease input your choice as an integer (from 1 to 4). Try again!");
                        }
                    }
                    switch (operation) {
                        case 1:
                            list.displayExpiredProducts();
                            break;
                        case 2:
                            list.displaySellingProducts();
                            break;
                        case 3:
                            list.outOfStockProducts();
                            break;
                        case 4:
                            list.receiptProduct();
                            break;
                    }
                    break;
                case 4:
                    list.saveToFile(Constant.PRODUCT_FILE_NAME);
                    break;
                case 5:
                    System.out.println("Bye bye, see you next time");
                    break;
                default:
                    System.out.println("Please choose from 1 to 5!");
                    break;
            }
        } while (choice != 5);

    }

    public static void printMenu() {
        System.out.println("\nWelcome to Store Management at Convenience Store!");
        System.out.println("Please choose the desired action to perform:");
        System.out.println("1. Manage products.");
        System.out.println("   1.1.Add a product.");
        System.out.println("   1.2.Update product information.");
        System.out.println("   1.3.Delete product.");
        System.out.println("   1.4.Show all product.");

        System.out.println("2. Manage Warehouse.");
        System.out.println("   2.1.Create an import receipt.");
        System.out.println("   2.2.Create an export receipt.");

        System.out.println("3. Report.");
        System.out.println("   3.1.Expired products.");
        System.out.println("   3.2.The store's current products.");
        System.out.println("   3.3.Out-of-stock products.");
        System.out.println("   3.4.Import/export receipt of a product.");
        System.out.println("4. Store data to files.");
        System.out.println("5. Exit.\n");

    }

}
