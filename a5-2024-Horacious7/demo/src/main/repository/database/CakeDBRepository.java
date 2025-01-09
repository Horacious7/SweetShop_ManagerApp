package main.repository.database;

import main.domain.BirthdayCake;
import main.repository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CakeDBRepository implements IRepository<Integer, BirthdayCake<Integer>> {
    private final String url;

    public CakeDBRepository(String url) {
        this.url = url;
    }

    @Override
    public Integer add(BirthdayCake<Integer> entity) {
        String sql = "INSERT INTO cakes (id, name, flavour, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entity.getId());
            pstmt.setString(2, entity.getName());
            pstmt.setString(3, entity.getFlavour());
            pstmt.setDouble(4, entity.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM cakes WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Integer id, BirthdayCake<Integer> entity) {
        String sql = "UPDATE cakes SET name = ?, flavour = ?, price = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getFlavour());
            pstmt.setDouble(3, entity.getPrice());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<BirthdayCake<Integer>> getAll() {
        List<BirthdayCake<Integer>> cakes = new ArrayList<>();
        String sql = "SELECT * FROM cakes";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String flavour = rs.getString("flavour");
                double price = rs.getDouble("price");
                cakes.add(new BirthdayCake<>(id, name, flavour, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cakes;
    }
}