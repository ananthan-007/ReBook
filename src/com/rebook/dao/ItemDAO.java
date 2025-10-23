package com.rebook.dao;

import com.rebook.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // ✅ INSERT (Add new item)
    public void addItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (title, description, price, quantity, image_path, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setString(5, item.getImagePath());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) item.setId(rs.getInt(1));
            }
        }
    }

    // ✅ SELECT ALL (Fetch all items)
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, description, price, quantity, image_path, created_at FROM items ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(mapRowToItem(rs));
            }
        }
        return items;
    }

    // ✅ SELECT BY ID (Get single item)
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT id, title, description, price, quantity, image_path, created_at FROM items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToItem(rs);
            }
        }
        return null;
    }

    // ✅ UPDATE (Edit item details)
    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET title = ?, description = ?, price = ?, quantity = ?, image_path = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setString(5, item.getImagePath());
            ps.setInt(6, item.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // ✅ DELETE (Remove an item)
    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ SEARCH (Used by MyListingsPanel)
    public List<Item> searchItems(String keyword) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, description, price, quantity, image_path, created_at " +
                "FROM items WHERE title LIKE ? OR description LIKE ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRowToItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // ✅ Helper: Convert ResultSet row → Item object
    private Item mapRowToItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setTitle(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        item.setQuantity(rs.getInt("quantity"));
        item.setImagePath(rs.getString("image_path"));
        item.setCreatedAt(rs.getTimestamp("created_at"));
        return item;
    }
}
