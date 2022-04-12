package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

class StatusBar extends JPanel implements TrafficSimObserver {
	private static final String START_TIME = "Time: 0";
	private static final String START_EVENT = "Until now no event has happend";
	private static final String TIME = "Time: ";
	private static final String ADD_EVENT = "Event added ";
	private static final String NOTHING = "";
	
	//Elements of the JPanel
	private JLabel txtTime;
	private JLabel txtEvent;
	
	public StatusBar(Controller ctrl) {
		ctrl.addObserver(this);
		this.initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.txtTime = new JLabel(StatusBar.START_TIME);
		txtTime.setMinimumSize(new Dimension(100, 20));
		
		
		this.txtEvent = new JLabel(StatusBar.START_EVENT);
		
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	
		this.add(txtTime);
		this.add(new JSeparator(SwingUtilities.VERTICAL));
		this.add(txtEvent);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.txtTime.setText(StatusBar.TIME + time);
		this.txtEvent.setText(StatusBar.NOTHING);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.txtTime.setText(StatusBar.TIME + time);
		this.txtEvent.setText(StatusBar.NOTHING);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.txtTime.setText(StatusBar.TIME + time);
		this.txtEvent.setText(StatusBar.ADD_EVENT + "(" + e + ")");
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.txtTime.setText(StatusBar.TIME + time);
		this.txtEvent.setText(StatusBar.NOTHING);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// Do nothing
	}

	@Override
	public void onError(String err) {
		this.txtEvent.setText(err);		
	}
}
