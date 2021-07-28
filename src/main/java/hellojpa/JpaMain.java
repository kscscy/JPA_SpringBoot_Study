package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;

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

            Member member1 = new Member();
            member1.setUsername("user1");
            em.persist(member1);

//            Member member2 = new Member();
//            member2.setUsername("user2");
//            em.persist(member2);

            em.flush();
            em.clear();

//            Member findMember = em.getReference(Member.class, member.getId());

//            Member m1 = em.find(Member.class, member1.getId());
//            System.out.println("m1 = " + m1.getClass());
//            Member m1reference = em.getReference(Member.class, member1.getId());
//            System.out.println("m1reference.getClass() = " + m1reference.getClass());
//            System.out.println("(m1 == m1reference) = " + (m1 == m1reference));

//            Member m1proxy1 = em.getReference(Member.class, member1.getId());
//            System.out.println("m1proxy1.getClass() = " + m1proxy1.getClass());
//            Member m1proxy2 = em.getReference(Member.class, member1.getId());
//            System.out.println("m1proxy2.getClass() = " + m1proxy2.getClass());
//            System.out.println("(m1proxy1 == m1proxy2) = " + (m1proxy1 == m1proxy2));


//            Member m2 = em.getReference(Member.class, member2.getId());
//            System.out.println("m2 = " + m2.getClass());
//            System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
//            someLogic(m1, m2);

            /*
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember.getClass());

            System.out.println("refMember == findMember : " + (refMember == findMember));
             */

            /*
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());


//            em.detach(refMember);
//            em.close();
//            em.clear();

            // 더 이상 영속성 컨텍스트의 도움을 받을 수 없음
            System.out.println("refMember = " + refMember.getUsername());

             */

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

//            refMember.getUsername();
            Hibernate.initialize(refMember); // 강제초기화

            // emf 에서 얻을 수 있다.
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

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

    // proxy 로 넘어오는지 아닌지 알 수가 없다?
    private static void someLogic(Member m1, Member m2) {
        System.out.println("m1 instanceof Member? " + (m1 instanceof Member));
        System.out.println("m1 instanceof Member? " + (m2 instanceof Member));
    }

}
