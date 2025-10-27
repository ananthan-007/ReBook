package com.rebook.dao;

import com.rebook.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // INSERT
    public void addItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (title, description, quantity, type, condition_status, image_path, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getType());
            ps.setString(5, item.getConditionStatus());
            ps.setString(6, item.getImagePath());
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) item.setId(rs.getInt(1));
            }
        }
    }

    // GET ALL
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, description, quantity, type, condition_status, image_path, created_at FROM items ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) items.add(mapRowToItem(rs));
        }
        return items;
    }

    // GET BY ID
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT id, title, description, quantity, type, condition_status, image_path, created_at FROM items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToItem(rs);
            }
        }
        return null;
    }

    // UPDATE
    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET title = ?, description = ?, quantity = ?, type = ?, condition_status = ?, image_path = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getType());
            ps.setString(5, item.getConditionStatus());
            ps.setString(6, item.getImagePath());
            ps.setInt(7, item.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // SEARCH
    public List<Item> searchItems(String keyword) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, description, quantity, type, condition_status, image_path, created_at " +
                "FROM items WHERE title LIKE ? OR description LIKE ? OR type LIKE ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) items.add(mapRowToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Helper
    private Item mapRowToItem(ResultSet rs) throws SQLException {
        Item it = new Item();
        it.setId(rs.getInt("id"));
        it.setTitle(rs.getString("title"));
        it.setDescription(rs.getString("description"));
        it.setQuantity(rs.getInt("quantity"));
        it.setType(rs.getString("type"));
        it.setConditionStatus(rs.getString("condition_status"));
        it.setImagePath(rs.getString("image_path"));
        it.setCreatedAt(rs.getTimestamp("created_at"));
        return it;
    }
}
