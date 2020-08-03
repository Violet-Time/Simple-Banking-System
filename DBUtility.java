package banking;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DBUtility {

    private String url;

    public DBUtility(String path) {
        url = "jdbc:sqlite:" + path;
        createTableDB();
    }

    public User getUser(String number) {
        User user = null;

        String sql = "SELECT * FROM card WHERE number = ?";

        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, number);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        user = new User(resultSet.getString("number"), resultSet.getString("pin"), resultSet.getInt("balance"));
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public void delete(String number) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, number);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public User getLastAddedUser() {
        User user = null;

        String sql = "SELECT * FROM card WHERE id = (SELECT MAX(id) FROM card)";

        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        user = new User(resultSet.getString("number"), resultSet.getString("pin"), resultSet.getInt("balance"));
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO card(number, pin) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, user.getCardNumber());
                    statement.setString(2, user.getPin());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateBalance(String number, int on) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, on);
                    statement.setString(2, number);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void createTableDB() {
        String sql = "CREATE TABLE IF NOT EXISTS card(" +
                "id INTEGER PRIMARY KEY," +
                "number TEXT NOT NULL," +
                "pin TEXT NOT NULL," +
                "balance INTEGER DEFAULT 0)";
        try (Connection connection = DriverManager.getConnection(url)) {

            if (connection != null) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
