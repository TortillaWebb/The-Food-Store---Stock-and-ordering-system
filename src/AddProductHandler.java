import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.ArrayList;

/**
 * <code> AddProductHandler class</code>
 * <p>This class handles HTTP requests for adding a new food product to the database.
 * It first checks if the user is logged in using the <code>LoggedIn() method</code> from the <code>HttpHelper class</code>.
 * If logged in and the request method is <code>GET</code>, a <code>HTTP Form</code> is displayed for the user to input food product details.
 * If logged in and the request method is <code>POST</code>, form data is processed into an <code>ArrayList</code> of <code>key value pairs</code> and used to add a new food product to the database using the <code>FoodDAO</code>'s <code>AddProduct() method</code> and the user is redirected to the main menu using the <code>HttpHelper class</code> <code>redirect()</code> method..
 * If not logged in, the user is redirected to the log in page using the <code>redirect method </code> in the <code>HttpHelper class</code>.</p>
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488
 * @version 1.0
 * @since Corretto-17
 */

class AddProductHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if (HttpHelper.loggedIn(exchange)) {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(exchange.getResponseBody()));
            if (exchange.getRequestMethod().equals("GET")) {
                writer.write(
                        "<html>" +
                                "<head> <title> Add Product </title>" +
                                "<link rel=\"stylesheet\" href=\"static/css/bootstrap.min.css\">" +
                                "</head>" +
                                "<body>" +
                                "<h2> Add a new product </h2>" +
                                "<form action=\"addProduct\" method=\"post\" enctype = \"text/plain\">" +
                                "<label for=\"sku\"><b>Enter product SKU</b></label><br>" +
                                "<input type=\"text\" id=\"sku\" name=\"sku\" required><br>" +
                                "<label for=\"description\"><b>Enter product description</b></label><br>" +
                                "<input type=\"text\" id=\"description\" name=\"description\" required><br>" +
                                "<label for=\"category\"><b>Enter product category</b></label><br>" +
                                "<input type=\"text\" id=\"category\" name=\"category\" required><br>" +
                                "<label for=\"price\"><b>Enter product price</b></label><br>" +
                                "<input type=\"text\" id=\"price\" name=\"price\" required><br>" +
                                "<input type=\"submit\" value =\"Submit\">" +
                                "</form>" +
                                "<a href= \"/menu\"> click here to go back to the main menu </a>" +
                                "</body>" +
                                "</html>");
                exchange.sendResponseHeaders(200, 0);
                writer.close();
            } else if (exchange.getRequestMethod().equals("POST")) {
            ArrayList<String> keyValuePairs = HttpHelper.getKVPairs(exchange);

            String SKU = null;
            String Description = null;
            String Category = null;
            Double Price = null;

            for (String KVPair : keyValuePairs) {
                String[] dataEntry = KVPair.split("=");
                if (dataEntry.length == 2) {
                    String key = dataEntry[0];
                    String value = dataEntry[1];
                    if ("sku".equals(key)) {
                        SKU = value;
                    } else if ("description".equals(key)) {
                        Description = value;
                    } else if ("category".equals(key)) {
                        Category = value;
                    } else if ("price".equals(key)) {
                        try {
                            Price =  Double.parseDouble(value);
                        } catch (NumberFormatException e) {
                        exchange.sendResponseHeaders(400, 0);
                        writer.write("Invalid price entered");
                        }
                        }
                    }
                }
            if (SKU != null && Description != null && Category != null && Price != null) {
                FoodDAO dao = new FoodDAO(new ConnectionFactory());
                FoodProduct product = new FoodProduct(SKU, Description, Category, Price);
                dao.addProduct(product);
                HttpHelper.redirect(exchange, "/menu");
            }
            }
        } else {
            HttpHelper.redirect(exchange, "/");
        }
        }
        }


