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
                    // TODO: Print out PCB info
                    break;
                default:
                    // TODO: error out of execution here
                    break;
            }
            counter++;
            while(scheduler.nextScheduled == clock.getClock()) {
            	scheduler.getArrival(clock.getClock());
            }
        }
        process.pcb.counter = counter;
        process.pcb.state = PCB.State.READY;
        return Status.CYCLE;
    }


    //---------REQUIRED---------
    private void advanceClock(){
        // TODO: Decide if there is anything else needed
        clock.execute();
    }
    private void detectInterrupt() {
        // TODO: Handle interrupts
    }
    private void detectPreemption() {
        // TODO: Handle preemption
    }
}
