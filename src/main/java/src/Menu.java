package src;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);

    public void display() {
        System.out.println("Choose an option:");
        System.out.println("0- Exit");
        System.out.println("1- Add a new List");
        System.out.println("2- Search a List");
        System.out.println("3- Edit a List");
        System.out.println("4- Delete a List");
        System.out.println("5- Recover 3 more accessible lists");
        System.out.println("6- Add a Product in a List");
        System.out.println("7- Delete a Product in a List");
        System.out.println("8- Edit a product in a List");
    }

    public Integer readOption() {
        return scanner.nextInt();
    }

    public String readString() {
        return scanner.nextLine();
    }

    public Double readDouble() {
        return scanner.nextDouble();
    }
}


