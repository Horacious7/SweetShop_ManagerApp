package main.ui;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.repository.RepositoryException;
import main.service.BirthdayCakeService;
import main.service.OrderService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private static final int ADD_CAKE = 1;
    private static final int VIEW_CAKES = 2;
    private static final int UPDATE_CAKE = 3;
    private static final int DELETE_CAKE = 4;
    private static final int FILTER_CAKE_BY_PRICE = 5;
    private static final int FILTER_CAKE_BY_FLAVOUR = 6;
    private static final int ADD_ORDER = 7;
    private static final int VIEW_ORDERS = 8;
    private static final int UPDATE_ORDER = 9;
    private static final int DELETE_ORDER = 10;
    private static final int FILTER_ORDER_BY_PRICE = 11;
    private static final int FILTER_ORDER_BY_STATUS = 12;
    private static final int FILTER_ORDER_BY_CUSTOMER_NAME = 13;
    private static final int FILTER_ORDER_BY_ORDER_DATE = 14;
    private static final int FILTER_ORDER_BY_ADDRESS = 15;
    private static final int EXIT = 0;

    private BirthdayCakeService<Integer> cakeService;
    private OrderService<Integer> orderService;
    private Scanner scanner;

    public ConsoleUI(BirthdayCakeService<Integer> cakeService, OrderService<Integer> orderService) {
        this.cakeService = cakeService;
        this.orderService = orderService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println(ADD_CAKE + ". Add a cake");
            System.out.println(VIEW_CAKES + ". View all the cakes");
            System.out.println(UPDATE_CAKE + ". Update a cake");
            System.out.println(DELETE_CAKE + ". Delete a cake");
            System.out.println(FILTER_CAKE_BY_PRICE + ". Filter cakes by price range");
            System.out.println(FILTER_CAKE_BY_FLAVOUR + ". Filter cakes by flavour");
            System.out.println(ADD_ORDER + ". Add an order");
            System.out.println(VIEW_ORDERS + ". View all the orders");
            System.out.println(UPDATE_ORDER + ". Update an order");
            System.out.println(DELETE_ORDER + ". Delete an order");
            System.out.println(FILTER_ORDER_BY_PRICE + ". Filter orders by price range");
            System.out.println(FILTER_ORDER_BY_STATUS + ". Filter orders by status");
            System.out.println(FILTER_ORDER_BY_CUSTOMER_NAME + ". Filter orders by customer name");
            System.out.println(FILTER_ORDER_BY_ORDER_DATE + ". Filter orders by order date");
            System.out.println(FILTER_ORDER_BY_ADDRESS + ". Filter orders by address");
            System.out.println(EXIT + ". Exit");
            System.out.print("Choose any option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice) {
                    case ADD_CAKE:
                        System.out.print("Enter cake name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter cake flavour: ");
                        String flavour = scanner.nextLine();
                        System.out.print("Enter cake price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        int newCakeId = cakeService.generateNewId();
                        BirthdayCake<Integer> newCake = new BirthdayCake<>(newCakeId, name, flavour, price);
                        cakeService.addCake(newCake);
                        System.out.println("The cake was added");
                        break;
                    case VIEW_CAKES:
                        List<BirthdayCake<Integer>> cakes = new ArrayList<>();
                        cakeService.getAllCakes().forEach(cakes::add);
                        cakes.sort(Comparator.comparing(cake -> (Integer) cake.getId()));
                        for (BirthdayCake<Integer> cake : cakes) {
                            System.out.println(cake);
                        }
                        break;
                    case UPDATE_CAKE:
                        System.out.print("Enter the ID of the cake which you want to update: ");
                        int idToUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new cake name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new cake flavour: ");
                        String newFlavour = scanner.nextLine();
                        System.out.print("Enter new cake price: ");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine();
                        BirthdayCake<Integer> updatedCake = new BirthdayCake<>(idToUpdate, newName, newFlavour, newPrice);
                        cakeService.updateCake(updatedCake);
                        System.out.println("The cake was updated");
                        break;
                    case DELETE_CAKE:
                        System.out.print("Enter the ID of the cake which you want to delete: ");
                        int idToDelete = scanner.nextInt();
                        scanner.nextLine();
                        cakeService.deleteCake(idToDelete);
                        System.out.println("The cake was deleted");
                        break;
                    case FILTER_CAKE_BY_PRICE:
                        System.out.print("Enter minimum price: ");
                        double minPrice = scanner.nextDouble();
                        System.out.print("Enter maximum price: ");
                        double maxPrice = scanner.nextDouble();
                        scanner.nextLine();
                        if (minPrice > maxPrice) {
                            System.out.println("Minimum price cannot be greater than maximum price.");
                        } else {
                            List<BirthdayCake<Integer>> cakesByPrice = new ArrayList<>();
                            cakeService.getCakesByPriceRange(minPrice, maxPrice).forEach(cakesByPrice::add);
                            for (BirthdayCake<Integer> cake : cakesByPrice) {
                                System.out.println(cake);
                            }
                        }
                        break;
                    case FILTER_CAKE_BY_FLAVOUR:
                        System.out.print("Enter flavour: ");
                        String filterFlavour = scanner.nextLine();
                        List<BirthdayCake<Integer>> cakesByFlavour = new ArrayList<>();
                        cakeService.getCakesByFlavour(filterFlavour).forEach(cakesByFlavour::add);
                        for (BirthdayCake<Integer> cake : cakesByFlavour) {
                            System.out.println(cake);
                        }
                        break;
                    case ADD_ORDER:
                        System.out.print("Enter customer name: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Enter address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double orderPrice = scanner.nextDouble();
                        scanner.nextLine();
                        int newOrderId = orderService.generateNewId();
                        Order<Integer> newOrder = new Order<>(newOrderId, newOrderId, customerName, address, "2023-01-01", "Pending", orderPrice);
                        orderService.addOrder(newOrder);
                        System.out.println("The order was added");
                        break;
                    case VIEW_ORDERS:
                        List<Order<Integer>> orders = new ArrayList<>();
                        orderService.getAllOrders().forEach(orders::add);
                        orders.sort(Comparator.comparing(order -> (Integer) order.getId()));
                        for (Order<Integer> order : orders) {
                            System.out.println(order);
                        }
                        break;
                    case UPDATE_ORDER:
                        System.out.print("Enter the ID of the order which you want to update: ");
                        int orderIdToUpdate = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new customer name: ");
                        String newCustomerName = scanner.nextLine();
                        System.out.print("Enter new address: ");
                        String newAddress = scanner.nextLine();
                        System.out.print("Enter new price: ");
                        double newOrderPrice = scanner.nextDouble();
                        scanner.nextLine();
                        Order<Integer> updatedOrder = new Order<>(orderIdToUpdate, orderIdToUpdate, newCustomerName, newAddress, "2023-01-01", "Pending", newOrderPrice);
                        orderService.updateOrder(updatedOrder);
                        System.out.println("The order was updated");
                        break;
                    case DELETE_ORDER:
                        System.out.print("Enter the ID of the order which you want to delete: ");
                        int orderIdToDelete = scanner.nextInt();
                        scanner.nextLine();
                        orderService.deleteOrder(orderIdToDelete);
                        System.out.println("The order was deleted");
                        break;
                    case FILTER_ORDER_BY_PRICE:
                        System.out.print("Enter minimum price: ");
                        double minOrderPrice = scanner.nextDouble();
                        System.out.print("Enter maximum price: ");
                        double maxOrderPrice = scanner.nextDouble();
                        scanner.nextLine();
                        if (minOrderPrice > maxOrderPrice) {
                            System.out.println("Minimum price cannot be greater than maximum price.");
                        } else {
                            List<Order<Integer>> ordersByPrice = new ArrayList<>();
                            orderService.getOrdersByPriceRange(minOrderPrice, maxOrderPrice).forEach(ordersByPrice::add);
                            for (Order<Integer> order : ordersByPrice) {
                                System.out.println(order);
                            }
                        }
                        break;
                    case FILTER_ORDER_BY_STATUS:
                        System.out.print("Enter status: ");
                        String filterStatus = scanner.nextLine();
                        List<Order<Integer>> ordersByStatus = new ArrayList<>();
                        orderService.filterOrdersByStatus(filterStatus).forEach(ordersByStatus::add);
                        for (Order<Integer> order : ordersByStatus) {
                            System.out.println(order);
                        }
                        break;
                    case FILTER_ORDER_BY_CUSTOMER_NAME:
                        System.out.print("Enter customer name: ");
                        String filterCustomerName = scanner.nextLine();
                        List<Order<Integer>> ordersByCustomerName = orderService.filterOrdersByCustomerName(filterCustomerName);
                        for (Order<Integer> order : ordersByCustomerName) {
                            System.out.println(order);
                        }
                        break;
                    case FILTER_ORDER_BY_ORDER_DATE:
                        System.out.print("Enter order date (YYYY-MM-DD): ");
                        String filterOrderDate = scanner.nextLine();
                        List<Order<Integer>> ordersByOrderDate = orderService.filterOrdersByOrderDate(filterOrderDate);
                        for (Order<Integer> order : ordersByOrderDate) {
                            System.out.println(order);
                        }
                        break;
                    case FILTER_ORDER_BY_ADDRESS:
                        System.out.print("Enter address: ");
                        String filterAddress = scanner.nextLine();
                        List<Order<Integer>> ordersByAddress = orderService.filterOrdersByAddress(filterAddress);
                        for (Order<Integer> order : ordersByAddress) {
                            System.out.println(order);
                        }
                        break;
                    case EXIT:
                        System.out.println("Exiting...");
                        return;
                }
            } catch (RepositoryException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }
    }
}