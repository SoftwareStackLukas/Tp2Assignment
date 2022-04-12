package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyDialog extends JDialog {
	public static final int CANCEL = 0;
	public static final int OK = 1;
	
	JPanel mainPanel;
	int closeOption;
	
	public MyDialog(JFrame parent) {
		super(parent, true);
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
}