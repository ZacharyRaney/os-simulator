import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class MainWindow {

	private JFrame frame;
	private JTextField txtConsoleinput;
	private JTable tableTaskManager;
	private JTextArea txtrConsoleoutput;
	
	private static Computer computer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		computer = new Computer();
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
		
		tableTaskManager = new JTable();
		tableTaskManager.setBounds(10, 10, 614, 180);
		frame.getContentPane().add(tableTaskManager);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 198, 614, 201);
		frame.getContentPane().add(scrollPane);
		
		txtrConsoleoutput = new JTextArea();
		txtrConsoleoutput.setEditable(false);
		scrollPane.setViewportView(txtrConsoleoutput);
	}
}
