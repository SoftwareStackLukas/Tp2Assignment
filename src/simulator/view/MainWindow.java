package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private Controller ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		this.ctrl = ctrl;
		this.initGUI();
	}
	
	public void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		mainPanel.add(new ControlPanel(this.ctrl), BorderLayout.PAGE_START);
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
	    JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(this.ctrl)), "Events");
		eventsView.setPreferredSize(new Dimension(500,200));
		// TODO add other tables
		// VehiclesTableModel
		// RoadsTableModel
		// JunctionsTableModel
		
		//maps
		JPanel mapView = createViewPanel(new MapComponent(this.ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		// TODO add a map for MapByRoadComponent
		// ...
	
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		//TODO add a framed border to p with title
		p.add(p)
		p.add(new JScrollPane(c));
		return null;
	}
	
}
