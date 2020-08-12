import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.Product;
import src.ProductList;
import java.util.ArrayList;

import static src.Product.deserializeProduct;
import static src.ProductList.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductListTest {
  @Test
  public void deserializeProduct_whenAStringIsValid_shouldReturnAProduct() {
    String product =
        "%%quantity%%#1#,\n"
            + "%%name%%#monitor#,\n"
            + "%%price%%#300.0#,\n"
            + "%%isPurchased%%#false#}}";
    Product p1 = deserializeProduct(product);
    assertEquals(1, p1.getQuantity());
    assertEquals("monitor", p1.getName());
    assertEquals(300d, p1.getPrice());
    assertEquals(false, p1.getPurchased());
  }

  @Test
  public void serializeProductList_whenAProductListIsValid_ShouldReturnAString() {
    var productListStr =
        "ProductList{\n"
            + "$$name$$#Shopping#,\n"
            + "$$listOfProducts$$#\n"
            + "Product{\n"
            + "%%quantity%%#11#,\n"
            + "%%name%%#Ink#,\n"
            + "%%price%%#44.55#,\n"
            + "%%isPurchased%%#true#}}#}}";

    var product = new Product(11, "Ink", 44.55, true);
    var list = new ArrayList<Product>();
    list.add(product);
    var productList = new ProductList("Shopping", list);
    assertEquals(productListStr, serializeProductList(productList));
  }

  @Test
  public void serializeProductList_whenAProductIsNull_ShouldReturnAnException() {
    assertThrows(IllegalArgumentException.class, () -> serializeProductList(null));
  }

  @Test
  public void deserializeProductList_whenAStringIsEmpty_ShouldThrowAnException() {
    assertThrows(IllegalArgumentException.class, () -> deserializeProductList(""));
  }

  @Test
  public void deserializeProductList_whenAStringIsNull_ShouldReturnAnException() {
    assertThrows(IllegalArgumentException.class, () -> deserializeProductList(null));
  }

  @Test
  public void deserializeProductList_whenAStringIsValid_shouldReturnProductList() {
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

  @Test
  public void deserializeProductList_whenAStringIsValid_shouldReturnProductList2() {
    var product = new Product(1, "soda", 2.23, true);
    var secondProduct = new Product(3, "fork", 21.23, false);
    ArrayList<Product> products = new ArrayList<>();
    products.add(product);
    products.add(secondProduct);
    var productList = new ProductList("Monthly Shopping", products);
    var serialized = serializeProductList(productList);
    assertEquals("Shopping", deserializeProductList(serialized).getName());
    var deserializedList = deserializeProductList(serialized).getListOfProducts();
    for (int i = 0; i < products.size(); i++) {
      assertEquals(products.get(i).getQuantity(), deserializedList.get(i).getQuantity());
      assertEquals(products.get(i).getName(), deserializedList.get(i).getName());
      assertEquals(products.get(i).getPrice(), deserializedList.get(i).getPrice());
      assertEquals(products.get(i).getPurchased(), deserializedList.get(i).getPurchased());
    }
  }
}
