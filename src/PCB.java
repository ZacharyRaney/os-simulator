public class PCB {
    public enum State {
        NEW, READY, RUN, WAIT, EXIT
    }

    public State state;
    public int counter;

    public PCB() {
        state = State.NEW;
        counter = 0;	// Keeps track of what line of the program we are on
    }
}
