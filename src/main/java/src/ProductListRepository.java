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

    private void writeProductList(ArrayList<ProductList> productList) {
        try (
                FileWriter myWriter = new FileWriter("allProducts.gbd")) {
            var serialized = serializeListOfProductList(productList);
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

    public void add(ProductList productList) throws IOException {
        products.add(productList);
        writeProductList(products);

    }

    public List<ProductList> getProductList() {
        return products;
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

    public ArrayList<ProductList> getMany(){
        return products;
    }

    public void delete(String name) {
        for (int i = 0; i < products.size(); i++) {
            ProductList currentProduct = products.get(i);
            if (currentProduct.getName().equals(name))
                products.remove(currentProduct);
        }
        writeProductList(products);
    }

    public void alterListName(Integer id) {
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
        writeProductList(products);
    }

}
