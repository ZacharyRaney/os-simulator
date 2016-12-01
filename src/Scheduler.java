import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {

    private static int CYCLES = 25;

    private Queue<Process> jobQueue;     // All processes
    public Queue<Process> readyQueue;   // Ready to execute
    private Queue<Process> waitingQueue; // Waiting for memory space
    private Queue<Process> deviceQueue;  // Waiting for IO
    private HashMap<Integer, Process> scheduled;
    
    private CPU cpu;
    public Memory memory;
    public EventQueue eventQueue;
    public IOScheduler ioScheduler;
    public InterruptProcessor interruptProcessor;
    public Computer computer;
    
    private boolean paused;
    private Process saved;
    public int nextScheduled;

    public Scheduler(Computer c) {
        jobQueue = new LinkedList<>();
        readyQueue = new LinkedList<>();
        deviceQueue = new LinkedList<>();
        waitingQueue = new LinkedList<>();
        scheduled = new HashMap<>();
        
        cpu = new CPU(this);
        memory = new Memory();
        eventQueue = new EventQueue();
        ioScheduler = new IOScheduler(eventQueue);
        interruptProcessor = new InterruptProcessor(eventQueue);
        computer = c;
        
        paused = false;
        nextScheduled = -1;
    }

    public void addProcess(Process process){
        if(!memory.addProgram(process.size)){   // no room in memory
            waitingQueue.add(process);
        } else {
        	process.pcb = new PCB();
            readyQueue.add(process);
            process.pcb.state = PCB.State.READY;
        }
        jobQueue.add(process);
    }

    public void schedulerLoop(int maxCycles) {
    	int cycleCount = 0;
    	int CYCLES = this.CYCLES;
    	boolean pause = false;
        while (!jobQueue.isEmpty()) {
        
        	Process interrupt = interruptProcessor.signalInterrupt(cpu.clock.getClock());	// Do we have any interrupts?
        	if(interrupt != null) {
        		readyQueue.add(interrupt);
        	}

            if(cycleCount + CYCLES > maxCycles && maxCycles != 0) {
            	CYCLES = maxCycles - cycleCount;
            	pause = true;
            }
            
            if(nextScheduled == cpu.clock.getClock()) {
            	addProcess(getArrival(cpu.clock.getClock()));
            }
            
            if(CYCLES == 0) return;
        	
        	Process current;
        	
        	if(paused){		// If we paused then resume with the saved process
        		if(saved.pcb.state == PCB.State.EXIT ) {	// Is the program we paused on closing?
        			removePCB(saved);
        			paused = false;
        			current = readyQueue.remove();
        			cpu.switchProcess(current);
        		} else {	// Paused process was running.
        			current = saved;
        			paused = false;
        		}
        	} else {
        		current = readyQueue.remove();
        		cpu.switchProcess(current);
        	}
            
            switch (cpu.run(CYCLES)) {
                case CYCLE: // CPU hit cycle max
                	if(pause) {
                		current.pcb.state = PCB.State.RUN; // The process should still be running
                		saved = current;
                		paused = true;
                	} else {
                		readyQueue.add(current);
                	}
                	cycleCount += CYCLES;
                    break;
                case END:   // CPU reached EOF
                    cycleCount += cpu.cpuCycles;
                    if(maxCycles - cycleCount == 0 && pause) {
                    	saved = current;
                    	paused = true;
                    } else removePCB(current);
                    break;
                case IO:    // Process ready for IO
                    // TODO: Handle IO
                    deviceQueue.add(current);
                    cycleCount += cpu.cpuCycles;
                    break;
                case YIELD: // Process wants to let next process run
                    readyQueue.add(current);
                    cycleCount += cpu.cpuCycles;
                    break;
            }
        }
    }
    
    public void addWaiting() { // Add a program waiting for memory space
    	if(waitingQueue.peek() != null && memory.addProgram(waitingQueue.peek().size)) { //	is there anything waiting for space and if so do we have the space?
    		readyQueue.add(waitingQueue.poll());
    	}
    }

    //---------REQUIRED---------
    public void insertPCB(Process process) {
        process.pcb = new PCB();
    }
    public void removePCB(Process process) {
        process.pcb = null;
        memory.removeProgram(process.size);
        jobQueue.remove(process);
        addWaiting(); // Since we removed a process from memory, try and add a waiting process
    }
    public PCB.State getState(Process process) {
    	return process.pcb.state;
    }
    public void setState(Process process, PCB.State state){
    	process.pcb.state = state;
    }
    public Queue<Process> getWait() {
    	return waitingQueue;
    }
    public void setWait(Process p) {
    	waitingQueue.add(p);
    }
    public Process getArrival(int t) {
        Process p = scheduled.remove(t);
        if(scheduled.isEmpty())
        	nextScheduled = -1;
        else {
        	Integer arr[] = new Integer[scheduled.keySet().size()];
        	arr = scheduled.keySet().toArray(arr);
        	Arrays.sort(arr);
        	nextScheduled = arr[0];
        }
    	return p;
    }
    public void setArrival(int t, Process p) {
    	if(t < cpu.clock.getClock() || t <= 0) {
    		addProcess(p);
    	} else {
    		scheduled.put(t, p);
    		if(nextScheduled == -1 || t < nextScheduled)
    			nextScheduled = t;
    	}
    }
    public int getCPUTime() {
    	return cpu.clock.getClock();
    }
    public void setCPUTime(int t) {
    	cpu.clock.setClock(t);
    }
}
