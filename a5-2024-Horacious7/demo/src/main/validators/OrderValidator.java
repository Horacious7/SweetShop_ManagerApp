package main.validators;

import main.domain.Order;

public class OrderValidator<ID> {
    public void validate(Order order) {
        validateOrder(order);
    }

    private void validateOrder(Order order) {
        if (order.getCakeId() == null) {
            throw new IllegalArgumentException("Cake ID cannot be null!");
        }
        if (order.getCustomerName() == null || order.getCustomerName().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty!");
        }
        if (order.getStatus() == null || (!order.getStatus().equalsIgnoreCase("finished") &&
                !order.getStatus().equalsIgnoreCase("preparing") &&
                !order.getStatus().equalsIgnoreCase("cancelled"))) {
            throw new IllegalArgumentException("Invalid order status!");
        }
        if (order.getOrderDate() == null) {
            throw new IllegalArgumentException("Order date cannot be null!");
        }
    }
}