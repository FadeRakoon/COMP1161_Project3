import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The ListInventory class represents the panel of the application that 
 * enables the user to view the inventory as an itemized list, 
 * providing a user interface with buttons for various actions such as 
 * sorting the inventory by id number and quantiy. Additionally, it enables 
 * the user to edit the inventory, provided that they input the correct password. 
 * It extends JFrame and uses a custom color scheme and button styling.
 * 
 * <p>This class includes:
 * <ul>
 *   <li>Custom color and font styling for buttons.</li>
 *   <li>Action listeners for handling button clicks.</li>
 *   <li>Methods that provide a layout of the inventory in stock.<li>
 * </ul>
 * </p>
 * 
 * <p>Dependencies:
 * <ul>
 *   <li>Done - Handles the "Edit Inventory" functionality, by saving the edit made.</li>
 *   <li>Edit - Handles the "Edit Inventory" functionality only if the user inputs the correct password.</li>
 *   <li>Close - Handles the "Closing Frame" functionality.</li>
 *   <li>Sortid - Handles the "Sort Inventory by id" functionality.</li>
 *   <li>SortQuantity - Handles the "Sort Inventory by quantity" functionality.</li>
 * </ul>
 * </p>
 */


public class ListInventory extends JFrame{
    private JPanel mainMenuPanel;
    private JPanel display;
    private JPanel popup;
    
    private JButton edit;
    private JButton close;
    private JButton idSort;
    private JButton quantitySort;
    private JButton showChartBtn;
    private JButton done; //for popup
    
    private JLabel itemIdLabel;// for  popup
    private JSpinner itemIdDropDown; //for popup
    private JLabel quantityLabel; //for popup
    private JSpinner quantitySpinner; //for popup
    
    private JScrollPane scrollpane;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<InventoryItem> IList;

    /**
    * Constructs the GUI for List Inventory Frame.
    */
    public ListInventory(){
        IList = Main.Inventory;

        setTitle("Current Inventory");
        setSize(700, 500);
        setLocationRelativeTo(null);

        mainMenuPanel = new JPanel();
        popup = new JPanel();
        display = new JPanel();
        
        // =========================
        // COLOUR DESIGN SECTION
        // This section sets background colours and button colours
        // for the main UI components using shades of cyan.
        // =========================
        Color lightCyan = new Color(224, 255, 255); // very light cyan
        Color mediumCyan = new Color(0, 206, 209);  // medium cyan
        Color darkerCyan = new Color(0, 139, 139);  // darker cyan
        
        mainMenuPanel.setBackground(lightCyan);
        popup.setBackground(lightCyan);
        display.setBackground(lightCyan);
        

        edit = new JButton("Edit Inventory");
        close = new JButton("Close");
        idSort = new JButton("Sort by ID");
        quantitySort = new JButton("Sort by Quantity");
        showChartBtn = new JButton("Pie Chart");
        
        

        done = new JButton("Done");
        done.addActionListener(new DoneButtonListener());
        
        itemIdLabel = new JLabel("ID of item to edit:");
        itemIdDropDown = new JSpinner(new SpinnerNumberModel(0,0,Main.TotItems,1));

        popup.add(itemIdLabel);
        popup.add(itemIdDropDown);

        quantityLabel = new JLabel("Quantity:");
        //spinner is simple way to deal with numerical data only so no need to convert from string to int
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
        //putting popup on screen
        popup.add(quantityLabel);
        popup.add(quantitySpinner);
        popup.add(done);
        
        edit.addActionListener(new EditButtonListener());
        close.addActionListener(new CloseButtonListener());
        idSort.addActionListener(new SortidListener());
        quantitySort.addActionListener(new SortQuantityListener());
        showChartBtn.addActionListener(new ShowChartListener());

        edit.setBackground(mediumCyan);
        edit.setForeground(Color.WHITE);

        close.setBackground(mediumCyan);
        close.setForeground(Color.WHITE);

        idSort.setBackground(mediumCyan);
        idSort.setForeground(Color.WHITE);

        quantitySort.setBackground(mediumCyan);
        quantitySort.setForeground(Color.WHITE);

        showChartBtn.setBackground(mediumCyan);
        showChartBtn.setForeground(Color.WHITE);

        done.setBackground(darkerCyan);
        done.setForeground(Color.WHITE);

        display.add(edit);
        display.add(idSort);
        display.add(quantitySort);
        display.add(showChartBtn);
        display.add(close);

        add(display,BorderLayout.SOUTH); //bottom of frame
        popup.setVisible(false);
        add(popup,BorderLayout.CENTER); //middle of frame

        //table things
        String[] columnNames ={
            "ID",
            "Item",
            "Quantity"
        };
        model=new DefaultTableModel(columnNames,0){
            public boolean isCellEditable(int row,int column){ //make table not be editable by user
                return false;
            }
        };
        table = new JTable(model);
        CustomCellRenderer renderer = new CustomCellRenderer();
        table.getColumnModel().getColumn(2).setCellRenderer(renderer);
        showTable(IList);

        table.setPreferredScrollableViewportSize(new Dimension(500, Main.Inventory.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollpane = new JScrollPane(table);
        add(scrollpane,BorderLayout.NORTH); //goes to top of frame
        setVisible(true);
        
    }

    /**
     * Displays inventory in a table format.
     */
    private void showTable(ArrayList<InventoryItem> IList){
        if (!IList.isEmpty()){
            for(InventoryItem item:IList){
                addToTable(item);
            }
        }
    }

    /**
     * Adds an inventory item to a row in a table. 
     */
    private void addToTable(InventoryItem item){
        String itemName = item.getName();
        String itemId = Integer.toString(item.getId());
        String itemQuantity = Integer.toString(item.getQuantity());
        String[] row = {itemId,itemName,itemQuantity};
        model.addRow(row);
    }

    private void UpdTable(ArrayList<InventoryItem> IList){
        model.setRowCount(0);
        showTable(IList);
    }

    /**
     * Saves the updated inventory to the Inventory.txt file.
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
     * Updates the quanitity of an item in inventory, then saves the updated inventory to the Inventory.txt file.
     */
    private void UpdInv(int itemId,int newQuantity){  
        for(InventoryItem item : Main.Inventory){
            int id = item.getId();
            if (id == itemId){
                item.setQuantity(newQuantity);
            }
        }
        saveUpdatedInvToFile();
        IList = Main.Inventory;
    }

    private class DoneButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            popup.setVisible(false);

            int itemId = (int) itemIdDropDown.getValue();
            int newQuantity = (int) quantitySpinner.getValue();
            
            UpdInv(itemId,newQuantity);
            UpdTable(IList);
        }
    }

    private class EditButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Login loginDialog = new Login(); //make login dialogbox
            loginDialog.setLocationRelativeTo(null); // Center on screen
            loginDialog.setVisible(true); // This will block until loginDialog is disposed because its a dialogbox

            if (Login.loginSuccessful) {
                popup.setVisible(true);
                itemIdDropDown.setValue(0);
                quantitySpinner.setValue(1);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Edit action cancelled or login failed.",
                    "Access Denied",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class CloseButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisible(false);
        }
    }

    private class SortidListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Collections.sort(IList, Comparator.comparingInt(InventoryItem::getId));
            model.setRowCount(0);
            showTable(IList);
        }
    }

    private class SortQuantityListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Collections.sort(IList, Comparator.comparingInt(InventoryItem::getQuantity));
            model.setRowCount(0);
            showTable(IList);
        }
    }

    private class ShowChartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //new InventoryPieChart(IList); // method that displays the chart window
            PieChartWindow.showChart(IList);
        }
    }

}
