
# L&R^2 Bakery Inventory System

## Introduction

Welcome to the **L&R^2 Bakery Inventory System**! This system helps manage the inventory of various bakery products, including adding new orders, deliveries and managing the inventory through a simple graphical user interface (GUI). This document will guide you through the different sections and functionalities of the system.

## System Overview

The system allows users to:
1. **View Inventory**: The inventory of bakery items can be accessed and updated.
2. **Add Orders**: Orders can be placed and processed.
3. **Add Deliveries**: Deliveries to restock inventory can be entered.
4. **Save Data**: Changes made to the inventory and orders are saved to files for persistence.

---

## Security Features

### Login Feature for Editing Items
 `Login.java`
- A login dialog was implemented using `JDialog` to ensure only authorized users can edit inventory items.
- The interface includes:
  - Username and password fields
  - Login and cancel buttons
  - Message label for login feedback
- Credentials are stored in a `.dat` file (`login.dat`) for basic obfuscation and are read during authentication.
- If the file does not exist, default credentials are created:
  - **Username:** `CookieMonsta`
  - **Password:** `ILuvCookies`

### Edit Button Functionality
- `EditButtonListener` class in `ListInventory.java`
- Before the edit popup appears, the system:
  - Prompts the user with the login dialog
  - Grants access only if credentials are valid
  - Shows an error message if login fails or is cancelled

---

## Key Features

### 1. Inventory Management
- **View and Edit Inventory**: The system loads inventory items from a file (`inventory.txt`). The inventory includes various baked items such as **Bread**, **Brownies**, **Cake**, **Cookies**, **Cupcakes**, **Danish**, and **Donut**. The inventory is updated whenever a delivery is made or an item is ordered.

- **Low/Overstocked Alert Feature** [Using Custom Cell rendering for JTable]
  - When there are less than 5 items the items are **highlighted in red** on the table for ease of viewing.
  - When there are more than 50 items the items are **highlighted in green** on the table for ease of viewing.

- **Pie Chart Visualization** 
  - Pie chart generated for ease of viewing distribution of stock.

- **File Persistence**: Inventory data is saved to a file named `Inventory.txt`. This ensures that all changes to the inventory are stored even after the program is closed.

### 2. Adding Orders
- **Orders**: Orders can be placed for different bakery items. When an order is placed, it is added to the `OrderList` and saved to a file (`orders.txt`), ensuring that all orders are stored.
- **Order ID**: Each order has a unique ID, which is managed automatically by the system.

 **Order Class**
- The `Order` class is responsible for managing individual orders in the system.
- **Fields**:
  - `id`: A unique identifier for the order, which is automatically incremented with each new order.
  - `itemName`: The name of the ordered item.
  - `quantity`: The quantity of the item ordered.
- **Constructor**: The constructor takes the `itemName` and `quantity` as parameters, assigns the unique `id` to the order and increments the `nextId` for future orders.
- **Methods**:
  - `getItemName()`: Returns the name of the item ordered.
  - `getQuantity()`: Returns the quantity of the item ordered.
  - `toString()`: Provides a string representation of the order in a comma-separated format, which is useful for saving the order to the `orders.txt` file.
  - `setNextId()`: Sets the next order ID to ensure that each order has a unique ID.

### 3. Adding Deliveries
The **Add Delivery** functionality allows users to restock inventory by entering a delivery. This functionality is facilitated by the `AddDelivery` class, which provides a simple GUI to add a delivery to the system.

#### **Delivery Class**
- The `Delivery` class represents a delivery made to restock inventory.
- **Fields**:
  - `id`: A unique identifier for the delivery, automatically incremented with each new delivery.
  - `itemName`: The name of the item being delivered.
  - `quantity`: The quantity of the item being delivered.
- **Constructor**: The constructor takes the `itemName` and `quantity` as parameters, assigns the unique `id` to the delivery and increments the `nextId` for future deliveries.
- **Methods**:
  - `getItemName()`: Returns the name of the item delivered.
  - `getQuantity()`: Returns the quantity of the item delivered.
  - `toString()`: Provides a string representation of the delivery in a comma-separated format for file saving.
  - `setNextId()`: Sets the next delivery ID, ensuring each delivery has a unique ID.

