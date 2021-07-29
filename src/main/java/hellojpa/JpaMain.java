package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
            member.setHomeAddress(new Address("homeCity", "street1", "zipcode1"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

//            member.getAddressHistory().add(new Address("oldCity1", "street1", "zipcode1"));
//            member.getAddressHistory().add(new Address("oldCity2", "street1", "zipcode1"));

            // AddressEntity
            member.getAddressHistory().add(new AddressEntity("oldCity1", "street1", "zipcode1"));
            member.getAddressHistory().add(new AddressEntity("oldCity2", "street1", "zipcode1"));


            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============find==============");
            Member findMember = em.find(Member.class, member.getId());

            /*
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }
             */

            // homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity") // side Effect 발생할 수 있다.

//            Address a = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨"); // String 자체가 값 타입이다.
//            findMember.getFavoriteFoods().add("한식");

            // oldCity1 만 변경하고 싶다 => Address 인스턴스를 통으로 갈아끼워야한다.
            // remove()로 제거하기 위해서는 equals(), hashCode() 구현돼있어야 한다.
//            findMember.getAddressHistory().remove(new Address("oldCity1", "street1", "zipcode1"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street1", "zipcode1"));

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
