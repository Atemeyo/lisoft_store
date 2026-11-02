import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Store {
    public static class SaleRecord {
        String productName;
        int quantitySold;
        double total;
        String date;
        String paymentMethod;
        String employeeName;

        SaleRecord(String productName, int quantitySold, double total, String paymentMethod, String employeeName) {
            this.productName = productName;
            this.quantitySold = quantitySold;
            this.total = total;
            this.paymentMethod = paymentMethod;
            this.employeeName = employeeName;
            this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        public String getReceipt() {
            return "\n========== LISOFT STORE RECEIPT ==========\n" +
                   "Date: " + date + "\n" +
                   "Employee: " + employeeName + "\n" +
                   "----------------------------------------\n" +
                   "Product: " + productName + "\n" +
                   "Quantity: " + quantitySold + "\n" +
                   "Payment: " + paymentMethod + "\n" +
                   "----------------------------------------\n" +
                   String.format("TOTAL: %,.0f FCFA\n", total) +
                   "========================================\n" +
                   "Thank you for your purchase!\n";
        }

        @Override
        public String toString() {
            return String.format("[%s] Emp: %s | %s x %d = %,.0f FCFA | Pay: %s", date, employeeName, productName, quantitySold, total, paymentMethod);
        }
    }

    private Map<Integer, Product> products = new HashMap<>();
    private List<SaleRecord> history = new ArrayList<>();
    private Map<Integer, Employee> employees = new HashMap<>();
    private int nextProductId = 1;
    private int nextEmployeeId = 1;

    public Store() {
        products.put(nextProductId, new Product(nextProductId++, "Garri", 1500, 100));
        products.put(nextProductId, new Product(nextProductId++, "Eru", 2500, 50));
        employees.put(nextEmployeeId, new Employee(nextEmployeeId++, "Default Staff"));
    }

    public boolean isDuplicate(String name) {
        for (Product p : products.values()) {
            if (p.getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }

    public void addProduct(String name, double price, int qty) {
        products.put(nextProductId, new Product(nextProductId, name, price, qty));
        nextProductId++;
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Product searchById(int id) {
        return products.get(id);
    }

    public List<Product> searchByName(String name) {
        List<Product> results = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    public SaleRecord sellProduct(int id, int qty, String paymentMethod, String employeeName) {
        Product p = products.get(id);
        if (p == null || !p.isActive() || p.getQuantity() < qty) {
            return null;
        }
        p.setQuantity(p.getQuantity() - qty);
        double total = p.getPrice() * qty;
        SaleRecord record = new SaleRecord(p.getName(), qty, total, paymentMethod, employeeName);
        history.add(record);
        return record;
    }

    public boolean restockProduct(int id, int qty) {
        Product p = products.get(id);
        if (p == null) return false;
        p.setQuantity(p.getQuantity() + qty);
        return true;
    }

    public boolean setProductStatus(int id, boolean active) {
        Product p = products.get(id);
        if (p == null) return false;
        p.setActive(active);
        return true;
    }

    public List<SaleRecord> getHistory() {
        return history;
    }

    public void addEmployee(String name) {
        employees.put(nextEmployeeId, new Employee(nextEmployeeId, name));
        nextEmployeeId++;
    }

    public Collection<Employee> getAllEmployees() {
        return employees.values();
    }

    public Employee getEmployee(int id) {
        return employees.get(id);
    }
}