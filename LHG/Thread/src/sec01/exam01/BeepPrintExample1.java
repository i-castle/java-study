package sec01.exam01;

public class BeepPrintExample1 {
    public static void main(String[] args) {
        // BeepTask 객체 생성
        Runnable beepTask = new BeepTask();
        Thread thread = new Thread(beepTask);

        thread.start();

        for(int i=0; i<5; i++){
            System.out.println("띵");
            try{Thread.sleep(500);}catch (Exception e){}
        }
    }
}
