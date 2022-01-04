import java.util.ArrayList;

public class Caller {
    public static int[] callers = {0,0,0,0,0,0,0,0,0,0};

    public static Boolean checkCallPossibility(int from,int to){
        if(callers[from]==1 || callers[to]==1){
            return false;
        }
        return true;
    }
}
