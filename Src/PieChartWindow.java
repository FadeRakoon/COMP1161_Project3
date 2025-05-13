import org.knowm.xchart.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * The {@code PieChart} class is responsible for creating and displaying a pie chart
 * based on a set of labeled data values. This class handles the calculation of angles,
 * rendering of pie slices, and display features such as labels and a legend.
*/


public class PieChartWindow {

    /**
     * Displays the pie chart using data from the inventory list.
     *
     * @param inventoryList An arraylist containing all items in the inventory.
     */
    public static void showChart(ArrayList<InventoryItem> inventoryList) {
        PieChart chart = new PieChartBuilder()
                .width(400)
                .height(400)
                .title("Inventory Pie Chart")
                .build();

        for (InventoryItem item : inventoryList) {
            chart.addSeries(item.getName(), item.getQuantity());
        }

        // Chart panel
        XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);

        // Create a frame manually so we can fully control layout
        JFrame chartFrame = new JFrame("Inventory Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setLayout(new BorderLayout());

        // Close button
        JButton closeButton = new JButton("Close");
        // make it red with white text
        closeButton.setBackground(new Color(220, 53, 69)); 
        closeButton.setForeground(Color.WHITE);

        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.addActionListener(e -> chartFrame.dispose()); 
        //^ closes the frame not entire gui program

        // Bottom panel for the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        chartFrame.add(chartPanel, BorderLayout.CENTER);//chart in "middle"
        chartFrame.add(buttonPanel, BorderLayout.SOUTH);//button panel at "bottom"

        chartFrame.pack();
        chartFrame.setLocationRelativeTo(null); //spawn frame in middle of screen
        chartFrame.setVisible(true);
    }
}
