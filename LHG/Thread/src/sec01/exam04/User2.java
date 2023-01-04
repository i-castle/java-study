package sec01.exam04;

public class User2 extends Thread{
    private Calculator calculator;

    public void setCalculator(Calculator calculator){
        // 스레드 명 User2
        this.setName("User2");
        // 공유 객체 Calculator를 변수에 저장
        this.calculator = calculator;
    }

    @Override
    public void run() {
        // 공유 객체인 Calculator의 메모리에 50 저장
        calculator.setMemory(50);
    }
}
