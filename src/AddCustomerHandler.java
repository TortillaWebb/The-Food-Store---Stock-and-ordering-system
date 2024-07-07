import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * <code> AddCustomerHandler class</code>
* <p>This class handles HTTP requests for adding a new customer.
 * It first checks if the user is logged in using the <code>LoggedIn() method</code> from the <code>HttpHelper class</code>.
 * If logged in, response header 200 is sent.
 * If not logged in, the user is redirected to the log in page using the <code>redirect method </code> in the <code>HttpHelper class</code>.
 * If the request method is <code>GET</code>, a <code>HTTP Form</code> is displayed for the user to input customer details.
 * If the request method is <code>POST</code>, form data is processed into an <code>ArrayList</code> of <code>key value pairs</code> and used to add a new customer to the database using the <code>CustomerDAO</code>'s <code>addCustomer() method</code> and the user is redirected to the main menu using the <code> HttpHelper class</code>' <code>redirect()</code> method.</code></code>.</p>
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488
 * @version 1.0
 * @since Corretto-17
 */
public class AddCustomerHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if (HttpHelper.loggedIn(exchange)) {
            exchange.sendResponseHeaders(200, 0);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(exchange.getResponseBody()));
            if (exchange.getRequestMethod().equals("GET")) {
                writer.write(
                        "<html>" +
                                "<head> <title> Add Customer </title>" +
                                "<link rel=\"stylesheet\" href=\"static/css/bootstrap.min.css\">" +
                                "</head>" +
                                "<body>" +
                                "<h2> Add a new customer </h2>" +
                                "<form action=\"addCustomer\" method=\"post\" enctype = \"text/plain\">" +
                                "<label for=\"businessName\"><b>Enter customer business name</b></label><br>" +
                                "<input type=\"text\" id=\"businessName\" name=\"businessName\" required><br>" +
                                "<label for=\"addressLine1\"><b>Enter address line 1</b></label><br>" +
                                "<input type=\"text\" id=\"addressLine1\" name=\"addressLine1\" required><br>" +
                                "<label for=\"addressLine2\"><b>Enter address line 2</b></label><br>" +
                                "<input type=\"text\" id=\"addressLine2\" name=\"addressLine2\" required><br>" +
                                "<label for=\"addressLine3\"><b>Enter address line 3</b></label><br>" +
                                "<input type=\"text\" id=\"addressLine3\" name=\"addressLine3\" required><br>" +
                                "<label for=\"country\"><b>Enter country</b></label><br>" +
                                "<input type=\"text\" id=\"country\" name=\"country\" required><br>" +
                                "<label for=\"postCode\"><b>Enter postcode</b></label><br>" +
                                "<input type=\"text\" id=\"postCode\" name=\"postCode\" required><br>" +
                                "<label for=\"telephoneNumber\"><b>Enter telephone number </b></label><br>" +
                                "<input type=\"text\" id=\"telephoneNumber\" name=\"telephoneNumber\" required><br>" +
                                "<input type=\"submit\" value =\"Submit\">" +
                                "</form>" +
                                "<a href= \"/menu\"> click here to go back to the main menu </a>" +
                                "</body>" +
                                "</html>");
                writer.close();
            } else if (exchange.getRequestMethod().equals("POST")) {
                ArrayList<String> keyValuePairs = HttpHelper.getKVPairs(exchange);

                String businessName = null;
                String addressLine1 = null;
                String addressLine2 = null;
                String addressLine3 = null;
                String country = null;
                String postCode = null;
                String telephoneNumber = null;

                for (String KVPair : keyValuePairs) {
                    String[] dataEntry = KVPair.split("=");
                    if (dataEntry.length == 2) {
                        String key = dataEntry[0];
                        String value = dataEntry[1];
                        if ("businessName".equals(key)) {
                            businessName = value;
                        } else if ("addressLine1".equals(key)) {
                            addressLine1 = value;
                        } else if ("addressLine2".equals(key)) {
                            addressLine2 = value;
                        } else if ("addressLine3".equals(key)) {
                            addressLine3 = value;
                        } else if ("country".equals(key)) {
                            country = value;
                        } else if ("postCode".equals(key)) {
                            postCode = value;
                        } else if ("telephoneNumber".equals(key)) {
                            telephoneNumber = value;
                        }
                    }
                }
                if (businessName != null && addressLine1 != null && addressLine2 != null && addressLine3 != null && country !=null && postCode != null && telephoneNumber != null) {
                    CustomerAddress address = new CustomerAddress(addressLine1, addressLine2, addressLine3, country, postCode);
                    CustomerDAO dao = new CustomerDAO(new ConnectionFactory());
                    Customer customer = new Customer(businessName, address, telephoneNumber);
                    dao.addCustomer(customer);
                    HttpHelper.redirect(exchange, "/menu");
                }
            }
        } else {
            HttpHelper.redirect(exchange, "/");
        }
    }
}
