/*
 * Allows limiting the speed of the CPU and keeps a counter of total cycles
 */

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
        tick++;
        return 0;
    }
    public int getClock() {
        return tick;
    }
    public void setClock(int t) {
    	tick = t;
    }
}
