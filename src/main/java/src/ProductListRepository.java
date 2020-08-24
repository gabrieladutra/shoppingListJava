package src;

import java.io.*;

import java.util.ArrayList;

import java.util.List;
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
            String fileContent = allFile.toString();
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

    public ProductList getProductList(String id) {
        return productsLists.get(getIndexOfProductList(id));
    }

    public void updateProductsLists(ProductList updatedProductList) {
        productsLists.set(getIndexOfProductList(updatedProductList.getId()), updatedProductList);
        writeProductList();
    }

    public void deleteProductList(String id) {
        productsLists.remove(getIndexOfProductList(id));
        writeProductList();
    }


    private int getIndexOfProductList(String id) {
        for (int i = 0; i < productsLists.size(); i++) {
            if (productsLists.get(i).getId().equals(id)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Product List id :" + id + "not found");
    }

    public List<ProductList> searchProductList(String name){
        var searchedLists = new ArrayList<ProductList>();
        for(ProductList list : productsLists){
            if(list.getName().contains(name)){
                searchedLists.add(list);
            }
        }
        return  searchedLists;
    }
}