#### How to Add a Delivery:
1. **Select Item**: From the dropdown menu, select the item being delivered (e.g., **Bread**, **Brownies**, etc.).
2. **Enter Quantity**: Enter the quantity of the item being delivered using the spinner widget.
3. **Save Delivery**: Press the **Save** button to save the delivery, update the inventory and write the changes to the file.
4. **Cancel Delivery**: If you wish to cancel the delivery, press the **Cancel** button.

---

### 4. Inventory Item Class
The **InventoryItem** class is used to represent an individual item in the inventory. Each item has a unique ID, a name and a quantity. The items in the inventory are stored in a list and can be sorted by id or quantity in ascending order.

#### **InventoryItem Class**
- **Fields**:
  - `name`: The name of the inventory item (e.g., Bread, Cake, Brownies).
  - `quantity`: The quantity of the inventory item available.
  - `id`: A unique identifier for each item in the inventory. This ID is immutable after it is set during object creation.
- **Constructor**: The constructor initializes an `InventoryItem` object with the `name`, `quantity` and `id` of the item.
- **Methods**:
  - `getName()`: Returns the name of the item.
  - `setName()`: Allows setting a new name for the item (though this is generally not necessary for inventory management once items are added).
  - `getQuantity()`: Returns the quantity of the item available in the inventory.
  - `setQuantity()`: Allows setting a new quantity for the item (useful for updating inventory when deliveries are made or orders are placed).
  - `getId()`: Returns the unique ID of the item.
  - **compareTo()**: Implements the `Comparable` interface to allow sorting inventory items by their unique ID. The comparison is made in reverse order (smaller IDs come first).
  - `toString()`: Provides a string representation of the inventory item in the format: `"ID: {id} | Name: {name} | Quantity: {quantity}"`.

---
### Detailed Breakdown of Key Classes

#### **AddDelivery Class**
- This class provides the functionality to add a delivery and update the inventory accordingly.
- **UI Components**:
  - **Item Dropdown**: Allows the user to select the item being delivered.
  - **Quantity Spinner**: Allows the user to specify the quantity of the item being delivered.
  - **Save and Cancel Buttons**: Save the delivery or cancel the operation.

##### Methods:
- **saveDeliveriesToFile()**: Saves the details of the deliveries made to a file named `deliver.txt`.
- **saveUpdatedInvToFile()**: Saves the updated inventory to a file (`Inventory.txt`).
- **UpdInv(Delivery delivery)**: Updates the inventory by adding the quantity of the delivered item to the current stock.

#### **Main Class**
- The main class serves as the entry point to the application. It initializes the system, loads the inventory, orders, and deliveries and sets up the GUI.
  
##### Key Operations:
- **init()**: Initializes the inventory from the `inventory.txt` file and ensures that any missing inventory files are created.
- **loadOrders()**: Loads existing orders from the `orders.txt` file.
- **loadDeliveries()**: Loads previous deliveries from the `Delivery.txt` file.
- **SwingUtilities.invokeLater()**: Launches the GUI on the event dispatch thread.

---

## File Structure
The system reads and writes the following files to store data:

- **Inventory.txt**: Contains the inventory of bakery items. Each line consists of an item’s ID, name and quantity.
- **Orders.txt**: Stores the orders placed by customers, with each line containing an order's ID, item name and quantity.
- **Delivery.txt**: Stores the deliveries made to restock inventory. Each line contains a delivery's ID, item name and quantity.
- **login.dat**: Used to store and verify Login details.

---

## User Interface

The system provides a simple GUI for interacting with the inventory and adding orders or deliveries. The GUI is built using Java's Swing library, and it includes the following components:

- **JComboBox**: For selecting the item being delivered.
- **JSpinner**: For selecting the quantity of the item being delivered.
- **JButton**: For saving or canceling actions.
- **JLabel**: For displaying text information (e.g., labels for item and quantity).

---

## Conclusion

This system simplifies bakery inventory management by automating the process of adding orders, deliveries and inventory updates. By utilizing a GUI and maintaining persistent data through file storage, it offers an easy-to-use solution for bakery operations.
