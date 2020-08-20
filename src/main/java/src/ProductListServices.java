package src;

public class ProductListServices {

    public void alterProductListName() {
        var menu = new Menu();
        System.out.println("List Id: ");
        Integer id  = menu.readOption();
        ProductListRepository.getInstance().alterListName(id);
    }

}
