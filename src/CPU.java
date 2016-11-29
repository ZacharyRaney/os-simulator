public class CPU {
    private Clock clock;
    private Process process;
    private int counter;
    private String currentInstruction;

    public enum Status {    //Used to let scheduler know process status
        CYCLE, END, IO, YIELD
    }

    public CPU() {
        clock = new Clock();
    }

    public void switchProcess(Process process) {
        this.process = process;
        counter = this.process.pcb.counter;

    }

    // CPU loop
    public Status run(int cycles) {
        for(int i = 0; i < cycles; i++) {
            if (counter < process.instructions.length) {
                currentInstruction = process.instructions[counter];
            } else {
                return Status.END;
            }
            switch (currentInstruction) {
                case "CALCULATE":
                    break;
                case "I/O":
                    process.pcb.counter = counter;
                    return Status.IO;
                case "YIELD":
                    process.pcb.counter = counter;
                    return Status.YIELD;
                case "OUT":
                    // TODO: Print out PCB info
                    break;
                default:
                    // TODO: error out of execution here
                    break;
            }
            advanceClock();
            counter++;
        }
        process.pcb.counter = counter;
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
