import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class MainWindow {

	private JFrame frame;
	private JTextField txtConsoleinput;
	private JTable tableTaskManager;
	private JTextArea txtrConsoleoutput;
	
	private static Computer computer;
	public ArrayList<Process> processes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					computer = new Computer(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		processes = new ArrayList<>();
		
		txtConsoleinput = new JTextField();
		txtConsoleinput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result;
				switch(txtConsoleinput.getText()) {
					case "EXIT":
						System.exit(0);
						break;
					default:
						result = computer.Input(txtConsoleinput.getText());
						txtrConsoleoutput.setText(txtrConsoleoutput.getText() + result + "\n");
						txtConsoleinput.setText("");
						break;
				}
			}
		});
		txtConsoleinput.setBounds(10, 410, 505, 20);
		frame.getContentPane().add(txtConsoleinput);
		txtConsoleinput.setColumns(10);
		
		JButton btnConsolesubmit = new JButton("Submit");
		btnConsolesubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String result;
				switch(txtConsoleinput.getText()) {
					case "EXIT":
						System.exit(0);
						break;
					default:
						result = computer.Input(txtConsoleinput.getText());
						txtrConsoleoutput.setText(txtrConsoleoutput.getText() + result + "\n");
						txtConsoleinput.setText("");
						break;
				}
			}
		});
		btnConsolesubmit.setBounds(525, 409, 99, 23);
		frame.getContentPane().add(btnConsolesubmit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 198, 614, 201);
		frame.getContentPane().add(scrollPane);
		
		txtrConsoleoutput = new JTextArea();
		txtrConsoleoutput.setEditable(false);
		scrollPane.setViewportView(txtrConsoleoutput);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 614, 180);
		frame.getContentPane().add(scrollPane_1);
		
		tableTaskManager = new JTable();
		scrollPane_1.setViewportView(tableTaskManager);
		tableTaskManager.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Process", "State", "Line"
			}
		));
	}
	
	public void printData(String s) {
		txtrConsoleoutput.setText(txtrConsoleoutput.getText() + s + "\n");
	}
	
	public void updateTaskManager(Process p) {
		if(processes.contains(p)) {
			int index;
			index = processes.indexOf(p);
			tableTaskManager.getModel().setValueAt(p.pcb.state, index, 1);
			tableTaskManager.getModel().setValueAt((p.pcb.counter + 1), index, 2);
		} else {
			processes.add(p);
			((DefaultTableModel)tableTaskManager.getModel()).addRow(new Object[]{p.name, p.pcb.state, (p.pcb.counter + 1)});
		}
	}
	
	public void reset() {
		txtrConsoleoutput.setText("");
		((DefaultTableModel)tableTaskManager.getModel()).setNumRows(0);
		processes.clear();
	}
}
