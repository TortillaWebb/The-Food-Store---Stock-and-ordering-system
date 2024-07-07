import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * <code> ProductsHandler class</code>
 * <p>This class handles HTTP requests for displaying the food products in the database.
 * It first checks if the user is logged in using the <code>loggedIn() method</code> from the <code>HttpHelper class</code>.
 * If logged in, response header 200 is sent.
 *If logged in and the request method is <code>GET </code>, the page will display all food products in the database using the <code>FoodDAO class</code>'s <code>listProducts()</code> method.
 *If the request method is <code>GET</code> and the query parameter is search, food products matching the users query will be displayed using the <code>FoodDAO class</code> and it's <code>searchProducts()</code> method.
 *If the query parameter is filter, filtered food products matching the user specified category will be displayed using the <code>FoodDAO class</code> and it's <code>filterProducts()</code> method.
 * If not logged in, the user is redirected to the log in page using the <code>redirect method</code> from the <code>HttpHelper class</code>.</p>
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488
 * @version 1.0
 * @since Corretto-17
 */

class ProductsHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if (HttpHelper.loggedIn(exchange)) {
            exchange.sendResponseHeaders(200, 0);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(exchange.getResponseBody()));

            if (exchange.getRequestMethod().equals("GET")) {
                FoodDAO products = new FoodDAO(new ConnectionFactory());
                    String queryParam = exchange.getRequestURI().getQuery();
                List<FoodProduct> FoodProductList = null;

                    if (queryParam !=null) {
                    String[] KVPair = queryParam.split("=");
                    if (KVPair.length == 2) {
                        String key = KVPair[0];
                        String value = KVPair[1];
                        if ("search".equals(key)) {
                            String search = value;
                            FoodProductList = products.searchProducts(value);
                        } else if  ("filter".equals(key)) {
                            String filter = value;
                            FoodProductList = products.filterProducts(value);
                        }
                    }
                }else {  FoodProductList = products.listProducts(); }


                writer.write(
                        "<html>" +
                                "<form id =\"searchForm\" role =\"search\" \"action=\"products\" method=\"get\">" +
                                "<input type =\"search\" id=\"searchQuery\" name = \"search\" placeholder = \"Search products\" aria-label=\"Search products\">" +
                                "<input type=\"submit\" value =\"Search\">" +
                                "</form>" +
                                "<form id=\"filterForm\" role =\"search\" \"action=\"products\" method=\"get\">" +
                                "<input type = \"search\" id=\"filterQuery\" name = \"filter\" placeholder = \"Filter by category\" aria-label=\" Filter products\">" +
                                "<input type=\"submit\" value =\"Filter\">" +
                                "</form>" +
                                "<head> " +
                                "<link rel=\"stylesheet\" href=\"static/css/bootstrap.min.css\">" +
                                "<title> Food Products </title>" +
                                "</head>" +
                                "<body>" +
                                "<h1> Food products in stock! </h1>" +
                                "<table class =\"table\">" +
                                "<thead>" +
                                "<tr>" +
                                "<th> ProductID</th>" +
                                "<th> SKU </th>" +
                                "<th> Description </th> " +
                                "<th> Category </th>" +
                                "<th> Price </th>" +
                                "<th> Edit </th>" +
                                "<th> Delete </th>" +
                                "</tr>" +
                                "</thead>" +
                                "<tbody>");

                assert FoodProductList != null;
                for (FoodProduct f : FoodProductList) {
                    writer.write(f.toHTMLString());
                }
                writer.write(
                        "</tbody>" +
                                "</table>" +
                                "<a href=\"/menu\"> Click here to go back to the main menu </a>" +
                                "</body>" +
                                "</html>");

                writer.close();
            }
        } else {
            HttpHelper.redirect(exchange, "/");
        }
    }
}