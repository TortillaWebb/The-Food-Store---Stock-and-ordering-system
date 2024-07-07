/**
 * <code> User class</code>
 * <p>This class represents a user of the Food Store's stock control and ordering system.
 * It holds a <code>constructor</code> for creating a <code>user object</code> consisting of a username and password.
 * This class also holds <code>getters</code> for retrieval of the user's username and password.
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class User {

    String username;
    String password;

    /**
     * <code>User constructor</code>
     * Constructs an instance of the<code>User class</code> as a <code>User object</code>with required values.
     * @param username A string containing the user's username.
     * @param password A string containing the user's password.
     */
    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
     * <code>getUsername()</code>
     * A <code>getter</code> which retrieves <code>username</code> from a user object.
     * @return A String representing the <code>username</code> of the user.
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * <code>getPassword()</code>
     * A <code>getter</code> which retrieves <code>password</code> from a user object.
     * @return A String representing the <code>password</code> of the user.
     */
    public String getPassword() {
        return this.password;
    }
}
