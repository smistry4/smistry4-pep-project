package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDao {
    public Account insertUser(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        
        try {
            String username = account.getUsername();
            String password = account.getPassword();
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    int createdAccountId = (int) rs.getLong(1);
                    return new Account(createdAccountId, username, password);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();
        
        try {
            String sql = "SELECT * FROM account WHERE username=(?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public Account getAccountById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        
        try {
            String sql = "SELECT * FROM account WHERE account_id=(?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
}
