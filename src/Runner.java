import java.time.Clock;

public class Runner {
    public static void main(String args[]){
        Call call=new Call();
        System.out.println(call);
        while(true){
//            System.out.println(Caller.callers[1]);
            call.checkProgressCalls();
            call.checkArrivalCalls();
            System.out.println(Utility.clock++);
            Utility.pause();
            if(Utility.clock==3000){
                break;
            }
        }
        System.out.println("Thankyou For Simulating our Lost Call System.");
    }
}
