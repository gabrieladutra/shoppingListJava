package src;

public class ProductListServices {
    Menu menu = new Menu();
    public void alterProductListName() {
        System.out.println("List Id: ");
        String id  = menu.readString();
        ProductListRepository.getInstance().alterListName(id);
    }

    public void deleteProduct(String id, String productName){
        var repository = ProductListRepository.getInstance();
        var productList = repository.getProductList(id);
        productList.deleteProduct(productName);
        repository.updateProducts(productList);
    }

    public void addProduct(String id){
        var repository = ProductListRepository.getInstance();
        var productList = repository.getProductList(id);
    }
}
