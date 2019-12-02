package dao;

import protocol.entity.Message;
import protocol.entity.UserIP;
import util.SystemLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class DataSourceClient {

    private static final String USER_IP_TABLE = "users_ip";
    private static final String UNREAD_MESSAGE_TABLE = "unread_messages";

    private static DataSourceClient instance = null;

    public static DataSourceClient getInstance() {
        if (instance == null) {
            synchronized (DataSourceClient.class) {
                if (instance == null)
                    instance = new DataSourceClient();
            }
        }
        return instance;
    }

    public boolean addOrUpdateUserIP(UserIP userIP) {
        String query =
                String.format("INSERT INTO %s (username, user_ip) " +
                                "VALUES (?, ?) " +
                                "ON CONFLICT(username) DO UPDATE " +
                                "SET user_ip = ?",
                        USER_IP_TABLE);

        boolean IsUserIpAdded = false;

        try (Connection connection = DataSource.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, userIP.getUserName());
            statement.setString(2, userIP.getAddress());
            statement.setString(3, userIP.getAddress());

            IsUserIpAdded = statement.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            SystemLogger.info("User %s with ip %s already exists", userIP.getUserName(), userIP.getAddress());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return IsUserIpAdded;
    }

    public String getUserIP(String username) {
        String query =
                String.format("SELECT user_ip FROM %s " +
                                "WHERE username = ?",
                        USER_IP_TABLE);

        String ipAddress = "";
        try (Connection connection = DataSource.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next())
                    ipAddress = rs.getString("user_ip");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    public boolean addUnreadMessages(Message message) {
        String query =
                String.format("INSERT INTO %s (from_username, message) VALUES (?, ?)",
                        UNREAD_MESSAGE_TABLE);

        boolean IsUnreadMessagesAdded = false;

        try (Connection connection = DataSource.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, message.getSenderId());
            statement.setString(2, message.getContent());

            IsUnreadMessagesAdded = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return IsUnreadMessagesAdded;
    }

    public boolean deleteUnreadMessages(String username) {
        String query =
                String.format("DELETE FROM %s " +
                                "WHERE from_username = ?",
                        UNREAD_MESSAGE_TABLE);

        boolean IsUnreadMessagesDeleted = false;
        try (Connection connection = DataSource.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            IsUnreadMessagesDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return IsUnreadMessagesDeleted;
    }

    public List<String> getUnreadMessages(String username) {
        String query =
                String.format("SELECT message FROM %s " +
                                "WHERE from_username = ?",
                        UNREAD_MESSAGE_TABLE);

        var unreadMessageList = new ArrayList<String>();
        try (Connection connection = DataSource.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    unreadMessageList.add(rs.getString("message"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unreadMessageList;
    }

}
