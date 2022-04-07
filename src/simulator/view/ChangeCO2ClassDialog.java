package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.model.Road;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class ChangeCO2ClassDialog extends JDialog {
	int status;
	JDialog dialog;
	
	JComboBox<Vehicle> vehicleBox;
	JComboBox<Integer> classBox;
	int contClass;
	
	
	public ChangeCO2ClassDialog (Frame parent) {
		super(parent, true);
		initDialog();
	}
	
	private void initDialog() {
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout( new BoxLayout( dialogPanel, BoxLayout.Y_AXIS) );
		
		JLabel description = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.");
		description.setAlignmentY(Component.TOP_ALIGNMENT);
		dialogPanel.add(description);
//		vehicleBox = new JComboBox<Vehicle>();
		
		JPanel boxPanel = new JPanel();
		boxPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		dialogPanel.add(boxPanel);
		
		boxPanel.add(new JLabel("Vehicle: "));
		classBox = new JComboBox<Integer>(new Integer[] {0,1,2,3,4,5,6,7,8,9,10});
		classBox.setPreferredSize(new Dimension(50, 20));
		classBox.addActionListener((e) -> {
			contClass = (int) classBox.getSelectedItem();
		});
		boxPanel.add(classBox);
		
		boxPanel.add(new JLabel("CO2 Class: "));
		classBox = new JComboBox<Integer>(new Integer[] {0,1,2,3,4,5,6,7,8,9,10});
		boxPanel.add(classBox);
		
		setContentPane(dialogPanel);
		setMinimumSize(new Dimension(100, 100));
		
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dialogPanel.setSize(640, 480);
		pack();
		setVisible(true);
	}	
	
//	public int open() {
//		setLocation(getParent().getLocation().x + 50, getParent().getLocation().y + 50);
//		pack();
//		setVisible(true);
//		return _status;
//	}
	
	public int getContClass() {
		return contClass;
	}

}
