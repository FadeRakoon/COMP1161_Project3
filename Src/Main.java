import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 * Main class containing initializaton methods + main method 
 */
public class Main {
    public static ArrayList<InventoryItem> Inventory;
    public static ArrayList<Order> OrderList = new ArrayList<>();
    public static ArrayList<Delivery> DeliveryList = new ArrayList<>();
    /**
     * Enum used to consistently assign ids to items sold
     */
    public enum idNum{
        Bread,
        Brownies,
        Cake,
        Cookies,
        Cupcakes,
        Danish,
        Donut,
    }

    /**
     * Constant storing Total number of items being sold
     */
    public static final int TotItems = 7;

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            createGUI();
        });
    }

    /**
     * Create and initialize main frame as well as loading past orders and deliveries
     */
    private static void createGUI(){
        //create and set frame properties
        JFrame frame = new JFrame("L&R^2 Bakery Inventory System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);//center window when it appears
        frame.setLayout(new BorderLayout());

        LandingFrame contentPane = new LandingFrame();
        contentPane.setOpaque(true);
        frame.setContentPane(contentPane);

        //frame.pack();
        Inventory = new ArrayList<>();
        init(); //loading inventory and generating files
        loadOrders(); //loading orders or generating files
        loadDeliveries(); //loading delivery logs or generating new files
        frame.setVisible(true);
    }

    /**
     * initialization method - handles initial storage/retrieval of inventory data form file
     */
    private static void init(){
        //file storing actual inventoy
        File inv = new File("inventory.txt");
        File invLog = new File("invLog.txt");

        try (PrintWriter writeLog = new PrintWriter(invLog)) {
            if (!inv.exists()) { //check if the inventory file exists
                writeLog.println("File not found. Creating new inventory file...");
    
                if (inv.createNewFile()) {//ifit doesnt exist we create it
                    writeLog.println("inventory.txt created successfully.");
                } else {
                    writeLog.println("Failed to create inventory.txt.");
                }
                //using try with resources so PrintWriter automatically closes when the try block is completed
                try (PrintWriter writeInv = new PrintWriter(inv)) {
                    //make sure inventory is empty
                    Inventory = new ArrayList<>();
                    // for items with index 0 to 1 less than totalnumber of items
                    for (int i = 0; i < TotItems; i++) {
                        //pull the name from the enum
                        String name = idNum.values()[i].name();
                        //pull the id from the enum
                        int id = idNum.values()[i].ordinal();
                        //write to file in format id name quantity
                        writeInv.println(id + " " + name + " 0");
                        //add to arraylist
                        Inventory.add(new InventoryItem(name, 0, id));
                    }
                }
    
            } else {
                writeLog.println("File found. Loading existing inventory...");
                Inventory = new ArrayList<>();
    
                try (Scanner read = new Scanner(inv)) {
                    while (read.hasNextLine()) {
                        String line = read.nextLine();
                        String[] parts = line.split(" ");
                        if (parts.length >= 3) {
                            int itemID = Integer.parseInt(parts[0]);
                            String itemName = parts[1];
                            int itemQuantity = Integer.parseInt(parts[2]);
                            Inventory.add(new InventoryItem(itemName, itemQuantity, itemID));
                        }
                    }
                } catch (FileNotFoundException e) {
                    writeLog.println("Error reading inventory file.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads past orders into memory from file  
     */

    private static void loadOrders() {
        File ordersFile = new File("orders.txt");
        int maxId = 0;
        //if the file exists
        if (ordersFile.exists()) {
            //try with resources to auto close scanner when try block done
            try (Scanner scanner = new Scanner(ordersFile)) {
                while (scanner.hasNextLine()) {
                    //split read line into parts
                    String[] parts = scanner.nextLine().split(","); //comma separated because we use default toString
                    //there should be 3 parts everytime but just to be sure
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0]);
                        String itemName = parts[1];
                        int quantity = Integer.parseInt(parts[2]);
                        //add order to list
                        Main.OrderList.add(new Order(itemName, quantity));
                        //update maximum id so that the last id in the arraylist is always unique
                        maxId = Math.max(maxId, id);
                    }
                }
                Order.setNextId(maxId + 1); // ensure ID is always unique
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * loads past deliveries into memory from file
     */

    private static void loadDeliveries() {
        File DeliveryFile = new File("Delivery.txt");
        int maxId = 0;
        //if the file exists
        if (DeliveryFile.exists()) {
            //try with resources to auto close scanner when try block done
            try (Scanner scanner = new Scanner(DeliveryFile)) {
                while (scanner.hasNextLine()) {
                    //split read line into parts
                    String[] parts = scanner.nextLine().split(","); //comma separated because we use default toString
                    //there should be 3 parts everytime but just to be sure
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0]);
                        String itemName = parts[1];
                        int quantity = Integer.parseInt(parts[2]);
                        //add order to list
                        Main.OrderList.add(new Order(itemName, quantity));
                        //update maximum id so that the last id in the arraylist is always unique
                        maxId = Math.max(maxId, id);
                    }
                }
                Order.setNextId(maxId + 1); // ensure ID is always unique
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
