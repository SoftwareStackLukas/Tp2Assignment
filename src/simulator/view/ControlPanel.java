package simulator.view;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Dimension;
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
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.factories.BuilderBasedFactory;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
import tp.examples.swing.graphviewer.ControlPanel;

@SuppressWarnings("serial")
class ControlPanel extends JPanel implements TrafficSimObserver {
	private static final String TICKER_LABEL = "Ticks:";
	private static final int TICK_MIN = 1;
	private static final int TICK_MAX = 1000;
	private static final int TICK_DEFAULT = 10;
	private static final int TICK_STEP = 1;
	private static final String TICKER_HELP_TEXT = ("Simulation tick to run: " + ControlPanel.TICK_MIN + "-" + ControlPanel.TICK_MAX);
	
	private Controller ctrl;
	private JFrame mainFrame;
	private JFileChooser fileChooser;
	private JToolBar tb;
	private boolean stopped;

	private JButton runButton;
	private JButton stopButton;
	private JLabel tickLabel;
	private JSpinner ticker;
	private JButton exitButton;
	
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
		
		this.initRunStopTick();
		this.initExitButton();
	}
	

	private JButton createToolButton(String imageName, String tipText) {
		JButton button = new JButton();
		Icon icon = new ImageIcon(Locations.getIconsDir() + imageName + ".png");
		button.setIcon(icon);
		button.setToolTipText(tipText);
		return button;
	}
	
	private void initRunStopTick() {
		this.runButton = new JButton(new ImageIcon("resources/icons/run.png"));
		this.runButton.setHorizontalAlignment(0);
		this.runButton.setToolTipText("Runs the game");
		this.runButton.setSize(new Dimension(60,60));
		this.runButton.addActionListener((e) -> {
			this.stopped = true;
			enableToolBar(false);
			run_sim((Integer)this.ticker.getValue());
		});
		
		this.stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
		this.stopButton.setHorizontalAlignment(0);
		this.stopButton.setToolTipText("Stops the game");
		this.stopButton.setSize(new Dimension(60,60));
		this.stopButton.addActionListener((e) -> {
			this.stopped = true;
		});
		
		this.tickLabel = new JLabel(ControlPanel.TICKER_LABEL);	
		this.tickLabel.setHorizontalAlignment(0);
		SpinnerModel model = new SpinnerNumberModel(ControlPanel.TICK_DEFAULT, //initial value
				   								  		  ControlPanel.TICK_MIN, //min
				   								  		  ControlPanel.TICK_MAX, //max
				   								  		  ControlPanel.TICK_STEP); //step
		this.ticker = new JSpinner(model);
		this.ticker.setToolTipText(TICKER_LABEL);
		this.ticker.setToolTipText(ControlPanel.TICKER_HELP_TEXT);
		this.ticker.setMaximumSize(new Dimension(80, 40));
		this.ticker.setMinimumSize(new Dimension(80, 40));
		this.ticker.setPreferredSize(new Dimension(80, 40));
		
		this.add(runButton);
		this.add(stopButton);
		this.add(tickLabel);
		this.add(ticker);
	}
	
	private void run_sim(int n) {
		if (n > 0 && !stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				this.showErrorMessage(e);
				stopped = true;
				return;
			}
			
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			enableToolBar(true);
			stopped = true;
		}
	}		
	
	private void enableToolBar(Boolean enable) {
		//Lucas fill here the reset of the button 
		//
		//
		this.runButton.setEnabled(enable);
		this.stopButton.setEnabled(!enable);
		this.exitButton.setEnabled(enable);
	}
	
	private void showErrorMessage(Exception e) {
		JOptionPane.showMessageDialog(this,
			    e.toString(),
			    "An error occured",
			    JOptionPane.ERROR_MESSAGE);
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
