public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private boolean active;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.active = true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public boolean isActive() { return active; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        String status = active ? "ACTIVE" : "INACTIVE";
        return String.format("ID: %d | Name: %-15s | Price: %,.0f FCFA | Stock: %d | Status: %s", id, name, price, quantity, status);
    }
}