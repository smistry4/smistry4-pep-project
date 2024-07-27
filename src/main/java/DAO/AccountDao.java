package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDao {

    /**
     * This method inserts a new user based on the information provided.
     * @param account which contain user information like username and passowrd.
     * @return the newly created user Account
     */
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
    
    /**
     * This method fetches user account using username
     * @param username to find the user account
     * @return Account containing information about the user with username
     */
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
    
    /**
     * This method fetches a user account using the account_id.
     * @param id to find the user account
     * @return Account containing information about the user with account_id.
     */
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
