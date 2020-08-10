
package src;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        Product p = new Product(1, "monitor", 300d, false);
        ArrayList<Product> products = new ArrayList<>();
        products.add(p);
        ProductList list = new ProductList("week shopping",products);
    }}
