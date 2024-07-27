package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class MessageDao {
    
    /**
     * This method fetches all messages contained in the database.
     * @return a list of messages
     */
    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                messages.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
    /**
     * This messages fetches a messages using the message_id.
     * @param id to find the message
     * @return Message retrieved using message_id.
     */
    public Message getMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id=(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * This method adds a message to the database.
     * @param message contains the information to add.
     * @return the newly added Message.
     */
    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    int message_id = rs.getInt(1);
                    return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * This method updates the content of the message.
     * @param id used to locate the message to update
     * @param text the new content to be placed in the message
     */
    public void updateMessage(int id, String text) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text=(?) WHERE message_id=(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, text);
            ps.setInt(2, id);
            
            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * This method deletes the message from the database
     * @param id used to locate which message to delete
     */
    public void deleteMessage(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id=(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * This method fetches all messages by a particular user.
     * @param posted_by used to locate messages by a particular user
     * @return
     */
    public List<Message> getAllMessagesByUser(int posted_by) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messagesByUser = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by=(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, posted_by);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messagesByUser.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messagesByUser;
    }
}
