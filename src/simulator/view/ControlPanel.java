package simulator.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.factories.BuilderBasedFactory;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

@SuppressWarnings("serial")
class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller ctrl;
	private JPanel mainPanel;
	private JFileChooser fileChooser;
	
	ControlPanel(Controller ctrl, JPanel mainPanel) {
		this.ctrl = ctrl;
		this.mainPanel = mainPanel;
		this.initGUI();
	}
	
	//Building the gui
	private void initGUI() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
		setBackground(Color.GRAY);
		
		JToolBar tb = new JToolBar();
		this.add(tb);
		
//		JButton loadButton = new LoadButton();
		fileChooser = new JFileChooser();
		JButton loadButton = createToolButton("open", "Load File");
		loadButton.addActionListener((e) -> {
			load();
		});
		tb.add(loadButton);
		
//		JButton changeCO2Button = new ChangeContClassButton(); co2class changeWeather
		JButton changeCO2Button = createToolButton("co2class", "Change CO2 Class");
		changeCO2Button.addActionListener((e) -> {
			changeCO2();
		});
		tb.add(changeCO2Button);
		
//		JButton weatherButton = new ChangeWeatherButton();
		JButton weatherButton = createToolButton("weather", "Change Weather");
		weatherButton.addActionListener((e) -> {
			changeWeather();
		});
		tb.add(weatherButton);
//		setVisible(true);
	}
	

	private JButton createToolButton(String imageName, String tipText) {
		JButton button = new JButton();
		Icon icon = new ImageIcon(Locations.getIconsDir() + imageName + ".png");
		button.setIcon(icon);
		button.setToolTipText(tipText);
		return button;
	}
	
	//Exit the Simulator -- This method has to be checked
	private void initExitButton() {
		JButton exit = new JButton(new ImageIcon("resources/icons/exit.png"));
		exit.setSize(10,10); //Which size should we take? 
		exit.addActionListener(new ActionListener() {
			Object[] options = {"Yes", "No"};
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(null, "Are you sure to quickt the simulator?", "Quit", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, (Object[]) options[0], (Object[]) options[1]);
				if (n == 0) System.exit(n);
			}
		});
	}
	private void changeCO2() {
		// TODO Auto-generated method stub
		System.out.println("Changing CO2");
	}
	
	private void load() {
//		ctrl.reset();s
//		try {
			// Should open a dialog for selecting an events file
//			InputStream in = new FileInputStream("resources2/examples/ex1.json");
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				System.out.println("Loading: " + file.getName());
//				ctrl.loadEvents(file);
			} else {
				System.out.println("Load cancelled by user.");
			}
//		} catch (IOException e){
//			System.out.println("File doesn't exist");
//		}
		System.out.println("Im loading");
	}
	
	private void changeWeather() {
		JOptionPane panel = new JOptionPane();
		int option = panel.showOptionDialog(mainPanel, "Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.", "Change CO2 Class", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (option == JOptionPane.CLOSED_OPTION)
		System.out.println("Changing Weather");
	}
	
	//gui end 
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	//Observer methods end
}
