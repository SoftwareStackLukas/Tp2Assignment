package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class SpeedDialog extends MyDialog {
	private final static String HELP_TEXT = "Select a speed limit and press UPDATE to show the vehicels that exceed this speed in each tick.";
	private final static String TICKER_LABEL = "Speed Limit";
	private final static String TICKER_HELP_TEXT = "Increase or degrease the Limit";
	private final static int TICK_DEFAULT = 60;
	private final static int TICK_MIN = 0;
	private final static int TICK_MAX = Integer.MAX_VALUE;
	private final static int TICK_STEP = 1;
	
	private int sLimit;
	private JLabel speedLimit;
	private JSpinner speedSpinner;
	private JButton close;
	private JButton update;
	private innerTable table;
	private GridBagConstraints constraints;
	
	//Vehicle logic
	private int tickCounter = 1;
	private Map<Integer, List<Pair<Integer, String>>> ticks;
	
	public SpeedDialog(JFrame parent, Controller ctrl) {
		super(parent, ctrl);
		initDialog();
		this.ticks = new HashMap<>();
		this.table = new innerTable();
	}
	
	private void initDialog() {
		setTitle("Vehicles Speed History");
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS) );
		setContentPane(mainPanel);
		initHelpPanel(SpeedDialog.HELP_TEXT);
		
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setAlignmentY(JComponent.TOP_ALIGNMENT);
		this.mainPanel.add(spinnerPanel);
		//Add label + spinner
		this.speedLimit = new JLabel(SpeedDialog.TICKER_LABEL);	
		SpinnerModel model = new SpinnerNumberModel(SpeedDialog.TICK_DEFAULT, //initial value
				   								    SpeedDialog.TICK_MIN, //min
				   								  	SpeedDialog.TICK_MAX, //max
				   								  	SpeedDialog.TICK_STEP); //step
		this.speedSpinner = this.initSpinner(model);
		spinnerPanel.add(this.speedLimit);
		spinnerPanel.add(this.speedSpinner);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		this.mainPanel.add(buttonPanel);
		//JPanel buttonPanel = new JPanel(new BorderLayout());
		this.close = new JButton("Close");
		this.close.addActionListener( (e) -> {
			closeOption = CANCEL;
			//dispose();
			this.setVisible(false);
		});
		this.close.setSize(new Dimension (50,30));
		buttonPanel.add(this.close);
		
		//Button has to be added 
		this.update = new JButton("Update");
		this.update.setLocation(30, 50);
		this.update.addActionListener((e) -> {
			table.fireTableStructureChanged();
			table.fireTableDataChanged();
		});
		this.update.setSize(new Dimension (50,30));
		buttonPanel.add(update);
		
		JPanel viewsPanel = new JPanel(new GridBagLayout());
		//Build gridconstraints
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		//End Build
		
		JTable t = new JTable(new innerTable());
		t.setFillsViewportHeight(true);
	    JPanel eventsView = createViewPanel(t, "Events");
	    viewsPanel.add(eventsView, constraints);
	    this.mainPanel.add(viewsPanel);
	    
		setMinimumSize(new Dimension(100, 500));
		setPreferredSize(new Dimension(500, 500));
	}
	
	private JSpinner initSpinner(SpinnerModel model) {
		JSpinner speedSpinner = new JSpinner(model);
		speedSpinner.setToolTipText(TICKER_LABEL);
		speedSpinner.setToolTipText(SpeedDialog.TICKER_HELP_TEXT);
		speedSpinner.setMaximumSize(new Dimension(80, 40));
		speedSpinner.setMinimumSize(new Dimension(80, 40));
		speedSpinner.setPreferredSize(new Dimension(80, 40));
		return speedSpinner;
	}
	
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		//Adding cars to a list
		LinkedList<Pair<Integer, String>> ls = new LinkedList<>();
		for (Vehicle v : map.getVehicles()) {
			ls.add(new Pair<Integer, String>(v.getSpeed(), v.getId()));
		}
		this.ticks.put(++this.tickCounter, ls);
	}
	
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.ticks.clear();
		this.tickCounter = 1;		
	}
	
	@SuppressWarnings("serial")
	private class innerTable extends AbstractTableModel {
		private final String[] columnNames = {"Tick", "Vehicle"};
		
		@Override
		public int getRowCount() {
			return SpeedDialog.this.ticks != null ? SpeedDialog.this.ticks.size() : 0;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String result = "";
			if (columnIndex == 0) {
				result = Integer.toString(rowIndex + 1);
			} else if (columnIndex == 1) {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				List<Pair<Integer, String>> ls = SpeedDialog.this.ticks.get(rowIndex);
				for (Pair<Integer, String> p : ls) {
					if (SpeedDialog.this.sLimit < p.getFirst()) sb.append(p.getSecond() + ",");
				}
				int length = sb.length();
				sb.replace(length, length, "");
				sb.append("]");
				result = sb.toString();
			}
			return result;
		}
		
		@Override
		public String getColumnName(int col) {
			return columnNames[col].toString();
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), title,
													TitledBorder.LEFT, TitledBorder.TOP)); 
		
		JScrollPane sp = new JScrollPane(c);
		sp.setBorder(BorderFactory.createEmptyBorder());
		p.setBackground(MainWindow.mainColor);
		p.add(sp);
		p.getAutoscrolls();
		
		return p;
	}

	@Override
	public int open() {
		this.mainPanel = new JPanel();
		this.initDialog();
		this.closeOption = MyDialog.OK;
		setVisible(true);
		pack();
		return closeOption;
	}
	
}
