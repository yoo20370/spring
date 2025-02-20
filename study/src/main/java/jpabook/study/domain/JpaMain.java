package jpabook.study.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction ts = em.getTransaction();

        ts.begin();
        try {

            Category category = new Category();
            category.setName("노트북");

            em.persist(category);

            Item item = new Album();
            item.setName("맥북 16인치");

            CategoryItem categoryItem = new CategoryItem();
            categoryItem.setCategory(category);
            categoryItem.setItem(item);

            em.persist(item);
            em.persist(categoryItem);

            ts.commit();
        } catch (Exception e) {
            ts.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
