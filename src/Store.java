import java.util.*;

public class Store {
    private Map<Integer, Product> products = new HashMap<>();
    private List<String> salesHistory = new ArrayList<>();
    private int nextId = 1;

    public Store() {
        // Your two Cameroonian products
        addProductInternal("Garri", 1500, 100);
        addProductInternal("Eru", 2500, 50);
    }

    private void addProductInternal(String name, double price, int qty) {
        products.put(nextId, new Product(nextId, name, price, qty));
        nextId++;
    }

    public void addProduct(String name, double price, int qty) {
        products.put(nextId, new Product(nextId, name, price, qty));
        System.out.println("Product.java added: " + products.get(nextId));
        nextId++;
    }

    public void viewAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\n--- All Products ---");
        for (Product p : products.values()) {
            System.out.println(p);
        }
    }

    public void searchProduct(String keyword) {
        boolean found = false;
        for (Product p : products.values()) {
            if (p.name.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) System.out.println("No product found for: " + keyword);
    }

    public void sellProduct(int id, int qty) {
        Product p = products.get(id);
        if (p == null) {
            System.out.println("Product.java ID not found.");
            return;
        }
        if (qty > p.quantity) {
            System.out.println("Not enough stock! Available: " + p.quantity);
            return;
        }
        p.quantity -= qty;
        double total = p.price * qty;
        String sale = String.format("Sold %d x %s = %,.0f FCFA", qty, p.name, total);
        salesHistory.add(sale);
        System.out.println(sale);
    }

    public void restockProduct(int id, int qty) {
        Product p = products.get(id);
        if (p == null) {
            System.out.println("Product.java ID not found.");
            return;
        }
        p.quantity += qty;
        System.out.println("Restocked. New stock: " + p.quantity);
    }

    public void removeProduct(int id) {
        if (products.remove(id) != null) {
            System.out.println("Product.java removed.");
        } else {
            System.out.println("ID not found.");
        }
    }

    public void viewSalesHistory() {
        if (salesHistory.isEmpty()) {
            System.out.println("No sales yet.");
            return;
        }
        System.out.println("\n--- Sales History ---");
        for (String s : salesHistory) {
            System.out.println(s);
        }
    }
}