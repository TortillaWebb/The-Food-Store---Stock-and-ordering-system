import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;

public class StaticFileHandler implements HttpHandler {
    public void handle (HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();

            FileReader reader = new FileReader("." + path);
            BufferedReader bReader = new BufferedReader(reader);

            exchange.sendResponseHeaders(200, 0);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(exchange.getResponseBody()));

            String line;
            while ((line = bReader.readLine()) != null) {
                writer.write(line);
            }
            writer.close();
    }
}
