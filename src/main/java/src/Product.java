package src;

import java.util.ArrayList;
import static src.Validator.*;
public class Product {
  private Integer quantity;
  private String name;
  private Double price;
  private Boolean isPurchased;

  public Product(int quantity, String name, Double price, Boolean isPurchased) {
    validateNumberIsPositive(quantity);
    this.quantity = quantity;
    this.name = sanitizeName(name);
    this.price = price;
    this.isPurchased = isPurchased;
  }

  private static String sanitizeName(String name) {
    return name.replace("\n", "").replace("%%", "").replace("}}", "");
  }

  public int getQuantity() {
    return quantity;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  public Boolean getPurchased() {
    return isPurchased;
  }

  public void setQuantity(Integer newQuantity){
    quantity = newQuantity;
  }

  public void setName(String newName){

    name = newName;
  }

  public void setPrice (Double newPrice){

    price = newPrice;
  }

  public void setIsPurchased(boolean newIsPurchased){
    isPurchased = newIsPurchased;
  }

  @Override
  public String toString() {
    return "Product{"
        + "quantity="
        + quantity
        + ", name='"
        + name
        + '\''
        + ", price="
        + price
        + ", isPurchased="
        + isPurchased
        + '}';
  }

  public String serializeProduct(Product product) {
    return "\nProduct{"
        + "\n%%quantity%%"
        + "#"
        + product.getQuantity()
        + "#"
        + ",\n%%name%%"
        + "#"
        + product.getName()
        + "#"
        + ",\n%%price%%"
        + "#"
        + product.getPrice()
        + "#"
        + ",\n%%isPurchased%%"
        + "#"
        + product.getPurchased()
        + "#"
        + "}}";
  }

  public static Product deserializeProduct(String productString) {
    int quantity = 0;
    String name = "";
    double price = 0;
    boolean isPurchased = false;
    String[] splitString = productString.split("\n");
    for (int i = 0; i < splitString.length; i++) {
      splitString[i] = splitString[i].replace("#,", "").replace("#}}", "");
      if (splitString[i].contains("%%quantity%%")) {
        quantity = Integer.parseInt(splitString[i].substring(13));
      } else if (splitString[i].contains("%%name%%")) {
        name = splitString[i].substring(9);
      } else if (splitString[i].contains("%%price%%")) {
        price = Double.parseDouble(splitString[i].substring(10));
      } else if (splitString[i].contains("%%isPurchased%%")) {
        isPurchased = Boolean.parseBoolean(splitString[i].substring(16));
      }
    }
    return new Product(quantity, name, price, isPurchased);
  }

  public String serializeListOfProducts(ArrayList<Product> productList) {
    String[] stringList = new String[productList.size()];
    StringBuilder productString = new StringBuilder();
    for (int i = 0; i < productList.size(); i++) {
      stringList[i] = productList.get(i).serializeProduct(productList.get(i));
      productString.append(stringList[i]);
    }
    return productString.toString();
  }

  public static ArrayList<Product> deserializeListsOfProducts(String productString) {
    String[] splitString = productString.split("}}");
    ArrayList<Product> productList = new ArrayList<>();
    for (String s : splitString) {
      Product deserializeProduct = deserializeProduct(s);
      productList.add(deserializeProduct);
    }
    return productList;
  }
}
