package src;

import java.io.Serializable;
import java.util.ArrayList;

import static src.Product.*;


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

    public String serializeProductList() {
        String[] stringList = new String[listOfProducts.size()];
        String productString = "";
        for (int i = 0; i < listOfProducts.size(); i++) {
            stringList[i] = serializeProduct(listOfProducts.get(i));
            productString += stringList[i];
        }
        return productString;

    }
    public  ArrayList<Product> deserializeProductList(String productString){
        String[] splitString = productString.split("}}");
        for(int i = 0; i < splitString.length;i++){
            Product deserializeProduct = deserializeProduct(splitString[i]);
            listOfProducts.add(deserializeProduct);
        }
        return listOfProducts;
    }


}
