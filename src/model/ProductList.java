/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import util.Constant;

/**
 *
 * @author ADMIN
 */
public class ProductList implements FileInteraction {
    private List<Product> products;

    public ProductList() {
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            File file = new File(Constant.STORAGE_FOLDER + fileName);
            if (!file.exists()) {
                System.out.println("File " + fileName + " doesn't exist");
                return;
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line; //br đi qua từng dòng, lấy String lưu thành biến line;
            while ((line = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(line, "|"); //split 
                String productId = stk.nextToken();
                if (productId.startsWith("DP")) {
                    DailyProduct dailyProduct = new DailyProduct(
                            productId,
                            stk.nextToken(),
                            LocalDate.parse(stk.nextToken(), Constant.DATE_TIME_FORMAT),
                            LocalDate.parse(stk.nextToken(), Constant.DATE_TIME_FORMAT),
                            Integer.parseInt(stk.nextToken()),
                            stk.nextToken()
                    );
                    products.add(dailyProduct);

                } else {
                    LslProduct lslProduct = new LslProduct(
                            productId,
                            stk.nextToken(),
                            LocalDate.parse(stk.nextToken(), Constant.DATE_TIME_FORMAT),
                            LocalDate.parse(stk.nextToken(), Constant.DATE_TIME_FORMAT),
                            Integer.parseInt(stk.nextToken()),
                            stk.nextToken()
                    );
                    products.add(lslProduct);
                }
            }
            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            File file = new File(Constant.STORAGE_FOLDER + fileName);
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            products.forEach(product -> pw.println(product.print()));
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //add property
    private String addCodeDaily() {
        String code;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input code: ");
            code = scanner.nextLine().trim().toUpperCase();
            if (Constant.testCodeDaily(code)) {
                Product x = searchAProduct(code);
                if (x == null) {
                    break;
                } else {
                    System.out.println("ID duplication! Try with another one.");
                }
            } else {
                System.out.println("Code format is not correct, please input in the format DLXXXXXX (X: digit). Try again!");
            }
        }
        return code;
    }

    private String addCodeLsl() {
        String code;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Input code: ");
            code = scanner.nextLine().trim().toUpperCase();
            if (Constant.testCodeLsl(code)) {
                Product x = searchAProduct(code);
                if (x == null) {
                    break;
                } else {
                    System.out.println("ID duplication! Try with another one.");
                }
            } else {
                System.out.println("Code format is not correct, please input in the format LSLXXXXXX (X: digit). Try again!");
            }
        }
        return code;
    }

    private String addName() {
        String name;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input name: ");
        name = scanner.nextLine().trim().toUpperCase();
        return name;

    }

    protected LocalDate addManufacturingDate() {
        LocalDate manufacturingDate;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Input manufacturing date(dd/mm/yyyy): ");
                String manufacturingDateStr = scanner.nextLine();
                manufacturingDate = LocalDate.parse(manufacturingDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate currentDate = LocalDate.now();
                int daysLeft = currentDate.compareTo(manufacturingDate);
                if (daysLeft >= 0) {
                    break;
                } else {
                    System.out.println("Invalid manufacture date!");
                }
            } catch (Exception e) {
                System.out.println("Format is not correct, please input in the format dd/mm/yyyy . Try again!");
            }

        }
        return manufacturingDate;
    }

