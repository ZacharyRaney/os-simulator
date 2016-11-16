import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zacharyraney on 11/3/16.
 */
public class Scheduler {

    public Queue<Process> jobQueue;
    public Queue<Process> readyQueue;
    public Queue<Process> deviceQueue;

    public Scheduler() {
        jobQueue = new LinkedList<Process>();
        readyQueue = new LinkedList<Process>();
        deviceQueue = new LinkedList<Process>();
    }
    //insertPCB()
    //removePCB()
    //getState()
    //setState()
    //getWait()
    //setWait()
    //getArrival()
    //setArrival()
    //getCPUTime()
    //setCPUTime()
}
