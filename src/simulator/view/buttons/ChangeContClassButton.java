package simulator.view.buttons;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import simulator.view.Locations;

@SuppressWarnings("serial")
public class ChangeContClassButton extends JButton {
	public ChangeContClassButton() {
		Icon icon = new ImageIcon(Locations.getIconsDir() + "co2class.png");
		setIcon(icon);
		addActionListener((e) -> {
			// TODO: implement this
			System.out.println("Im changing the cont class");
		});
	}
}
