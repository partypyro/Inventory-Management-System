package model;

// Summary: This class represents a part 'outsourced' or bought from another company with a company name given by the
//      instance variable companyName.
public class Outsourced extends Part
{
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    // Getters and setters
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
