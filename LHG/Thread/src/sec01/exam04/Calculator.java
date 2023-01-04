package sec01.exam04;

public class Calculator {
    private int memory;

    // 스레드가 사용 중인 객체를 다른 스레드가 변경할 수 없게 하려면 객체 잠금을 위해서 synchronized를 넣어줘야 함
    // 내부가 임계 영역으로 단 하나의 스레드만 실행할 수 있게 됨
    public synchronized void setMemory(int memory){
        this.memory = memory;
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){}
        System.out.println(Thread.currentThread().getName() + ":" + this.memory);
    }

    public int getMemory(){
        return memory;
    }
}
