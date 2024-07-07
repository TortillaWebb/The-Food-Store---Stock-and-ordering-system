import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
import java.security.MessageDigest;
/**
 * <code> loginHandler class</code>
 *This class handles HTTP requests for logging in.
 * <p>It holds methods for authenticating a username and password, hashing a password and providing a response to incorrect login details.
 * If the HTTP Exchange method is <code>POST</code>, form data is extracted as an <code>ArrayList</code> of <code>Key Value Pairs</code> using the <code>HttpHelper class</code>'s <code>getKVPairs()</code> method.
 *The <code>Key Value Pairs</code> are split and values are extracted and assigned to variables to be authenticated using the <code>authenticate()</code> method. The password is hashed using the <code>hashPassword()</code> method.
 *  If successfully authenticated, the user is redirected to the menu using the <class>HttpHelper class</class>'s <code>redirect()</code> method.
 *  If authentication fails, the user is shown a login page with an error message, using the <code>writeHTMLError()</code> method.
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
class loginHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            ArrayList<String> keyValuePairs = HttpHelper.getKVPairs(exchange);

            String username = null;
            String password = null;

            for(String KVPair:keyValuePairs) {
                String[] dataEntry = KVPair.split("=");
                if (dataEntry.length == 2) {
                    String key = dataEntry[0];
                    String value = dataEntry[1];
                    if ("uname".equals(key)) {
                        username = value;
                    } else if ("psw".equals(key)) {
                        password = value;
                        try {
                            password = hashPassword(value);
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (authenticate(username, password)) {
                exchange.getResponseHeaders().set("Set-Cookie", "logged-in=true");
               HttpHelper.redirect (exchange, "/menu");
            } else {
                writeHtmlError(exchange);
            }
        } else {
            writeHtmlError(exchange);
        }
    }
    /**
     * <code>authenticate()</code>
     * Checks the user inputted username and password against data in the database. Retrieves database data using the <code>UserDAO</code> and it's <code>getUser()</code> method.
     * @param <code>String username</code>, <code>String password</code>. The username and password from the form data.
     * @return True if username and password match those in database, otherwise false.
     */
    private boolean authenticate(String username, String password) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(username, password);

        if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return true;
        } return false;
    }
    /**
     * <code>hashPassword()</code>
     *Hashes a plaintext password using the MD5 algorithm.
     * @param <code>String password</code>. A user's password.
     * @return <code>hashedPassword</code>. The hashed password.
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte [] digest = messageDigest.digest();
        String hashedPassword = Base64.getEncoder().encodeToString(digest);
        return hashedPassword;
    }
    /**
     * <code>writeHtmlError()</code>
     *Sends response header 401 and writes HTML for an almost identical login page, with the addition of an error message reading "incorrect login details". For use when the <code>authenticate()</code> method returns false.
     * @param <code>exchange</code> The Http Exchange.
     */
    private void writeHtmlError(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(401, 0);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(exchange.getResponseBody()));
        writer.write(
                "<html>" +
                        "<head><title> Login </title>" +
                        "<link rel=\"stylesheet\" href=\"static/css/bootstrap.min.css\">" +
                        "</head>" +
                        "<h1> Welcome to The Food Store's stock control & ordering system.</h1>" +
                        "<h2> Please enter your log in credentials to begin </h2>" +
                        "<body>" +
                        new loginForm().getLoginForm()
                        +
                        "<p> Incorrect login details </p>" +
                        "</body>" +
                        "</html>");
        writer.close();
    }
}
