import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
/**
 * <code> EditProductHandler class</code>
 * This class handles HTTP requests for editing a product's details.
 * <p>It first checks if the user is logged in using the <code>LoggedIn() method</code> from the <code>HttpHelper class</code>.
 * If the user is not logged in, they are redirected to the login page using the <code>redirect()</code> method from the <code>HttpHelper class</code>. </p>
 * <p>If logged in and the request method is <code>GET</code> and an ID is provided as a query parameter, the ID value is extracted using a key value pair.
 *The ID value is assigned to a variable and used to list the product's details in a pre-populated <code>HTTP form</code> using the <code>FoodDAO class</code> and it's <code>FindProduct()</code> method. Response header 200 is sent. </p>
 *<p>If logged in and the request method is <code>POST</code>, form data is extracted as an <code>ArrayList</code> of <code>Key Value Pairs</code> using the <code>HttpHelper class</code>'s <code>getKVPairs()</code> method.
 *The <code>Key Value Pairs</code> are split and values are extracted and assigned to variables related to the food product object. The product information is updated using these variables, the <code>FoodDAO</code> and it's <code>UpdateProduct()</code> method.
 * The user is redirected to the menu using the <code>redirect()</code> method from the <code>HttpHelper class</code></p>
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class EditProductHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if (HttpHelper.loggedIn(exchange)) {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(exchange.getResponseBody()));

            if (exchange.getRequestMethod().equals("GET")) {
                exchange.sendResponseHeaders(200, 0);
                String queryParam = exchange.getRequestURI().getQuery();
                String[] KVPair = queryParam.split("=");
                int id = 0;
                if (KVPair.length == 2) {
                    String key = KVPair[0];
                    String value = KVPair[1];
                    if ("id".equals(key)) {
                        try {
                            id = Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            exchange.sendResponseHeaders(400, 0);
                            writer.write("Invalid id");
                        }
                    }
                    FoodDAO dao = new FoodDAO(new ConnectionFactory());
                    FoodProduct product = dao.findProduct(id);

                    writer.write(
                            "<html>" +
                                    "<head> <title> Edit product </title>" +
                                    "<link rel=\"stylesheet\" href=\"static/css/bootstrap.min.css\">" +
                                    "</head>" +
                                    "<body>" +
                                    "<h2> Edit Product </h2>" +
                                    "<form action=\"editProduct\" method=\"post\" enctype = \"text/plain\">" +
                                    "<input type=\"hidden\" id=\"ProdId\" name=\"ProdId\" value=" + id + ">" +
                                    "<label for=\"sku\"><b>Enter product SKU</b></label><br>" +
                                    "<input type=\"text\" id=\"sku\" name=\"sku\" required value =" + product.getSKU() + "><br>" +
                                    "<label for=\"description\"><b>Enter product description</b></label><br>" +
                                    "<input type=\"text\" id=\"description\" name=\"description\" required value =" + product.getDescription() + "><br>" +
                                    "<label for=\"category\"><b>Enter product category</b></label><br>" +
                                    "<input type=\"text\" id=\"category\" name=\"category\" required value =" + product.getCategory() + "><br>" +
                                    "<label for=\"price\"><b>Enter product price</b></label><br>" +
                                    "<input type=\"text\" id=\"price\" name=\"price\" required value =" + product.getPrice() + "><br>" +
                                    "<input type=\"submit\" value =\"Submit\">" +
                                    "</form>" +
                                    "<a href= \"/menu\"> click here to go back to the main menu </a>" +
                                    "</body>" +
                                    "</html>");
                    writer.close();
                }
            } else if (exchange.getRequestMethod().equals("POST")) {
                ArrayList<String> keyValuePairs = HttpHelper.getKVPairs(exchange);
                    int ProdID = 0;
                    String SKU = null;
                    String Description = null;
                    String Category = null;
                    Double Price = null;

                    for (String KVpairs:  keyValuePairs) {
                        String[] dataEntry = KVpairs.split("=");
                        if (dataEntry.length == 2) {
                            String key = dataEntry[0];
                            String value = dataEntry[1];
                            if ("ProdId".equals(key)) {
                                try {
                                    ProdID = Integer.parseInt(value);
                                } catch (NumberFormatException e) {
                                    exchange.sendResponseHeaders(400, 0);
                                    writer.write("Invalid ID");
                                }
                            }
                            if ("sku".equals(key)) {
                                SKU = value;
                            } else if ("description".equals(key)) {
                                Description = value;
                            } else if ("category".equals(key)) {
                                Category = value;
                            } else if ("price".equals(key)) {
                                try {
                                    Price = Double.parseDouble(value);
                                } catch (NumberFormatException e) {
                                    exchange.sendResponseHeaders(400, 0);
                                    writer.write("Invalid price entered");
                                }
                            }
                        }
                    }
                    if (SKU != null && Description != null && Category != null) {
                        FoodDAO dao = new FoodDAO(new ConnectionFactory());
                        FoodProduct product = new FoodProduct(SKU, Description, Category, Price);
                        product.setID(ProdID);
                        dao.updateProduct(product);
                        HttpHelper.redirect(exchange, "/menu");
                    }
                }
        } else {
            HttpHelper.redirect(exchange, "/");
        }
}
}
