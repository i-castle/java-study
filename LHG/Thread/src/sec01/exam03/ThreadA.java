package sec01.exam03;

public class ThreadA extends Thread{
    
    //
    public ThreadA(){
        // Thread 이름은 setName으로 변경 가능
        setName("ThreadA");
    }
    
    @Override
    public void run() {
        System.out.println("내 스레드의 이름은 " + getName() + "입니다");
    }
}
