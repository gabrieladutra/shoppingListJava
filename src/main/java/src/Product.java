package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Product implements java.io.Serializable {
    private Integer quantity;
    private String name;
    private Double price;
    private Boolean isPurchased;


    public Product(int quantity, String name, Double price, Boolean isPurchased) {

        this.quantity = quantity;
        this.name = sanitizeString(name);
        this.price = price;
        this.isPurchased = isPurchased;
    }

    private static String sanitizeString(String name) {
        return name.replace("\n", "").replace("%%", "").replace("}}", "");
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getPurchased() {
        return isPurchased;
    }

    @Override
    public String toString() {
        return "Product{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isPurchased=" + isPurchased +
                '}';
    }

    public static String serializeProduct(Product product) {
        return "\nProduct{" +
                "\n%%quantity%%" + "#" + product.getQuantity() + "#" +
                ",\n%%name%%" + "#" + product.getName() + "#" +
                ",\n%%price%%" + "#" + product.getPrice() + "#" +
                ",\n%%isPurchased%%" + "#" + product.getPurchased() + "#" +
                "}}";
    }

    public static Product deserializeProduct(String productString) {
        int quantity = 0;
        String name = "";
        double price = 0d;
        boolean isPurchased = false;
        String[] splitString = productString.split("\n");
        for (int i = 0; i < splitString.length; i++) {
            splitString[i] = splitString[i].replace("#,", "").replace("#}}", "");
            if (splitString[i].contains("%%quantity%%")) {
                quantity = Integer.parseInt(splitString[i].substring(13));
            } else if (splitString[i].contains("%%name%%")) {
                name = splitString[i].substring(9);
            } else if (splitString[i].contains("%%price%%")) {
                price = Double.parseDouble(splitString[i].substring(10));
            } else if (splitString[i].contains("%%isPurchased%%")) {
                isPurchased = Boolean.parseBoolean(splitString[i].substring(16));
            }
        }
        return new Product(quantity, name, price, isPurchased);

    }

    public static String serializeProductList(ArrayList<Product> productList) {
        String[] stringList = new String[productList.size()];
        String productString = "";
        for (int i = 0; i < productList.size(); i++) {
            stringList[i] = serializeProduct(productList.get(i));
            productString += stringList[i];
        }
        return productString;
    }

    public static ArrayList<Product> deserializeProductList(String productString){
        String[] splitString = productString.split("}}");
        ArrayList<Product> productList = new ArrayList<>();
        for(int i = 0; i < splitString.length;i++){
          Product deserializeProduct = deserializeProduct(splitString[i]);
            productList.add(deserializeProduct);
        }
        return productList;
    }
    public static void splitProductString(String productString){
        String[] productArray;
        String str = "";
        while(productString.indexOf(productString)!= 1){

        }

    }
    

    public static void main(String[] args) {

        Product p = new Product(10, "Potatoes", 12.00, true);
        String result = serializeProduct(p);
        System.out.println(result);
        System.out.println(deserializeProduct(result));
        System.out.println("---------------");
        ArrayList<Product> array = new ArrayList<>();
        array.add(0, p);
        array.add(1, new Product(1, "Coca-cola 50%", 5.00, true));
        array.add(2, new Product(2, "Orange", 5.00, true));
        System.out.println(array);
        System.out.println(serializeProductList(array));
        splitProductString(result);
      System.out.println("+++++++++++++++++++");
     System.out.println(deserializeProductList(serializeProductList(array)));
    }
}
