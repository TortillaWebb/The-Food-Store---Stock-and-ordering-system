import java.sql.*;
/**
 * <code> UserDAO class</code>
 * This DAO class holds methods to test connection to the database and retrieve the username and password of a user.
 * Connection testing method included is <code> connectDB() </code>. CRUD methods in this class include <code> getUser()</code>.
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class UserDAO {
    /*
         <code>connectDB()</code>
         Tests connection to the database.
         @return null if connection is successful, otherwise <code> SQLException</code>
        */
    private Connection connectDB() {
        String url = "jdbc:sqlite:Database.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                return conn;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    /**
         *<code>getUser()</code>
         *Retrieves a user object with matching queried username and password from the database.
         *@return user with matching user inputted username and password.
        */
    public User getUser(String username, String password) {

        Connection conn = null;
        User user = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";

        try {
            conn = connectDB();
            statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            results = statement.executeQuery();

            while (results.next()) {
                String dbUsername = results.getString("Username");
                String dbPassword = results.getString("Password");
                user = new User(dbUsername, dbPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }
}