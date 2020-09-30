package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Summary: This class represents an inventory item, a product, that is made of multiple constituent parts, as stored in
//      the 'associatedParts' list. A product has an id number, an inventory stock, a min and max, stock, a product
//      name, and a price.
public class Product
{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;

    public Product(int id, String name, int stock, int min, int max, double price)
    {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
    }

    public void addAssociatedPart(Part part) { this.associatedParts.add(part); }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    public ObservableList<Part> getAllAssociatedParts() { return this.associatedParts; }

    // Getters and setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }
    public int getMin() { return min; }

    public void setMin(int min) { this.min = min; }
    public int getMax() { return max; }

    public void setMax(int max) { this.max = max; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }
}
