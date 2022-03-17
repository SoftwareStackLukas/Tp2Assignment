package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

class ControlPanel extends JPanel implements TrafficSimObserver {

	ControlPanel(Controller ctrl) {
		
	}
	//Building the gui
	private void initGUI() {
		
	}
	
	
	//Exit the Simulator
	private void initExitButton() {
		JButton exit = new JButton(new ImageIcon("resources/icons/exit.png"));
		exit.setSize(10,10); //Which size should we take? 
		exit.addActionListener(new ActionListener() {
			Object[] options = {"Yes", "No"};
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(null, "Are you sure to quickt the simulator?", "Quit", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, (Object[]) options[0], (Object[]) options[1]);
				if (n == 0) System.exit(n);
			}
		});
	}
	
	//gui end 

	//Oberserver methods -- We can talk about that in the next lecture 
	//For now please build the gui
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
