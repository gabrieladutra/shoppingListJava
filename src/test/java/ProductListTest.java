import org.junit.jupiter.api.Test;
import src.Product;
import src.ProductList;
import java.util.ArrayList;

import static src.ProductList.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductListTest {

  @Test
  public void serializeProductList_whenAProductListIsValid_shouldReturnAString() {
    var productListStr =
        "ProductList{\n"
            + "$$name$$#Shopping#,\n"
            + "$$listOfProducts$$#\n"
            + "Product{\n"
            + "%%quantity%%#11#,\n"
            + "%%name%%#Ink#,\n"
            + "%%price%%#44.55#,\n"
            + "%%isPurchased%%#true#}}#}}\n";

    var product = new Product(11, "Ink", 44.55, true);
    var list = new ArrayList<Product>();
    list.add(product);
    var productList = new ProductList("Shopping", list);
    assertEquals(productListStr, serializeProductList(productList));
  }

  @Test
  public void serializeProductList_whenAProductIsNull_shouldReturnAnException() {
    assertThrows(IllegalArgumentException.class, () -> serializeProductList(null));
  }

  @Test
  public void deserializeProductList_whenAStringIsEmpty_shouldThrowAnException() {
    assertThrows(IllegalArgumentException.class, () -> deserializeProductList(""));
  }

  @Test
  public void deserializeProductList_whenAStringIsNull_shouldReturnAnException() {
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
    assertEquals("Monthly Shopping", deserializeProductList(serialized).getName());
    var deserializedList = deserializeProductList(serialized).getListOfProducts();
    for (int i = 0; i < products.size(); i++) {
      assertEquals(products.get(i).getQuantity(), deserializedList.get(i).getQuantity());
      assertEquals(products.get(i).getName(), deserializedList.get(i).getName());
      assertEquals(products.get(i).getPrice(), deserializedList.get(i).getPrice());
      assertEquals(products.get(i).getPurchased(), deserializedList.get(i).getPurchased());
    }
  }

  @Test
  public void serialize_whenAProductLisIsValid_shouldReturnAString() {
    var listsOfProductsStr =
        "ProductList{\n"
            + "$$name$$#weekly#,\n"
            + "$$listOfProducts$$#\n"
            + "Product{\n"
            + "%%quantity%%#1#,\n"
            + "%%name%%#pen#,\n"
            + "%%price%%#1.0#,\n"
            + "%%isPurchased%%#false#}}\n"
            + "Product{\n"
            + "%%quantity%%#2#,\n"
            + "%%name%%#notebook#,\n"
            + "%%price%%#1234.0#,\n"
            + "%%isPurchased%%#true#}}#}}\n"
            + "ProductList{\n"
            + "$$name$$#every month and week#,\n"
            + "$$listOfProducts$$#\n"
            + "Product{\n"
            + "%%quantity%%#1#,\n"
            + "%%name%%#pen#,\n"
            + "%%price%%#1.0#,\n"
            + "%%isPurchased%%#false#}}\n"
            + "Product{\n"
            + "%%quantity%%#2#,\n"
            + "%%name%%#notebook#,\n"
            + "%%price%%#1234.0#,\n"
            + "%%isPurchased%%#true#}}#}}\n";
    var array = new ArrayList<Product>();
    array.add(new Product(1, "pen", 1d, false));
    array.add(new Product(2, "notebook", 1234d, true));
    ArrayList<ProductList> list = new ArrayList<>();
    var list2 = new ProductList("every month and week", array);
    list.add(new ProductList("weekly", array));
    list.add(list2);
    assertEquals(listsOfProductsStr, serializeListOfProductList(list));
  }

  @Test
  public void deserialize_whenAStringIsNull_shouldThrowAnException() {
    assertThrows(IllegalArgumentException.class, () -> deserializeProductList(null));
  }

  @Test
  public void deserialize_whenAStringIsEmpty_shouldThrowAnException() {
    assertThrows(IllegalArgumentException.class, () -> deserializeProductList(""));
  }

  @Test
  public void deserialize_whenAStringIsValid_shouldReturnAListOfProductList() {

    var firstProduct = new Product(4, "Book", 234d, false);
    var secondProduct = new Product(4, "Magazine", 4d, true);

    var productArrayList = new ArrayList<Product>();
    productArrayList.add(firstProduct);
    productArrayList.add(secondProduct);

    var productList = new ProductList("january", productArrayList);
    var productList2 = new ProductList("february", productArrayList);

    var listOfProductList = new ArrayList<ProductList>();
    listOfProductList.add(productList);
    listOfProductList.add(productList2);

    var serialized = serializeListOfProductList(listOfProductList);
    var deserialized = deserializeListOfProductList(serialized);
    assertEquals("january", deserialized.get(0).getName());
    assertEquals(4, deserialized.get(0).getListOfProducts().get(0).getQuantity());
    assertEquals("Book", deserialized.get(0).getListOfProducts().get(0).getName());
    assertEquals(234d, deserialized.get(0).getListOfProducts().get(0).getPrice());
    assertEquals(false, deserialized.get(0).getListOfProducts().get(0).getPurchased());
  }
}
