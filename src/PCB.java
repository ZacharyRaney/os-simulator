/**
 * Created by zacharyraney on 11/16/16.
 */
public class PCB {
    public enum State {
        NEW, READY, RUN, WAIT, EXIT
    }

    public State state;
    public int counter;

    public PCB() {
        state = State.NEW;
        counter = 0;
    }
}
