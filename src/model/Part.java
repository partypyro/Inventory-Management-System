package model;

// Summary: This class abstractly represents a part, or component, of a product. A part has an id number, inventory,
//      stock, a min and max stock, a part name, and a price.
public abstract class Part
{
    private int id, stock, min, max;
    private String name;
    private double price;

    Part(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    // Getters and setters
    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }
    public double getPrice() { return this.price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public void setMin(int min) { this.min = min; }
    public int getMin() { return min; }

    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }
}