    private LocalDate addExpirationDate() {
        LocalDate expirationDate;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Input expiration date(dd/mm/yyyy): ");
                String expirationDateStr = scanner.nextLine();
                expirationDate = LocalDate.parse(expirationDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate manufacturingDate = addManufacturingDate();
                int daysLeft = expirationDate.compareTo(manufacturingDate);
                if (daysLeft >= 0) {
                    break;
                } else {
                    System.out.println("Invalid expiration date!");
                }
            } catch (Exception e) {
                System.out.println("Format is not correct, please input in the format dd/mm/yyyy . Try again!");
            }

        }
        return expirationDate;
    }

    private int addQuantity() {
        int quantity;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Input quantity of product: ");
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity > 0) {
                    break;
                } else {
                    System.out.println("Quantity is invalid. Year Of Work must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" must be integer. Try again!");
            }
        }
        return quantity;
    }

    private String addManufacturer() {
        String manufacturer;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input name of manufacturer: ");
        manufacturer = scanner.nextLine().trim().toUpperCase();
        return manufacturer;
    }

    //search
    public Product searchAProduct(String code) {
        if (products.isEmpty()) {
            return null;
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductCode().equalsIgnoreCase(code)) {
                return products.get(i);
            }
        }

        return null;
    }

    //add 
    public void addDailyProducts() {
        System.out.println("Input daily use product information:");
        String productCode = addCodeDaily();
        String productName = addName();
        LocalDate manufacturingDate = addManufacturingDate();
        LocalDate exporationDate = addExpirationDate();
        int quantity = addQuantity();
        String manufacturer = addManufacturer();

        products.add(new DailyProduct(productCode, productName, manufacturingDate, exporationDate, quantity, manufacturer));
        System.out.println("Add a new Daily Use product successfully!\n");
    }

    public void addLslProducts() {
        System.out.println("Input long shelf life product information:");
        String productCode = addCodeLsl();
        String productName = addName();
        LocalDate manufacturingDate = addManufacturingDate();
        LocalDate exporationDate = addExpirationDate();
        int quantity = addQuantity();
        String manufacturer = addManufacturer();

        products.add(new LslProduct(productCode, productName, manufacturingDate, exporationDate, quantity, manufacturer));
        System.out.println("Add a new Daily Use product successfully!\n");
    }

    //update
    public void updateAProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input code to update a information: ");
        String code = scanner.nextLine();
        Product x = searchAProduct(code);
        if (x == null) {
            System.out.println("NOT FOUND! No product to updated.");
        } else {
            System.out.println("EMPLOYEE FOUND!");
            System.out.println("Information of product before updating.");
            System.out.println(x.toString());
            //Thông báo xác nhận
            Confirmation confirmDelete = null;
            while (true) {
                try {
                    System.out.println("Do you want to update this product?[YES/NO]: ");
                    confirmDelete = Confirmation.valueOf(scanner.nextLine().toUpperCase());
                    break;
                } catch (Exception e) {
                    System.out.println("Format is not correct, please input in the format YES or NO. Try again!");
                }
            }
            if (confirmDelete == Confirmation.YES) {
                System.out.println("Please update all the information:");
                if (x instanceof DailyProduct) {
                    x.setProductName(addName());
                    x.setManufacturingDate(addManufacturingDate());
                    x.setExpirationDate(addExpirationDate());
                    x.setQuantity(addQuantity());
                    x.setManufacturer(addManufacturer());
                } else if (x instanceof LslProduct) {
                    x.setProductName(addName());
                    x.setManufacturingDate(addManufacturingDate());
                    x.setExpirationDate(addExpirationDate());
                    x.setQuantity(addQuantity());
//                x.setManufacturer(addManufacturer());
                }
                System.out.println("Update completed successfully.");
                System.out.println("The information has been successfully updated:");
                System.out.println(x.toString());
            } else {
                System.out.println("Product was not updated.");
            }
        }
    }

    //remove
    public void removeAProduct() {
        Scanner scanner = new Scanner(System.in);
        String code;
        System.out.println("Input code that you want to remove: ");
        code = scanner.nextLine();
        Product x = searchAProduct(code);

        if (x == null) {
            System.out.println("NOT FOUND!");
        } else {
            System.out.println("PRODUCT FOUND!");
            System.out.println("Information of product before updating.");
            System.out.println(x.toString());
            //Thông báo xác nhận
            Confirmation confirmDelete = null;
            while (true) {
                try {
                    System.out.println("Do you want to delete this product?[YES/NO]: ");
                    confirmDelete = Confirmation.valueOf(scanner.nextLine().toUpperCase());
                    break;
                } catch (Exception e) {
                    System.out.println("Format is not correct, please input in the format YES or NO. Try again!");
                }
            }
            if (confirmDelete == Confirmation.YES) {
                products.remove(x);
                System.out.println("Employee removed successfully!");
                System.out.println("The employee list after updated: ");
            } else {
                System.out.println("Product was not removed.");
            }

            displayAllProducts();

        }
    }

    //show
    public void displayAllProducts() {
        System.out.println("All products: ");
        System.out.println("\t + Daily use products: ");
        displayDailyUseProducts();
        System.out.println("\t + Long shelf life products: ");
        displayLongShelfLifeProducts();

    }

    public void displayDailyUseProducts() {
        products.forEach(product -> {
            if(product instanceof DailyProduct) System.out.println("\t\t" + ((DailyProduct)product).toString());
        });
    }

    public void displayLongShelfLifeProducts() {
       products.forEach(product -> {
            if(product instanceof LslProduct) System.out.println("\t\t" + ((LslProduct)product).toString());
        });
    }

    //III
    //Expired!
    public List<Product> displayExpiredProducts() {
        List<Product> expiredProducts = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < products.size(); i++) {
            int daysLeft = currentDate.compareTo(products.get(i).getExpirationDate());
            if (daysLeft < 0) {
                expiredProducts.add(products.get(i));
            }
        }

        for (Product expiredProduct : expiredProducts) {
            System.out.println("\t\t" + expiredProduct.toString()); // Print each expired product
        }
        return expiredProducts;
    }

    public List<Product> displaySellingProducts() {
        List<Product> sellingProducts = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < products.size(); i++) {
            int daysLeft = currentDate.compareTo(products.get(i).getExpirationDate());
            if (daysLeft >= 0) {
                sellingProducts.add(products.get(i));
            }
        }

        for (Product sellingProduct : sellingProducts) {
            System.out.println("\t\t" + sellingProduct.toString()); // Print each expired product
        }
        return sellingProducts;
    }

    private String reverseName(String name) {
        String[] names = name.split(" ");
        List<String> listName = new ArrayList<>(Arrays.asList(names));
        Collections.reverse(listName);
        return String.join(" ", listName);
    }

    public List<Product> outOfStockProducts() {
        List<Product> outOfStockProducts = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getQuantity() == 0) { // !!!
                outOfStockProducts.add(products.get(i));
            }
        }

        if (outOfStockProducts.isEmpty()) {
            System.out.println("No products are running out-of-stock.");
        } else {
            for (int i = 0; i < outOfStockProducts.size() - 1; i++) {
                for (int j = i + 1; j < outOfStockProducts.size(); j++) {
                    String name1 = reverseName(outOfStockProducts.get(i).getProductName());
                    String name2 = reverseName(outOfStockProducts.get(j).getProductName());
                    if (name1.compareTo(name2) > 0) { //tăng dần theo tên 
                        Product temp = outOfStockProducts.get(i);
                        outOfStockProducts.set(i, outOfStockProducts.get(j));
                        outOfStockProducts.set(j, temp);

                    }

                }

            }
        }

        for (Product outOfStockProduct : outOfStockProducts) {
            System.out.println("\t\t" + outOfStockProducts.toString()); // 
        }
        return outOfStockProducts;
    }

    public void receiptProduct() {
        Scanner scanner = new Scanner(System.in);
        String code;
        System.out.println("Input code to search information: ");
        code = scanner.nextLine();
        Product x = searchAProduct(code);

        if (x == null) {
            System.out.println("NOT FOUND!");
        } else {
            System.out.println("PRODUCT FOUND!");
            System.out.println("Information: ");
            System.out.println(x.toString());
        }
    }

}
