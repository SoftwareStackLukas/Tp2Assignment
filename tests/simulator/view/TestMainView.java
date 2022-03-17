package simulator.view;

import javax.swing.SwingUtilities;

public class TestMainView {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainWindow(null));
	}
}
