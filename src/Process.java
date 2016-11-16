/**
 * Created by zacharyraney on 11/3/16.
 */
public class Process {
    private int size;
    private String[] instructions;

    public Process(int size, String[] instructions) {
        this.size = size;
        this.instructions = instructions.clone();
    }

}
