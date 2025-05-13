
/**
 * The {@code InventoryItem} class represents an item in an inventory system.
 * Each item has a unique identifier, a name, and a quantity. This class 
 * implements the {@code Comparable} interface to allow sorting of items 
 * based on their unique identifier in ascending order. 
 * 
 * <p>Features of this class include:
 * <ul>
 *   <li>Getter and setter methods for the name and quantity fields.</li>
 *   <li>A final {@code id} field to uniquely identify each item.</li>
 *   <li>A {@code compareTo} method for comparing items by their IDs.</li>
 *   <li>A {@code toString} method for a readable representation of the item.</li>
 * </ul>
 * 
 * 
 */
public class InventoryItem implements Comparable<InventoryItem> {
    
    /**
     * Name of the inventory item.
     */
    private String name;

    /**
     * The current stock quantity of the item.
     */
    private int quantity;

    /**
     * Unique identifier for the inventory item.
     */
    private int id; //can be final


    /**
     * Constructs an InventoryItem with the specified details.
     *
     * @param name      Name of the item
     * @param quantity  Initial quantity in stock  
     * @param id        Unique identifier of the item  
     */
    public InventoryItem(String name, int quantity, int id) {
        this.name = name;
        this.quantity = quantity;
        this.id = id; 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    // public void setId(int id) {
    //     this.id = id;
    // }

    public int compareTo(InventoryItem other){
       return other.getId() - this.getId();   
    }

    /**
     * Returns a string representation of the inventory item.
     *
     * @return String containing item details
     */
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Quantity: " + quantity;
    }
}


