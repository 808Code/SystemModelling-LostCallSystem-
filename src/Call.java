
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Map;


public class Call {
    private int max;
    private int inUse;
    private ArrayList<Progress>callsInProgress;
    private ArrayList<Arrival>callsToArrive;
    //function to check callsInProgress and callsToArrive and remove add every sec


    Call(){
        this.callsInProgress=new ArrayList<Progress>();
        this.callsToArrive=new ArrayList<Arrival>();

        //attributes of simulation
        this.max=3;

        //here calls in progress:
        this.inUse=2;
        //premade in progress how???
//        this.addProgressToCallsInProgress(new Progress(4,7,1075));
//        this.addProgressToCallsInProgress(new Progress(4,7,1086));

        //here calls that arrive
        this.callsToArrive.add(new Arrival(3,6,98,1063));
        this.callsToArrive.add(new Arrival(5,6,98,1080));
        this.callsToArrive.add(new Arrival(1,6,98,1162));


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
        System.out.println("Call Dropped due to one of the participant being busy");

        return false;
    }

    public void removeProgressFromCallsInProgress(Progress progress){
        Caller.callers[progress.getFrom()]=0;
        Caller.callers[progress.getTo()]=0;
        this.callsInProgress.remove(progress);
        System.out.println("Call has successfully Ended:"+progress);
    }

    public void checkProgressCalls(){
        if(this.getCallsInProgress().size()!=0){
            try {
                for (Progress progress : this.getCallsInProgress()) {
                    System.out.println(progress);
                    if (progress.getEnd() == Utility.clock) {
                        this.removeProgressFromCallsInProgress(progress);
                        if (this.getCallsInProgress().size() == 0) {
                            //to prevent empty for each loop exception when progress calls empty.
                            break;
                        }
                    }
                }
            }catch (ConcurrentModificationException exception){
                System.out.println(exception);

            }
        }
    }

    public void checkArrivalCalls(){
        if(this.getCallsToArrive().size()!=0){
            try{
                    for (Arrival arrival:this.getCallsToArrive()) {
                        System.out.println(arrival);
                        if(arrival.getArrivalTime()==Utility.clock){
                            if(addArrivalCallToProgress(arrival)){
                                System.out.println("Has been added to progress:"+arrival);
                            }else{
                                //no link available call is lost
                                System.out.println("Lost Call = "+arrival);
                            }
                            if(this.getCallsToArrive().size()==0){
                                //to prevent empty for each loop exception when progress calls empty.
                                break;
                            }
                        }
                    }
            }catch (ConcurrentModificationException exception){
               System.out.println(exception);
            }
        }
    }


    public Boolean addArrivalCallToProgress(Arrival arrival){
        callsToArrive.remove(arrival);
        if(callsInProgress.size()!=3){
            Progress progress=ArrivalToProgress.mapArrivalToProgress(arrival);
            return this.addProgressToCallsInProgress(progress);

        }
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
