package src;

import java.util.ArrayList;

import static src.Product.deserializeProduct;

public class ProductList {
  private final String name;
  private final ArrayList<Product> listOfProducts;

  public ProductList(String name, ArrayList<Product> listOfProducts) {
    this.name = sanitizeString(name);
    this.listOfProducts = listOfProducts;
  }

  public String getName() {
    return name;
  }

  private static String sanitizeString(String name) {
    return name.replace("\n", "").replace("$$", "").replace("}}", "");
  }

  public ArrayList<Product> getListOfProducts() {
    return listOfProducts;
  }

  public static String serializeProductList(ProductList productList) {
    if (productList == null) {
      throw new IllegalArgumentException("ProductList of argument is null");
    }
    return "ProductList{"
        + "\n$$name$$"
        + "#"
        + productList.getName()
        + "#"
        + ",\n$$listOfProducts$$"
        + "#"
        + Product.serializeProductList(productList.listOfProducts)
        + "#"
        + "}}";
  }

  public static ProductList deserializeProductList(String productListString) {
    if (productListString == null || productListString.equals("")) {
      throw new IllegalArgumentException("String of argument is null or empty");
    }
    String[] splitString = productListString.split("Product\\{");
    String[] names = splitString[0].substring("ProductList{\n $$name$$".length()).split("#,\n");
    String name = names[0];
    Product product;
    var listOfProduct = new ArrayList<Product>();
    for (int i = 1; i < splitString.length; i++) {
      product = deserializeProduct(splitString[i]);
      listOfProduct.add(product);
    }
    return new ProductList(name, listOfProduct);
  }

  @Override
  public String toString() {
    return "ProductList{" + "name='" + name + '\'' + ", listOfProducts=" + listOfProducts + '}';
  }
}
