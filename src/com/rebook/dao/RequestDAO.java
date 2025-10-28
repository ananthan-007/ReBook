package com.rebook.dao;

import java.sql.*;

public class RequestDAO {
    
    // Create a request
    public static boolean createRequest(int itemId, int requesterId) throws SQLException {
        String sql = "INSERT INTO requests (item_id, requester_id, status) VALUES (?, ?, 'Pending')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, itemId);
            ps.setInt(2, requesterId);
            return ps.executeUpdate() > 0;
        }
    }
    
    // Get all requests for a user's items
    public static java.util.List<java.util.Map<String, Object>> getRequestsForUser(int userId) throws SQLException {
        java.util.List<java.util.Map<String, Object>> requests = new java.util.ArrayList<>();
        String sql = "SELECT r.request_id, r.item_id, r.requester_id, r.status, r.created_at, " +
                    "i.title, u.name as requester_name, u.email as requester_email, u.phone as requester_phone " +
                    "FROM requests r " +
                    "JOIN items i ON r.item_id = i.id " +
                    "JOIN users u ON r.requester_id = u.user_id " +
                    "WHERE i.user_id = ? AND r.status = 'Pending' " +
                    "ORDER BY r.created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    java.util.Map<String, Object> request = new java.util.HashMap<>();
                    request.put("request_id", rs.getInt("request_id"));
                    request.put("item_id", rs.getInt("item_id"));
                    request.put("requester_id", rs.getInt("requester_id"));
                    request.put("status", rs.getString("status"));
                    request.put("created_at", rs.getTimestamp("created_at"));
                    request.put("title", rs.getString("title"));
                    request.put("requester_name", rs.getString("requester_name"));
                    request.put("requester_email", rs.getString("requester_email"));
                    request.put("requester_phone", rs.getString("requester_phone"));
                    requests.add(request);
                }
            }
        }
        return requests;
    }
    
    // Update request status
    public static boolean updateRequestStatus(int requestId, String status) throws SQLException {
        String sql = "UPDATE requests SET status = ? WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, status);
            ps.setInt(2, requestId);
            return ps.executeUpdate() > 0;
        }
    }
}

