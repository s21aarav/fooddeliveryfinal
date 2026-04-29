# 🍔 Premium Food Delivery App

A feature-rich Java Swing GUI application for managing food delivery orders with sorting, filtering, and revenue tracking capabilities.

## Features

- **Add Orders**: Create new food delivery orders with customer details and amounts
- **Sort Orders**: 
  - Sort by Amount (using Lambda expressions)
  - Sort by Order ID (using Anonymous Inner Classes)
- **View Orders**: Display all registered orders or filter premium orders (>₹1000)
- **Revenue Tracking**: Real-time calculation of total platform revenue
- **Beautiful UI**: Modern Swing interface with custom styling and emoji icons
- **Error Handling**: Input validation with user-friendly error messages

## Project Structure

```
FoodDeliveryApp/
├── src/
│   └── FoodDeliveryApp.java
└── README.md
```

## Architecture

The application follows a 3-layer architecture:

1. **Data Model**: `FoodOrder` class - Represents individual orders
2. **Business Logic**: `OrderTrackerLogic` class - Handles order management and operations
3. **UI Layer**: `FoodDeliveryApp` class - Swing GUI implementation

## Getting Started

### Prerequisites
- Java 8 or higher
- No external dependencies required (uses standard Java Swing library)

### Compilation
```bash
javac src/FoodDeliveryApp.java
```

### Running the Application
```bash
cd src
java FoodDeliveryApp
```

## Technical Highlights

- **Lambda Expressions**: Used for modern functional programming (Add Order, Sort by Amount, Show Premium Orders)
- **Anonymous Inner Classes**: Traditional approach for event listeners (Show All, Sort by ID)
- **Collections API**: Efficient sorting using `Collections.sort()` with custom comparators
- **Exception Handling**: Comprehensive error handling for user input validation
- **Swing Components**: GridLayout, BorderLayout, JTextArea, custom styled buttons and fields

## Usage Instructions

1. **Add an Order**: Fill in all fields (Order ID, Customer Name, Restaurant, Amount, Status) and click "➕ Add Order"
2. **View Orders**: Click "📋 Show All Orders" to see all registered orders
3. **Sort Orders**: Use "↕️ Sort by Amount" or "🔠 Sort by ID" buttons
4. **Premium Orders**: Click "💎 Show > ₹1000" to view high-value orders
5. **Revenue**: Check the total platform revenue displayed at the bottom

## Sample Data to Test

| Order ID | Customer | Restaurant | Amount | Status |
|----------|----------|-----------|--------|--------|
| O001 | Raj Kumar | Biryani Palace | 450.00 | Delivered |
| O002 | Priya Singh | Pizza Hub | 1200.00 | In Transit |
| O003 | Amit Patel | Dosa Factory | 350.00 | Preparing |

## Author Notes

This application demonstrates best practices in Java GUI development, including proper event handling, data validation, and separation of concerns through layered architecture.
