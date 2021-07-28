package hellojpa;

public class ValueMain {
    public static void main(String[] args) {

        int a = 10;
        // a 의 값이 복사가 돼서 b 로 넘어가기 때문에 따로 저장공간을 가진다
        int b = a;

        b = 20;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        Integer c = new Integer(10);
        // 주소값(참조)이 넘어간다. 하지만 Integer 는 넘어가지 않음
        Integer d = c;

        System.out.println("c = " + c);
        System.out.println("d = " + d);

    }
}
