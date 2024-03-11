package org.example;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Set<Mage> mages;
        String sortingMode = "";

        if(args.length == 0){
            mages = new HashSet<>();
            sortingMode = "None";
        }
        else
        {
            if (args[0].equals("Alternative")) {
                sortingMode = "Alternative";
                mages = new TreeSet<>(new Mage());
            } else {
                sortingMode = "Natural";
                mages = new TreeSet<>();
            }
        }

        Mage mage1 = new Mage("Gandalf", 10, 100.0, sortingMode);
        mages.add(mage1);
        Mage mage2 = new Mage("Merlin", 8, 80.0, sortingMode);
        mages.add(mage2);
        Mage mage3 = new Mage("Dumbledore", 12, 120.0, sortingMode);
        mages.add(mage3);
        Mage mage4 = new Mage("Dumbledor", 12, 120.0, sortingMode);
        mages.add(mage4);
        Mage mage5 = new Mage("Radagast", 7, 70.0, sortingMode);
        mages.add(mage5);
        Mage mage6 = new Mage("Morgana", 11, 110.0, sortingMode);
        mages.add(mage6);
        Mage mage7 = new Mage("Ursula", 6, 60.0, sortingMode);
        mages.add(mage7);
        Mage mage8 = new Mage("Maleficent", 13, 130.0, sortingMode);
        mages.add(mage8);
        Mage mage9 = new Mage("Yenifer", 14, 140.0, sortingMode);
        mages.add(mage9);
        Mage mage10 = new Mage("Triss", 15, 150.0, sortingMode);
        mages.add(mage10);
        
        mage1.addApprentice(mage2);
        mage1.addApprentice(mage3);
        mage1.addApprentice(mage4);

        mage2.addApprentice(mage5);
        mage2.addApprentice(mage6);

        mage3.addApprentice(mage5);

        mage7.addApprentice(mage6);
        mage7.addApprentice(mage8);

        mage9.addApprentice(mage10);

        // Print the mages in the test set
        //int counter = 1;
        for (Mage mage : mages) {
            mage.printHierarchy("");
            //mage.printHierarchy(String.valueOf(counter));
            //counter++;
        }

        mage1.descendantMap(sortingMode, mages);
        
    }
}

