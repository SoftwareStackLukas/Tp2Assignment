package simulator.view.buttons;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import simulator.view.Locations;

@SuppressWarnings("serial")
public class LoadButton extends JButton{
	
	public LoadButton() {
		Icon icon = new ImageIcon(Locations.getIconsDir() + "open.png");
		setIcon(icon);
		addActionListener((e) -> {
			// TODO: implement this
			System.out.println("Im loading");
		});
	}
	
	
	
}
