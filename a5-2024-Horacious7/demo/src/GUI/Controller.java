package GUI;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.service.BirthdayCakeService;
import main.service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    BirthdayCakeService cakeService;
    OrderService orderService;

    @FXML
    private Button addCakeButton;

    @FXML
    private Button updateCakeButton;

    @FXML
    private Button filterCakesByFlavourButton;

    @FXML
    private Button filterByPriceRangeButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button deleteOrderButton;

    @FXML
    private Button filterOrdersByStatusButton;

    @FXML
    private Button filterOrdersByCustomerNameButton;

    @FXML
    private Button filterOrdersByAddressButton;

    @FXML
    private Button orderCountByCustomerButton;

    @FXML
    private Button filterOrdersByOrderDateButton;

    @FXML
    private Button totalAmountByCustomerButton;

    @FXML
    private Button orderCountPerCakeButton;

    @FXML
    private ListView<BirthdayCake<Integer>> cakesListView;

    @FXML
    private ListView<Order<Integer>> ordersListView;

    ObservableList<BirthdayCake<Integer>> cakesList;
    ObservableList<Order<Integer>> ordersList;


    public Controller(BirthdayCakeService cakeService, OrderService orderService) {
        this.cakeService = cakeService;
        this.orderService = orderService;
    }

    public void initialize(){
        Iterable<BirthdayCake<Integer>> elems = cakeService.getAllCakes();
        ArrayList<BirthdayCake<Integer>> cakes = new ArrayList<>();
        for(BirthdayCake<Integer> cake : elems){
            cakes.add(cake);
        }

        cakesList = FXCollections.observableArrayList(cakes);
        cakesListView.setItems(cakesList);

        Iterable<Order<Integer>> elements = orderService.getAllOrders();
        ArrayList<Order<Integer>> orders = new ArrayList<>();
        for(Order<Integer> order : elements){
            orders.add(order);
        }
        ordersList = FXCollections.observableArrayList(orders);
        ordersListView.setItems(ordersList);
    }

    void resetObservableList() {
        Iterable<BirthdayCake<Integer>> elems = cakeService.getAllCakes();
        ArrayList<BirthdayCake<Integer>> cakes = new ArrayList<>();
        for (BirthdayCake<Integer> cake : elems) {
            cakes.add(cake);
        }

        cakesList = FXCollections.observableArrayList(cakes);
        cakesListView.setItems(cakesList);

        Iterable<Order<Integer>> elements = orderService.getAllOrders();
        ArrayList<Order<Integer>> orders = new ArrayList<>();
        for (Order<Integer> order : elements) {
            orders.add(order);
        }
        ordersList = FXCollections.observableArrayList(orders);
        ordersListView.setItems(ordersList);
    }

    private void showError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    private String showInputDialog(String title, String prompt){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse(null);
    }

    @FXML
    void addCakeHandler(ActionEvent event) {
        String cakeName = showInputDialog("Add cake", "Enter cake name: ");
        if(cakeName == null || cakeName.isBlank() ){
            showError("Name cannot be empty!");
            return;
        }

        String cakeFlavour = showInputDialog("Add cake", "Enter cake flavour: ");
        if(cakeFlavour == null || cakeFlavour.isBlank() ){
            showError("Flavour cannot be empty!");
            return;
        }

        String cakePriceString = showInputDialog("Add cake", "Enter cake price: ");
        if(cakePriceString == null || cakePriceString.isBlank() ){
            showError("Price cannot be empty!");
            return;
        }

        try{
            double cakePrice = Double.parseDouble(cakePriceString);
            cakeService.addCake(new BirthdayCake<>(cakeName,cakeFlavour,cakePrice));
            resetObservableList();
        }catch(NumberFormatException e){
            showError("Invalid price format!");
        }catch(IllegalArgumentException e){
            showError(e.getMessage());
        }
    }

    @FXML
    void deleteCakeHandler(ActionEvent event) {
        BirthdayCake<Integer> selectedCake = cakesListView.getSelectionModel().getSelectedItem();

        if (selectedCake == null) {
            showError("No cake selected!");
            return;
        }

        try {
            cakeService.deleteCake(selectedCake.getId());
            resetObservableList();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Cake Deleted");
            successAlert.setContentText("Cake successfully deleted!");
            successAlert.showAndWait();
        } catch (IllegalArgumentException e) {
            showError("Error deleting cake: " + e.getMessage());
        } catch (RuntimeException e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    @FXML
    void updateCakeHandler(ActionEvent event) {
        BirthdayCake<Integer> selectedCake = cakesListView.getSelectionModel().getSelectedItem();
        if(selectedCake == null){
            showError("No cake selected!");
            return;
        }

        String cakeName = showInputDialog("Update cake", "Enter new cake name: ");
        if(cakeName == null || cakeName.isBlank() ){
            showError("Name cannot be empty!");
            return;
        }

        String cakeFlavour = showInputDialog("Update cake", "Enter new cake flavour: ");
        if(cakeFlavour == null || cakeFlavour.isBlank() ){
            showError("Flavour cannot be empty!");
            return;
        }

        String cakePriceString = showInputDialog("Update cake", "Enter new cake price: ");
        if (cakePriceString == null || cakePriceString.isBlank()) {
            showError("Price cannot be empty!");
            return;
        }


        try {
            double cakePrice = Double.parseDouble(cakePriceString);

            selectedCake.setName(cakeName);
            selectedCake.setFlavour(cakeFlavour);
            selectedCake.setPrice(cakePrice);

            cakeService.updateCake(selectedCake);
            resetObservableList();

        } catch (NumberFormatException e) {
            showError("Invalid price or layer format!");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    void filterCakesByFlavourHandler(ActionEvent event) {
        try {
            String flavourToFilter = showInputDialog("Filter by Flavour", "Enter flavour to filter by:");

            if (flavourToFilter == null || flavourToFilter.isBlank()) {
                showError("Please enter a flavour to filter by.");
                return;
            }

            Iterable<BirthdayCake<Integer>> filteredCakes = cakeService.getCakesByFlavour(flavourToFilter);
            ArrayList<BirthdayCake<Integer>> filteredCakesList = new ArrayList<>();
            for (BirthdayCake<Integer> cake : filteredCakes) {
                filteredCakesList.add(cake);
            }

            if (filteredCakesList.isEmpty()) {
                showError("No cakes found for flavour: " + flavourToFilter);
                return;
            }

            ListView<BirthdayCake<Integer>> filteredCakesListView = new ListView<>();
            ObservableList<BirthdayCake<Integer>> observableFilteredCakes = FXCollections.observableArrayList(filteredCakesList);
            filteredCakesListView.setItems(observableFilteredCakes);

            Stage popupStage = new Stage();
            popupStage.setTitle("Filtered Cakes by Flavour");

            VBox vbox = new VBox(filteredCakesListView);
            vbox.setSpacing(10);
            vbox.setPadding(new javafx.geometry.Insets(10));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> popupStage.close());
            vbox.getChildren().add(closeButton);

            Scene scene = new Scene(vbox, 400, 300);
            popupStage.setScene(scene);
            popupStage.show();
        }catch(RuntimeException e){
            showError(e.getMessage());
        }
    }

    @FXML
    void filterByPriceRangeHandler(ActionEvent event) {
        try {
            String minPriceString = showInputDialog("Filter by Price Range", "Enter minimum price:");
            if (minPriceString == null || minPriceString.isBlank()) {
                showError("Minimum price cannot be empty!");
                return;
            }

            String maxPriceString = showInputDialog("Filter by Price Range", "Enter maximum price:");
            if (maxPriceString == null || maxPriceString.isBlank()) {
                showError("Maximum price cannot be empty!");
                return;
            }

            double minPrice = Double.parseDouble(minPriceString);
            double maxPrice = Double.parseDouble(maxPriceString);

            Iterable<BirthdayCake<Integer>> filteredCakes = cakeService.getCakesByPriceRange(minPrice, maxPrice);
            ArrayList<BirthdayCake<Integer>> filteredCakesList = new ArrayList<>();
            for (BirthdayCake<Integer> cake : filteredCakes) {
                filteredCakesList.add(cake);
            }

            if (filteredCakesList.isEmpty()) {
                showError("No cakes found within the price range: " + minPrice + " - " + maxPrice);
                return;
            }

            ListView<BirthdayCake<Integer>> filteredCakesListView = new ListView<>();
            ObservableList<BirthdayCake<Integer>> observableFilteredCakes = FXCollections.observableArrayList(filteredCakesList);
            filteredCakesListView.setItems(observableFilteredCakes);

            Stage popupStage = new Stage();
            popupStage.setTitle("Filtered Cakes by Price Range");

            VBox vbox = new VBox(filteredCakesListView);
            vbox.setSpacing(10);
            vbox.setPadding(new javafx.geometry.Insets(10));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> popupStage.close());
            vbox.getChildren().add(closeButton);

            Scene scene = new Scene(vbox, 400, 300);
            popupStage.setScene(scene);
            popupStage.show();
        } catch (NumberFormatException e) {
            showError("Invalid price format! Please enter numeric values.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void placeOrderHandler(ActionEvent event) {
        try {
            String orderIdString = showInputDialog("Place Order", "Enter order ID:");
            if (orderIdString == null || orderIdString.isBlank()) {
                showError("Order ID cannot be empty!");
                return;
            }

            int orderId = Integer.parseInt(orderIdString);

            String cakeIdString = showInputDialog("Place Order", "Enter cake ID:");
            if (cakeIdString == null || cakeIdString.isBlank()) {
                showError("Cake ID cannot be empty!");
                return;
            }

            int cakeID = Integer.parseInt(cakeIdString);

            String customerName = showInputDialog("Place Order", "Enter the Customer Name:");
            if (customerName == null || customerName.isBlank()) {
                showError("Customer Name cannot be empty!");
                return;
            }

            String orderDate = LocalDate.now().toString();

            String address = showInputDialog("Place Order", "Enter the Address:");
            if (address == null || address.isBlank()) {
                showError("Address cannot be empty!");
                return;
            }

            String status = showInputDialog("Place Order", "Enter the Status:");
            if (status == null || status.isBlank()) {
                showError("Status cannot be empty!");
                return;
            }

            String priceString = showInputDialog("Place Order", "Enter the Price:");
            if (priceString == null || priceString.isBlank()) {
                showError("Price cannot be empty!");
                return;
            }

            double price = Double.parseDouble(priceString);

            Order<Integer> newOrder = new Order<>(orderId, cakeID, orderDate, customerName, address, status, price);
            orderService.addOrder(newOrder);
            resetObservableList();

        } catch (NumberFormatException e) {
            showError("Invalid Order ID, Cake ID, or Price!");
        } catch (IllegalArgumentException e) {
            showError("Error placing order!");
        } catch (RuntimeException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    void deleteOrderHandler(ActionEvent event) {
        Order<Integer> selectedOrder = ordersListView.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            showError("No order selected!");
            return;
        }

        try {
            orderService.deleteOrder(selectedOrder.getId());

            resetObservableList();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Order Deleted");
            successAlert.setContentText("Order successfully deleted!");
            successAlert.showAndWait();
        } catch (IllegalArgumentException e) {
            showError("Error deleting order: " + e.getMessage());
        } catch (RuntimeException e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    @FXML
    void updateOrderHandler(ActionEvent event) {
        Order<Integer> selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showError("No order selected!");
            return;
        }

        String orderIdString = showInputDialog("Update Order", "Enter new order ID:");
        if (orderIdString == null || orderIdString.isBlank()) {
            showError("Order ID cannot be empty!");
            return;
        }

        String cakeIdString = showInputDialog("Update Order", "Enter new cake ID:");
        if (cakeIdString == null || cakeIdString.isBlank()) {
            showError("Cake ID cannot be empty!");
            return;
        }

        String customerName = showInputDialog("Update Order", "Enter new customer name:");
        if (customerName == null || customerName.isBlank()) {
            showError("Customer name cannot be empty!");
            return;
        }

        String orderDate = showInputDialog("Update Order", "Enter new order date (YYYY-MM-DD):");
        if (orderDate == null || orderDate.isBlank()) {
            showError("Order date cannot be empty!");
            return;
        }

        String address = showInputDialog("Update Order", "Enter new address:");
        if (address == null || address.isBlank()) {
            showError("Address cannot be empty!");
            return;
        }

        String status = showInputDialog("Update Order", "Enter new status:");
        if (status == null || status.isBlank()) {
            showError("Status cannot be empty!");
            return;
        }

        String priceString = showInputDialog("Update Order", "Enter new price:");
        if (priceString == null || priceString.isBlank()) {
            showError("Price cannot be empty!");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdString);
            int cakeID = Integer.parseInt(cakeIdString);
            double price = Double.parseDouble(priceString);

            selectedOrder.setId(orderId);
            selectedOrder.setCakeId(cakeID);
            selectedOrder.setCustomerName(customerName);
            selectedOrder.setOrderDate(orderDate);
            selectedOrder.setAddress(address);
            selectedOrder.setStatus(status);
            selectedOrder.setPrice(price);

            orderService.updateOrder(selectedOrder);
            resetObservableList();

        } catch (NumberFormatException e) {
            showError("Invalid Order ID, Cake ID, or Price format!");
        } catch (IllegalArgumentException e) {
            showError("Error updating order: " + e.getMessage());
        }
    }

    @FXML
    void filterOrdersByStatusHandler(ActionEvent event) {
        try {
            String statusToFilter = showInputDialog("Filter by Status", "Enter status to filter by:");

            if (statusToFilter == null || statusToFilter.isBlank()) {
                showError("Please enter a status to filter by.");
                return;
            }

            Iterable<Order<Integer>> filteredOrders = orderService.filterOrdersByStatus(statusToFilter);
            ArrayList<Order<Integer>> filteredOrdersList = new ArrayList<>();
            for (Order<Integer> order : filteredOrders) {
                filteredOrdersList.add(order);
            }

            if (filteredOrdersList.isEmpty()) {
                showError("No orders found with status: " + statusToFilter);
                return;
            }

            ListView<Order<Integer>> filteredOrdersListView = new ListView<>();
            ObservableList<Order<Integer>> observableFilteredOrders = FXCollections.observableArrayList(filteredOrdersList);
            filteredOrdersListView.setItems(observableFilteredOrders);

            Stage popupStage = new Stage();
            popupStage.setTitle("Filtered Orders by Status");

            VBox vbox = new VBox(filteredOrdersListView);
            vbox.setSpacing(10);
            vbox.setPadding(new javafx.geometry.Insets(10));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> popupStage.close());
            vbox.getChildren().add(closeButton);

            Scene scene = new Scene(vbox, 400, 300);
            popupStage.setScene(scene);
            popupStage.show();
        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void filterOrdersByCustomerNameHandler(ActionEvent event) {
        try {
            String customerNameToFilter = showInputDialog("Filter by Customer Name", "Enter customer name to filter by:");

            if (customerNameToFilter == null || customerNameToFilter.isBlank()) {
                showError("Please enter a customer name to filter by.");
                return;
            }

            Iterable<Order<Integer>> filteredOrders = orderService.filterOrdersByCustomerName(customerNameToFilter);
            ArrayList<Order<Integer>> filteredOrdersList = new ArrayList<>();
            for (Order<Integer> order : filteredOrders) {
                filteredOrdersList.add(order);
            }

            if (filteredOrdersList.isEmpty()) {
                showError("No orders found for customer: " + customerNameToFilter);
                return;
            }

            ListView<Order<Integer>> filteredOrdersListView = new ListView<>();
            ObservableList<Order<Integer>> observableFilteredOrders = FXCollections.observableArrayList(filteredOrdersList);
            filteredOrdersListView.setItems(observableFilteredOrders);

            Stage popupStage = new Stage();
            popupStage.setTitle("Filtered Orders by Customer Name");

            VBox vbox = new VBox(filteredOrdersListView);
            vbox.setSpacing(10);
            vbox.setPadding(new javafx.geometry.Insets(10));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> popupStage.close());
            vbox.getChildren().add(closeButton);

            Scene scene = new Scene(vbox, 400, 300);
            popupStage.setScene(scene);
            popupStage.show();
        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void filterOrdersByAddressHandler(ActionEvent event) {
        String addressToFilter = showInputDialog("Filter by Address", "Enter address to filter by:");

        if (addressToFilter == null || addressToFilter.isBlank()) {
            showError("Please enter an address to filter by.");
            return;
        }

        try {
            List<Order<Integer>> filteredOrders = orderService.filterOrdersByAddress(addressToFilter);

            if (filteredOrders.isEmpty()) {
                showError("No orders found for address: " + addressToFilter);
                return;
            }

            ListView<Order<Integer>> filteredOrdersListView = new ListView<>();
            ObservableList<Order<Integer>> observableFilteredOrders = FXCollections.observableArrayList(filteredOrders);
            filteredOrdersListView.setItems(observableFilteredOrders);

            Stage popupStage = new Stage();
            popupStage.setTitle("Filtered Orders by Address");

            VBox vbox = new VBox(filteredOrdersListView);
            vbox.setSpacing(10);
            vbox.setPadding(new javafx.geometry.Insets(10));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> popupStage.close());
            vbox.getChildren().add(closeButton);

            Scene scene = new Scene(vbox, 400, 300);
            popupStage.setScene(scene);
            popupStage.show();
        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void orderCountByCustomerHandler(ActionEvent event) {
        String customerName = showInputDialog("Order Count by Customer", "Enter customer name:");

        if (customerName == null || customerName.isBlank()) {
            showError("Customer name cannot be empty!");
            return;
        }

        try {
            long orderCount = orderService.getOrderCountByCustomerName(customerName);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Count by Customer");
            alert.setHeaderText("The number of orders placed by " + customerName + " is:");
            alert.setContentText(String.valueOf(orderCount));
            alert.showAndWait();
        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }


    @FXML
    void filterOrdersByOrderDateHandler(ActionEvent event) {
        String orderDateToFilter = showInputDialog("Filter by Order Date", "Enter order date to filter by (YYYY-MM-DD):");

        if (orderDateToFilter == null || orderDateToFilter.isBlank()) {
            showError("Please enter an order date to filter by.");
            return;
        }

        try {
            List<Order<Integer>> filteredOrders = orderService.filterOrdersByOrderDate(orderDateToFilter);

            if (filteredOrders.isEmpty()) {
                showError("No orders found for date: " + orderDateToFilter);
                return;
            }

            ListView<Order<Integer>> filteredOrdersListView = new ListView<>();
            ObservableList<Order<Integer>> observableFilteredOrders = FXCollections.observableArrayList(filteredOrders);
            filteredOrdersListView.setItems(observableFilteredOrders);

            Stage popupStage = new Stage();
            popupStage.setTitle("Filtered Orders by Order Date");

            VBox vbox = new VBox(filteredOrdersListView);
            vbox.setSpacing(10);
            vbox.setPadding(new javafx.geometry.Insets(10));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> popupStage.close());
            vbox.getChildren().add(closeButton);

            Scene scene = new Scene(vbox, 400, 300);
            popupStage.setScene(scene);
            popupStage.show();
        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void totalAmountByCustomerHandler(ActionEvent event) {
        String customerName = showInputDialog("Total Amount by Customer", "Enter customer name:");

        if (customerName == null || customerName.isBlank()) {
            showError("Customer name cannot be empty!");
            return;
        }

        try {
            double totalAmount = orderService.getTotalAmountByCustomerName(customerName);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total Amount by Customer");
            alert.setHeaderText("The total amount to be paid by " + customerName + " is:");
            alert.setContentText(String.format("$%.2f", totalAmount));
            alert.showAndWait();
        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void orderCountPerCakeHandler(ActionEvent event) {
        try {
            Map<String, Long> orderCountPerCake = orderService.getOrderCountPerCake();

            StringBuilder content = new StringBuilder();

            if (orderCountPerCake.isEmpty()) {
                content.append("No orders have been placed yet.");
            } else {
                for (Map.Entry<String, Long> entry : orderCountPerCake.entrySet()) {
                    content.append(String.format("Cake: %s - Orders: %d%n", entry.getKey(), entry.getValue()));
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Count per Cake");
            alert.setHeaderText("Total orders placed for each cake:");
            alert.setContentText(content.toString());

            alert.showAndWait();

        } catch (RuntimeException e) {
            showError("An unexpected error occurred: " + e.getMessage());
        }
    }

}
