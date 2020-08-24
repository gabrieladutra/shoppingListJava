package src;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductListServices {
    private final ProductListRepository repository;

    public ProductListServices(ProductListRepository repository) {
        this.repository = repository;
    }

    public void alterProductListName(String id, String name) {
        var productList = repository.getProductList(id);
        productList.setName(name);
        repository.updateProductsLists(productList);
    }

    public void deleteProduct(String id, String productName) {
        var productList = repository.getProductList(id);
        productList.deleteProduct(productName);
        repository.updateProductsLists(productList);
    }

    public void addProduct(String id, Product product) {
        var productList = repository.getProductList(id);
        productList.addProduct(product);
        repository.updateProductsLists(productList);
    }

    public void addProductList(String name) {
        var newProductList = new ProductList(name);
        repository.add(newProductList);
    }

    public ProductList findProductList(String id) {
        return repository.getProductList(id);
    }

    public void editProduct(String id, String name, Product product) {
        var productList = repository.getProductList(id);
        productList.editProduct(name, product);
        repository.updateProductsLists(productList);
    }

    public List<ProductList> searchProductList(String name) {
        return repository.searchProductList(name);
    }


}
