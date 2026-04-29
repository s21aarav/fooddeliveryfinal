import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// --- 1. THE DATA MODEL ---
class FoodOrder {
    private String orderId;
    private String customerName;
    private String restaurant;
    private double amount;
    private String status;

    public FoodOrder(String orderId, String customerName, String restaurant, double amount, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.restaurant = restaurant;
        this.amount = amount;
        this.status = status;
    }

    public double getAmount() { return amount; }
    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getRestaurant() { return restaurant; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Order ID: %-5s | Customer: %-10s | Restaurant: %-15s | Amount: ₹%-7.2f | Status: %s", 
                             orderId, customerName, restaurant, amount, status);
    }
}

// --- 2. THE BUSINESS LOGIC ---
class OrderTrackerLogic {
    private List<FoodOrder> orderList = new ArrayList<>();

    public void addOrder(FoodOrder order) {
        orderList.add(order);
    }

    // REQUIREMENT MET: Sort using modern LAMBDA expression
    public void sortByAmount() {
        Collections.sort(orderList, (o1, o2) -> Double.compare(o1.getAmount(), o2.getAmount()));
    }

    // REQUIREMENT MET: Sort using traditional ANONYMOUS INNER CLASS (No Lambda)
    public void sortByOrderId() {
        Collections.sort(orderList, new Comparator<FoodOrder>() {
            @Override
            public int compare(FoodOrder o1, FoodOrder o2) {
                // compareToIgnoreCase sorts strings alphabetically
                return o1.getOrderId().compareToIgnoreCase(o2.getOrderId());
            }
        });
    }

    public List<FoodOrder> getPremiumOrders() {
        List<FoodOrder> premium = new ArrayList<>();
        for (FoodOrder order : orderList) {
            if (order.getAmount() > 1000.0) {
                premium.add(order);
            }
        }
        return premium;
    }

    public String generateReceiptMessage(FoodOrder order) {
        StringBuffer sb = new StringBuffer();
        sb.append("====================================\n");
        sb.append("          ORDER RECEIPT             \n");
        sb.append("====================================\n");
        sb.append("Order #      : ").append(order.getOrderId()).append("\n");
        sb.append("Customer     : ").append(order.getCustomerName()).append("\n");
        sb.append("Restaurant   : ").append(order.getRestaurant()).append("\n");
        sb.append("Order Status : ").append(order.getStatus()).append("\n");
        sb.append("------------------------------------\n");
        sb.append("TOTAL AMOUNT : ₹").append(order.getAmount()).append("\n");
        sb.append("====================================\n");
        return sb.toString();
    }
    
    public List<FoodOrder> getAllOrders() {
        return orderList;
    }

    public double calculateTotalRevenue() {
        double total = 0;
        for (FoodOrder order : orderList) {
            total += order.getAmount();
        }
        return total;
    }
}

// --- 3. THE BEAUTIFIED SWING USER INTERFACE ---
public class FoodDeliveryApp {
    private JFrame frame;
    private JTextField txtOrderId, txtName, txtRestaurant, txtAmount, txtStatus;
    private JTextArea displayArea;
    private JLabel lblRevenue;
    private OrderTrackerLogic trackerLogic;

    // UI Styling variables
    private Font mainFont = new Font("SansSerif", Font.PLAIN, 14);
    private Font boldFont = new Font("SansSerif", Font.BOLD, 14);
    private Font receiptFont = new Font("Monospaced", Font.PLAIN, 13);
    private Color primaryColor = new Color(41, 128, 185); 
    private Color bgColor = new Color(245, 246, 250); 

