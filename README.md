# Sweetshop App

Sweetshop App is a JavaFX-based application designed to manage a sweetshop's inventory and orders. The application allows users to perform various operations such as adding, viewing, and filtering birthday cakes and orders. It also includes functionalities for calculating order counts and total amounts by customer.

## Features

- **Add and View Birthday Cakes**: Manage the inventory of birthday cakes with details such as name, flavor, and price.
- **Update Birthday Cakes**: Modify details of existing birthday cakes.
- **Filter Cakes by Flavor and Price Range**: Filter cakes based on their flavor or a specific price range.
- **Add and View Orders**: Manage customer orders with details such as customer name, address, order date, and delivery date.
- **Delete Orders**: Remove specific orders from the system.
- **Filter Orders**: Filter orders based on various criteria, including status, customer name, address, and order date.
- **Order Count by Customer**: View the number of orders placed by a specific customer.
- **Total Amount by Customer**: Calculate the total amount to be paid by a specific customer.
- **Order Count per Cake**: View the total number of orders placed for each type of cake.
- **Database Integration**: All data is stored in a local SQLite database (`sweetshop.db`), and changes are saved automatically during runtime.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 23** or higher
- **Maven**
- **IntelliJ IDEA** or any other preferred IDE

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/sweetshop-app.git
   ```
2. Navigate to the project directory:
   ```bash
   cd sweetshop-app
   ```
3. Open the project in your IDE.
4. Add the required JavaFX and database modules:
   - **Ensure all required modules** (e.g., `javafx.controls`, `javafx.fxml`, `java.sql`) are added to your project.
   - Verify the module path and dependencies in your IDE's configuration.
5. Set the path to the database file:
   - Open the application's GUI.
   - Navigate to the **Settings** or **Configuration** section.
   - Set the path to the `sweetshop.db` file (located in the project's root directory).

### Usage

1. Run the application from your IDE.
2. Use the interface to:
   - Add new birthday cakes and manage their details.
   - Update or filter birthday cakes by flavor or price range.
   - Add, view, or delete customer orders.
   - Filter orders by status, customer name, address, or order date.
   - Calculate statistics, such as the total amount payable or the number of orders per customer or cake.
3. All modifications will be automatically saved to the database (`sweetshop.db`).

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add a new feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

## Acknowledgments

- JavaFX for the UI framework.
- SQLite for database management.
- Open-source libraries and tools that made this project possible.

