import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The LandingFrame class represents the main panel of the application, 
 * providing a user interface with buttons for various actions such as 
 * adding orders, adding deliveries, and listing inventory. It extends 
 * JPanel and uses a custom color scheme and button styling.
 * 
 * <p>This class includes:
 * <ul>
 *   <li>A main menu panel with buttons for user actions.</li>
 *   <li>Custom color and font styling for buttons.</li>
 *   <li>Action listeners for handling button clicks.</li>
 * </ul>
 * </p>
 * 
 * <p>Overview:
 * <ul>
 *   <li>AddOrder - Handles the "Add Order" functionality.</li>
 *   <li>AddDelivery - Handles the "Add Delivery" functionality.</li>
 *   <li>ListInventory - Handles the "List Inventory" functionality.</li>
 * </ul>
 * </p>
 */



public class LandingFrame extends JPanel {
    
    //private LandingFrame landingFrame;
    private JPanel mainMenuPanel;
    private JPanel display; //used to dispaly content over main panel
    
    //buttons
    private JButton addOrderButton;
    private JButton addDeliveryButton;
    private JButton editButton;
    private JButton listButton;
    private JButton sortButton;
    private Dimension Bsize = new Dimension(150,100);

    //Colours and fonts
    // Brown colour scheme
    private final Color lightBrown = new Color(210, 180, 140); // tan
    private final Color mediumBrown = new Color(160, 82, 45);  // sienna
    private final Color darkBrown = new Color(101, 67, 33);    // dark wood
    private final Color cream = new Color(255, 248, 220);      // for text
    private final Font buttonFont = new Font("Monospaced", Font.BOLD, 14);

    /**
     * Constructs the GUI for Landing frame.
     */
    public LandingFrame(){
        //landingFrame = this;

        mainMenuPanel = new JPanel();
        display = new JPanel();
        //mainMenuPanel.setLayout(new GridLayout(3,1));
        mainMenuPanel.setBackground(lightBrown);
        

        //button creation
        addOrderButton = new JButton("Add Order");
        addOrderButton.setPreferredSize(Bsize);
        addDeliveryButton = new JButton("Add Delivery");
        addDeliveryButton.setPreferredSize(Bsize);
        listButton = new JButton("List Inventory");
        listButton.setPreferredSize(Bsize);

        // Set button styles based on colour scheme
        styleButton(addOrderButton, mediumBrown, cream);
        styleButton(addDeliveryButton, darkBrown, cream);
        styleButton(listButton, mediumBrown, cream);
        
        //action listeners
        addOrderButton.addActionListener(new AddOrderListener());
        addDeliveryButton.addActionListener(new AddDeliveryListener());
        listButton.addActionListener(new ListItemsListener());

        mainMenuPanel.add(addOrderButton,BorderLayout.LINE_START);
        mainMenuPanel.add(addDeliveryButton,BorderLayout.CENTER);
        mainMenuPanel.add(listButton,BorderLayout.LINE_END);

        add(mainMenuPanel);
    }   


    private class AddOrderListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            new AddOrder();
        }
    }
    
    private class AddDeliveryListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            new AddDelivery();
        }
    }
    
    private class ListItemsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            new ListInventory();
        }
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(buttonFont);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
    }
}



