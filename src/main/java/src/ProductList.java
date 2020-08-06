package src;

import java.util.ArrayList;

public class ProductList {
    private final String name;
    private ArrayList<Product> listOfProducts;

    public ProductList(String name, ArrayList<Product> listOfProducts) {
        this.name = name;
        this.listOfProducts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getListOfProducts() {
        return listOfProducts;
    }
}
