import java.util.Arrays;

public class Process {
    public int size;
    public String[] instructions;

    public PCB pcb;

    public Process(String[] instructions) {
        this.size = Integer.parseInt(instructions[0]);
        this.instructions = Arrays.copyOfRange(instructions, 1, instructions.length);
    }

}
