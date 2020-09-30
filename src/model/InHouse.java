package model;

// Summary: This class represents a part made 'In-House' by a machine with an ID given by the instance variable
//      machine ID
public class InHouse extends Part
{
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    // Getters and Setters
    public int getMachineId() { return machineId; }
    public void setMachineId(int machineId) { this.machineId = machineId; }
}
