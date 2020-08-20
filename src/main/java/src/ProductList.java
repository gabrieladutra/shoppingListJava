package src;

import java.util.ArrayList;
import java.util.UUID;

import static src.Product.*;

public class ProductList {
    private String name;
    private String id;
    private final ArrayList<Product> listOfProducts;

    public ProductList(String name, ArrayList<Product> listOfProducts) {
        this.id = UUID.randomUUID().toString();
        this.name = sanitizeName(name);
        this.listOfProducts = listOfProducts;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String newName) {

        name = newName;
    }


    private static String sanitizeName(String name) {
        return name.replace("\n", "").replace("$$", "").replace("}}", "");
    }

    public ArrayList<Product> getListOfProducts() {
        return listOfProducts;
    }

    public static String serializeProductList(ProductList productList) {
        if (productList == null) {
            throw new IllegalArgumentException("ProductList of argument is null");
        }
        Product product = new Product(0, "", 0d, false);
        return "ProductList{"
                + "\n$$name$$"
                + "#"
                + productList.getName()
                + "#"
                + ",\n$$listOfProducts$$"
                + "#"
                + product.serializeListOfProducts(productList.listOfProducts)
                + "#"
                + "}}\n";
    }

    public static String serializeListOfProductList(ArrayList<ProductList> productLists) {
        if (productLists == null) {
            throw new IllegalArgumentException("ProductList Array of  argument is null");
        }

        StringBuilder productString = new StringBuilder();
        for (ProductList productList : productLists) {
            productString.append(serializeProductList(productList));
        }
        return productString.toString();
    }

    public static ArrayList<ProductList> deserializeListOfProductList(String productString) {
        ArrayList<ProductList> list = new ArrayList<>();
        var split = productString.split("#}}#}}\n");
        for (String s : split) {
            var productList = deserializeProductList(s);
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
}
