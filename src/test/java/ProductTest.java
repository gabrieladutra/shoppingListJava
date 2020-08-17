import org.junit.jupiter.api.Test;
import src.Product;
import src.ProductList;

import java.util.ArrayList;

import static src.Product.*;

import static org.junit.jupiter.api.Assertions.*;
import static src.ProductList.deserializeProductList;

public class ProductTest {
  @Test
  public void serializeProduct_whenAProductIsValid_shouldReturnAString() {
    var productString =
        "\nProduct{\n"
            + "%%quantity%%#2#,\n"
            + "%%name%%#soap#,\n"
            + "%%price%%#0.99#,\n"
            + "%%isPurchased%%#true#}}";

    var product = new Product(2, "soap", 0.99, true);
    assertEquals(productString, serializeProduct(product));
  }

  @Test
  public void deserializeProduct_whenAStringIsValid_shouldReturnAProduct() {

    Product product = new Product(10, "Apple", 4.00, true);
    String productString = serializeProduct(product);
    Product deserializeProduct = deserializeProduct(productString);
    assertEquals(product.getQuantity(), deserializeProduct.getQuantity());
    assertEquals(product.getName(), deserializeProduct.getName());
    assertEquals(product.getPrice(), deserializeProduct.getPrice());
    assertTrue(deserializeProduct.getPurchased());
  }

  @Test
  public void deserializeProduct_whenAStringHasBreakLine_shouldReturnAProduct() {

    Product product = new Product(10, "Red bull 50% \n yeah", 4.00, true);
    String productString = serializeProduct(product);
    Product deserializeProduct = deserializeProduct(productString);
    assertEquals(product.getQuantity(), deserializeProduct.getQuantity());
    assertEquals(product.getName(), deserializeProduct.getName());
    assertEquals(product.getPrice(), deserializeProduct.getPrice());
    assertTrue(deserializeProduct.getPurchased());
  }

  @Test
  public void serializeListOfProducts_whenAListIsValid_shouldReturnAString() {
    var productString =
        "\nProduct{\n"
            + "%%quantity%%#2#,\n"
            + "%%name%%#soap#,\n"
            + "%%price%%#0.99#,\n"
            + "%%isPurchased%%#true#}}";
    var product = new Product(2, "soap", 0.99, true);
    var productList = new ArrayList<Product>();
    productList.add(product);
    var serializedProduct = serializeListOfProducts(productList);
    assertEquals(productString, serializedProduct);
  }

  @Test
  public void deserializeListOfProducts_whenAListIsValid_shouldReturnAProduct() {
    var product = new Product(5, "computer \n last generation $$i5$$ ", 4567.99, false);
    var productList = new ArrayList<Product>();
    productList.add(product);
    var serialized = serializeListOfProducts(productList);
    for (int i = 0; i < productList.size(); i++) {
      assertEquals(product.getQuantity(), deserializeListsOfProducts(serialized).get(i).getQuantity());
      assertEquals(product.getName(), deserializeListsOfProducts(serialized).get(i).getName());
      assertEquals(product.getPrice(), deserializeListsOfProducts(serialized).get(i).getPrice());
      assertEquals(
          product.getPurchased(), deserializeListsOfProducts(serialized).get(i).getPurchased());
    }
  }
}
