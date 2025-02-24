package jpabook.yoo.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("yoo");
        EntityManager em = emf.createEntityManager();

        EntityTransaction ts = em.getTransaction();

        ts.begin();
        try {

            Item iphone = new Item();
            iphone.setName("아이폰");

            Item AppleWatch = new Item();
            AppleWatch.setName("애플워치");

            em.persist(iphone);
            em.persist(AppleWatch);

            Category phone = new Category();
            phone.setName("휴대전화");

            Category watch = new Category();
            watch.setName("시계");

            em.persist(phone);
            em.persist(watch);

            CategoryItem categoryItem = new CategoryItem();
            categoryItem.setCategory(phone);
            categoryItem.setItem(iphone);
            phone.getItems().add(categoryItem);


            CategoryItem categoryItem1 = new CategoryItem();
            categoryItem1.setCategory(watch);
            categoryItem1.setItem(AppleWatch);
            watch.getItems().add(categoryItem1);

            em.persist(categoryItem);
            em.persist(categoryItem1);

            em.flush();
            em.clear();

            Item findItem = em.find(Item.class, iphone.getId());
            for (CategoryItem category : findItem.getCategories()) {
                System.out.println("category.getItem() = " + category.getItem().getName());
            }

            Category findCategory = em.find(Category.class, watch.getId());
            for (CategoryItem categoryItem2 : findCategory.getItems()) {
                System.out.println("categoryItem2.getCategory().getName() = " + categoryItem2.getCategory().getName());
            }

            ts.commit();
        } catch (Exception e) {
            ts.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
