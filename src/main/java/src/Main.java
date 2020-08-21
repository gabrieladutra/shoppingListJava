package src;

import java.util.ArrayList;

import static src.ProductListRepository.getInstance;

public class Main {
    public static void main(String[] args) {
        var repository = getInstance();
        var services = new ProductListServices();
        Menu menu = new Menu();
        Integer option = null;

        while (option == null || option != 0) {
            menu.display();
            option = menu.readOption();


        }
    }
}
