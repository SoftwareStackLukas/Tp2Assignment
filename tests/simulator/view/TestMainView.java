package simulator.view;

import javax.swing.SwingUtilities;

import simulator.control.Controller;

public class TestMainView {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainWindow(null));
//		SwingUtilities.invokeLater(() -> new MainWindow(new Controller()));
	}
}
