package sec02.exam03;

public class InterruptExample2 {
    public static void main(String[] args) {
        PrintThread3 printThread3 = new PrintThread3();
        printThread3.start();

        try{Thread.sleep(1000);}catch (InterruptedException e){}

        printThread3.interrupt();
    }
}
