package src;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var repository = ProductListRepository.getInstance();
        var services = new ProductListServices();
        Menu menu = new Menu();
        Integer option  = null;

        while (option == null || option != 0) {
            menu.display();
            option = menu.readOption();

            switch (option) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:

                    break;
                case 3:
                    services.alterProductListName();
                    break;
            }
        }
    }
}
