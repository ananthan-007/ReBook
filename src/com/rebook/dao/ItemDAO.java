package com.rebook.dao;

import com.rebook.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    // INSERT
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (title, description, condition_status, type, image_path, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getConditionStatus());
            ps.setString(4, item.getType());
            ps.setString(5, item.getImagePath());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // FETCH ALL ITEMS
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Item it = new Item();
                it.setId(rs.getInt("id"));
                it.setTitle(rs.getString("title"));
                it.setDescription(rs.getString("description"));
                it.setConditionStatus(rs.getString("condition_status"));
                it.setType(rs.getString("type"));
                it.setImagePath(rs.getString("image_path"));
                it.setCreatedAt(rs.getTimestamp("created_at"));
                items.add(it);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
