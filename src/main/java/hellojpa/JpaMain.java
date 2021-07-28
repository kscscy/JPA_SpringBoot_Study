package hellojpa;

import org.hibernate.Hibernate;

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
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("city1", "street1", "zipcode1"));
            member.setWorkPeriod(new Period());

            em.persist(member);

            System.out.println("=============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
