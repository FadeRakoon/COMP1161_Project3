/**
 * Represents a delivery in the inventory management system.
 * <p>
 * This class contains the details related to a delivery, including delivery ID,
 * item name and quantity.
 * </p>
 */


public class Delivery {

    /**
     * Used to generate the id of the delivery.
     */
    private static int nextId = 1;

    /**
     * Unique identifier of a delivery.
     */
    private int id;

    /**
     * Name of the item delivered.
     */
    private String itemName;

    /**
     * Quantity of the item delivered.
     */
    private int quantity;


    /**
         * Constructs a new delivery with the specified details. An id is auto-generated for each order using nextId.
         *
         * @param itemName      Name of the item delivered
         * @param quantity      Quantity of the item delivered
     */
    public Delivery(String itemName, int quantity) {
        this.id = nextId++;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns a string representation of the delivery.
     *
     * @return String representation of the delivery
     */
    // String representation for file writing
    @Override
    public String toString() {
        return itemName + "," + quantity;
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
