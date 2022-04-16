package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
public class CO2Dialog extends MyDialog {
	
	private static final String HELP_TEXT = "Schedule an event to change the weather of a road after a given number of simulation ticks from now.";

	
	JComboBox<Vehicle> vehicleBox;
	JComboBox<Integer> classBox;
	JSpinner ticksSpinner;
	
	int closeOption;
	
	public CO2Dialog (JFrame parent, Controller ctrl) {
		super(parent, ctrl);
		initDialog();
	}
	
	private void initDialog() {
		setTitle("Change CO2 Class");
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS) );
		setContentPane(mainPanel);
		
		initHelpPanel(HELP_TEXT);
		initBoxPanel();
		initOptionButtons();
		
		setMinimumSize(new Dimension(100, 100));
		setPreferredSize(new Dimension(500, 250));
	}
	
	private void initBoxPanel() {
		JPanel boxPanel = new JPanel();
		boxPanel.setMaximumSize(new Dimension(1000, 1000));
		mainPanel.add(boxPanel);
		
		boxPanel.add(new JLabel("CO2 Class: "));
		classBox = new JComboBox<Integer>(new Integer[] {0,1,2,3,4,5,6,7,8,9,10});
		classBox.setPreferredSize(new Dimension(50, 20));
		boxPanel.add(classBox);
		
		boxPanel.add(new JLabel("Vehicle: "));
		boxPanel.add(vehicleBox);
		
		
		boxPanel.add(new JLabel("Ticks: "));
		SpinnerModel model = new SpinnerNumberModel(ControlPanel.TICK_DEFAULT, //initial value
			  		  ControlPanel.TICK_MIN, //min
			  		  ControlPanel.TICK_MAX, //max
			  		  ControlPanel.TICK_STEP); //step

		ticksSpinner = new JSpinner(model);
		boxPanel.add(ticksSpinner);
	}
	
	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		super.onRegister(map, events, time);
		vehicleBox = new JComboBox<Vehicle>();
		for (Vehicle vehicle: map.getVehicles()) {
			vehicleBox.addItem(vehicle);
		}
		
	}
	
	public int getContClass() {
		return (int) classBox.getSelectedItem();
	}
	
	public Vehicle getVehicle() {
		return (Vehicle) vehicleBox.getSelectedItem();
	}
	
	public int getTicks() {
		return (int) ticksSpinner.getValue() + timeAtRegister;
	}

}
