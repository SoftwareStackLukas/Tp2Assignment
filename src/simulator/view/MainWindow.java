package simulator.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.io.File;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private Controller ctrl;
	
	JPanel viewsPanel;
	private GridBagConstraints constraints;
	
	public static final Color mainColor = new Color(240, 240, 240);
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		this.ctrl = ctrl;
		this.initGUI();
		this.setFrameIcon();
	}
	
	private void initGUI() {		
		JPanel mainPanel = new JPanel(new BorderLayout());
//		mainPanel.setBackground(MainWindow.mainColor);
		this.setContentPane(mainPanel);
//		
		// Like this
		ControlPanel ctrlPanel = new ControlPanel(this.ctrl, this);
		mainPanel.add(ctrlPanel, BorderLayout.PAGE_START);
		// Or like this?
//		mainPanel.add(new StatusBar(this.ctrl), BorderLayout.PAGE_END);
		

		constraints = new GridBagConstraints();
		initViewsPanel();
		mainPanel.add(viewsPanel);

//		tablesPanel.add(junctionView);
	    
	    
		//Common constraints
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
	    
		//MapComponent
		JPanel mapView = new MapComponent(this.ctrl);
		mapView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Map",
				TitledBorder.LEFT, TitledBorder.TOP)); 
		constraints.gridx = 1;
		constraints.gridy = 0;
		viewsPanel.add(mapView, constraints);
		
//		//MapByRoadComponent
		JPanel mapRoadView = new MapByRoadComponent(this.ctrl);
		mapRoadView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "MapRoad",
				TitledBorder.LEFT, TitledBorder.TOP)); 
		constraints.gridx = 1;
		constraints.gridy = 2;
		viewsPanel.add(mapRoadView, constraints);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		this.pack();
		this.setSize(640, 480);
		this.setVisible(true);
	}
	
	private void initViewsPanel() {
		// General settings
		viewsPanel = new JPanel(new GridBagLayout());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;

//		EventsTableModel
	    JPanel eventsView = createViewPanel(new PersonalizedTable(new EventsTableModel(this.ctrl)), "Events");
		viewsPanel.add(eventsView, constraints);
//		
		// VehiclesTableModel
	    JPanel vehicleView = createViewPanel(new PersonalizedTable(new VehiclesTableModel(this.ctrl)), "Vehicles");
	    constraints.gridy++;
	    viewsPanel.add(vehicleView, constraints);
	    
		// RoadsTableModel
	    JPanel roadView = createViewPanel(new PersonalizedTable(new RoadsTableModel(this.ctrl)), "Roads");
	    constraints.gridy++;
	    viewsPanel.add(roadView, constraints);
		
		// JunctionsTableModel
	    JPanel junctionView = createViewPanel(new PersonalizedTable(new JunctionsTableModel(this.ctrl)), "Junctions");
	    constraints.gridy++;
	    viewsPanel.add(junctionView, constraints);
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), title,
													TitledBorder.LEFT, TitledBorder.TOP)); 
		
		JScrollPane sp = new JScrollPane(c);
		sp.setBorder(BorderFactory.createEmptyBorder());
		p.setBackground(MainWindow.mainColor);
		p.add(sp);
		
		return p;
	}
	
	//I did this because I do not like the standard frame icon from java!
	private void setFrameIcon() {
		// Why the try catch though?
		try {
			this.setIconImage(ImageIO.read(new File("resources/icons/car_front.png")));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
