package simulator.view;

import java.awt.BorderLayout;
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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.factories.BuilderBasedFactory;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;
import simulator.model.Weather;

@SuppressWarnings("serial")
class ControlPanel extends JPanel implements TrafficSimObserver {
	private static final String TICKER_LABEL = "Ticks: ";
	static final int TICK_MIN = 1;
	static final int TICK_MAX = 1000;
	static final int TICK_DEFAULT = 10;
	static final int TICK_STEP = 1;
	private static final String TICKER_HELP_TEXT = ("Simulation tick to run: " + ControlPanel.TICK_MIN + "-" + ControlPanel.TICK_MAX);
	
	private Controller ctrl;
	private JFrame mainFrame;
	private JFileChooser fileChooser;
	private JToolBar toolBar;
	private boolean stopped;
	
	JButton loadButton;
	JButton changeCO2Button;
	JButton weatherButton;
	
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	private JLabel tickLabel;
	private JSpinner ticker;
	private JLabel delayLabel;
	private JSpinner delaySpinner;
	
	private volatile Thread thread;
	
	ControlPanel(Controller ctrl, JFrame mainFrame) {
		super(new BorderLayout()); 
		this.ctrl = ctrl;
		this.mainFrame = mainFrame;
		this.initGUI();
	}
	
	//Building the gui
	private void initGUI() {
//		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
//		setBackground(Color.GRAY);
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(MainWindow.mainColor);
//		toolBar.setMinimumSize(new Dimension(80, 40));
		this.add(toolBar, BorderLayout.CENTER);
		
		
//		JButton loadButton = new LoadButton();
		fileChooser = new JFileChooser();
		loadButton = createToolButton("open", "Load File");
		loadButton.addActionListener((e) -> {
			load();
		}); 
		toolBar.add(loadButton);
		
//		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.addSeparator();
		
//		JButton changeCO2Button = new ChangeContClassButton(); co2class changeWeather
		changeCO2Button = createToolButton("co2class", "Change CO2 Class");
		changeCO2Button.addActionListener((e) -> {
			changeCO2();
		});
		toolBar.add(changeCO2Button);
		
//		JButton weatherButton = new ChangeWeatherButton();
		weatherButton = createToolButton("weather", "Change Weather");
		weatherButton.addActionListener((e) -> {
			changeWeather();
		});
		toolBar.add(weatherButton);
		
//		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.addSeparator();
		
		this.initRunStopTick();
		
		
		toolBar.add(Box.createHorizontalGlue());
		
		
		
//		addSeparator.setAlignmentX(JComponent.LEFT_ALIGNMENT);
//		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.addSeparator();
		
		// Exit
		initExitButton();
	}
	

	private JButton createToolButton(String imageName, String tipText) {
		JButton button = new JButton();
		Icon icon = new ImageIcon(Locations.getIconsDir() + imageName + ".png");
		button.setBackground(MainWindow.mainColor);
		button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		button.setIcon(icon);
		button.setToolTipText(tipText);
		return button;
	}
	
	private void initRunStopTick() {
		this.runButton = createToolButton("run", "Runs the game");
		this.runButton.addActionListener((e) -> {
			this.stopped = false;
			enableToolBar(stopped);
			thread = new Thread() {
				public void run() {
					run_sim((int) ticker.getValue(), ((Number) delaySpinner.getValue()).longValue());		 // Is there an other way?		
					enableToolBar(true);
					stopped = true;
				}
			};
			thread.start();
//			run_sim((int) this.ticker.getValue(), (long) delaySpinner.getValue());
		});
		
		this.stopButton = createToolButton("stop", "Stops the game");
		stopButton.setEnabled(false);
		this.stopButton.addActionListener((e) -> {
			if (thread != null) {
				thread.interrupt();
			}
		});
		
		this.tickLabel = new JLabel(ControlPanel.TICKER_LABEL);	
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
		
		this.delayLabel = new JLabel("Delay: ");	
		SpinnerModel delaySpinnerModel = new SpinnerNumberModel(10, //initial value
				   								    0, //min
				   								  	1000, //max
				   								  	1); //step
		this.delaySpinner = new JSpinner(delaySpinnerModel);
		this.delaySpinner.setToolTipText("Delay: ");
		this.delaySpinner.setToolTipText("Simulation delay");
		this.delaySpinner.setMaximumSize(new Dimension(80, 40));
		this.delaySpinner.setMinimumSize(new Dimension(80, 40));
		this.delaySpinner.setPreferredSize(new Dimension(80, 40));
		
		Dimension sepDim = new Dimension(5, 0);
		toolBar.add(runButton);
		toolBar.add(stopButton);
		toolBar.addSeparator(sepDim);
		toolBar.add(tickLabel);
		toolBar.add(ticker);
		toolBar.addSeparator(sepDim);
		toolBar.add(delayLabel);
		toolBar.add(delaySpinner);
	}
	
