package main.repository.file.text;

import main.domain.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextFileOrderRepository extends TextFileRepository<Integer, Order<Integer>> {
    public TextFileOrderRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected Order<Integer> parseEntity(String line) {
        String[] tokens = line.split(",");
        Integer orderId = Integer.parseInt(tokens[0].trim());
        Integer cakeId = Integer.parseInt(tokens[1].trim());
        String customerName = tokens[2].trim();
        String address = tokens[3].trim();
        String orderDate = tokens[4].trim();
        String orderStatus = tokens[5].trim();
        double price = Double.parseDouble(tokens[6].trim());
        Order<Integer> order = new Order<>(orderId,cakeId, customerName, address, orderDate, orderStatus, price);
        order.setId(orderId);
        order.setStatus(orderStatus);
        order.setOrderDate(orderDate);
        return order;
    }
}
