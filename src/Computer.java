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
	public Scheduler scheduler;
	
	public Computer() {
		scheduler = new Scheduler();
	}
	
	public String Input(String string) {
		//PROC, MEM, LOAD, EXE, RESET
		if(string.startsWith("PROC")) {
			
		} else if(string.startsWith("MEM")) {
			return Integer.toString(scheduler.memory.getAvailable());
		} else if(string.startsWith("LOAD")) {
			Process process = new Process(ReadProgram(string.substring(5)));
			scheduler.addProcess(process);
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
			scheduler = new Scheduler();
			return "Simulation reset!";
		}
		return "INVALID COMMAND";
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

}
