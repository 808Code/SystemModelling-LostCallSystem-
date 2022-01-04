
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
        this.callsInProgress.add(new Progress(4,7,1075));
        this.callsInProgress.add(new Progress(4,7,1086));

        //here calls that arrive
        this.callsToArrive.add(new Arrival(3,6,98,1063));

    }

    void checkProgressCalls(){
        if(this.getCallsInProgress().size()!=0){
            try {
                for (Progress progress : this.getCallsInProgress()) {
                    System.out.println(progress);
                    if (progress.getEnd() == Utility.clock) {
                        callsInProgress.remove(progress);
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

    void checkArrivalCalls(){
        if(this.getCallsToArrive().size()!=0){
            for (Arrival arrival:this.getCallsToArrive()) {
                System.out.println(arrival);
                if(arrival.getArrivalTime()==Utility.clock){
                    addArrivalCallToProgress(arrival);
                    System.out.println("Has been added to progress:"+arrival);
                    if(this.getCallsToArrive().size()==0){
                        //to prevent empty for each loop exception when progress calls empty.
                        break;
                    }
                }
            }
        }
    }


    void addArrivalCallToProgress(Arrival arrival){
        callsToArrive.remove(arrival);
        if(callsInProgress.size()!=3){
            Progress progress=ArrivalToProgress.mapArrvialToProgress(arrival);
            callsInProgress.add(progress);
        }else{
            //no link available call is lost
            System.out.println("Lost Call = "+arrival);
        }
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
