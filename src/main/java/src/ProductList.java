package src;

import java.util.ArrayList;
import java.util.UUID;

import static src.Product.*;

public class ProductList {
    private String name;
    private final String id;
    private final ArrayList<Product> listOfProducts;

    public ProductList(String name, ArrayList<Product> listOfProducts) {
        this.id = UUID.randomUUID().toString();
        this.name = sanitizeName(name);
        this.listOfProducts = listOfProducts;
    }

    public ProductList(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.listOfProducts = new ArrayList<>();
    }

    private ProductList(String name, String id, ArrayList<Product> listOfProducts) {
        if (id == null) {
            throw new IllegalArgumentException("The id is null");
        }
        this.name = sanitizeName(name);
        this.id = id;
        this.listOfProducts = listOfProducts;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String newName) {
        if(newName == null || newName.equals("")){
            throw new IllegalArgumentException("Name is empty or null");
        }

        name = newName;
    }


    private static String sanitizeName(String name) {
        return name.replace("\n", "").replace("$$", "").replace("}}", "");
    }

    public ArrayList<Product> getProducts() {
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
                + ",\n$$id$$#"
                + productList.getId() + "#"
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
        var productListProperty = splitString[0].substring("ProductList{\n $$name$$".length()).split("#,\n");
        String name = productListProperty[0];
        String id = productListProperty[1].substring("$$id$$#".length());
        Product product;
        var listOfProduct = new ArrayList<Product>();
        for (int i = 1; i < splitString.length; i++) {
            product = deserializeProduct(splitString[i]);
            listOfProduct.add(product);
        }
        return new ProductList(name, id, listOfProduct);
    }

    @Override
    public String toString() {
        return "ProductList{" + "name='" + name + '\'' + ", listOfProducts=" + listOfProducts + '}';
    }

    public void deleteProduct(String productName) {
        for (Product currentProduct : listOfProducts) {
            if (currentProduct.getName().equals(productName)) {
                listOfProducts.remove(currentProduct);
                return;
            }
        }
        throw new IllegalArgumentException("product not found");
    }

    public void addProduct(Product product) {
        listOfProducts.add(product);
    }

}