	private void run_sim(int n, long delay) {
		while ( n > 0 && thread.getState() == Thread.State.RUNNABLE) {
			// 1. execute the simulator one step, i.e., call method _ctrl.run(1) and handle exceptions if any
			try {
				ctrl.run(1);
			} catch (Exception e) {
				this.showErrorMessage(e);
				stopped = true;
				return;
			}
			// 2. sleep the current thread for ?delay? milliseconds
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// Thread was interrupted but if I catch this exception it needs to be interrupted again
//				thread.interrupt(); 	// This does not work somehow 
				return;
			}
			n--;
		}
			
//		if (n > 0 && !stopped) {
//			try {
//				ctrl.run(1);
//			} catch (Exception e) {
//				this.showErrorMessage(e);
//				stopped = true;
//				return;
//			}
//			
//			SwingUtilities.invokeLater(() -> run_sim(n - 1));
//		} else {
//			enableToolBar(true);
//			stopped = true;
//		}
	}		
	
	private void enableToolBar(Boolean enable) {
		loadButton.setEnabled(enable);
		changeCO2Button.setEnabled(enable);
		weatherButton.setEnabled(enable);
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
		exitButton = createToolButton("exit", "Quit");
		exitButton.addActionListener((e) -> {
			int n = JOptionPane.showOptionDialog((Frame) SwingUtilities.getWindowAncestor(this),
					"Are sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					null, null);
			if (n == 0) {
				System.exit(0);
			}
		});
		toolBar.add(exitButton);
	}
	
	private void load() {
		ctrl.reset();
		try {
			File file = new File("resources/examples/ex1.json");	// Debug only
			int returnVal = JFileChooser.APPROVE_OPTION;
//			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				File file = fileChooser.getSelectedFile();
				ctrl.loadEvents(new FileInputStream(file));
			} else {
				System.out.println("Load cancelled by user.");
			}
		} catch (IOException e){
			System.out.println("File doesn't exist"); 
		}
	}
	
	private void changeWeather() {
		ChangeWeatherDialog dialog = new ChangeWeatherDialog(mainFrame, ctrl);
		int closeOption = dialog.open();
		if (closeOption == CO2Dialog.OK) {
			ArrayList<Pair<String, Weather>> weathers = new ArrayList<Pair<String, Weather>>();
			weathers.add(new Pair<String, Weather>(dialog.getRoad().getId(), dialog.getWeather()));
			ctrl.addEvent(new SetWeatherEvent(dialog.getTicks(), weathers));
		} else {
			// Do nothing I guess
		}
	}	
	
	private void changeCO2() {
		CO2Dialog dialog = new CO2Dialog(mainFrame, ctrl); // Maybe should be outside of this method
		int closeOption = dialog.open();
		if (closeOption == CO2Dialog.OK) {
			ArrayList<Pair<String, Integer>> changes = new ArrayList<Pair<String, Integer>>();
			changes.add(new Pair<String, Integer>(dialog.getVehicle().getId(), dialog.getContClass()));
			ctrl.addEvent(new SetContClassEvent(dialog.getTicks(), changes));
		} else {
			// Do nothing I guess
		}
	}
	//gui end 
	
	
	// I just dont understand why should the ControlPanel be an observer when it observes nothing
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
