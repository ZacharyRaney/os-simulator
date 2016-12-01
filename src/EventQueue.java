import java.util.HashMap;

public class EventQueue {

	public HashMap<Integer, ECB> queue;
	
	public EventQueue(){
		queue = new HashMap<>();
	}
	
	public void enQueue(ECB ecb) {
		queue.put(ecb.time, ecb);
	}
	
	//enQueue()
	//deQueue()
}
