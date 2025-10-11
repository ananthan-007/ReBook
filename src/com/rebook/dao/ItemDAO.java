package com.rebook.dao;

import com.rebook.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // INSERT
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

    // SELECT ALL
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, title, description, price, quantity, image_path, created_at FROM items ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Item it = mapRowToItem(rs);
                items.add(it);
            }
        }
        return items;
    }

    // SELECT BY ID
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

    // UPDATE
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

    // DELETE
    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // SEARCH (safe use of LIKE, with escaping)
    public List<Item> searchItemsByTitle(String query) throws SQLException {
        List<Item> items = new ArrayList<>();
        // Escape user input for LIKE wildcards, then use parameterized query
        String escaped = escapeForLike(query);
        String sql = "SELECT id, title, description, price, quantity, image_path, created_at FROM items WHERE title LIKE ? ESCAPE '\\' ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + escaped + "%"); // parameterized, safe
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) items.add(mapRowToItem(rs));
            }
        }
        return items;
    }

    // Helper: map a ResultSet row to an Item
    private Item mapRowToItem(ResultSet rs) throws SQLException {
        Item it = new Item();
        it.setId(rs.getInt("id"));
        it.setTitle(rs.getString("title"));
        it.setDescription(rs.getString("description"));
        it.setPrice(rs.getDouble("price"));
        it.setQuantity(rs.getInt("quantity"));
        it.setImagePath(rs.getString("image_path"));
        it.setCreatedAt(rs.getTimestamp("created_at"));
        return it;
    }

    // Escape %, _ and backslash for safe LIKE usage
    private static String escapeForLike(String input) {
        if (input == null) return null;
        return input.replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
}
