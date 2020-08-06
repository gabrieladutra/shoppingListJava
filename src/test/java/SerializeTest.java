import org.junit.jupiter.api.Test;
import src.Product;
import src.ProductList;

import java.util.ArrayList;

import static src.Product.*;

import static org.junit.jupiter.api.Assertions.*;

public class SerializeTest {
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

        Product product = new Product(10, "Red bull 50% energitico te da asas\n yeah", 4.00, true);
        String productString = serializeProduct(product);
        Product deserializeProduct = deserializeProduct(productString);
        assertEquals(product.getQuantity(), deserializeProduct.getQuantity());
        assertEquals(product.getName(), deserializeProduct.getName());
        assertEquals(product.getPrice(), deserializeProduct.getPrice());
        assertTrue(deserializeProduct.getPurchased());
    }

    @Test
    public void serializeProductList_whenAListIsValid_shouldReturnAString() {

    }

    @Test
    public void deserializeProductList_whenAStringIsValid_shouldReturnAProductList() {

        ArrayList<Product> products = new ArrayList<>();
        Product p1 = new Product(1, "Chocolate 80%", 2.00, true);
        Product p2 = new Product(1, "Coca-cola 50%", 5.00, true);
        Product p3 = new Product(2, "Orange", 5.00, true);
        products.add(0, p1);
        products.add(1, p2);
        products.add(2, p3);
        assertEquals(products, deserializeProductList(serializeProductList(products)));
    }
}
