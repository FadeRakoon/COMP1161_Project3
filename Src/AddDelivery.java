import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.*;


/**
 * The {@code AddDelivery} class provides a graphical user interface (GUI) for adding 
 * delivery records to restock the inventory. It allows the user to select an item 
 * from a drop-down menu, specify the quantity of the item being delivered, and save 
 * the delivery details. The inventory is updated accordingly, and the delivery 
 * information is saved to a file.
 * 
 * <p>This class extends {@link JFrame} and uses Swing components to create the GUI. 
 * It includes functionality for saving delivery records, updating inventory, and 
 * handling user interactions through buttons and input fields.
 * 
 * <h2>Features:</h2>
 * <ul>
 *   <li>Select an item from a drop-down menu populated with inventory items.</li>
 *   <li>Specify the quantity of the item using a spinner.</li>
 *   <li>Save the delivery details to a file and update the inventory.</li>
 *   <li>Cancel the operation and close the window.</li>
 * </ul>
 * 
 * <h2>Design:</h2>
 * <p>The GUI follows a teal/green color theme for a visually appealing design. 
 * The main components include:
 * <ul>
 *   <li>A drop-down menu for selecting items.</li>
 *   <li>A spinner for specifying the quantity.</li>
 *   <li>Buttons for saving or canceling the operation.</li>
 * </ul>
 * 
 * <h2>Dependencies:</h2>
 * <p>This class depends on the following:
 * <ul>
 *   <li>{@link Main.Inventory} - A list of inventory items.</li>
 *   <li>{@link Main.DeliveryList} - A list to store delivery records.</li>
 *   <li>{@link InventoryItem} - Represents an item in the inventory.</li>
 *   <li>{@link Delivery} - Represents a delivery record.</li>
 * </ul>
 * 
 * <h2>File Operations:</h2>
 * <p>The class performs file operations to save delivery and inventory data:
 * <ul>
 *   <li>Delivery records are saved to "deliver.txt".</li>
 *   <li>Updated inventory data is saved to "Inventory.txt".</li>
 * </ul>
 * 
 * <h2>Event Handling:</h2>
 * <p>The class includes inner classes for handling button actions:
 * <ul>
 *   <li>{@code CancelDeliveryListener} - Closes the window when the cancel button is clicked.</li>
 *   <li>{@code SaveDeliveryListener} - Saves the delivery details and updates the inventory when the save button is clicked.</li>
 * </ul>
 * 
 * <h2>Usage:</h2>
 * <p>To use this class, create an instance of {@code AddDelivery}, which will display 
 * the GUI for adding a delivery. The class interacts with the {@code Main} class to 
 * access and update inventory and delivery lists.
 *
 */


/**
 * Constructs the GUI for Delivery entry form.
 */
public class AddDelivery extends JFrame{
    private JPanel mainMenuPanel;
    private JPanel display;

    private JLabel itemLabel;
    private JComboBox<String> itemDropDown;
    private JLabel quantityLabel;
    private JSpinner quantitySpinner;
    private JButton saveButton;
    private JButton cancelButton;

    private AddDelivery thisDelivery;
    private ArrayList<InventoryItem> IList;

    public AddDelivery(){
        thisDelivery = this;
        IList = Main.Inventory;

        setTitle("Adding Delivery");
        setSize(500, 500);
        setLocationRelativeTo(null);//center window when it appears
        mainMenuPanel = new JPanel();
        display = new JPanel();

        // =========================
        // COLOUR DESIGN SECTION
        // This section applies a teal/green theme to panels and buttons
        // =========================
        Color backgroundTeal = new Color(232, 250, 255); // light teal
        Color buttonTeal = new Color(38, 166, 154); // medium teal
        Color buttonText = Color.WHITE;

        mainMenuPanel.setBackground(backgroundTeal);
        display.setBackground(backgroundTeal);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.setBackground(buttonTeal);
        saveButton.setForeground(buttonText);
        cancelButton.setBackground(buttonTeal);
        cancelButton.setForeground(buttonText);

        itemLabel = new JLabel("Select the item being Delivered");
        display.add(itemLabel);
        itemDropDown = new JComboBox<>();
        itemDropDown.setEditable(false);
        //populating itemDropDown
        for (InventoryItem item: Main.Inventory){
            itemDropDown.addItem(item.getName());
        }
        display.add(itemDropDown);

        quantityLabel = new JLabel("Quantity:");
        //spinner is simple way to deal with numerical data only so no need to convert from string to int
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        display.add(quantityLabel);
        display.add(quantitySpinner);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new SaveDeliveryListener());
        cancelButton.addActionListener(new CancelDeliveryListener());

        mainMenuPanel.add(saveButton);
        mainMenuPanel.add(cancelButton);

        add(display, BorderLayout.CENTER);
        add(mainMenuPanel, BorderLayout.SOUTH);
        setVisible(true); // Show the frame
    } 

    /**
     *Saves the deliveres to the deliver.txt file.
     */
    private void saveDeliveriesToFile() {
        //try with resources to auto close printwriter
        try (PrintWriter out = new PrintWriter("deliver.txt")) {
            for (Order order : Main.OrderList) {
                out.println(order.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *Saves the updated inventory to the Inventory.txt file.
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
     *Updates the inventory based on the deliveries recieved, and saves the updated 
     inventory to Inventory.txt file using {@code UpdInv}.
     */
    private void UpdInv(Delivery Delivery){  
        for(InventoryItem item : Main.Inventory){
            String itemName = item.getName();
            String DeliveryItemName = Delivery.getItemName();
            if (itemName.equals(DeliveryItemName)){
                //set the DeliveryItem quantity to the original value + the quantity on delivery
                //increases since Delivery = company stock inventory increases
                item.setQuantity(item.getQuantity()+ Delivery.getQuantity());
            }
        }
        saveUpdatedInvToFile();
    }

    private class CancelDeliveryListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisible(false);
        }
    }

    private class SaveDeliveryListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //pull selected item from the drop down
            String selectedItem = (String) itemDropDown.getSelectedItem();
            //pull quantity from the spinner
            int quantity = (int) quantitySpinner.getValue();
            

            //if there is a selected item and a non zero quantity
            if (selectedItem != null && quantity > 0) { 
                //create delivery object
                Delivery delivery = new Delivery(selectedItem, quantity);
                //add delivery object to arraylist
                Main.DeliveryList.add(delivery); //WHY IS THIS RED?
                saveDeliveriesToFile(); // Save every time
                UpdInv(delivery);
                JOptionPane.showMessageDialog(thisDelivery, "Delivery saved!");
                dispose(); // close the AddDelivery window
            } else {
                JOptionPane.showMessageDialog(thisDelivery, "Please select a valid item and quantity.");
            }
        }
    }
 
}

