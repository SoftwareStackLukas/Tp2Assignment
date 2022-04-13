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
import simulator.model.Vehicle;
import simulator.model.Weather;

@SuppressWarnings("serial")
class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	//To encapsulate the used colors
	private static class COLOR_CLASS {
		static final Color BG_COLOR = Color.WHITE;
		static final Color JUNCTION_COLOR = Color.BLUE;
		static final Color JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
		static final Color GREEN_LIGHT_COLOR = Color.GREEN;
		static final Color RED_LIGHT_COLOR = Color.RED;
		static final Color ROAD_LABEL_COLOR = Color.BLACK;
	}

	private static final Integer WEATHER_SIZE_IMAGE = 32;
	private static final Integer CONT_CLASS_SIZE_IMAGE = 32;
	private static final Integer CAR_SIZE_IMAGE = 16;
	private static final Integer JUNCTION_RADIUS = 10;
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
		//this.setPreferredSize(new Dimension(300, 300));
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//Clear Background
		g.setColor(COLOR_CLASS.BG_COLOR);
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
	
	//Wrappes the coodinates
	private class Coordinates {
		final int x;
		final int y;
		
		Coordinates(int x, int i) {
			this.x = x;
			this.y =  (i + 1) * 50;
		}
	}
	
	private void drawRoad(Graphics g, Road r, int index) {
		//We have to fit the sizeing part in here
		
		//Lines
		Coordinates co1 = new Coordinates(50, index);
		Coordinates co2 = new Coordinates(getWidth() - 100, index);		
		g.drawLine(co1.x, co1.y, co2.x, co2.y);
		
		//Circles
		//Source Junction
		g.setColor(COLOR_CLASS.JUNCTION_COLOR);
		g.fillOval(co1.x - JUNCTION_RADIUS / 2, co1.y - JUNCTION_RADIUS / 2, JUNCTION_RADIUS, JUNCTION_RADIUS);
		g.setColor(COLOR_CLASS.JUNCTION_LABEL_COLOR);
		g.drawString(r.getSrc().getId(), co1.x - 5, co1.y - 15);
		
		//Destination Junction
		int idx = r.getDest().getGreenLightIndex();
		if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
			g.setColor(COLOR_CLASS.GREEN_LIGHT_COLOR);
		} else {
			g.setColor(COLOR_CLASS.RED_LIGHT_COLOR);
		}
		g.fillOval(co2.x - JUNCTION_RADIUS / 2, co2.y - JUNCTION_RADIUS / 2, JUNCTION_RADIUS, JUNCTION_RADIUS);
		g.drawString(r.getDest().getId(), co2.x - 5, co2.y - 15);
		
		//Draw the vehicles
		final List<Vehicle> vehicles = r.getVehicles();
		int x = 0;
		for (Vehicle v : vehicles) {
			x = co1.x + (int) ((co2.x - co1.x) * ((double) v.getLocation() / (double) r.getLength()));
			g.drawImage(this.car, x, co1.y - 15, MapByRoadComponent.CAR_SIZE_IMAGE, MapByRoadComponent.CAR_SIZE_IMAGE, this);
			
			// Choose a color for the vehcile's label and background, depending on its
			// contamination class
			int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
			g.setColor(new Color(0, vLabelColor, 0));
			g.drawString(v.getId(), x + 2, co1.y - 15);
		}
		
		//Draw the road id
		g.setColor(COLOR_CLASS.ROAD_LABEL_COLOR);
		g.drawString(r.getId(), co1.x - 30, co1.y + 3);
		
		//Draw the weather
		g.drawImage(this.getWeatherI(r), co2.x + 10, co2.y - 15, MapByRoadComponent.WEATHER_SIZE_IMAGE, MapByRoadComponent.WEATHER_SIZE_IMAGE, this);
		
		//Draw contamination level
		g.drawImage(this.getContI(r), co2.x + 50, co2.y - 15, MapByRoadComponent.CONT_CLASS_SIZE_IMAGE, MapByRoadComponent.CONT_CLASS_SIZE_IMAGE, this);		
	}
		
	private Image getContI(Road road) {
		Image i = contClass[0];
		final int C = (int) Math.floor(Math.min((double) road.getTotalCO2()/(1.0 + (double) road.getContLimit()),1.0) / 0.19);
		if(C >= 0 && C < contClass.length){
			i = contClass[C];
		}
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
				i = this.sun;
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
