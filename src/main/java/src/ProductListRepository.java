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
    private final ArrayList<ProductList> products = readProductList();

    public ProductListRepository() {

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
            var serialized = serializeListOfProductList(products);
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

    public void add(ProductList productList)  {
        products.add(productList);
        writeProductList();

    }


    public ProductList getItem(String name) {
        for (ProductList currentObject : products) {
            if (currentObject.getName().contains(name)) {
                return currentObject;
            }
        }
        return null;
    }

    public Integer getSize() {
        return products.size();

    }

    public ProductList getProductList(String id){
        for (ProductList productList : products) {
            if (productList.getId().equals(id)) {
                return productList;
            }
        }
        throw new IllegalArgumentException("Product List id not found");
    }
    public void updateProducts(ProductList updatedProductList){
        for (ProductList currentProducts : products) {
            if (currentProducts.getId().equals(updatedProductList.getId())) {
                currentProducts = updatedProductList;
                writeProductList();
            }
        }
        throw new IllegalArgumentException("Product List not found");
    }

    public void alterListName(String id) {
        for (ProductList currentProduct : products) {
            if (currentProduct.getId().equals(id)) {
                Menu menu = new Menu();
                System.out.println("Name" + "[" + currentProduct.getName() + "] = ");
                String name = menu.readString();
                if (!name.equals("")) {
                    currentProduct.setName(name);
                }
            }
        }
        writeProductList();
    }

}
