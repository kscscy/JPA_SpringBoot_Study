package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // META-INF/persistence.xml 에 설정해 놓은 persistence-unit name 을 전달한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        // code
        EntityTransaction tx = em.getTransaction();

        // transaction 시작
        tx.begin();

        try {
            // 영속

            Movie movie = new Movie();
            movie.setDirector("a");
            movie.setActor("b");
            movie.setName("영화1");
            movie.setPrice(10000);

            em.persist(movie);
            
            em.flush();
            em.clear();
            
            Item item = em.find(Item.class, movie.getId());
            System.out.println("item = " + item);

            System.out.println("=============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
