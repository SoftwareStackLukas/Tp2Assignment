package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

@SuppressWarnings("serial")
class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	private static final Color BG_COLOR = Color.WHITE;
	private static final Color JUNCTION_COLOR = Color.BLUE;
	private static final Color JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color RED_LIGHT_COLOR = Color.RED;
	
	private static final String PNG = ".png";
	
	private RoadMap map;
	
	//Introduced const for no MagicLetters(-Numbers)
	private Image car;
	private static final String CAR = "car";
	//final fix just the array size but the image are change able
	private final Image[] contClass = new Image[6];
	private static final String CONT = "cont_";
	private Image rain;
	private static final String RAIN = "rain";
	private Image storm;
	private static final String STORM = "storm";
	private Image cloud;
	private static final String CLOUD = "cloud";
	private Image sun;
	private static final String SUN = "sun";
	private Image wind;
	private static final String WIND = "wind";

	public MapByRoadComponent(Controller ctrl) {
		this.initGUI();
		ctrl.addObserver(this);
		
		try {
			loadImgEsInCache();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private void loadImgEsInCache() throws IOException {
		this.car = this.loadI(MapByRoadComponent.CAR + MapByRoadComponent.PNG);
		for (int i = 0; i <= 5; i++) {
			this.contClass[i] =  this.loadI(MapByRoadComponent.CONT + Integer.toString(i) + MapByRoadComponent.PNG);
		}
		this.rain = this.loadI(MapByRoadComponent.RAIN + MapByRoadComponent.PNG);
		this.storm = this.loadI(MapByRoadComponent.STORM + MapByRoadComponent.PNG);
		this.sun = this.loadI(MapByRoadComponent.SUN + MapByRoadComponent.PNG);
		this.wind = this.loadI(MapByRoadComponent.WIND + MapByRoadComponent.PNG);
		this.cloud = this.loadI(MapByRoadComponent.CLOUD + MapByRoadComponent.PNG);
	}

	private void initGUI() {
		this.setPreferredSize(new Dimension(300, 300));
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//Clear Background
		g.setColor(BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());
		
		if (map == null || map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void updatePrefferedSize() {
		//DO SOMETHING
	}
	
	private void drawMap(Graphics g) {
		
		final List<Road> roads = map.getRoads();
		for (int i = 0; i < roads.size(); i++) {
			this.drawRoad(g, roads.get(i), i);
		}
	}
	
	private class Coordinates {
		final int x;
		final int y;
		
		Coordinates(int x, int i) {
			this.x = x;
			this.y =  (i + 1) * 50;
		}
	}
	
	private void drawRoad(Graphics g, Road r, int index) {
		//Lines
		Coordinates co1 = new Coordinates(50, index);
		Coordinates co2 = new Coordinates(getWidth() - 100, index);		
		g.drawLine(co1.x, co1.y, co2.x, co2.y);
		
		//Circles
		//Source Junction
		
		
		
		// draw the left junction's circle
		g.setColor(Color.blue);
		g.fillOval(leftJuncX - _JUNC_RADIUS / 2, leftJuncY - _JUNC_RADIUS / 2,
				_JUNC_RADIUS, _JUNC_RADIUS);

		// draw the left junction's id
		g.setColor(_JUNCTION_LABEL_COLOR);
		String id = road.getSrcJunc().getId();
		g.drawString(id, leftJuncX - _JUNC_RADIUS / 2, leftJuncY - _JUNC_RADIUS);

		
	}
		
	private Image getContI(Road road) {
		Image i = null;
		//DO SOMETHING
		return i;
	}
	
	private Image getWeatherI(Road road) {
		Image i = null;
		switch (road.getWeather()) {
			case RAINY: 
				i = this.rain;
				break;
			case STORM:
				i = this.storm;
				break;
			case CLOUDY:
				i = this.cloud;
				break;
			case SUNNY:
				i = this.sun;
				break;
			case WINDY:
				i = this.wind;
				break;
			default:
				//What do we set as default?
				break;
		}
		return i;
	}
	
	private Image loadI(String imageName) {		
		Image i = null;
		try {
			i = (Image) ImageIO.read(new File("resources/icons/" + imageName)); 
		} catch (IOException e) {
			System.out.println(e.toString()); 
		}
		return i; 
	}
	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			this.map = map;
			repaint();
		});
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		//Do nothing
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.update(map);
	}

	@Override
	public void onError(String err) {
		//Do nothing
	}
}
