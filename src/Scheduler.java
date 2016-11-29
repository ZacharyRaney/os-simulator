import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

    private static int CYCLES = 25;

    private Queue<Process> jobQueue;     // All processes
    private Queue<Process> readyQueue;   // Ready to execute
    private Queue<Process> waitingQueue; // Waiting for memory space
    private Queue<Process> deviceQueue;  // Waiting for IO
    private CPU cpu;

    public Scheduler() {
        jobQueue = new LinkedList<>();
        readyQueue = new LinkedList<>();
        deviceQueue = new LinkedList<>();
        waitingQueue = new LinkedList<>();
        cpu = new CPU();
    }

    public void schedulerLoop(){
        while (true) {      // TODO: Add conditions for scheduler loop
            Process current = readyQueue.remove();
            cpu.switchProcess(current);

            switch (cpu.run(CYCLES)) {
                case CYCLE: // CPU hit cycle max
                    readyQueue.add(current);
                    break;
                case END:   // CPU reached EOF
                    // TODO: Remove PCB
                    jobQueue.remove(current);
                    break;
                case IO:    // Process ready for IO
                    // TODO: Handle IO
                    deviceQueue.add(current);
                    break;
                case YIELD: // Process wants to let next process run
                    readyQueue.add(current);
                    break;
            }
        }
    }

    //---------REQUIRED---------
    public void insertPCB(Process process) {
        process.pcb = new PCB();
    }
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
