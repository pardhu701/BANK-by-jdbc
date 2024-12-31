package bst;
class MM{
    public static int hello(){
        return 0;
    }
}

public class SleepTest extends MM{
    public static void main(String[] args) throws InterruptedException {
        System.out.println(hello());
        for(int i =0 ;i<10;i++){
        //Thread.sleep(000);
        System.out.println("Hello World");}
    }
    
}
