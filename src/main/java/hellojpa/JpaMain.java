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
            Address address = new Address("city1", "street1", "zipcode1");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            /*
//            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setHomeAddress(copyAddress);
            em.persist(member2);
//            member.getHomeAddress().setCity("newCity");
*/

            // address 를 통으로 갈아끼는 것이 맞다
            // 또는 필요하면 내부에 copy 메소드를 만든다
            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);

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
