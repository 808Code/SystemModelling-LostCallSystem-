import java.time.Clock;

public class Runner {
    public static void main(String args[]){
        Call call=new Call();
        System.out.println(call);
        while(true){
//            System.out.println(Caller.callers[1]);
            call.checkProgressCalls();
            call.checkArrivalCalls();
            System.out.println("Current Clock="+Utility.clock++);
            Utility.pause();
            if(Utility.clock==3000){
                break;
            }
            System.out.println("The Progress Queue:");
            for (Progress progress:call.getCallsInProgress()) {
                System.out.println(progress);
            }
            System.out.println("The Arrival Queue:");
            for (Arrival arrival:call.getCallsToArrive()) {
                System.out.println(arrival);
            }

            System.out.println("-------------------------------------------------");
        }
        System.out.println("Thankyou For Simulating our Lost Call System.");
    }
}
