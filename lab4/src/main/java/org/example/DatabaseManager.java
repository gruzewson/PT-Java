package org.example;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DatabaseManager {
    private static EntityManagerFactory factory;

    public DatabaseManager() {
        factory = Persistence.createEntityManagerFactory("pers-name");
    }
    public void insertMage(Mage mage) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(mage);
        transaction.commit();
        System.out.println("Mage inserted successfully: " + mage.getName());
        entityManager.close();
    }

    public void insertTower(Tower tower) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(tower);
        transaction.commit();
        System.out.println("Tower inserted successfully: " + tower.getName());
        entityManager.close();
    }

    public void removeMage(String name) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entityManager.find(Mage.class, name));
        transaction.commit();
        System.out.println("Mage removed successfully: " + name);
        entityManager.close();
    }
    public void removeTower(String name) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entityManager.find(Tower.class, name));
        transaction.commit();
        System.out.println("Tower removed successfully: " + name);
        entityManager.close();
    }

    public void print() {
        EntityManager entityManager = factory.createEntityManager();
        String jpql = "SELECT t FROM Tower t";
        var query = entityManager.createQuery(jpql, Tower.class);
        List<Tower> towers = query.getResultList();

        System.out.println("Towers:");
        for (Tower tower : towers) {
            System.out.println(tower);
        }
        entityManager.close();
    }

    public void queryMagesBiggerLevelThan(int level){
        EntityManager entityManager = factory.createEntityManager();
        String jpql = "SELECT m FROM Mage m WHERE level > :level";
        var query = entityManager.createQuery(jpql, Mage.class);
        query.setParameter("level", level);
        List<Mage> mages = query.getResultList();

        System.out.println("Mages with level bigger than " + level);
        for (Mage mage : mages) {
            System.out.println(mage.getName() + " lvl: " + mage.getLevel());
        }
        entityManager.close();
    }

    public void queryTowerSmallerThan(int height){
        EntityManager entityManager = factory.createEntityManager();
        String jpql = "SELECT t FROM Tower t WHERE height < :height";
        var query = entityManager.createQuery(jpql, Tower.class);
        query.setParameter("height", height);
        List<Tower> towers = query.getResultList();

        System.out.println("Towers with height smaller than " + height);
        for (Tower tower : towers) {
            System.out.println(tower.getName() + " height: " + tower.getHeight());
        }
        entityManager.close();
    }

    public void queryMageLevelBiggerThanFromTower(int level, String towerName){
        EntityManager entityManager = factory.createEntityManager();
        String jpql = "SELECT m FROM Mage m WHERE m.level > :level AND m.tower.name = :towerName";
        var query = entityManager.createQuery(jpql, Mage.class);
        query.setParameter("level", level);
        query.setParameter("towerName", towerName);
        List<Mage> mages = query.getResultList();

        System.out.println("Mages with level bigger than " + level + " from tower " + towerName);
        for (Mage mage : mages) {
            System.out.println(mage.getName() + " lvl: " + mage.getLevel());
        }
        entityManager.close();
    }

    public Tower getTower(String name) {
        EntityManager entityManager = factory.createEntityManager();
        Tower tower = entityManager.find(Tower.class, name);
        entityManager.close();
        return tower;
    }

    public void close(){
        if (factory != null && factory.isOpen()) {
            factory.close();
            System.out.println("Factory closed successfully.");
        }
    }


}
