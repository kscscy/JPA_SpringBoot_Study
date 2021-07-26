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
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            //member.setTeamId(team.getId()); // 객체지향스럽지 않은 코드
            member.setTeam(team); // jpa가 알아서 team 에서 PK를 꺼내서 FK로 사용함
            em.persist(member);

            em.flush();
            em.clear(); // 영속성 컨텍스트 초기화

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);
//            Team findTeam = findMember.getTeam(); // 이제는 바로 꺼낼 수 있다

            System.out.println("=============================");

            // DB에 TeamB(100L) 있다고 가정
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam); // FK update

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
