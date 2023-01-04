package sec01.exam03;

public class ThreadB extends Thread{

    //
    public ThreadB(){
        // Thread 이름은 setName으로 변경 가능
        setName("ThreadB");
    }

    @Override
    public void run() {
        System.out.println("내 스레드의 이름은 " + getName() + "입니다");
    }
}
