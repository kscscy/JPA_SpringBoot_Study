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

            Member member = new Member();
            member.setUsername("member1");

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            // 이 내용은 Team 테이블에 insert 될만한 내용이 아니다
            team.getMembers().add(member);

            em.persist(team);

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
