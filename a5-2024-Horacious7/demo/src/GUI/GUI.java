package GUI;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.repository.CreateRepository;
import main.repository.IRepository;
import main.service.BirthdayCakeService;
import main.service.OrderService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));

        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("settings.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            return;
        }

        String repositoryType = properties.getProperty("Repository", "inmemory");
        String cakesFile = properties.getProperty("Cakes", "cakes.txt");
        String orderFile = properties.getProperty("Order", "order.txt");

        if (repositoryType.equalsIgnoreCase("database")) {
            cakesFile = properties.getProperty("Location", "jdbc:sqlite:C:/Users/maier/Desktop/facultate/an 2/sem 1/MAP/proiecte/a5-2024-Horacious7/demo/data/sweetshop.db");
            orderFile = cakesFile;
        }

        IRepository<Integer, BirthdayCake<Integer>> cakeRepository = CreateRepository.createCakeRepository(repositoryType, cakesFile);
        IRepository<Integer, Order<Integer>> orderRepository = CreateRepository.createOrderRepository(repositoryType, orderFile);

        BirthdayCakeService cakeService = new BirthdayCakeService(cakeRepository);
        OrderService orderService = new OrderService(orderRepository, cakeRepository);

        if (repositoryType.equalsIgnoreCase("inmemory")) {
            cakeService.addCake(new BirthdayCake<>(1, "Forest", "Hazelnut", 90.5));
            cakeService.addCake(new BirthdayCake<>(2, "Jungle", "Banana", 107.0));
            cakeService.addCake(new BirthdayCake<>(3, "Red", "Strawberry", 65.8));
            cakeService.addCake(new BirthdayCake<>(4, "BeeBee", "Honey", 200.5));
            cakeService.addCake(new BirthdayCake<>(5, "Darkness", "Chocolate", 78.3));

            orderService.addOrder(new Order<>(5, 1, "Anna", "Address1", "2023-10-01", "2023-10-02", 100.0));
            orderService.addOrder(new Order<>(4, 2, "Jack", "Address2", "2023-10-03", "2023-10-04", 150.0));
            orderService.addOrder(new Order<>(1, 3, "Richard", "Address3", "2023-10-05", "2023-10-06", 200.0));
            orderService.addOrder(new Order<>(2, 4, "John", "Address4", "2023-10-07", "2023-10-08", 250.0));
            orderService.addOrder(new Order<>(4, 5, "Sonia", "Address5", "2023-10-09", "2023-10-10", 300.0));
        }

        Controller controller = new Controller(cakeService, orderService);
        loader.setController(controller);
        Scene scene = new Scene(loader.load(), 1000, 1000);
        stage.setScene(scene);
        stage.setTitle("Sweetshop App");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}