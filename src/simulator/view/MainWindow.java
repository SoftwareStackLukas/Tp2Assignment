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
		mainPanel.add(new StatusBar(this.ctrl), BorderLayout.PAGE_END);
		

		constraints = new GridBagConstraints();
		initViewsPanel();
		mainPanel.add(viewsPanel);
		
		//Common constraints
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
	    
		//MapComponent
		JPanel mapView = createViewPanel(new MapComponent(this.ctrl), "Map");
		constraints.gridx = 1;
		constraints.gridy = 0;
		viewsPanel.add(mapView, constraints);
		
		//MapByRoadComponent
		JPanel mapRoadView = createViewPanel(new MapByRoadComponent(this.ctrl), "MapRoad");
		constraints.gridx = 1;
		constraints.gridy = 2;
		viewsPanel.add(mapRoadView, constraints);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	
		this.pack();
		this.setSize(800, 700);
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
		JTable t = new JTable(new EventsTableModel(this.ctrl));
		t.setFillsViewportHeight(true);
	    JPanel eventsView = createViewPanel(t, "Events");
		viewsPanel.add(eventsView, constraints);
//		
		// VehiclesTableModel
		t = new JTable(new VehiclesTableModel(this.ctrl));
		t.setFillsViewportHeight(true);
	    JPanel vehicleView = createViewPanel(t, "Vehicles");
	    constraints.gridy++;
	    viewsPanel.add(vehicleView, constraints);
	    
		// RoadsTableModel
	    t = new JTable(new RoadsTableModel(this.ctrl));
	    t.setFillsViewportHeight(true);
	    JPanel roadView = createViewPanel(t, "Roads");
	    constraints.gridy++;
	    viewsPanel.add(roadView, constraints);
		
		// JunctionsTableModel
	    t = new JTable(new JunctionsTableModel(this.ctrl));
	    t.setFillsViewportHeight(true);
	    JPanel junctionView = createViewPanel(t, "Junctions");
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
