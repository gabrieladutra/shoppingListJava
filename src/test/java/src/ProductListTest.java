package src;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static src.ProductList.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductListTest {

    @Test
    public void serializeProductList_whenAProductListIsValid_shouldReturnAString() {
        var product = new Product(11, "Ink", 44.55, true);
        var list = new ArrayList<Product>();
        list.add(product);
        var productList = new ProductList("Shopping", list);

        var productListStr =
                "ProductList{\n" +
                        "$$name$$#Shopping#,\n" +
                        "$$id$$#" + productList.getId() + "#,\n" +
                        "$$listOfProducts$$#\n" +
                        "Product{\n" +
                        "%%quantity%%#11#,\n" +
                        "%%name%%#Ink#,\n" +
                        "%%price%%#44.55#,\n" +
                        "%%isPurchased%%#true#}}#}}\n";

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
        var products = new ArrayList<Product>();
        products.add(new Product(1, "soda", 2.23, true));
        var productList = new ProductList("Shopping", products);
        String str =
                "ProductList{\n"
                        + "$$name$$#Shopping#,\n"
                        + "$$id$$#" + productList.getId() + "#,\n"
                        + "$$listOfProducts$$#\n"
                        + "Product{\n"
                        + "%%quantity%%#1#,\n"
                        + "%%name%%#soda#,\n"
                        + "%%price%%#2.23#,\n"
                        + "%%isPurchased%%#true#}}#}}";
        assertEquals("Shopping", deserializeProductList(str).getName());
        assertEquals(productList.getId(), deserializeProductList(str).getId());
        var deserializedList = deserializeProductList(str).getProducts();
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
        var deserializedList = deserializeProductList(serialized).getProducts();
        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i).getQuantity(), deserializedList.get(i).getQuantity());
            assertEquals(products.get(i).getName(), deserializedList.get(i).getName());
            assertEquals(products.get(i).getPrice(), deserializedList.get(i).getPrice());
            assertEquals(products.get(i).getPurchased(), deserializedList.get(i).getPurchased());
        }
    }

    @Test
    public void serializeListOfProductList_whenAProductLisIsValid_shouldReturnAString() {
        var productArray = new ArrayList<Product>();
        productArray.add(new Product(1, "pen", 1d, false));
        productArray.add(new Product(2, "notebook", 1234d, true));

        ArrayList<ProductList> listOfProductList = new ArrayList<>();
        var productList = new ProductList("every month and week", productArray);
        listOfProductList.add(new ProductList("weekly", productArray));
        listOfProductList.add(productList);
        var serialized = serializeListOfProductList(listOfProductList);
        var listsOfProductsStr =
                "ProductList{\n" +
                        "$$name$$#weekly#,\n" +
                        "$$id$$#" + listOfProductList.get(0).getId() + "#,\n" +
                        "$$listOfProducts$$#\n" +
                        "Product{\n" +
                        "%%quantity%%#1#,\n" +
                        "%%name%%#pen#,\n" +
                        "%%price%%#1.0#,\n" +
                        "%%isPurchased%%#false#}}\n" +
                        "Product{\n" +
                        "%%quantity%%#2#,\n" +
                        "%%name%%#notebook#,\n" +
                        "%%price%%#1234.0#,\n" +
                        "%%isPurchased%%#true#}}#}}\n" +
                        "ProductList{\n" +
                        "$$name$$#every month and week#,\n" +
                        "$$id$$#" + listOfProductList.get(1).getId() + "#,\n" +
                        "$$listOfProducts$$#\n" +
                        "Product{\n" +
                        "%%quantity%%#1#,\n" +
                        "%%name%%#pen#,\n" +
                        "%%price%%#1.0#,\n" +
                        "%%isPurchased%%#false#}}\n" +
                        "Product{\n" +
                        "%%quantity%%#2#,\n" +
                        "%%name%%#notebook#,\n" +
                        "%%price%%#1234.0#,\n" +
                        "%%isPurchased%%#true#}}#}}\n";
        assertEquals(listsOfProductsStr, serialized);
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
        assertEquals(4, deserialized.get(0).getProducts().get(0).getQuantity());
        assertEquals("Book", deserialized.get(0).getProducts().get(0).getName());
        assertEquals(234d, deserialized.get(0).getProducts().get(0).getPrice());
        assertEquals(false, deserialized.get(0).getProducts().get(0).getPurchased());
    }

    @Test
    public void deleteProduct_whenProductNameIsValid_shouldDeleteAProduct() {
        var productToDelete = new Product(4, "Magazine", 4d, true);
        var productArrayList = new ArrayList<Product>();
        productArrayList.add(productToDelete);
        var productList = new ProductList("january", productArrayList);

        productList.deleteProduct("Magazine");

        for (Product currentProduct : productArrayList) {
            if (currentProduct.getName().equals("Magazine")) {
                fail("Product not deleted");
            }
        }
    }

    @Test
    public void deleteProductList_whenProductListIsValid_shouldDeleteAProductList() {
        var productList = new ProductList("my shops");
        var productsLists = new ArrayList<ProductList>();
        productsLists.add(productList);
        ProductListRepository.getInstance().deleteProductList(productList.getId());
        for (ProductList currentProduct : productsLists) {
            if (currentProduct.getName().equals("my shops")) {
                fail("Product not deleted");
            }
        }
    }

    @Test
    public void addProduct_whenProductNameIsValid_shouldAddAProduct() {
        var productToAdd = new Product(4, "Magazine", 4d, true);
        var productList = new ProductList("january");

        productList.addProduct(productToAdd);

        for (Product currentProduct : productList.getProducts()) {
            if (currentProduct.getName().equals("Magazine")) {
                assertEquals(productToAdd, currentProduct);
                return;
            }
        }
        fail("Product not added");
    }

    @Test
    public void alterListName_whenProductNameIsValid_shouldAlterProductListName() {
        var productList = new ProductList("january");
        productList.setName("new name");
        assertEquals("new name", productList.getName());

    }

    @Test
    public void alterListName_whenProductNameIsNull_shouldThrowAnException() {
        var productList = new ProductList("list");
        assertThrows(IllegalArgumentException.class, () -> productList.setName(null));
    }

    @Test
    public void alterListName_whenProductNameIsEmpty_shouldThrowAnException() {
        var productList = new ProductList("list");
        assertThrows(IllegalArgumentException.class, () -> productList.setName(""));
    }


}
