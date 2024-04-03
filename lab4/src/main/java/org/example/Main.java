package org.example;
import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        DatabaseManager manager = new DatabaseManager();
        Tower tower1 = new Tower("OliwaStar", 29, null);
        Mage mage1 = new Mage("kempix", 22, tower1);
        Mage mage2 = new Mage("olafix", 17, tower1);

        Tower tower2 = new Tower("Eiffel", 32, null);
        Mage mage3 = new Mage("zelipapou", 32, tower2);
        Mage mage4 = new Mage("ute", 10, tower2);
        Mage mage5 = new Mage("zidane", 50, tower2);
        manager.insertTower(tower1);
        manager.insertTower(tower2);
        Scanner scanner = new Scanner(System.in);

        Boolean finish = false;
        String input = "";
        while (finish == false)
        {
            System.out.print("Enter '1' to add new Mage\n");
            System.out.print("Enter '2' to add new Tower\n");
            System.out.print("Enter '3' to delete Mage\n");
            System.out.print("Enter '4' to delete Tower\n");
            System.out.print("Enter '5' to print everything\n");
            System.out.print("Enter '6' to print queries\n");
            System.out.print("Enter 'x' to end program\n");
            System.out.print("Enter command: ");
            input = scanner.nextLine();
            switch(input){
                case "x":
                    finish = true;
                    break;
                case "1":
                    System.out.print("Enter the name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter the level: ");
                    int power = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the tower name: ");
                    String towerName = scanner.nextLine();
                    Mage mage = new Mage(name, power, manager.getTower(towerName));
                    manager.insertMage(mage);
                    break;
                case "2":
                    System.out.print("Enter the name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter the height: ");
                    int height = scanner.nextInt();
                    scanner.nextLine();
                    Tower tower = new Tower(name, height, null);
                    manager.insertTower(tower);
                    break;
                case "3":
                    System.out.print("Enter the name: ");
                    name = scanner.nextLine();
                    manager.removeMage(name);
                    break;
                case "4":
                    System.out.print("Enter the name: ");
                    name = scanner.nextLine();
                    manager.removeTower(name);
                    break;
                case "5":
                    manager.print();
                    break;
                case "6":
                    System.out.println("");
                    manager.queryMagesBiggerLevelThan(18);
                    System.out.println("");
                    manager.queryTowerSmallerThan(30);
                    System.out.println("");
                    manager.queryMageLevelBiggerThanFromTower(20, "Eiffel");
                    System.out.println("");
                    break;
            }

        }

        manager.close();
    }


}