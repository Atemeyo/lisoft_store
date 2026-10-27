import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store myStore = new Store();

        while (true) {
            System.out.println("\n===== STORE MANAGEMENT SYSTEM (FCFA) =====");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Sell Product");
            System.out.println("5. Restock Product.java");
            System.out.println("6. Remove Product.java");
            System.out.println("7. View Sales History");
            System.out.println("9. Exit");
            System.out.print("Choose an Option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter Product.java Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Price (FCFA): ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter Quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        myStore.addProduct(name, price, qty);
                        break;
                    case "2":
                        myStore.viewAllProducts();
                        break;
                    case "3":
                        System.out.print("Enter keyword to search: ");
                        String keyword = scanner.nextLine();
                        myStore.searchProduct(keyword);
                        break;
                    case "4":
                        System.out.print("Enter Product.java ID to sell: ");
                        int sellId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Quantity to sell: ");
                        int sellQty = Integer.parseInt(scanner.nextLine());
                        myStore.sellProduct(sellId, sellQty);
                        break;
                    case "5":
                        System.out.print("Enter Product.java ID to restock: ");
                        int restId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Quantity to add: ");
                        int addQty = Integer.parseInt(scanner.nextLine());
                        myStore.restockProduct(restId, addQty);
                        break;
                    case "6":
                        System.out.print("Enter Product.java ID to remove: ");
                        int remId = Integer.parseInt(scanner.nextLine());
                        myStore.removeProduct(remId);
                        break;
                    case "7":
                        myStore.viewSalesHistory();
                        break;
                    case "9":
                        System.out.println("Exiting... Bye!");
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter valid numbers.");
            }
        }
    }
}