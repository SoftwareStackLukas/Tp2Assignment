package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import simulator.model.Weather;

@SuppressWarnings("serial")
public class ChangeWeatherDialog extends MyDialog {
	public static final int CANCEL = 0;
	public static final int OK = 1;
	
	private static final String HELP_TEXT = "Schedule an event to change the weather of a road after a given number of simulation ticks from now.";
	
	Controller ctrl;
	
//	JComboBox<Integer> weatherBox;
//	JComboBox<Integer> roadBox;
	JComboBox<Weather> weatherBox;
	JComboBox<Road> roadBox;
	JSpinner ticksSpinner;
	int closeOption;
	
	public ChangeWeatherDialog (JFrame parent, Controller ctrl) {
		super(parent, ctrl);
		this.ctrl = ctrl;
		initDialog();
	}
	
	private void initDialog() {
		setTitle("Change Road Weather");
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
		boxPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		mainPanel.add(boxPanel);
		
		boxPanel.add(new JLabel("Road: "));
		roadBox = new JComboBox<Road>();
		roadBox.setPreferredSize(new Dimension(50, 20));
		boxPanel.add(roadBox);
		
		boxPanel.add(new JLabel("Weather: "));
		weatherBox = new JComboBox<Weather>();
		weatherBox.setPreferredSize(new Dimension(50, 20));
		boxPanel.add(weatherBox);
		
		
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
		for (Road road: map.getRoads()) {
			roadBox.addItem(road);
		}
		
		for (Weather weather: Weather.values()) {
			weatherBox.addItem(weather);
		}
	}
	
	public Road getRoad() {
		return (Road) roadBox.getSelectedItem();
	}
	
	public Weather getWeather() {
		return (Weather) weatherBox.getSelectedItem();
	}
	
	public int getTicks() {
		return (int) ticksSpinner.getValue();
	}
}

