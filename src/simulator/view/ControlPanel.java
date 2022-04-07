package simulator.view;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.factories.BuilderBasedFactory;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
import tp.examples.swing.graphviewer.ControlPanel;

@SuppressWarnings("serial")
class ControlPanel extends JPanel implements TrafficSimObserver {

	private Controller ctrl;
	private JFrame mainFrame;
	private JFileChooser fileChooser;
	private JToolBar tb;
	
	ControlPanel(Controller ctrl, JFrame mainFrame) {
		this.ctrl = ctrl;
		this.mainFrame = mainFrame;
		this.initGUI();
	}
	
	//Building the gui
	private void initGUI() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
		setBackground(Color.GRAY);
		
		tb = new JToolBar();
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
		
		// Exit
		initExitButton();
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
		JButton exit = createToolButton("exit", "Quit");
		exit.setSize(10,10); //Which size should we take? 
		exit.addActionListener((e) -> {
			int n = JOptionPane.showOptionDialog((Frame) SwingUtilities.getWindowAncestor(this),
					"Are sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					null, null);

			if (n == 0) {
				System.exit(0);
			}
		});
		tb.add(exit);
	}
	private void changeCO2() {
		// TODO Auto-generated method stub
		System.out.println("Changing CO2");
		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(mainFrame);
		System.out.println(dialog.getContClass());
//		int option = JOptionPane.showOptionDialog(mainPanel, "Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.", "Change CO2 Class", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
//		if (option == JOptionPane.CLOSED_OPTION) {
//			System.out.println("Closed");
//		} else {
//			
//		}

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
		System.out.println("Changing Weather");
	}	

	private ImageIcon loadImage(String file) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(ControlPanel.class.getResource(file)));
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
