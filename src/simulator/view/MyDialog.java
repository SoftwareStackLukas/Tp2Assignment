package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class MyDialog extends JDialog implements TrafficSimObserver{
	public static final int CANCEL = 0;
	public static final int OK = 1;
	
	JPanel mainPanel;
	int closeOption;
	
	int timeAtRegister;
	
	public MyDialog(JFrame parent, Controller ctrl) {
		super(parent, true);
		ctrl.addObserver(this);
		mainPanel = new JPanel();
	}
	
	protected void initOptionButtons() {
		JPanel botPannel = new JPanel();
		botPannel.setMaximumSize(new Dimension(1000, 1000));
		botPannel.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
		mainPanel.add(botPannel);
		
		JButton cancel = new JButton();
		cancel.setText("Cancel");
		cancel.addActionListener((e) -> {
			closeOption = CANCEL;
			dispose();
		});
		botPannel.add(cancel);
		
		JButton ok = new JButton();
		ok.setText("Ok");
		ok.addActionListener((e) -> {
			closeOption = OK;
			dispose();
		});
		botPannel.add(ok);
	}
	
	
	protected void initHelpPanel(String text) {
		JPanel helpPanel = new JPanel(new BorderLayout());
		helpPanel.setMaximumSize( new Dimension(1000, 700));
		helpPanel.setAlignmentY(JComponent.TOP_ALIGNMENT);
		mainPanel.add(helpPanel);

//		JLabel description = new JLabel("<html><p>Schedule an event to change the weather of a road after a given number of simulation ticks from now.</p></html>");
		JLabel description = new JLabel("<html><p>" + text + "</p></html>");
		helpPanel.add(description, BorderLayout.CENTER);
	}
	
	public int open() {
		pack();
		setVisible(true);
		return closeOption;
	}

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
		timeAtRegister = time;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
