package org.example;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DatabaseManager {
    private static EntityManagerFactory factory;
    private static  EntityManager entityManager;

    public DatabaseManager() {
        factory = Persistence.createEntityManagerFactory("pers-name");
        entityManager = factory.createEntityManager();
    }
    public void insertMage(Mage mage) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(mage);
        transaction.commit();
        System.out.println("Mage inserted successfully: " + mage.getName());
    }

    public void insertTower(Tower tower) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(tower);
        transaction.commit();
        System.out.println("Tower inserted successfully: " + tower.getName());
    }

    public void removeMage(String name) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.find(Mage.class, name).getTower().removeMage(entityManager.find(Mage.class, name));
        entityManager.remove(entityManager.find(Mage.class, name));
        transaction.commit();
        System.out.println("Mage removed successfully: " + name);
    }
    public void removeTower(String name) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entityManager.find(Tower.class, name));
        transaction.commit();
        System.out.println("Tower removed successfully: " + name);
    }

    public void print() {
        String jpql = "SELECT t FROM Tower t";
        var query = entityManager.createQuery(jpql, Tower.class);
        List<Tower> towers = query.getResultList();

        System.out.println("Towers:");
        for (Tower tower : towers) {
            System.out.println(tower);
        }
    }

    public void queryMagesBiggerLevelThan(int level){
        String jpql = "SELECT m FROM Mage m WHERE level > :level";
        var query = entityManager.createQuery(jpql, Mage.class);
        query.setParameter("level", level);
        List<Mage> mages = query.getResultList();

        System.out.println("Mages with level bigger than " + level);
        for (Mage mage : mages) {
            System.out.println(mage.getName() + " lvl: " + mage.getLevel());
        }
    }

    public void queryTowerSmallerThan(int height){
        String jpql = "SELECT t FROM Tower t WHERE height < :height";
        var query = entityManager.createQuery(jpql, Tower.class);
        query.setParameter("height", height);
        List<Tower> towers = query.getResultList();

        System.out.println("Towers with height smaller than " + height);
        for (Tower tower : towers) {
            System.out.println(tower.getName() + " height: " + tower.getHeight());
        }
    }

    public void queryMageLevelBiggerThanFromTower(int level, String towerName){
        String jpql = "SELECT m FROM Mage m WHERE m.level > :level AND m.tower.name = :towerName";
        var query = entityManager.createQuery(jpql, Mage.class);
        query.setParameter("level", level);
        query.setParameter("towerName", towerName);
        List<Mage> mages = query.getResultList();

        System.out.println("Mages with level bigger than " + level + " from tower " + towerName);
        for (Mage mage : mages) {
            System.out.println(mage.getName() + " lvl: " + mage.getLevel());
        }
    }

    public Tower getTower(String name) {
        Tower tower = entityManager.find(Tower.class, name);
        return tower;
    }

    public void close(){
        if (factory != null && factory.isOpen()) {
            entityManager.close();
            factory.close();
            System.out.println("Factory closed successfully.");
        }
    }


}
