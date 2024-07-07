/**
 * <code> Address class</code>
 * <p>This class represents a customer address.
 * It holds a <code>constructor</code> for creating an <code>address object</code>.
 * This class also holds <code>getters</code> and <code>setters</code> for retrieval and modification of an address object.
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488
 * @version 1.0
 * @since Corretto-17
 */
public class CustomerAddress {

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String country;
    private String postCode;
    /**
     * <code>Address constructor</code>
     * Constructs an instance of the<code>Address class</code> as an <code>address object</code>with required values.
     * @param addressLine1 A string containing the first line of the customer\s address.
     * @param addressLine2 A string containing the second line of the customer's address.
     *@param  addressLine3 A string containing the third line of the customer's address.
     * @param country A string containing the country of the customer's address.
     * @param postCode A string containing the post code of the Customer's address.
     */
    public CustomerAddress(String addressLine1, String addressLine2, String addressLine3, String country, String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.country = country;
        this.postCode = postCode;
    }
    /**
     * <code>getAddressLine1()</code>
     * A <code>getter</code> which retrieves <code>addressLine1</code> from a customer's <code>Address</code>.
     * @return A String representing <code>addressLine1</code> of the customer's <code>Address</code>.
     */
    public String getAddressLine1() {
        return this.addressLine1;
    }
    /**
     * <code>setAddressLine1()</code>
     * A <code>setter</code> which sets <code>addressLine1</code> of a customer's <code>Address</code>.
     * @param <code>addressLine1</code>. A string representing the first line of a customer's <code>Address</code>.
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    /**
     * <code>getAddressLine2()</code>
     * A <code>getter</code> which retrieves <code>addressLine2</code> from a customer's <code>Address</code>.
     * @return A String representing <code>addressLine2</code> of the customer's <code>Address</code>.
     */
    public String getAddressLine2() {
        return this.addressLine2;
    }
    /**
     * <code>setAddressLine2()</code>
     * A <code>setter</code> which sets <code>addressLine2</code> of a customer's <code>Address</code>.
     * @param <code>addressLine2</code>. A string representing the second line of a customer's <code>Address</code>.
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    /**
     * <code>getAddressLine3()</code>
     * A <code>getter</code> which retrieves <code>addressLine3</code> from a customer's <code>Address</code>.
     * @return A String representing <code>addressLine3</code> of the customer's <code>Address</code>.
     */
    public String getAddressLine3() {
        return this.addressLine3;
    }
    /**
     * <code>setAddressLine3()</code>
     * A <code>setter</code> which sets <code>addressLine3</code> of a customer's <code>Address</code>.
     * @param <code>addressLine3</code>. A string representing the third line of a customer's <code>Address</code>.
     */
    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }
    /**
     * <code>getCountry()</code>
     * A <code>getter</code> which retrieves the <code>country</code> from a customer's <code>Address</code>.
     * @return A String representing the <code>country</code> of the customer's <code>Address</code>.
     */
    public String getCountry() {
        return this.country;
    }
    /**
     * <code>setCountry()</code>
     * A <code>setter</code> which sets the <code>country</code> of a customer's <code>Address</code>.
     * @param <code>country</code>. A string representing the country of a customer's <code>Address</code>.
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * <code>getPostCode()</code>
     * A <code>getter</code> which retrieves the <code>postCode</code> from a customer's <code>Address</code>.
     * @return A String representing the <code>postCode</code> of the customer's <code>Address</code>.
     */
    public String getPostCode() {
        return this.postCode;
    }
    /**
     * <code>setPostCode()</code>
     * A <code>setter</code> which sets the <code>postCode</code> of a customer's <code>Address</code>.
     * @param <code>PostCode</code>. A string representing the postcode of a customer's <code>Address</code>.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String addressToString() {
        return "Address:" + getAddressLine1() + "\n" + getAddressLine2() + "\n" + getAddressLine3() + "\n" + getCountry() + "\n" + getPostCode();
    }

    }
