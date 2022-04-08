package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class ChangeCO2ClassDialog extends JDialog {
	public static final int CANCEL = 0;
	public static final int OK = 1;
	
	int status;
	JPanel dialogPanel;
	
	JComboBox<Vehicle> vehicleBox;
	JComboBox<Integer> classBox;
	int contClass;
	int closeOption;
	
	public ChangeCO2ClassDialog (Frame parent) {
		super(parent, true);
		initDialog();
	}
	
	private void initDialog() {
		dialogPanel = new JPanel();
		setTitle("Change CO2 Class");
		dialogPanel.setLayout( new BoxLayout( dialogPanel, BoxLayout.Y_AXIS) );
		setContentPane(dialogPanel);

		JPanel helpPanel = new JPanel(new BorderLayout());
		helpPanel.setMaximumSize( new Dimension(1000, 700));
		dialogPanel.add(helpPanel);
	
		JLabel description = new JLabel("<html><p>Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.</p></html>");
		description.setAlignmentX(LEFT_ALIGNMENT);
		helpPanel.add(description);
		
//		dialogPanel.add(description);
		
//		dialogPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		initBoxPanel();
		initOptionButtons();
		
		setMinimumSize(new Dimension(100, 100));
		setPreferredSize(new Dimension(500, 250));
	}
	
	public int open() {
		pack();
		setVisible(true);
		return closeOption;
	}
	
	private void initOptionButtons() {
		// TODO Auto-generated method stub
		JPanel botPannel = new JPanel();
		botPannel.setMaximumSize(new Dimension(1000, 1000));
		dialogPanel.add(botPannel);
		
		JButton cancel = new JButton();
		cancel.setText("Cancel");
		cancel.addActionListener((e) -> {
			closeOption = CANCEL;
			dispose();
		});
		botPannel.add(cancel);
		
		JButton ok = new JButton();
		ok.setText("Ok");
		ok.addActionListener((e) -> {
			closeOption = OK;
			dispose();
		});
		botPannel.add(ok);
	}

	//	private void initTopPanel () {
//		JPanel topPanel = new JPanel();
//		topPanel.setAlignmentY(Component.TOP_ALIGNMENT);
//		dialogPanel.add(topPanel);
//		
//		
//		dialogPanel.add(description);
//	}
//	
	private void initBoxPanel() {
		JPanel boxPanel = new JPanel();
		boxPanel.setMaximumSize(new Dimension(1000, 1000));
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
		
		
		boxPanel.add(new JLabel("Ticks: "));
		SpinnerModel model = new SpinnerNumberModel(ControlPanel.TICK_DEFAULT, //initial value
			  		  ControlPanel.TICK_MIN, //min
			  		  ControlPanel.TICK_MAX, //max
			  		  ControlPanel.TICK_STEP); //step

		JSpinner ticksSpinner = new JSpinner(model);
		boxPanel.add(ticksSpinner);
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
