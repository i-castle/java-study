package sec01.exam03;

public class ThreadNameExmaple {
    public static void main(String[] args) {

        // 현재 스레드 출력
        System.out.println("현재 스레드의 이름은 " + Thread.currentThread().getName());

        // ThreadA 생성
        Thread threadA = new ThreadA();
        threadA.start();

        // ThreadB 생성
        Thread threadB = new ThreadA();
        threadB.start();
    }
}
