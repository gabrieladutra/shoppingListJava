import org.junit.jupiter.api.Test;
import src.Product;
import java.util.ArrayList;

import static src.Product.*;

import static org.junit.jupiter.api.Assertions.*;
import static src.ProductList.deserializeProductList;

public class ProductTest {
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
  public void serializeProductList_whenAListIsValid_shouldReturnAString() {
    var productString =
        "Product{\n"
            + "%%quantity%%#2#,\n"
            + "%%name%%#soap#,\n"
            + "%%price%%#0.99#,\n"
            + "%%isPurchased%%#true#}}";
    var product = new Product(2, "soap", 0.99, true);
    var productList = new ArrayList<Product>();
    productList.add(product);
    var serializedProduct = serializeProductList(productList);
    assertEquals(productString, serializedProduct);
  }

  @Test
  public void deserializeProductList_whenAStringIsValid_shouldReturnAProductList() {
    String str =
        "ProductList{\n"
            + "$$name$$#Shopping#,\n"
            + "$$listOfProducts$$#\n"
            + "Product{\n"
            + "%%quantity%%#1#,\n"
            + "%%name%%#soda#,\n"
            + "%%price%%#2.23#,\n"
            + "%%isPurchased%%#true#}}#}}";
    ArrayList<Product> products = new ArrayList<>();
    products.add(new Product(1, "soda", 2.23, true));
    assertEquals("Shopping", deserializeProductList(str).getName());
    var deserializedList = deserializeProductList(str).getListOfProducts();
    for (int i = 0; i < products.size(); i++) {
      assertEquals(products.get(i).getQuantity(), deserializedList.get(i).getQuantity());
      assertEquals(products.get(i).getName(), deserializedList.get(i).getName());
      assertEquals(products.get(i).getPrice(), deserializedList.get(i).getPrice());
      assertEquals(products.get(i).getPurchased(), deserializedList.get(i).getPurchased());
    }
  }
}
