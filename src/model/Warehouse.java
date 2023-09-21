/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import util.Constant;
import util.Validate;

/**
 *
 * @author ADMIN
 */
public class Warehouse implements FileInteraction {

    private ProductList productList;

    private List<Receipt> importReceipt;

    private List<Receipt> exportReceipt;

    public Warehouse(ProductList productList) {
        this.productList = productList;
        importReceipt = new ArrayList<>();
        exportReceipt = new ArrayList<>();
    }

    public List<Receipt> getImportReceipt() {
        return importReceipt;
    }

    public List<Receipt> getExportReceipt() {
        return exportReceipt;
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
                String code = stk.nextToken();
                LocalDateTime createdAt = LocalDateTime.parse(stk.nextToken());

                List<Receipt.ProductItem> productItems = new ArrayList<>();
                for (String item : stk.nextToken().split(";")) {
                    StringTokenizer itemStk = new StringTokenizer(item, ",");
                    Receipt.ProductItem productItem = new Receipt.ProductItem(
                            itemStk.nextToken(),
                            Integer.parseInt(itemStk.nextToken())
                    );
                    productItems.add(productItem);
                }
                Receipt receipts = new Receipt(code, createdAt, productItems);
                if (code.startsWith("I")) {
                    importReceipt.add(receipts);
                } else {
                    exportReceipt.add(receipts);
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
    }

    public void displayAllReceipt() {
        System.out.println("Import receipt: ");
        displayImportReceipt();
        System.out.println("Export receipt: ");
        displayExportReceipt();
    }

    public void displayImportReceipt() {
        importReceipt.forEach(receipt -> System.out.println("\t" + receipt.toString()));
    }

    public void displayExportReceipt() {
        exportReceipt.forEach(receipt -> System.out.println("\t" + receipt.toString()));
    }

    private int addItemQuantity() {
        int itemQuantity;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Input quantity of product: ");
                itemQuantity = Integer.parseInt(scanner.nextLine());
                if (itemQuantity > 0) {
                    break;
                } else {
                    System.out.println("Quantity is invalid. Quantity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" must be integer. Try again!");
            }
        }
        return itemQuantity;
    }

    private String addItemCode() {
        String code;
        Scanner scanner = new Scanner(System.in);
        while (true) {
        System.out.println("Input code: ");
        code = scanner.nextLine().trim().toUpperCase();
            if (Validate.testCode(code)) {
                Product x = searchAProduct(code);
                if (x != null) {
                    break;
                } else {
                    System.out.println("Product ID does not exist");
                }
            } else {
                System.out.println("Code format is not correct, please input in the format DPXXXXXX or LSLXXXXXX (X: digit). Try again!");
            }
        }
        return code;
    }

    public Product searchAProduct(String code) {
        List<Product> products = productList.getProducts();

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

    public Receipt.ProductItem addImformation() {
        Scanner scanner = new Scanner(System.in);
        String itemCode;
        int quantity;
        int choice;
        System.out.println("Add information of product, please: ");
        itemCode = addItemCode();
        quantity = addItemQuantity();
        Confirmation confirm = null;
        while (true) {
            try {
                System.out.println("Are you sure you want to add this item to your receipt?: ");
                confirm = Confirmation.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (Exception e) {
                System.out.println("Format is not correct, please input in the format YES or NO. Try again!");
            }
        }

        if (confirm == Confirmation.YES) {
            Receipt.ProductItem productItem = new Receipt.ProductItem(itemCode, quantity);
            System.out.println("Add item successfully!");
            return productItem;
        } else {
            System.out.println("Adding item failed!");
        }
        return null;
    }
    
    public void importReceipt() {
        Receipt receipt = new Receipt();
        String code = "I" + String.format("%06d", importReceipt.size());
        receipt.setCode(code);
        Scanner scanner = new Scanner(System.in);
        int choice;
        Receipt.ProductItem productItem = addImformation();
        if (productItem != null) receipt.getProducts().add(productItem);

        do {
            System.out.println("What do you want to do next?");
            System.out.println("1. Add more");
            System.out.println("2. Confirm to add receipt to warehouse");
            System.out.println("3. Exit!");
            while (true) {
                try {
                    System.out.print("\nInput your choice: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice >= 1 && choice <= 3) {
                        break;
                    } else {
                        System.out.println("\nPlease choose 1,2 or 3!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please input your choice must be integer(1,2 or 3). Try again!");
                }
            }
            switch (choice) {
                case 1:
                    Receipt.ProductItem otherProductItem = addImformation();
                    if (productItem != null) receipt.getProducts().add(otherProductItem);
                    break;
                case 2:
                    importReceipt.add(receipt);
                    System.out.println("Add receipt to warehouse successfully. Go back main menu!");
                    break;
                case 3:
                    System.out.println("Go back main menu!");
                    break;
            }
        } while (choice != 2 && choice != 3);
    }

    public void exportReceipt() {
        Receipt receipt = new Receipt();
        String code = "E" + String.format("%06d", exportReceipt.size());
        receipt.setCode(code);
    }
}
