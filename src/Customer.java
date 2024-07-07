/**
 * <code> Customer class</code>
 * <p>This class represents a customer.
 * It holds a <code>constructor</code> for creating a <code>customer object</code>.
 * This class also holds <code>getters</code> and <code>setters</code> for retrieval and modification of a customer object.
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class Customer {

   private int CustomerID;
   private String businessName;
   private String telephoneNumber;
   private CustomerAddress address;

    /**
     * <code>Customer constructor</code>
     * Constructs an instance of the<code>Customer class</code> as a <code>customer object</code>with required values.
     * CustomerID is not required as the database will assign and autoincrement customer ID.
     * @param businessName A string containing the customer's business name.
     * @param address An address instance containing the customer's address details.
     *@param telephoneNumber A string containing the customer's telephone number.
     */
   public Customer(String businessName, CustomerAddress address, String telephoneNumber) {
       this.businessName = businessName;
       this.address = address;
       this.telephoneNumber = telephoneNumber;
   }
    /**
     * <code>getCustomerID()</code>
     * A <code>getter</code> which retrieves <code>CustomerID</code> from a customer object after it has been set using a <code>setter</code>.
     * @return An integer representing the <code>CustomerID</code> of the customer.
     */
   public int getCustomerID() {
       return this.CustomerID;
   }
    /**
     * <code>setCustomerID()</code>
     * A <code>setter</code> which sets the <code>CustomerID</code> of a customer in the database.
     * @param <code>CustomerID</code>. An integer representing the CustomerID of a customer.
     */
   public void setCustomerID(int CustomerID) {
       this.CustomerID = CustomerID;
   }
    /**
     * <code>getBusinessName()</code>
     * A <code>getter</code> which retrieves the <code>businessName</code> from a customer object.
     * @return A String representing the <code>businessName</code> of the customer.
     */
    public String getBusinessName() {
        return this.businessName;
    }
    /**
     * <code>setBusinessName()</code>
     * A <code>setter</code> which sets the <code>businessName</code> of a customer in the database.
     * @param <code>businessName</code>. A String representing the business name of a customer.
     */
    public void setBusinessName(String businessName) {
       this.businessName = businessName;
    }
    /**
     * <code>getTelephoneNumber()</code>
     * A <code>getter</code> which retrieves the <code>telephoneNumber</code> from a customer object.
     * @return A String representing the <code>telephoneNumber</code> of the customer.
     */
    public String getTelephoneNumber() {
       return this.telephoneNumber;
    }
    /**
     * <code>setTelephoneNumber()</code>
     * A <code>setter</code> which sets the <code>telephoneNumber</code> of a customer in the database.
     * @param <code>telephoneNumber</code>. A String representing the telephone number of a customer.
     */
    public void setTelephoneNumber(String telephoneNumber) {
       this.telephoneNumber = telephoneNumber;
    }
    /**
     * <code>getAddress()</code>
     * A <code>getter</code> which retrieves the <code>Address</code> from a customer object.
     * @return <code>Address</code> an instance of the <code>Address class</code>. The address of the customer.
     */
    public CustomerAddress getAddress() {
       return this.address;
    }
    /**
     * <code>customerToHTMLString()</code>
     * Compiles customer information and converts it to HTML String for display on website as a row of a table.
     * @return HTML <code>String</code> of customer information, including <code>customerID</code>, <code>BusinessName</code>, <code>Address</code> and <code>TelephoneNumber</code>.
     */
    public String customerToHTMLString() {
        return "<tr>" +
                "<td>" + getCustomerID() + "</td>" +
                "<td>" + getBusinessName() + "</td>" +
                "<td>" + getAddress().addressToString() + "</td>" +
                "<td>" + getTelephoneNumber() + "</td>" +
                "<td><a href =\"/editCustomer?id=" + getCustomerID() + " \">  edit </a>" +
                "<td><a href=\"/deleteCustomer?id=" + getCustomerID() + "\"> delete </a>" +
                "</tr>";
    }

}
