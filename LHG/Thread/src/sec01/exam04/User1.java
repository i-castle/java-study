package sec01.exam04;

public class User1 extends Thread{
    private Calculator calculator;

    public void setCalculator(Calculator calculator){
        // 스레드 명 User1
        this.setName("User1");
        // 공유 객체 Calculator를 변수에 저장
        this.calculator = calculator;
    }

    @Override
    public void run() {
        // 공유 객체인 Calculator의 메모리에 100 저장
        calculator.setMemory(100);
    }
}
