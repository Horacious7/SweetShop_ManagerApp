package main;

import main.repository.CreateRepository;
import main.ui.ConsoleUI;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.repository.IRepository;
import main.service.BirthdayCakeService;
import main.service.OrderService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("C:\\Users\\maier\\Desktop\\facultate\\an 2\\sem 1\\MAP\\proiecte\\a5-2024-Horacious7\\settings.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            return;
        }

        String repositoryType = properties.getProperty("Repository", "inmemory");
        String cakesFile = properties.getProperty("Cakes", "cakes.txt");
        String orderFile = properties.getProperty("Order", "order.txt");

        if (repositoryType.equalsIgnoreCase("database")) {
            cakesFile = properties.getProperty("Location", "jdbc:sqlite:C:\\Users\\maier\\Desktop\\facultate\\an 2\\sem 1\\MAP\\proiecte\\a5-2024-Horacious7\\demo\\data\\sweetshop.db");
            orderFile = cakesFile;
        }

        IRepository<Integer, BirthdayCake<Integer>> cakeRepository = CreateRepository.createCakeRepository(repositoryType, cakesFile);
        IRepository<Integer, Order<Integer>> orderRepository = CreateRepository.createOrderRepository(repositoryType, orderFile);

        BirthdayCakeService<Integer> cakeService = new BirthdayCakeService<>(cakeRepository);
        OrderService<Integer> orderService = new OrderService<>(orderRepository, cakeRepository);


        if (repositoryType.equalsIgnoreCase("inmemory")) {
            cakeService.addCake(new BirthdayCake<>(1, "Forest", "Hazelnut", 90.5));
            cakeService.addCake(new BirthdayCake<>(2, "Jungle", "Banana", 107.0));
            cakeService.addCake(new BirthdayCake<>(3, "Red", "Strawberry", 65.8));
            cakeService.addCake(new BirthdayCake<>(4, "BeeBee", "Honey", 200.5));
            cakeService.addCake(new BirthdayCake<>(5, "Darkness", "Chocolate", 78.3));

            orderService.addOrder(new Order<>(1, 5, "Anna", "preparing", "2023-10-01", "2023-10-02", 100.0));
            orderService.addOrder(new Order<>(2, 4, "Jack", "preparing", "2023-10-01", "2023-10-02", 150.0));
            orderService.addOrder(new Order<>(3, 1, "Richard", "preparing", "2023-10-01", "2023-10-02", 200.0));
            orderService.addOrder(new Order<>(4, 2, "John", "preparing", "2023-10-01", "2023-10-02", 250.0));
            orderService.addOrder(new Order<>(5, 4, "Sonia", "preparing", "2023-10-01", "2023-10-02", 300.0));
        }

        ConsoleUI ui = new ConsoleUI(cakeService, orderService);
        ui.start();
    }
}