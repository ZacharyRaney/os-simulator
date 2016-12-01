/*
 * Computer class is the command interface between the UI and the OS
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Computer {
	public MainWindow window;
	public Scheduler scheduler;
	
	public Computer(MainWindow w) {
		scheduler = new Scheduler(this);
		window = w;
	}
	
	public String Input(String string) {
		//PROC, MEM, LOAD, EXE, RESET
		if(string.startsWith("PROC")) {
			// TODO: Print process info
		} else if(string.startsWith("MEM")) {
			return Integer.toString(scheduler.memory.getAvailable());
		} else if(string.startsWith("LOAD")) {
			readJobFile(string.substring(5));
			return "Added file: " + string.substring(5);
		} else if(string.startsWith("EXE")) {
			if(string.length() > 4) {
				scheduler.schedulerLoop(Integer.valueOf(string.substring(4)));
				return "Ran for " + string.substring(4) + " cycles";
			} else {
				scheduler.schedulerLoop(0);
				return "Finished executing";
			}
			
		} else if(string.startsWith("RESET")) {
			scheduler = new Scheduler(this);
			return "Simulation reset!";
		}
		return "INVALID COMMAND";
	}
	
	private String[] readJobFile(String file) {
		BufferedReader in;
		List<String> list = new ArrayList<>();
		try {
			in = new BufferedReader(new FileReader(file));
			String str;

			while((str = in.readLine()) != null){
			    if(!str.equals("EXE"))
			    	list.add(str);
			}

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String s : list) {
			int time = Integer.valueOf(s.split(" ")[1]);
			String processLoc = s.split(" ")[2];
			Process process = new Process(processLoc, ReadProgram(processLoc));
			scheduler.setArrival(time, process);
		}
		
		return null;
	}
	
	private String[] ReadProgram(String file) {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String str;

			List<String> list = new ArrayList<String>();
			while((str = in.readLine()) != null){
			    list.add(str);
			}

			in.close();
			String[] result = list.toArray(new String[0]);
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void CPUOut(Process p) {
		String s = p.name + "\n  STATE: " + p.pcb.state.toString() + "\n  LINE: " + (p.pcb.counter + 1); 
		window.printData(s);
	}

}
