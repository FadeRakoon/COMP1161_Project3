
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * CustomCellRenderer is a custom implementation of the DefaultTableCellRenderer
 * that applies specific background colors to cells in the 3rd column (index 2)
 * of a JTable based on the cell's value.
 * 
 * <p>The rendering logic is as follows:
 * <ul>
 *   <li>If the value in the cell is less than 2, the background color is set to light coral.</li>
 *   <li>If the value in the cell is greater than or equal to 50, the background color is set to light green.</li>
 *   <li>For all other values, the cell retains the default background and foreground colors of the table.</li>
 * </ul>
 * 
 * <p>If the value in the cell cannot be parsed as an integer, the default background
 * and foreground colors of the table are applied.
 * 
 * <p>This renderer is designed to visually highlight specific conditions in the data
 * for better user experience and readability.
 * 
 * <p>Usage example:
 * <pre>
 * JTable table = new JTable(data, columnNames);
 * table.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer());
 * </pre>
 * 
 */

public class CustomCellRenderer extends DefaultTableCellRenderer {
    private final Color lightCoral = new Color(240, 128, 128);
    private final Color lightGreen = new Color(128,240,128);
    

    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column
    ) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Only apply custom color to the 3rd column (index 2)
        if (column == 2) {
            try {
                int quantity = Integer.parseInt(value.toString());
                if (quantity <= 5) { //red if less then 2 of item present
                    c.setBackground(lightCoral);
                    //c.setForeground(Color.WHITE);
                } else if(quantity >= 50){ //green if more than 50 present
                    c.setBackground(lightGreen);

                } else { //regular otherwise
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }

            } catch (NumberFormatException e) {
                c.setBackground(table.getBackground());
            }
        } else {
            c.setBackground(table.getBackground());
            c.setForeground(table.getForeground());
        }

        return c;
    }
}
