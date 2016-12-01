import java.util.LinkedList;
import java.util.Queue;

public class InterruptProcessor {
	
	public EventQueue eventQueue;
	private Queue<ECB> immediateEvents;
	
	public InterruptProcessor(EventQueue e) {
		eventQueue = e;
		immediateEvents = new LinkedList<>();
	}
	
	
	
	public Process signalInterrupt(int time) {
		if(eventQueue.queue.containsKey(time)) {
			return getEvent(time);
		} else if(!immediateEvents.isEmpty()) {
			return immediateEvent();
		}
		return null;
	}
	public void addEvent(ECB ecb) {
		if(ecb.time == 0) {
			immediateEvents.add(ecb);
		} else 
			eventQueue.enQueue(ecb);
	}
	public Process getEvent(int t) {
		return eventQueue.queue.remove(t).process;
	}
	public Process immediateEvent() {
		return immediateEvents.poll().process;
	}
}
