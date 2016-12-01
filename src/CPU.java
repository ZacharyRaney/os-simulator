public class CPU {
    public Clock clock;
    private Process process;
    private int counter;
    private String currentInstruction;
    
    public Scheduler scheduler;
    
    public int cpuCycles;

    public enum Status {    //Used to let scheduler know process status
        CYCLE, END, IO, YIELD
    }

    public CPU(Scheduler s) {
        clock = new Clock();
        scheduler = s;
    }

    public void switchProcess(Process process) {
        this.process = process;
        counter = this.process.pcb.counter;
        process.pcb.state = PCB.State.RUN;
    }

    // CPU loop
    public Status run(int cycles) {
    	cpuCycles = 0;
        process.pcb.state = PCB.State.RUN;
        for(int i = 0; i < cycles; i++) {
            if (counter < process.instructions.length) {
                currentInstruction = process.instructions[counter];
            } else {
                process.pcb.state = PCB.State.EXIT;
                return Status.END;
            }
            advanceClock();
            cpuCycles++;
            detectInterrupt();
        	counter++;
            switch (currentInstruction) {
                case "CALCULATE":
                    break;
                case "I/O":
                    process.pcb.counter = counter;
                    process.pcb.state = PCB.State.WAIT;
                    return Status.IO;
                case "YIELD":
                    process.pcb.counter = counter;
                    process.pcb.state = PCB.State.READY;
                    return Status.YIELD;
                case "OUT":
                	process.pcb.counter = counter;
                    scheduler.computer.CPUOut(process);
                    break;
                default:
                    process.pcb.counter = counter;
                    process.pcb.state = PCB.State.EXIT;
                    scheduler.computer.window.printData("COMMAND ERR! Process: " + process.name + " Line: " + (process.pcb.counter + 1));
                    return Status.END;
            }
        }
        process.pcb.counter = counter;
        process.pcb.state = PCB.State.READY;
        return Status.CYCLE;
    }


    //---------REQUIRED---------
    private void advanceClock(){
        clock.execute();
    }
    private void detectInterrupt() {
    	Process interrupt = scheduler.interruptProcessor.signalInterrupt(clock.getClock());	// Do we have any interrupts?
    	if(interrupt != null) {
    		scheduler.readyQueue.add(interrupt);
    	}
    	while(scheduler.nextScheduled == clock.getClock()) {
        	scheduler.addProcess(scheduler.getArrival(clock.getClock()));
        }
    }
}