    public FoodDeliveryApp() {
        trackerLogic = new OrderTrackerLogic();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("🍔 Premium Food Delivery Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 600);
        frame.setLayout(new BorderLayout(15, 15));
        frame.getContentPane().setBackground(bgColor);

        // Header Panel
        JLabel headerLabel = new JLabel("Online Food Delivery Order Management", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(primaryColor);
        headerLabel.setBorder(new EmptyBorder(15, 10, 5, 10));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Center Panel 
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(bgColor);
        centerPanel.setBorder(new EmptyBorder(0, 15, 0, 15));

        // Input Form
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        inputPanel.setBackground(Color.WHITE);
        TitledBorder inputBorder = BorderFactory.createTitledBorder("📝 Enter New Order Details");
        inputBorder.setTitleFont(boldFont);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(inputBorder, new EmptyBorder(10, 10, 10, 10)));

        inputPanel.add(createStyledLabel("Order ID:"));
        txtOrderId = createStyledTextField();
        inputPanel.add(txtOrderId);

        inputPanel.add(createStyledLabel("Customer Name:"));
        txtName = createStyledTextField();
        inputPanel.add(txtName);

        inputPanel.add(createStyledLabel("Restaurant:"));
        txtRestaurant = createStyledTextField();
        inputPanel.add(txtRestaurant);

        inputPanel.add(createStyledLabel("Amount (₹):"));
        txtAmount = createStyledTextField();
        inputPanel.add(txtAmount);

        inputPanel.add(createStyledLabel("Current Status:"));
        txtStatus = createStyledTextField();
        inputPanel.add(txtStatus);

        centerPanel.add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel (Updated to 5 rows to fit the new button)
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(bgColor);
        
        JButton btnAddOrder = createStyledButton("➕ Add Order", new Color(46, 204, 113));
        JButton btnShowAll = createStyledButton("📋 Show All Orders", primaryColor);
        JButton btnSortAmount = createStyledButton("↕️ Sort by Amount", primaryColor);
        JButton btnSortId = createStyledButton("🔠 Sort by ID", primaryColor); // NEW BUTTON
        JButton btnPremium = createStyledButton("💎 Show > ₹1000", new Color(155, 89, 182));

        buttonPanel.add(btnAddOrder);
        buttonPanel.add(btnShowAll);
        buttonPanel.add(btnSortAmount);
        buttonPanel.add(btnSortId);     // NEW BUTTON ADDED TO UI
        buttonPanel.add(btnPremium);

        centerPanel.add(buttonPanel, BorderLayout.EAST);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Output Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(bgColor);
        bottomPanel.setBorder(new EmptyBorder(0, 15, 15, 15));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(receiptFont); 
        displayArea.setBackground(new Color(253, 253, 253));
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        TitledBorder outputBorder = BorderFactory.createTitledBorder("💻 System Output Console");
        outputBorder.setTitleFont(boldFont);
        scrollPane.setBorder(outputBorder);
        scrollPane.setPreferredSize(new Dimension(700, 200));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        lblRevenue = new JLabel("Total Platform Revenue: ₹0.00");
        lblRevenue.setFont(boldFont);
        lblRevenue.setForeground(new Color(192, 57, 43));
        bottomPanel.add(lblRevenue, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // --- ACTION LISTENERS ---

        // Listener 1 (Lambda)
        btnAddOrder.addActionListener(e -> {
            try {
                String id = txtOrderId.getText().trim();
                String name = txtName.getText().trim();
                String restaurant = txtRestaurant.getText().trim();
                String status = txtStatus.getText().trim();

                if (id.isEmpty() || name.isEmpty() || restaurant.isEmpty() || status.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled out to process an order.");
                }

                double amount = Double.parseDouble(txtAmount.getText().trim());
                if (amount <= 0) {
                    throw new IllegalArgumentException("Order amount must be greater than zero.");
                }

                FoodOrder newOrder = new FoodOrder(id, name, restaurant, amount, status);
                trackerLogic.addOrder(newOrder);
                
                displayArea.setText("✅ Order Added Successfully!\n\n");
                displayArea.append(trackerLogic.generateReceiptMessage(newOrder));
                
                lblRevenue.setText(String.format("Total Platform Revenue: ₹%.2f", trackerLogic.calculateTotalRevenue()));

                txtOrderId.setText("");
                txtName.setText("");
                txtRestaurant.setText("");
                txtAmount.setText("");
                txtStatus.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input: Please enter a numeric value for the Amount.", "Data Entry Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Data Entry Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Listener 2: REQUIREMENT MET - Traditional Anonymous Inner Class (No Lambda)
        btnShowAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOrders(trackerLogic.getAllOrders(), "--- ALL REGISTERED ORDERS ---");
            }
        });
        
        // Listener 3 (Lambda)
        btnSortAmount.addActionListener(e -> {
            trackerLogic.sortByAmount();
            displayOrders(trackerLogic.getAllOrders(), "--- ORDERS SORTED BY AMOUNT (LOW TO HIGH) ---");
        });

        // Listener 4 (Lambda) - NEW BUTTON LISTENER
        btnSortId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trackerLogic.sortByOrderId();
                displayOrders(trackerLogic.getAllOrders(), "--- ORDERS SORTED BY ID (ALPHABETICAL) ---");
            }
        });

        // Listener 5 (Lambda)
        btnPremium.addActionListener(e -> displayOrders(trackerLogic.getPremiumOrders(), "--- PREMIUM ORDERS (ABOVE ₹1000) ---"));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(mainFont);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(mainFont);
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(boldFont);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    private void displayOrders(List<FoodOrder> orders, String title) {
        if (orders.isEmpty()) {
            displayArea.setText(title + "\n\n⚠️ No orders found in the system.");
            return;
        }
        StringBuilder sb = new StringBuilder(title + "\n\n");
        for (FoodOrder order : orders) {
            sb.append(order.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FoodDeliveryApp());
    }
}
