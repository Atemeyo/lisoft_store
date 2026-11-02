import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store myStore = new Store();

        System.out.println("===== EMPLOYEE LOGIN / REGISTER =====");
        Employee currentEmployee = null;
        while (currentEmployee == null) {
            System.out.println("1. Login with Employee ID");
            System.out.println("2. Register New Employee");
            System.out.print("Choose: ");
            String empChoice = scanner.nextLine();
            if (empChoice.equals("1")) {
                System.out.print("Enter Employee ID: ");
                try {
                    int eid = Integer.parseInt(scanner.nextLine());
                    currentEmployee = myStore.getEmployee(eid);
                    if (currentEmployee == null) System.out.println("Employee not found.");
                } catch (Exception e) {
                    System.out.println("Invalid ID.");
                }
            } else if (empChoice.equals("2")) {
                System.out.print("Enter Employee Name: ");
                String ename = scanner.nextLine();
                if (!ename.trim().isEmpty()) {
                    myStore.addEmployee(ename);
                    System.out.println("Employee registered. Please login.");
                    for (Employee e : myStore.getAllEmployees()) System.out.println(e);
                }
            }
        }
        System.out.println("Welcome, " + currentEmployee.getName() + "!");

        while (true) {
            System.out.println("\n===== LISOFT STORE MANAGEMENT (FCFA) =====");
            System.out.println("Logged as: " + currentEmployee.getName());
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Sell Product");
            System.out.println("5. Restock Product");
            System.out.println("6. Activate / Deactivate Product");
            System.out.println("7. View Sales History");
            System.out.println("8. Register New Employee");
            System.out.println("9. Exit");
            System.out.print("Choose an Option: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter Product Name: ");
                        String name = scanner.nextLine();
                        if (myStore.isDuplicate(name)) {
                            System.out.println("Error: Product with name '" + name + "' already exists!");
                            break;
                        }
                        System.out.print("Enter Price (FCFA): ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter Quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        myStore.addProduct(name, price, qty);
                        System.out.println("Product added successfully.");
                        break;
                    case "2":
                        if (myStore.getAllProducts().isEmpty()) {
                            System.out.println("No products available.");
                        } else {
                            for (Product p : myStore.getAllProducts()) System.out.println(p);
                        }
                        break;
                    case "3":
                        System.out.print("Enter Product ID or Name: ");
                        String input = scanner.nextLine().trim();
                        try {
                            int id = Integer.parseInt(input);
                            Product p = myStore.searchById(id);
                            if (p != null) System.out.println(p);
                            else System.out.println("No product found with ID: " + id);
                        } catch (NumberFormatException e) {
                            List<Product> results = myStore.searchByName(input);
                            if (results.isEmpty()) System.out.println("No product found with name: " + input);
                            else for (Product p : results) System.out.println(p);
                        }
                        break;
                    case "4":
                        System.out.print("Enter Product ID: ");
                        int sellId = Integer.parseInt(scanner.nextLine());
                        Product prod = myStore.searchById(sellId);
                        if (prod == null) {
                            System.out.println("Product not found.");
                            break;
                        }
                        if (!prod.isActive()) {
                            System.out.println("Product is INACTIVE and cannot be sold.");
                            break;
                        }
                        System.out.print("Enter Quantity to Sell: ");
                        int sellQty = Integer.parseInt(scanner.nextLine());

                        if (sellQty > prod.getQuantity()) {
                            System.out.println("Not enough stock. Available quantity: " + prod.getQuantity());
                            System.out.print("Do you want to buy the available quantity " + prod.getQuantity() + "? (y/n): ");
                            String confirm = scanner.nextLine();
                            if (confirm.equalsIgnoreCase("y")) {
                                sellQty = prod.getQuantity();
                            } else {
                                System.out.println("Sale cancelled.");
                                break;
                            }
                        }

                        System.out.println("Select Payment Method:");
                        System.out.println("1. Cash  2. Mobile Money  3. Card");
                        System.out.print("Choose: ");
                        String payChoice = scanner.nextLine();
                        String paymentMethod = "Cash";
                        if (payChoice.equals("2")) paymentMethod = "Mobile Money";
                        else if (payChoice.equals("3")) paymentMethod = "Card";

                        System.out.println("Total to pay: " + (prod.getPrice() * sellQty) + " FCFA via " + paymentMethod);
                        System.out.print("Confirm payment? (y/n): ");
                        String payConfirm = scanner.nextLine();
                        if (!payConfirm.equalsIgnoreCase("y")) {
                            System.out.println("Payment cancelled.");
                            break;
                        }

                        Store.SaleRecord record = myStore.sellProduct(sellId, sellQty, paymentMethod, currentEmployee.getName());
                        if (record != null) {
                            System.out.println("Sale successful. Payment of " + record.total + " FCFA received via " + paymentMethod);
                            System.out.print("Do you want to print receipt? (y/n): ");
                            String receiptChoice = scanner.nextLine();
                            if (receiptChoice.equalsIgnoreCase("y")) {
                                System.out.println(record.getReceipt());
                            }
                        } else {
                            System.out.println("Sale failed.");
                        }
                        break;
                    case "5":
                        System.out.print("Enter Product ID: ");
                        int restockId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Quantity: ");
                        int restockQty = Integer.parseInt(scanner.nextLine());
                        if (myStore.restockProduct(restockId, restockQty)) System.out.println("Restocked successfully.");
                        else System.out.println("Product ID not found.");
                        break;
                    case "6":
                        System.out.print("Enter Product ID: ");
                        int statusId = Integer.parseInt(scanner.nextLine());
                        Product sp = myStore.searchById(statusId);
                        if (sp == null) {
                            System.out.println("Product not found.");
                            break;
                        }
                        System.out.println("Current status: " + (sp.isActive() ? "ACTIVE" : "INACTIVE"));
                        System.out.print("Set to (1=Active, 0=Inactive): ");
                        String s = scanner.nextLine();
                        if (s.equals("1")) {
                            myStore.setProductStatus(statusId, true);
                            System.out.println("Product activated.");
                        } else if (s.equals("0")) {
                            myStore.setProductStatus(statusId, false);
                            System.out.println("Product deactivated.");
                        }
                        break;
                    case "7":
                        if (myStore.getHistory().isEmpty()) System.out.println("No sales yet.");
                        else for (Store.SaleRecord sr : myStore.getHistory()) System.out.println(sr);
                        break;
                    case "8":
                        System.out.print("Enter New Employee Name: ");
                        String newEmpName = scanner.nextLine();
                        myStore.addEmployee(newEmpName);
                        System.out.println("Employee added. All employees:");
                        for (Employee e : myStore.getAllEmployees()) System.out.println(e);
                        break;
                    case "9":
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}