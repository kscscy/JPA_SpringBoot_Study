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

//            Member member = new Member();
//            member.setUsername("CC");
//            em.persist(member);
            /*
                sequence strategy
                MEMBER_SEQ 의 다음값을 DB에서 가져온다
                call next value for MEMBER_SEQ

             */

//            System.out.println("member.getId() = " + member.getId());

            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("=============================");

            // 처음 호출하면
            // DB SEQ = 1  | App 1
            // 그 다음
            // DB SEQ = 51 | App 2
            // DB SEQ = 51 | App 3

            em.persist(member1);    // 1, 51
//            em.persist(member2);    // Memory
//            em.persist(member3);    // Memory

            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());

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
