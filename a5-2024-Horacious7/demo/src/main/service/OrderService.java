package main.service;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.filter.OrderFilters.FilterOrderByPrice;
import main.repository.IRepository;
import main.repository.RepositoryException;
import main.repository.memory.InMemoryFilteredRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderService<ID> {
    private IRepository<ID, Order<ID>> orderRepository;
    private IRepository<ID, BirthdayCake<ID>> cakeRepository;
    private Integer currentOrderId;

    public OrderService(IRepository<ID, Order<ID>> orderRepository, IRepository<ID, BirthdayCake<ID>> cakeRepository) {
        if (orderRepository == null || cakeRepository == null) {
            throw new RepositoryException("Repository cannot be null.");
        }
        this.orderRepository = orderRepository;
        this.cakeRepository = cakeRepository;
        this.currentOrderId = ((List<Order<ID>>) orderRepository.getAll()).size() + 1;
    }

    public ID addOrder(Order<ID> order) {
        if (order.getId() == null) {
            order.setId((ID) currentOrderId);
            currentOrderId++;
        }
        orderRepository.add(order);
        return order.getId();
    }

    public int generateNewId() {
        List<Order<ID>> orders = (List<Order<ID>>) getAllOrders();
        if (orders.isEmpty()) {
            return 1;
        }
        for (int i = 1; i <= orders.size() + 1; i++) {
            boolean idExists = false;
            for (Order<ID> order : orders) {
                if (order.getId().equals(i)) {
                    idExists = true;
                    break;
                }
            }
            if (!idExists) {
                return i;
            }
        }
        return orders.size() + 1;
    }

    public Iterable<Order<ID>> getAllOrders() {
        return orderRepository.getAll();
    }

    public void updateOrder(Order<ID> order) {
        orderRepository.update(order.getId(), order);
    }

    public void deleteOrder(ID idToDelete) {
        orderRepository.delete(idToDelete);
    }

    public Iterable<Order<ID>> getOrdersByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Prices cannot be negative!");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price cannot be greater than max price!");
        }
        FilterOrderByPrice<ID> priceFilter = new FilterOrderByPrice<>(minPrice, maxPrice);
        InMemoryFilteredRepository<ID, Order<ID>> filteredRepository = new InMemoryFilteredRepository<>(orderRepository, priceFilter);
        return filteredRepository.getAll();
    }

    public List<Order<ID>> filterOrdersByCustomerName(String customerName) {
        return StreamSupport.stream(orderRepository.getAll().spliterator(), false)
                .filter(order -> order.getCustomerName().equalsIgnoreCase(customerName))
                .collect(Collectors.toList());
    }

    public List<Order<ID>> filterOrdersByStatus(String status) {
        List<Order<ID>> allOrders = StreamSupport.stream(orderRepository.getAll().spliterator(), false)
                .collect(Collectors.toList());
        List<Order<ID>> filteredOrders = allOrders.stream()
                .filter(order -> order.getStatus().trim().equalsIgnoreCase(status.trim()))
                .collect(Collectors.toList());
        return filteredOrders;
    }

    public List<Order<ID>> filterOrdersByOrderDate(String orderDate) {
        return StreamSupport.stream(orderRepository.getAll().spliterator(), false)
                .filter(order -> order.getOrderDate().equals(orderDate))
                .collect(Collectors.toList());
    }

    public long getOrderCountByCustomerName(String customerName) {
        return StreamSupport.stream(orderRepository.getAll().spliterator(), false)
                .filter(order -> order.getCustomerName().equalsIgnoreCase(customerName))
                .count();
    }

    public List<Order<ID>> filterOrdersByAddress(String address) {
        return StreamSupport.stream(orderRepository.getAll().spliterator(), false)
                .filter(order -> order.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
    }

    public double getTotalAmountByCustomerName(String customerName) {
        return StreamSupport.stream(orderRepository.getAll().spliterator(), false)
                .filter(order -> order.getCustomerName().equalsIgnoreCase(customerName))
                .mapToDouble(Order::getPrice)
                .sum();
    }

    public Map<ID, Long> getOrderCountPerCake() {
        return StreamSupport.stream(orderRepository.getAll().spliterator(), false)
                .collect(Collectors.groupingBy(Order::getCakeId, Collectors.counting()));
    }
}