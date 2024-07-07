import jdk.jfr.Category;
import java.security.PublicKey;

/**
 * <code>FoodProduct class</code>
 * <p>This class represents a food product.
 * It holds a FoodProduct <code>constructor</code> for creating a FoodProduct object with values SKU, description, category and price and corresponding getters and setters. It also holds a <code> toHTMLString() </code> method for food product values.</p>
 * @author Tiarnaigh Downey-Webb. MMU ID: 19092488.
 * @version 1.0
 * @since Corretto-17
 */
public class FoodProduct {

    private int ID;
  private  String SKU;
    private String description;
    private String category;
    private double price;

    /**
     * <code> Constructor </code> for FoodProduct.
     * @param SKU The SKU of this food product
     * @param description The description of this food product
     * @param category The category of this food product
     * @param price The price of this food product
     */

    public FoodProduct(String SKU, String description, String category, double price) {
        this.SKU = SKU;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    /**
     * <code> Getter</code>gets ID of food product.
     * @return ID.
     */
    public int getID() {
        return this.ID;
    }

    /**
     * <code> Setter</code> sets ID of food product.
     * @param ID ID of product.
     */

    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * <code> Getter </code> gets SKU of food product.
     * @return SKU
     */

    public String getSKU() {
        return this.SKU;
    }

    /**
     * <code> Setter </code> sets SKU of food product.
     * @param SKU SKU of product.
     */
    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    /**
     * <code> Getter </code> gets description of food product.
     * @return description of food product.
     */

    public String getDescription() {
        return this.description;
    }

    /**
     * <code> Setter </code> sets description of food product.
     * @param Description Description of food product.
     */

    public void setDescription(String Description) {
        this.description = Description;
    }

    /**
     * <code> Getter </code> gets category of food product.
     * @return category of food product.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * <code> Setter </code> sets category of food product.
     * @param Category Category of food product.
     */

    public void setCategory(String Category) {
        this.category = Category;
    }

    /**
     * <code> Getter </code> gets price of food product.
     * @return price of food product.
     */
    public double getPrice() {
        return this.price;
        }

    /**
     * <code> Setter </code> sets price of food product.
     * @param Price Price of food product.
     */
    public void setPrice(double Price) {
        this.price = Price;
        }

    /**
     * Converts <code> String </code> to <code> HTML String </code>.
     * @return HTML String.
     */

    public String toHTMLString() {
        return "<tr>" +
                "<td>" + getID() + "</td>" +
                "<td>" + getSKU() + "</td>" +
                "<td>" + getDescription() + "</td>" +
                "<td>" + getCategory() + "</td>" +
                "<td>" + getPrice() + "</td>" +
                "<td><a href =\"/editProduct?id=" + getID() + " \">  edit </a>" +
                "<td><a href=\"/deleteProduct?id=" + getID() + "\"> delete </a>" +
                "</tr>";
        }
    }
