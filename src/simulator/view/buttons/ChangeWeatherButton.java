package simulator.view.buttons;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import simulator.view.Locations;

@SuppressWarnings("serial")
public class ChangeWeatherButton extends JButton {
	public ChangeWeatherButton() {
		Icon icon = new ImageIcon(Locations.getIconsDir() + "weather.png");
		setIcon(icon);
		addActionListener((e) -> {
			// TODO: implement this
			System.out.println("Im changing the weather");
		});
	}
}
