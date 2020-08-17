package src;

import java.util.ArrayList;

import static src.Product.*;

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
        + Product.serializeListOfProducts(productList.listOfProducts)
        + "#"
        + "}}\n";
  }

  public static String serialize(ArrayList<ProductList> productLists) {
    if (productLists == null) {
      throw new IllegalArgumentException("ProductList Array of  argument is null");
    }
    String[] stringList = new String[productLists.size()];
    StringBuilder productString = new StringBuilder();
    for (int i = 0; i < productLists.size(); i++) {
      stringList[i] = serializeProductList(productLists.get(i));
      productString.append(stringList[i]);
    }
    return productString.toString();
  }
  //
  public static ArrayList<ProductList> deserialize(String productString) {
    ArrayList<ProductList> list = new ArrayList<>();
    var split = productString.split("#}}#}}\n");
    for (int i = 0; i < split.length; i++) {
      var productList = deserializeProductList(split[i]);
      list.add(productList);
    }
    return list;
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

  public static void main(String[] args) {
    var array = new ArrayList<Product>();
    array.add(new Product(1, "pen", 1d, false));
    array.add(new Product(2, "mouse", 80d, true));
    array.add(new Product(3, "watch", 900d, true));
    ArrayList<ProductList> list = new ArrayList<>();
    var list2 = new ProductList("semanalmente", array);
    list.add(new ProductList("weekly", array));
    list.add(list2);
    System.out.println(serialize(list));
    System.out.println(deserialize(serialize(list)));
  }
}
