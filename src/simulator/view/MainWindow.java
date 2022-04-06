package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private Controller ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		this.ctrl = ctrl;
		this.initGUI();
		this.setFrameIcon();
	}
	
	private void initGUI() {		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
//		
		// Like this
		ControlPanel ctrlPanel = new ControlPanel(this.ctrl);
		mainPanel.add(ctrlPanel, BorderLayout.PAGE_START);
		// Or like this?
		mainPanel.add(new StatusBar(this.ctrl), BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1,2));	
		mainPanel.add(viewsPanel);
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
	
		//tables
		//EventsTableModel
//	    JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(this.ctrl)), "Events");
//		eventsView.setPreferredSize(new Dimension(500,200));
//		// VehiclesTableModel
//	    JPanel vehicleView = createViewPanel(new JTable(new VehiclesTableModel(this.ctrl)), "Vehicles");
//	    vehicleView.setPreferredSize(new Dimension(500,200));
//		// RoadsTableModel
//	    JPanel roadView = createViewPanel(new JTable(new RoadsTableModel(this.ctrl)), "Roads");
//	    roadView.setPreferredSize(new Dimension(500,200));
//		// JunctionsTableModel
//	    JPanel junctionView = createViewPanel(new JTable(new JunctionsTableModel(this.ctrl)), "Junctions");
//	    junctionView.setPreferredSize(new Dimension(500,200));
//		
//		//maps
//	    //MapComponent
//		JPanel mapView = createViewPanel(new MapComponent(this.ctrl), "Map");
//		mapView.setPreferredSize(new Dimension(500, 400));
//		mapsPanel.add(mapView);
//		//MapByRoadComponent
//		JPanel mapRoadView = createViewPanel(new MapByRoadComponent(this.ctrl), "MapRoad");
//		mapRoadView.setPreferredSize(new Dimension(500, 400));
//		mapRoadView.add(mapRoadView); 
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		this.pack();
		this.setSize(640, 480);
		this.setVisible(true);
		
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), title,
													TitledBorder.LEFT, TitledBorder.TOP)); 
		p.add(new JScrollPane(c));
		return null;
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
