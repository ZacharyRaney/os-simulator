import java.util.concurrent.TimeUnit;

public class Clock {

    private int tick;
    private static int speed = 0; // Set in milliseconds per tick

    public Clock() {
        tick = 0;
    }

    public int execute() {
        if(speed != 0){
            try {
                TimeUnit.MILLISECONDS.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    public int getClock() {
        return tick;
    }
}
