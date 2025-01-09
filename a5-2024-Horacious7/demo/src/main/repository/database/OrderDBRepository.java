package main.repository.database;

import main.domain.Order;
import main.repository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDBRepository implements IRepository<Integer, Order<Integer>> {
    private final String url;

    public OrderDBRepository(String url) {
        this.url = url;
    }

    @Override
    public Integer add(Order<Integer> entity) {
        String sql = "INSERT INTO orders (id, cakeId, orderDate, customerName, address, status, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getId());
            pstmt.setInt(2, entity.getCakeId());
            pstmt.setString(3, entity.getOrderDate());
            pstmt.setString(4, entity.getCustomerName());
            pstmt.setString(5, entity.getAddress());
            pstmt.setString(6, entity.getStatus());
            pstmt.setDouble(7, entity.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Integer id, Order<Integer> entity) {
        String sql = "UPDATE orders SET cakeId = ?, orderDate = ?, customerName = ?, address = ?, status = ?, price = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getCakeId());
            pstmt.setString(2, entity.getOrderDate());
            pstmt.setString(3, entity.getCustomerName());
            pstmt.setString(4, entity.getAddress());
            pstmt.setString(5, entity.getStatus());
            pstmt.setDouble(6, entity.getPrice());
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Order<Integer>> getAll() {
        List<Order<Integer>> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int cakeId = rs.getInt("cakeId");
                String orderDate = rs.getString("orderDate");
                String customerName = rs.getString("customerName");
                String address = rs.getString("address");
                String status = rs.getString("status");
                double price = rs.getDouble("price");
                orders.add(new Order<>(id, cakeId, orderDate, customerName, address, status, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}