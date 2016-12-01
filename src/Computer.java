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
			String s = "Running processes: (NAME|STATE|LINE)";
			for(Process p : window.processes) {
				if (!(p.pcb.state == PCB.State.EXIT || p.pcb.state == PCB.State.NEW)) {
					s += ("\n  " + p.name + " " + p.pcb.state.toString() + " " + (p.pcb.counter + 1));
				}
			}
			return s;
		} else if(string.startsWith("MEM")) {
			return Integer.toString(scheduler.memory.getAvailable());
		} else if(string.startsWith("LOAD")) {
			if(string.length() < 6)
				return "FILE ERROR";
			if(string.endsWith(".p")) {
				String s[] = ReadProgram(string.substring(5));
				if (s != null) {
					scheduler.addProcess(new Process(string.substring(5), s));
				} else return "FILE ERROR";
			} else if(string.endsWith(".job") && readJobFile(string.substring(5))) {
				return "Added JobFile: " + string.substring(5);
			} else return "FILE ERROR";
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
			window.reset();
			return "Simulation reset!";
		}
		return "INVALID COMMAND";
	}
	
	private Boolean readJobFile(String file) {
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
			return false;
		} catch (IOException e) {
			return false;
		}
		
		for(String s : list) {
			int time = Integer.valueOf(s.split(" ")[1]);
			String processLoc = s.split(" ")[2];
			String instructions[] = ReadProgram(processLoc);
			if(instructions == null) {
				window.printData("CANNOT FIND FILE " + processLoc);
			} else {
				Process process = new Process(processLoc, instructions);
				scheduler.setArrival(time, process);
			}
		}
		
		return true;
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
			if(list.isEmpty()) {
				return null;
			}
			String[] result = list.toArray(new String[0]);
			return result;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	public void CPUOut(Process p) {
		String s = p.name + "\n  STATE: " + p.pcb.state.toString() + "\n  LINE: " + (p.pcb.counter + 1); 
		window.printData(s);
	}

}
