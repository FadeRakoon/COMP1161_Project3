import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The  {@code AddOrder} class provides a graphical user interface (GUI) for adding orders
 * to the order log and updating the inventory accordingly. It allows users to 
 * select an item from a dropdown menu, specify the quantity using a spinner, 
 * and save the order. The inventory is updated to reflect the ordered quantity, 
 * and the changes are saved to files.
 * 
 * <p>Features of this class include:
 * <ul>
 *   <li>Dropdown menu for selecting items from the inventory.</li>
 *   <li>Spinner for specifying the quantity of the selected item.</li>
 *   <li>Buttons for saving the order or canceling the operation.</li>
 *   <li>Automatic updates to the inventory and order log files.</li>
 * </ul>
 * 
 * <p>Dependencies:
 * <ul>
 *   <li>Relies on the {@code Main.Inventory} list for inventory data.</li>
 *   <li>Relies on the {@code Main.OrderList} list for storing orders.</li>
 *   <li>Uses {@code InventoryItem} and {@code Order} classes for managing inventory and orders.</li>
 * </ul>
 * 
 * <p>File Operations:
 * <ul>
 *   <li>Orders are saved to "orders.txt".</li>
 *   <li>Updated inventory is saved to "Inventory.txt".</li>
 * </ul>
 * 
 * <p>Color Scheme:
 * <ul>
 *   <li>Soft pink background for panels.</li>
 *   <li>Rose red and light coral colors for labels and buttons.</li>
 * </ul>
 * 
 * <p>Usage:
 * <ol>
 *   <li>Create an instance of the {@code AddOrder} class to display the GUI.</li>
 *   <li>Select an item and specify the quantity.</li>
 *   <li>Click "Save" to save the order and update the inventory, or "Cancel" to close the window.</li>
 * </ol>
 * 
 * <p>Note: The class ensures that the ordered quantity does not exceed the available stock.
 * If the stock is insufficient, an error message is displayed.
 * 
 */


/**
 * Constructor to set up the GUI Order entry form.
 */
public class AddOrder extends JFrame{
    private JPanel mainMenuPanel;
    private JPanel display;

    private JLabel itemLabel;
    private JComboBox<String> itemDropDown;
    private JLabel quantityLabel;
    private JSpinner quantitySpinner;
    private JButton saveButton;
    private JButton cancelButton;

    private final Color softPink = new Color(255, 182, 193);  // Light pink
    private final Color roseRed = new Color(220, 20, 60);     // Crimson
    private final Color lightCoral = new Color(240, 128, 128);    // Light coral

    private AddOrder thisOrder;
    private ArrayList<InventoryItem> IList;

    public AddOrder(){
        thisOrder = this;
        IList = Main.Inventory;

        setTitle("Adding Order");
        setSize(500, 500);
        setLocationRelativeTo(null);    //center window when it appears
        mainMenuPanel = new JPanel();
        display = new JPanel();

        // Set background color
        mainMenuPanel.setBackground(softPink);
        display.setBackground(softPink);

        itemLabel = new JLabel("Select the item to be ordered");
        itemLabel.setForeground(roseRed);
        display.add(itemLabel);
        itemDropDown = new JComboBox<>();
        itemDropDown.setBackground(Color.WHITE);
        itemDropDown.setForeground(roseRed);
        itemDropDown.setEditable(false);
        
        //populating itemDropDown
        for (InventoryItem item: Main.Inventory){
            itemDropDown.addItem(item.getName());
        }
        display.add(itemDropDown);

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(roseRed);
        //spinner is simple way to deal with numerical data only so no need to convert from string to int
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        display.add(quantityLabel);
        display.add(quantitySpinner);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Set button colours
        saveButton.setBackground(lightCoral);
        saveButton.setForeground(Color.WHITE);

        cancelButton.setBackground(roseRed);
        cancelButton.setForeground(Color.WHITE);

        saveButton.addActionListener(new SaveOrderListener());
        cancelButton.addActionListener(new CancelOrderListener());

        mainMenuPanel.add(saveButton);
        mainMenuPanel.add(cancelButton);

        add(display, BorderLayout.CENTER);
        add(mainMenuPanel, BorderLayout.SOUTH);
        setVisible(true); // Show the frame
    } 

    /**
     * Method that saves the orders to the orders.txt file.
     */
    private void saveOrdersToFile() {
        //try with resources to auto close printwriter
        try (PrintWriter out = new PrintWriter("orders.txt")) {
            for (Order order : Main.OrderList) {
                out.println(order.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that saves the inventory that was updated using {@code UpdInv} to the Inventory.txt file.
     */
    private void saveUpdatedInvToFile() {
        //try with resources to auto close printwriter
        try (PrintWriter out = new PrintWriter("Inventory.txt")) {
            for (InventoryItem item : Main.Inventory) {
                out.println(item.getId() + " " + item.getName() + " " + item.getQuantity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that updates the inventory based on the orders recieved.
     */
    private void UpdInv(Order order){
        for(InventoryItem item : Main.Inventory){
            String itemName = item.getName();
            String orderItemName = order.getItemName();
            if (itemName.equals(orderItemName)){
                //set the item quantity to the original value - the quantity on order
                //decreases since order = someone buying from us
                item.setQuantity(item.getQuantity()-order.getQuantity());
            }
        }
        saveUpdatedInvToFile();
    }

    private class CancelOrderListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisible(false);
        }
    }

    private class SaveOrderListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //pull selected item from the drop down
            String selectedItem = (String) itemDropDown.getSelectedItem();
            //pull quantity from the spinner
            int quantity = (int) quantitySpinner.getValue();
            int checkQuantity =0;
            //if there is a selected item and a non zero quantity
            for(InventoryItem item : Main.Inventory){
                if (item.getName().equals(selectedItem)){
                    checkQuantity = item.getQuantity();
                    break;
                } 
            }

            if (checkQuantity >= quantity){
                if (selectedItem != null && quantity > 0) { 
                    //if(quantity<=)
                    //create order object
                    Order order = new Order(selectedItem, quantity);
                    //add order object to arraylist
                    Main.OrderList.add(order);
                    saveOrdersToFile(); // Save every time
                    UpdInv(order);
                    JOptionPane.showMessageDialog(thisOrder, "Order saved!");
                    dispose(); // close the AddOrder window
                } else {
                    JOptionPane.showMessageDialog(thisOrder, "Please select a valid item and quantity.");
                }
            } else {
                JOptionPane.showMessageDialog(thisOrder, "We currently only have " + checkQuantity + "in stock");
            }
        }
    }
 
}
