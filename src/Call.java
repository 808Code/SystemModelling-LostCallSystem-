
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Call {
    private int max;

    private ArrayList<Progress>callsInProgress;
    private ArrayList<Arrival>callsToArrive;
    //function to check callsInProgress and callsToArrive and remove add every sec


    Call(){
        this.callsInProgress=new ArrayList<Progress>();
        this.callsToArrive=new ArrayList<Arrival>();

        //attributes of simulation
        this.max=3;

        //here calls in progress:

        //premade in progress how???
//        this.addProgressToCallsInProgress(new Progress(4,7,1075));
//        this.addProgressToCallsInProgress(new Progress(4,7,1086));

        //here calls that arrive
        //dont put same arrival time.
        //

        Random ran = new Random();
        int firstCaller=1;
        int lastCaller=8;
//        for (int i=0;i<=(ThreadLocalRandom.current().nextInt(20,30));i++){
          for(int i=0;i<=10;i++){


            int from = ThreadLocalRandom.current().nextInt(firstCaller, lastCaller + 1);
            int to = ThreadLocalRandom.current().nextInt(firstCaller, lastCaller + 1);

            while(true){
                if(from!=to){
                    break;
                }
                to = ThreadLocalRandom.current().nextInt(firstCaller, lastCaller + 1);
            }
            int length =ThreadLocalRandom.current().nextInt(20,40);
            int arrivalTime=ThreadLocalRandom.current().nextInt(1060,1070);
//          int arrivalTime=ThreadLocalRandom.current().nextInt(1060,2000);
            this.callsToArrive.add(new Arrival(from,to,length,arrivalTime));
        }






    }

    public Boolean addProgressToCallsInProgress(Progress progress){
        //add progress object to calls to progress list
        //checking if both callers are free:
        if(Caller.checkCallPossibility(progress.getFrom(),progress.getTo())){
            Caller.callers[progress.getFrom()]=1;
            Caller.callers[progress.getTo()]=1;
            this.callsInProgress.add(progress);
            return true;
        }
        ++CallCounter.busy;
        System.out.println("Call Dropped due to one of the participant being busy");

        return false;
    }

    public void removeProgressFromCallsInProgress(Progress progress){
        Caller.callers[progress.getFrom()]=0;
        Caller.callers[progress.getTo()]=0;
        this.callsInProgress.remove(progress);
        System.out.println("Call has successfully Ended:"+progress);
        ++CallCounter.completed;
    }

    public void checkProgressCalls(){
        if(this.getCallsInProgress().size()!=0){
            try {
                for (Progress progress : this.getCallsInProgress()) {
//                    System.out.println(progress);
                    if (progress.getEnd() == Utility.clock) {
                        this.removeProgressFromCallsInProgress(progress);
                        if (this.getCallsInProgress().size() == 0) {
                            //to prevent empty for each loop exception when progress calls empty.
                            break;
                        }
                    }
                }
            }catch (ConcurrentModificationException exception){
//                System.out.println(exception);

            }
        }
    }

    public void checkArrivalCalls(){
        ArrayList<Arrival> arrivalOnCurrentClock=new ArrayList<Arrival>();
        if(this.getCallsToArrive().size()!=0){

                    for (Arrival arrival:this.getCallsToArrive()) {

                        if(arrival.getArrivalTime()==Utility.clock){
                            arrivalOnCurrentClock.add(arrival);
                        }

                    }
        }
        for (Arrival arrival: arrivalOnCurrentClock) {
            if(addArrivalCallToProgress(arrival)){
                System.out.println("Has been added to progress:"+arrival);
            }else{
                //no link available call is lost
                System.out.println("Lost Call = "+arrival);
            }
            ++CallCounter.processed;
            if(this.getCallsToArrive().size()==0){
                //to prevent empty for each loop exception when progress calls empty.
                break;
            }


        }
    }



    public Boolean addArrivalCallToProgress(Arrival arrival){
        callsToArrive.remove(arrival);
        if((callsInProgress.size())!=(this.max)){
            Progress progress=ArrivalToProgress.mapArrivalToProgress(arrival);
            return this.addProgressToCallsInProgress(progress);

        }
        ++CallCounter.blocked;
        System.out.println("The Links are full due to which call has been dropped.");
        return false;
    }

    public ArrayList<Arrival> getCallsToArrive() {
        return callsToArrive;
    }

    public void setCallsToArrive(ArrayList<Arrival> callsToArrive) {
        this.callsToArrive = callsToArrive;
    }

    public ArrayList<Progress> getCallsInProgress() {
        return callsInProgress;
    }

    public void setCallsInProgress(ArrayList<Progress> callsInProgress) {
        this.callsInProgress = callsInProgress;
    }
}
