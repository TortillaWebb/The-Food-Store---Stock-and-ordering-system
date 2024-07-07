import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodDAOTest {

    @Test
    void listProducts() throws SQLException {
        //Arrange
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet results = mock(ResultSet.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT * FROM FoodProducts;")).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getInt("ProductID")).thenReturn(1);
        when(results.getString("SKU")).thenReturn("testSKU");
        when(results.getString("Description")).thenReturn("testDescription");
        when(results.getString("Category")).thenReturn("testCategory");
        when(results.getDouble("Price")).thenReturn(1.0);

        ArrayList<FoodProduct> testArrayList = new ArrayList<FoodProduct>();
        FoodProduct testProduct = new FoodProduct("testSKU", "testDescription", "testCategory", 1.0);
        testProduct.setID(1);
        testArrayList.add(testProduct);

        FoodDAO dao = new FoodDAO(connectionFactory);

        //Act
        ArrayList<FoodProduct>result = dao.listProducts();

        //Assert
        assertEquals(testArrayList.get(0).getID(), result.get(0).getID());
        assertEquals(testArrayList.get(0).getSKU(), result.get(0).getSKU());
        assertEquals(testArrayList.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(testArrayList.get(0).getCategory(), result.get(0).getCategory());
        assertEquals(testArrayList.get(0).getPrice(), result.get(0).getPrice());
    }

    @Test
    void findProduct() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("SELECT * FROM FoodProducts WHERE ProductID = ?;")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getInt("ProductID")).thenReturn(1);
        when(results.getString("SKU")).thenReturn("testSKU");
        when(results.getString("Description")).thenReturn("testDescription");
        when(results.getString("Category")).thenReturn("testCategory");
        when(results.getDouble("Price")).thenReturn(1.0);

        FoodProduct testProduct = new FoodProduct("testSKU", "testDescription", "testCategory", 1.0);
        testProduct.setID(1);

        FoodDAO dao = new FoodDAO(connectionFactory);

        //Act
        FoodProduct newTestProduct = dao.findProduct(1);

        //Assert
        assertEquals(testProduct.getID(), newTestProduct.getID());
        assertEquals(testProduct.getSKU(), newTestProduct.getSKU());
        assertEquals(testProduct.getDescription(), newTestProduct.getDescription());
        assertEquals(testProduct.getCategory(), newTestProduct.getCategory());
        assertEquals(testProduct.getPrice(), newTestProduct.getPrice());
        verify(preparedStatement).setInt(1, 1);
    }

    @Test
    void addProduct() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("INSERT INTO FoodProducts (SKU, Description, Category, Price) VALUES (?, ?, ?, ?)")).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(1);
        FoodDAO dao = new FoodDAO(connectionFactory);
        FoodProduct testProduct = new FoodProduct("testSKU", "testDescription", "testCategory", 1.0);
        boolean ProductAdded = dao.addProduct(testProduct);
        assertTrue(ProductAdded);

        verify(preparedStatement).setString(1, "testSKU");
        verify(preparedStatement).setString(2, "testDescription");
        verify(preparedStatement).setString(3, "testCategory");
        verify(preparedStatement).setDouble(4, 1.0);
    }

    @Test
    void updateProduct() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("UPDATE FoodProducts SET SKU = ? ,"+
                "Description = ? ," +
                "Category = ? ," +
                "Price = ?" +
                "WHERE ProductID = ?")).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(1);
        FoodDAO dao = new FoodDAO(connectionFactory);
        FoodProduct testProduct = new FoodProduct("testSKU2", "testDescription", "testCategory", 1.0);
        testProduct.setID(1);
        boolean ProductUpdated = dao.updateProduct(testProduct);

        assertTrue(ProductUpdated);
        verify(preparedStatement).setString(1, "testSKU2");
        verify(preparedStatement).setString(2, "testDescription");
        verify(preparedStatement).setString(3, "testCategory");
        verify(preparedStatement).setDouble(4, 1.0);
        verify(preparedStatement).setInt(5, 1);
    }

    @Test
    void deleteProduct() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("DELETE FROM FoodProducts WHERE ProductID = ?")).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(1);
        FoodDAO dao = new FoodDAO(connectionFactory);
        boolean ProductDeleted = dao.deleteProduct(3);

        assertTrue(ProductDeleted);

        verify(preparedStatement).setInt(1, 3);
    }

    @Test
    void searchProducts() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("SELECT * FROM FoodProducts WHERE Description LIKE ?")).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(results);

        when(results.next()).thenReturn(true, false);
        when(results.getInt("ProductID")).thenReturn(1);
        when(results.getString("SKU")).thenReturn("testSKU");
        when(results.getString("Description")).thenReturn("testDescription");
        when(results.getString("Category")).thenReturn("testCategory");
        when(results.getDouble("Price")).thenReturn(1.0);

        FoodDAO dao = new FoodDAO(connectionFactory);
        ArrayList<FoodProduct> resultList = dao.searchProducts("testDescription");

        verify(preparedStatement).setString(1, "%testDescription%");
        assertEquals(1, resultList.size());
        FoodProduct resultProduct = resultList.get(0);
        FoodProduct expectedProduct = new FoodProduct("testSKU", "testDescription", "testCategory", 1.0);
        expectedProduct.setID(1);
        assertEquals(expectedProduct.getDescription(), resultProduct.getDescription());
    }

    @Test
    void filterProducts() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);

        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("SELECT * FROM FoodProducts WHERE Category = ?")).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(results);

        when(results.next()).thenReturn(true, false);
        when(results.getInt("ProductID")).thenReturn(1);
        when(results.getString("SKU")).thenReturn("testSKU");
        when(results.getString("Description")).thenReturn("testDescription");
        when(results.getString("Category")).thenReturn("testCategory");
        when(results.getDouble("Price")).thenReturn(1.0);

        FoodDAO dao = new FoodDAO(connectionFactory);
        ArrayList<FoodProduct> resultList = dao.filterProducts("testCategory");
        String expectedCategory = "testCategory";

        verify(preparedStatement).setString(1, expectedCategory);
        assertEquals(1, resultList.size());
        FoodProduct resultProduct = resultList.get(0);

        FoodProduct expectedProduct = new FoodProduct("testSKU", "testDescription", "testCategory", 1.0);
        expectedProduct.setID(1);
        assertEquals(expectedProduct.getCategory(), resultProduct.getCategory());
    }
}