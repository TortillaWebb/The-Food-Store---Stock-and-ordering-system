import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * <code> DeleteProductHandler class</code>
 * <p>This class handles HTTP requests for deleting a customer.
 * It first checks if the user is logged in using the <code>LoggedIn() method</code> from the <code>HttpHelper class</code>.
 * If logged in and the request method is <code>GET</code> and an ID is provided as a query parameter, the ID value is extracted using a key value pair.
 * The ID value is assigned to a variable and used to delete the specified product using the <code>FoodDAO class</code> and it's <code>DeleteProduct()</code> method.
 * The user is redirected to the menu using the <code>redirect()</code> method from the <code>HttpHelper class</code></p>
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class DeleteProductHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if (HttpHelper.loggedIn(exchange)) {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(exchange.getResponseBody()));

            if (exchange.getRequestMethod().equals("GET")) {
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
                    dao.deleteProduct(id);
                    HttpHelper.redirect(exchange, "/menu");
                }
            }
        }
    }
}
