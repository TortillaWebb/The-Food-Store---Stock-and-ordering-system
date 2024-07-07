import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
/**
 * <code> CustomerHandler class</code>
 * <p>This class contains the HTML for a table of customers.
 * It adds customer information from the database to a list via the <code>CustomerDAO</code> and it's <code>ListCustomers()</code> method.
 * It writes the customer list to HTML via the <code>Customer class</code>'s <code>customerToHTMLString()</code> method.
 * It first checks if the user is logged in using the <code>LoggedIn() method</code> from the <code>HttpHelper class</code>.
 * If logged in, response header 200 is sent.
 * If not logged in, the user is redirected to the log in page using the <code>redirect method </code> in the <code>HttpHelper class</code>.</p>
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class CustomerHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if (HttpHelper.loggedIn(exchange)) {
                exchange.sendResponseHeaders(200, 0);
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(exchange.getResponseBody()));

                CustomerDAO customers = new CustomerDAO(new ConnectionFactory());
                List<Customer> CustomerList = customers.listCustomers();

                writer.write(
                        "<html>" +
                                "<head> <title> Customers </title>" +
                                "<link rel=\"stylesheet\" href=\"static/css/bootstrap.min.css\">" +
                                "</head>" +
                                "<body>" +
                                "<h1> Current customers </h1>" +
                                "<table class =\"table\">" +
                                "<thead>" +
                                "<tr>" +
                                "<th> CustomerID</th>" +
                                "<th> Business name </th>" +
                                "<th> Address </th>" +
                                "<th> Telephone Number </th>" +
                                "<th> Edit </th>" +
                                "<th> Delete </th>" +
                                "</tr>" +
                                "</thead>" +
                                "<tbody>");

                for (Customer c : CustomerList) {
                    writer.write(c.customerToHTMLString());
                }
                writer.write(
                                "</tbody>" +
                                "</table>" +
                                           "<a href= \"/menu\"> click here to go back to the main menu </a>" +
                                "</body>" +
                                "</html>");

                writer.close();
            } else {
                HttpHelper.redirect(exchange, "/");
            }
        }
    }
