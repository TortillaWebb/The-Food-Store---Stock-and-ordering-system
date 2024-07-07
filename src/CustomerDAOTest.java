import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.ArrayList;


class CustomerDAOTest {

    @Test
    void listCustomers() throws SQLException {
        //Arrange
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        ResultSet results = mock(ResultSet.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery("SELECT * FROM Customers;")).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getInt("CustomerID")).thenReturn(1);
        when(results.getString("businessName")).thenReturn("testBusinessName");
        when(results.getString("telephoneNumber")).thenReturn("testTelephoneNumber");
        when(results.getString("addressLine1")).thenReturn("testAL1");
        when(results.getString("addressLine2")).thenReturn("testAL2");
        when(results.getString("addressLine3")).thenReturn("testAL3");
        when(results.getString("country")).thenReturn("testCountry");
        when(results.getString("postCode")).thenReturn("testPostCode");

        ArrayList<Customer>testArrayList = new ArrayList<Customer>();
        CustomerAddress testAddress = new CustomerAddress("testAL1", "testAL2", "testAL3", "testCountry", "testPostCode");
        Customer testCustomer = new Customer("testBusinessName", testAddress,"testTelephoneNumber" );
        testCustomer.setCustomerID(1);
        testArrayList.add(testCustomer);

        CustomerDAO dao = new CustomerDAO(connectionFactory);

        //Act
        ArrayList<Customer>result = dao.listCustomers();

        //Assert
        assertEquals(testArrayList.get(0).getCustomerID(), result.get(0).getCustomerID());
        assertEquals(testArrayList.get(0).getBusinessName(), result.get(0).getBusinessName());
        assertEquals(testArrayList.get(0).getAddress().getAddressLine1(), result.get(0).getAddress().getAddressLine1());
        assertEquals(testArrayList.get(0).getAddress().getAddressLine2(), result.get(0).getAddress().getAddressLine2());
        assertEquals(testArrayList.get(0).getAddress().getAddressLine3(), result.get(0).getAddress().getAddressLine3());
        assertEquals(testArrayList.get(0).getAddress().getCountry(), result.get(0).getAddress().getCountry());
        assertEquals(testArrayList.get(0).getAddress().getPostCode(), result.get(0).getAddress().getPostCode());
        assertEquals(testArrayList.get(0).getTelephoneNumber(), result.get(0).getTelephoneNumber());
    }

    @Test
    void findCustomer() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("SELECT * FROM customers WHERE customerID = ?;")).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(results);
        when(results.next()).thenReturn(true, false);
        when(results.getInt("customerID")).thenReturn(1);
        when(results.getString("businessName")).thenReturn("testBusinessName");
        when(results.getString("telephoneNumber")).thenReturn("testTelephoneNumber");
        when(results.getString("addressLine1")).thenReturn("testAL1");
        when(results.getString("addressLine2")).thenReturn("testAL2");
        when(results.getString("addressLine3")).thenReturn("testAL3");
        when(results.getString("country")).thenReturn("testCountry");
        when(results.getString("postCode")).thenReturn("testPostCode");


        CustomerAddress testAddress = new CustomerAddress("testAL1", "testAL2", "testAL3", "testCountry", "testPostCode");
        Customer testCustomer = new Customer("testBusinessName", testAddress,"testTelephoneNumber" );
        testCustomer.setCustomerID(1);

        CustomerDAO dao = new CustomerDAO(connectionFactory);

        //Act
        Customer newTestCustomer = dao.findCustomer(1);

        //Assert
        assertEquals(testCustomer.getCustomerID(), newTestCustomer.getCustomerID());
        assertEquals(testCustomer.getBusinessName(), newTestCustomer.getBusinessName());
        assertEquals(testCustomer.getAddress().getAddressLine1(), newTestCustomer.getAddress().getAddressLine1());
        assertEquals(testCustomer.getAddress().getAddressLine2(), newTestCustomer.getAddress().getAddressLine2());
        assertEquals(testCustomer.getAddress().getAddressLine3(), newTestCustomer.getAddress().getAddressLine3());
        assertEquals(testCustomer.getAddress().getCountry(), newTestCustomer.getAddress().getCountry());
        assertEquals(testCustomer.getAddress().getPostCode(), newTestCustomer.getAddress().getPostCode());
        assertEquals(testCustomer.getTelephoneNumber(), newTestCustomer.getTelephoneNumber());
        verify(preparedStatement).setInt(1, 1);
    }

    @Test
    void updateCustomer() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("UPDATE Customers SET businessName = ? ,"
                + "addressLine1 = ?, "
                + "addressLine2 = ?, "
                + "addressLine3 = ?, "
                + "country = ?, "
                + "postCode = ?, "
                + "telephoneNumber = ? "
                + "WHERE customerID = ?")).thenReturn(preparedStatement);

       when(preparedStatement.executeUpdate()).thenReturn(1);
       CustomerDAO dao = new CustomerDAO(connectionFactory);
       CustomerAddress testAddress = new CustomerAddress("testAL1", "testAL2", "testAL3", "testCountry", "testPostCode");
       Customer testCustomer = new Customer("testBusinessName", testAddress,"testTelephoneNumber" );
       testCustomer.setCustomerID(1);
       boolean customerUpdated = dao.updateCustomer(testCustomer);

       assertTrue(customerUpdated);
       verify(preparedStatement).setString(1, "testBusinessName");
       verify(preparedStatement).setString(2, "testAL1");
       verify(preparedStatement).setString(3, "testAL2");
       verify(preparedStatement).setString(4, "testAL3");
       verify(preparedStatement).setString(5, "testCountry");
       verify(preparedStatement).setString(6, "testPostCode");
       verify(preparedStatement).setString(7, "testTelephoneNumber");
       verify(preparedStatement).setInt(8, 1);

    }

    @Test
    void deleteCustomer() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("DELETE FROM customers WHERE customerID = ?")).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(1);
        CustomerDAO dao = new CustomerDAO(connectionFactory);
        boolean customerDeleted = dao.deleteCustomer(1);

        assertTrue(customerDeleted);

        verify(preparedStatement).setInt(1, 1);

    }

    @Test
    void addCustomer() throws SQLException {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(connectionFactory.createConnection("jdbc:sqlite:Database.db")).thenReturn(connection);
        when(connection.prepareStatement("INSERT INTO customers (businessName, addressLine1, addressLine2, addressLine3, country, postCode, telephoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)")).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(1);
        CustomerDAO dao = new CustomerDAO(connectionFactory);
        CustomerAddress testAddress = new CustomerAddress("testAL1", "testAL2", "testAL3", "testCountry", "testPostCode");
        Customer testCustomer = new Customer("testBusinessName", testAddress,"testTelephoneNumber" );
        boolean customerAdded = dao.addCustomer(testCustomer);

        assertTrue(customerAdded);
        verify(preparedStatement).setString(1, "testBusinessName");
        verify(preparedStatement).setString(2, "testAL1");
        verify(preparedStatement).setString(3, "testAL2");
        verify(preparedStatement).setString(4, "testAL3");
        verify(preparedStatement).setString(5, "testCountry");
        verify(preparedStatement).setString(6, "testPostCode");
        verify(preparedStatement).setString(7, "testTelephoneNumber");
    }
}