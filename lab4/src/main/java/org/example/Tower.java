package org.example;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;
    @OneToMany(mappedBy = "tower", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Mage> mages;

    public Tower() {
        this.mages = new ArrayList<>();
    }

    public Tower(String name, int height, List<Mage> mages) {
        this.name = name;
        this.height = height;
        if(mages == null)
            this.mages = new ArrayList<>();
        else
            this.mages = mages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void addMage(Mage mage) {
        this.mages.add(mage);
    }

    public void removeMage(Mage mage) {
        this.mages.remove(mage);
    }

    @Override
    public String toString() {
        return "Tower{" + "name = '" + name + '\'' + ", height = " + height + "\n" + mages + '}';
    }
}

