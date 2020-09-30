package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Summary: This class handles the storage and management of data regarding the parts and products held in the
//      physical inventory. Class methods reflect the ability to add new parts and products to the inventory, and
//      lookup, remove, or update existing parts or products in the inventory. generateNewPartID() and
//      generateNewProductID() may be used to generate new unique IDs for objects stored in the inventory.
public class Inventory
{
    // These lists store all products and parts in a list usable by JavaFX tables
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void addPart(Part newPart) { this.allParts.add(newPart); }
    public void addProduct(Product newProduct) { this.allProducts.add(newProduct); }

    // Summary: Find the part with the given part ID
    public Part lookupPart(int partId)
    {
        for (Part part : this.allParts)
            if (part.getId() == partId)
                return part;

        return null;
    }
    // Summary: Find the part with the given part name
    public Part lookupPart(String partName)
    {
        for (Part part : this.allParts)
            if (part.getName().equals(partName))
                return part;
        return null;
    }

    // Summary: Find the product with the given product ID
    public Product lookupProduct(int productId)
    {
        for (Product product : this.allProducts)
            if (product.getId() == productId)
                return product;
        return null;
    }
    // Summary: Find the product with the given product name
    public Product lookupProduct(String productName)
    {
        for (Product product : this.allProducts)
            if (product.getName().equals(productName))
                return product;

        return null;
    }

    // Summary: Replaces a currently existing part at the given index with a new part
    public void updatePart(int index, Part newPart)
    {
        this.allParts.set(index, newPart);
    }

    // Summary: Replaces a currently existing product at the given index with a new product
    public void updateProduct(int index, Product newProduct)
    {
        this.allProducts.set(index, newProduct);
    }

    public void deletePart(Part selectedPart)
    {
        this.allParts.remove(selectedPart);
    }

    public void deleteProduct(Product selectedProduct)
    {
        this.allProducts.remove(selectedProduct);
    }

    // Summary: This function generates a new unique part ID given that the 'next' part ID is one larger than the
    //      highest part ID.
    public int generatePartId()
    {
        // Find the max ID value for a part - the 'next' or automatically generated Id
        int nextId = 0;
        for(Part part : this.allParts)
            if (part.getId() > nextId) nextId = part.getId();
        return nextId + 1;
    }

    // Summary: This function generates a new unique product ID given that the 'next' product ID is one larger than the
    //      highest product ID.
    public int generateProductId()
    {
        // Find the max ID value for a part - the 'next' or automatically generated Id
        int nextId = 0;
        for(Product product : this.allProducts)
            if (product.getId() > nextId) nextId = product.getId();
        return nextId + 1;
    }

    // Getters and Setters
    public ObservableList<Part> getAllParts() { return this.allParts; }
    public ObservableList<Product> getAllProducts() { return this.allProducts; }
}
