import java.util.ArrayList;
import java.util.Scanner;

/**
 * <code> MenuConsole class</code>
 * Displays the main menu and takes in user input for navigation through different menu options. Calls DAO methods to list existing database data and allow users to add, remove or update.
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class MenuConsole {
    /**
     * <code>displayMenu()</code>
     * <p>Displays the main menu to the user, allowing options to list products and customers in the database, and add, update or delete products or customers using the <code>FoodDAO</code> and <code>CustomerDAO</code> classes and their methods.
     * The method operates a loop and case system to prompt a user for their choice while navigating through the menu. The system will exit when a user chooses to do so.
     */
   public void displayMenu() {

        Scanner in = new Scanner(System.in);
        FoodDAO foodDAO = new FoodDAO(new ConnectionFactory());
        CustomerDAO customerDAO = new CustomerDAO(new ConnectionFactory());

        int selected;
        do {
            System.out.println("--------------------");
            System.out.println("The Food Store");
            System.out.println("Choose from these options");
            System.out.println("--------------------");

            System.out.println("[1] List all products");
            System.out.println("[2] Search for product by ID");
            System.out.println("[3] Add a new product");
            System.out.println("[4] Update a product by ID");
            System.out.println("[5] Delete a product by ID");
            System.out.println("[6] List all customers");
            System.out.println("[7] Search for a customer by ID");
            System.out.println("[8] Add a customer");
            System.out.println("[9] Update a customer's details");
            System.out.println("[10] Delete a customer");
            System.out.println("[11] Exit");



            selected = in.nextInt();

            switch (selected) {
                case 1:
                    System.out.println("Listing products");
                    foodDAO.listProducts();

                    ArrayList<FoodProduct> List = foodDAO.listProducts();
                    for ( int i  = 0; i < List.size(); i ++) {
                  FoodProduct item =  List.get(i);
                        System.out.println(item.getID());
                        System.out.println(item.getSKU());
                        System.out.println(item.getDescription());
                        System.out.println(item.getCategory());
                        System.out.println(item.getPrice());
                        System.out.println("-------------");
                    }

                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice = in.nextInt();
                    if (choice == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11;
                    }
                    break;

                case 2:
                    System.out.println("Enter product ID");
                    int ID = in.nextInt();
                    FoodProduct product= foodDAO.findProduct(ID);
                    System.out.println("Product SKU = " + product.getSKU() + "\n" + "Product Description = " + product.getDescription() + "\n" + "Product Category =" + product.getCategory() + "\n" + "Product price = " + product.getPrice());
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice2 = in.nextInt();
                    if (choice2 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                case 3:
                    in.nextLine();
                    System.out.println("Enter product SKU");
                    String SKU = in.nextLine();
                    System.out.println("["+SKU+"]");
                    System.out.println("Enter product description");
                    String Description = in.nextLine();
                    System.out.println("Enter product category");
                    String Category = in.nextLine();
                    System.out.println("Enter product price");
                    double Price = in.nextDouble();

                    FoodProduct foodProduct = new FoodProduct(SKU, Description, Category, Price);

                    boolean Added = foodDAO.addProduct(foodProduct);
                    if (Added == true) {
                        System.out.println("Product added successfully.");
                    } else {
                        System.out.println("Error adding product.");
                    }
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice3 = in.nextInt();
                    if (choice3 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                case 4:
                    System.out.println("Enter the ID of the product you wish to update");
                    int ProdID = in.nextInt();
                    in.nextLine();

                    FoodProduct foodProduct1 = foodDAO.findProduct(ProdID);
                    System.out.println("Product ID = " + ProdID + "\n" + "Product SKU = " + foodProduct1.getSKU() + "\n" + "Product Description = " + foodProduct1.getDescription() + "\n" + "Product Category =" + foodProduct1.getCategory() + "\n" + "Product price = " + foodProduct1.getPrice());

                    System.out.println("Please enter the product SKU, press enter to leave unchanged.");
                    String Sku = in.nextLine();
                    if (!Sku.matches("\\s*")) {
                        foodProduct1.setSKU(Sku);
                    }
                    System.out.println("Please enter the product description, press enter to leave unchanged.");
                    String descripton = in.nextLine();
                    if(!descripton.matches("\\s*")) {
                        foodProduct1.setDescription(descripton);
                    }
                    System.out.println("Please enter the product category, press enter to leave unchanged.");
                    String category = in.nextLine();
                    if(!category.matches("\\s*")) {
                        foodProduct1.setCategory(category);
                    }
                    System.out.println("Please enter the product price, enter to leave unchanged.");
                    String price = in.nextLine();
                    if (!price.matches("\\s*")) {
                        double PriceAsDouble = Double.parseDouble(price);
                        foodProduct1.setPrice(PriceAsDouble);
                    }

                    boolean Updated = foodDAO.updateProduct(foodProduct1);
                    if (Updated) {
                        System.out.println("Product updated successfully!");
                    } else {
                        System.out.println("Error updating product.");
                    }
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice4 = in.nextInt();
                    if (choice4 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                case 5:
                    System.out.println("Enter the ID of the product you wish to delete.");
                    int id = in.nextInt();
                    boolean outcome = foodDAO.deleteProduct(id);
                    if (outcome == true)  {
                        System.out.println("Product deleted successfully.");
                    } else {
                        System.out.println("Error deleting product.");
                    }
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice5 = in.nextInt();
                    if (choice5 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                case 6:
                    System.out.println("Listing customers");
                    ArrayList<Customer> customerList = customerDAO.listCustomers();
                    for ( int i  = 0; i < customerList.size(); i ++) {
                        Customer customer =  customerList.get(i);
                        System.out.println(customer.getCustomerID());
                        System.out.println(customer.getBusinessName());
                        System.out.println(customer.getAddress());
                        System.out.println(customer.getTelephoneNumber());
                        System.out.println("-------------");
                    }

                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice6 = in.nextInt();
                    if (choice6 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11;
                    }
                    break;

                case 7:
                    System.out.println("Enter customer ID");
                    int customerID = in.nextInt();
                    Customer customer = customerDAO.findCustomer(customerID);
                    System.out.println("Business name = " + customer.getBusinessName() + "\n" + "Address = " + customer.getAddress() + "\n" + " Telephone number = " + customer.getTelephoneNumber());
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice7 = in.nextInt();
                    if (choice7 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                case 8:
                    in.nextLine();
                    System.out.println("Enter customer business name");
                    String businessName = in.nextLine();
                    System.out.println("Enter customer address line 1");
                    String addressLine1 = in.nextLine();
                    System.out.println("Enter customer address line 2");
                    String addressLine2 = in.nextLine();
                    System.out.println("Enter customer address line 3");
                    String addressLine3 = in.nextLine();
                    System.out.println("Enter customer country");
                    String country = in.nextLine();
                    System.out.println("Enter customer post code");
                    String postCode = in.nextLine();
                    System.out.println("Enter customer telephone number");
                    String telephoneNumber = in.nextLine();

                    CustomerAddress address = new CustomerAddress(addressLine1, addressLine2, addressLine3, country, postCode);
                   Customer customer3 = new Customer(businessName, address, telephoneNumber);

                    boolean customerAdded = customerDAO.addCustomer(customer3);
                    if (customerAdded == true) {
                        System.out.println("Product added successfully.");
                    } else {
                        System.out.println("Error adding product.");
                    }
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice8 = in.nextInt();
                    if (choice8 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                case 9:
                    System.out.println("Enter the ID of the customer you wish to update");
                    int CustID = in.nextInt();
                    in.nextLine();

                    Customer customer1 = customerDAO.findCustomer(CustID);
                    System.out.println("Customer ID = " + CustID + "\n" + "Business Name = " + customer1.getBusinessName() + "\n" + "Address  = " + customer1.getAddress() + "\n" + "Telephone Number  =" + customer1.getTelephoneNumber());

                    System.out.println("Please enter the new business name, press enter to leave unchanged.");
                    String newBusinessName = in.nextLine();
                    if (!newBusinessName.matches("\\s*")) {
                     customer1.setBusinessName(newBusinessName);
                    }
                    System.out.println("Please enter the new address line 1, press enter to leave unchanged.");
                    String newAddressLine1 = in.nextLine();
                    if(!newAddressLine1.matches("\\s*")) {
                        customer1.getAddress().setAddressLine1(newAddressLine1);
                    }
                    System.out.println("Please enter the new address line 2, press enter to leave unchanged.");
                    String newAddressLine2 = in.nextLine();
                    if(!newAddressLine2.matches("\\s*")) {
                        customer1.getAddress().setAddressLine2(newAddressLine2);
                    }
                    System.out.println("Please enter the new address line 3, press enter to leave unchanged.");
                    String newAddressLine3 = in.nextLine();
                    if (!newAddressLine3.matches("\\s*")) {
                        customer1.getAddress().setAddressLine3(newAddressLine3);
                    }
                    System.out.println("Please enter the new country, press enter to leave unchanged.");
                    String newCountry = in.nextLine();
                    if(!newCountry.matches("\\s*")) {
                        customer1.getAddress().setCountry(newCountry);
                    }
                    System.out.println("Please enter the new postcode, press enter to leave unchanged");
                    String newPostcode = in.nextLine();
                    if(!newPostcode.matches("\\s*")) {
                        customer1.getAddress().setPostCode(newPostcode);
                    }
                    System.out.println("Please enter the new telephone number, press enter to leave unchanged.");
                    String newTelephoneNumber = in.nextLine();
                    if(!newTelephoneNumber.matches("\\s*")) {
                        customer1.setTelephoneNumber(newTelephoneNumber);
                    }
                    boolean customerUpdated = customerDAO.updateCustomer(customer1);
                    if (customerUpdated) {
                        System.out.println("Customer updated successfully!");
                    } else {
                        System.out.println("Error updating customer.");
                    }
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice9 = in.nextInt();
                    if (choice9 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                case 10:
                    System.out.println("Enter the ID of the customer you wish to delete.");
                    int customerId = in.nextInt();
                    boolean customerOutcome = customerDAO.deleteCustomer(customerId);
                    if (customerOutcome == true)  {
                        System.out.println("Customer deleted successfully.");
                    } else {
                        System.out.println("Error deleting customer.");
                    }
                    System.out.println("Do you want to continue?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No");
                    int choice10 = in.nextInt();
                    if (choice10 == 2) {
                        System.out.println("Thank you, goodbye.");
                        selected = 11; }
                    break;

                default:
                    System.out.println("Exiting");
                    selected = 11;
            }
        } while(selected != 11);

        in.close();
    }
}
