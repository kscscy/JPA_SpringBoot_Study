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

            // 저장
            Team team = new Team();
            team.setName("TeamA");
//            team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.changeTeam(team); // 연관관계의 주인에 값을 입력
            em.persist(member);

            // 이 과정을 생략하면?
            /*
            * 영속성 컨텍스트를 초기화 하지 않으면 1차 캐시에 값이 생성되지 않는다.
            * 순수한 객체 상태가 되어 select 쿼리가 나가지 않음
            *
            * */
//            team.getMembers().add(member);

            team.addMember(member);

            em.flush();
            em.clear(); // 영속성 컨텍스트 초기화
            
            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

            System.out.println("members = " + findTeam);

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
