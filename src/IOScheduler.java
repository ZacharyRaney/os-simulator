import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class IOScheduler {
	private Queue<ECB> ioWaiting;
	public EventQueue eventQueue;
	
	private int lastTime;
	
	public IOScheduler(EventQueue e) {
		ioWaiting = new LinkedList<>();
		eventQueue = e;
		lastTime = 0;
	}
	
	public void scheduleIO(Process process, int currentTime) {
		int ioWait;
		if(currentTime < lastTime) {
			ioWait = ThreadLocalRandom.current().nextInt(25, 51) + (lastTime - currentTime);
		} else {
			ioWait = ThreadLocalRandom.current().nextInt(25, 51);
		}
		lastTime = ioWait;
		ioWaiting.add(new ECB(process, currentTime + ioWait));
		startIO();
	}
	public void startIO() {
		eventQueue.enQueue(ioWaiting.poll());
	}
}
