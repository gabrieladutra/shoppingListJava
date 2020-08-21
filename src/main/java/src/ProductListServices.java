package src;

public class ProductListServices {
    private final Menu menu = new Menu();
    private final ProductListRepository repository = ProductListRepository.getInstance();

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



}
