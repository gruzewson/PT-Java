package org.example;

import java.util.*;
import java.util.Set;
import java.util.Objects;

public class Mage implements Comparable<Mage>, Comparator<Mage> {
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public Mage(String name, int level, double power, String sortingMode) {
        this.name = name;
        this.level = level;
        this.power = power;
        if(sortingMode.equals("None")){
            this.apprentices = new HashSet<>();
        }
        else{
            if(sortingMode.equals("Alternative"))
                this.apprentices = new TreeSet<>();
            else
                this.apprentices = new TreeSet<>(new Mage());
        }
    }

    public Mage() {
        this.name = name;
        this.level = level;
        this.power = power;
    }

    public Set<Mage> getApprentices() {
        return apprentices;
    }
    

    @Override
    public int compare(Mage o1, Mage o2) {
        if(o1.level == o2.level){
            if(o1.name == o2.name){
                if(o1.power == o2.power) return 0;
                return o1.power > o2.power ? 1 : -1;
            }
            return o1.name.compareTo(o2.name);
        }
        return o1.level > o2.level ? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        Mage mage = (Mage) obj;
        return this.name == mage.name && this.level == mage.level
                && this.power == mage.power && Objects.equals(apprentices, mage.apprentices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, power, apprentices);
    }

    @Override
    public String toString() {
        return "Mage{name='" + name + "', level=" + level + ", power=" + power + "}";
    }

    @Override
    public int compareTo(Mage mage) {
        if(this.name == mage.name){
            if(this.level == mage.level){
                if(this.power == power) return 0;
                return this.power > power ? 1 : -1;
            }
            return this.level > level ? 1 : -1;
        }
        return this.name.compareTo(mage.name);
    }

    public void addApprentice(Mage mage) {
        apprentices.add(mage);
    }

    public int apprenticeSize() {
        return apprentices.size();
    }

    public void printHierarchy(String prefix) {
        System.out.println(prefix + " " + this);
        if (this.apprenticeSize() != 0) {
            //int counter = 1;
            for (Mage apprentice : this.apprentices) {
                apprentice.printHierarchy(prefix + "-");
                //apprentice.printHierarchy("\t" + prefix + "." + counter);
                //counter++;
            }
        }
    }
    
    public void descendantMap(String sortingMode, Set<Mage> mages) {
        Map<Mage, Integer> descendants;
        if(sortingMode.equals("Alternative")){
            descendants = new TreeMap<>(new Mage());
        }
        else if(sortingMode.equals("Natural")){
            descendants = new TreeMap<>();
        }
        else {
            descendants = new HashMap<>();
        }

        for (Mage mage : mages) {
            int counter = 0;
            for (Mage apprentice : mage.getApprentices()) {
                counter+=apprentice.apprenticeSize();
                counter++;
            }
                descendants.put(mage, counter);
        }

        for (Mage mage : descendants.keySet()) {
            System.out.println(mage.name + " has " + descendants.get(mage) + " descendants");
        }

    }
    
}