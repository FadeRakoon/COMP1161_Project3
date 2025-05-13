/**
 * Represents an order in the inventory management system.
 * <p>
 * This class contains the details related to an order, including order ID,
 * item name and quantity.
 * </p>
 */

public class Order {
    
    /**
     * Used to generate the id of the order.
     */
    private static int nextId = 1;

    /**
     * Unique identifier of the order.
     */
    private int id;

    /**
     * Name of the item ordered.
     */
    private String itemName;

    /**
     * Quantity of the item ordered.
     */
    private int quantity;

    /**
         * Constructs a new Order with the specified details. An id is auto-generated for each order using nextId.
         *
         * @param itemName      Name of the item being ordered
         * @param quantity      Quantity of the item being ordered
     */
    public Order(String itemName, int quantity) {
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
     * Returns a string representation of the order.
     *
     * @return String representation of the order
     */
    @Override
    public String toString() {
        return itemName + "," + quantity;
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
