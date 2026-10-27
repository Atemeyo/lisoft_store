public class Product {
    int id;
    String name;
    double price;
    int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %-15s | Price: %,.0f FCFA | Stock: %d",
                id, name, price, quantity);
    }
}