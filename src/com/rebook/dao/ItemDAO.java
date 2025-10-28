package com.rebook.dao;

import com.rebook.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // INSERT
    public static void addItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (title, description, quantity, type, condition_status, image_path, user_id, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getType());
            ps.setString(5, item.getConditionStatus());
            ps.setString(6, item.getImagePath());
            ps.setInt(7, item.getUserId());
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) item.setId(rs.getInt(1));
            }
        }
    }

    // GET ALL
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, description, quantity, type, condition_status, image_path, user_id, created_at FROM items ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) items.add(mapRowToItem(rs));
        }
        return items;
    }

    // GET ALL ITEMS BY TYPE (Donation/Exchange)
    public List<Item> getItemsByType(String type) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, description, quantity, type, condition_status, image_path, user_id, created_at FROM items WHERE type = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) items.add(mapRowToItem(rs));
            }
        }
        return items;
    }

    // GET BY ID
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT id, title, description, quantity, type, condition_status, image_path, user_id, created_at FROM items WHERE id = ?";
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

    // UPDATE ONLY TITLE AND TYPE
    public boolean updateItemTitleAndType(Item item) {
        String sql = "UPDATE items SET title = ?, type = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getType());
            ps.setInt(3, item.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // GET ITEM WITH USER INFO
    public Item getItemWithUser(int itemId) throws SQLException {
        String sql = "SELECT i.id, i.title, i.description, i.quantity, i.type, i.condition_status, i.image_path, i.user_id, i.created_at, " +
                "u.name as seller_name, u.email as seller_email, u.phone as seller_phone " +
                "FROM items i " +
                "JOIN users u ON i.user_id = u.user_id " +
                "WHERE i.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Item item = mapRowToItem(rs);
                    // Store seller info in item description or create a new field
                    item.setDescription(item.getDescription() + " | Seller: " + rs.getString("seller_name") + " | Email: " + rs.getString("seller_email") + " | Phone: " + rs.getString("seller_phone"));
                    return item;
                }
            }
        }
        return null;
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
        String sql = "SELECT id, title, description, quantity, type, condition_status, image_path, user_id, created_at " +
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
        it.setUserId(rs.getInt("user_id"));
        it.setCreatedAt(rs.getTimestamp("created_at"));
        return it;
    }
}
