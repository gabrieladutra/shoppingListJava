package src;

import java.io.*;

import java.util.ArrayList;

import java.util.Scanner;
import java.util.StringJoiner;

import static src.ProductList.deserializeListOfProductList;
import static src.ProductList.serializeListOfProductList;

public class ProductListRepository {
    private static ProductListRepository repositoryInstance;
    private final ArrayList<ProductList> productsLists = readProductList();

    private ProductListRepository() {

    }

    private ArrayList<ProductList> readProductList() {
        File productsFile = new File("allProducts.gbd");
        try (Scanner reader = new Scanner(productsFile)) {
            StringJoiner allFile = new StringJoiner("\n");

            while (reader.hasNextLine()) {

                String nextLine = reader.nextLine();
                allFile.add(nextLine);
            }
            return deserializeListOfProductList(allFile.toString());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void writeProductList() {
        try (
                FileWriter myWriter = new FileWriter("allProducts.gbd")) {
            var serialized = serializeListOfProductList(productsLists);
            myWriter.write(serialized);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static ProductListRepository getInstance() {
        if (repositoryInstance == null) {
            repositoryInstance = new ProductListRepository();
        }
        return repositoryInstance;
    }

    public void add(ProductList productList) {
        productsLists.add(productList);
        writeProductList();

    }


    public ProductList getItem(String name) {
        for (ProductList currentObject : productsLists) {
            if (currentObject.getName().contains(name)) {
                return currentObject;
            }
        }
        return null;
    }

    public Integer getSize() {
        return productsLists.size();

    }

    public ProductList getProductList(String id) {
        for (ProductList productList : productsLists) {
            if (productList.getId().equals(id)) {
                return productList;
            }
        }
        throw new IllegalArgumentException("Product List id not found");
    }

    public void updateProductsLists(ProductList updatedProductList) {
        for (var i = 0; i < productsLists.size(); i++) {
            var currentProductList = productsLists.get(i);
            if (currentProductList.getId().equals(updatedProductList.getId())) {
                productsLists.set(i, updatedProductList);
                writeProductList();
                return;
            }
        }
        throw new IllegalArgumentException("Product List not found");
    }

}
