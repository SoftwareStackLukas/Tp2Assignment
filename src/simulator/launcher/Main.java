package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int _ticks;
	private static Mode mode;
	
	enum Mode {
		BATCH, GUI
	}
	
	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			parseInFileOption(line);
			parseOutFileOption(line);				
			parseTicksOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}
	

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator’s main loop (default value is 10).").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Mode of execution (gui/batch)").build());
		return cmdLineOptions;
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		// By default its gui
		Main.mode = Mode.GUI;
		if (line.hasOption("m")) {
			String mode = line.getOptionValue("m").toLowerCase();
			if (mode.equals("gui"))
				Main.mode = Mode.GUI;
			else if (mode.equals("batch")) {
				Main.mode = Mode.BATCH;
			} else {
				throw new ParseException("Invalid mode");
			}
		} 		
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		if (line.hasOption("i")) {
			_inFile = line.getOptionValue("i");			
		}
		if (Main.mode == Mode.BATCH && _inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if (line.hasOption("o")) {
			_outFile = line.getOptionValue("o");
		}		
	}
	
	private static void parseTicksOption(CommandLine line) {
		if (line.hasOption("t")) {
			Main._ticks = Integer.parseInt(line.getOptionValue("t"));
		} else {
			Main._ticks = _timeLimitDefaultValue;
		}
	}

	private static void initFactories() {

		// This method to initializes _eventsFactory
		
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder() );
		lsbs.add( new MostCrowdedStrategyBuilder() );
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add( new MoveFirstStrategyBuilder() );
		dqbs.add( new MoveAllStrategyBuilder() );
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory) );
		ebs.add( new NewCityRoadEventBuilder() );
		ebs.add( new NewInterCityRoadEventBuilder() );
		ebs.add( new NewVehicleEventBuilder() );
		ebs.add( new SetWeatherEventBuilder() );
		ebs.add( new SetContClassEventBuilder() );
		Main._eventsFactory = new BuilderBasedFactory<>(ebs);

	}
	
	private static void startGUIMode() throws IOException{
		Controller ctrl = new Controller(new TrafficSimulator(), Main._eventsFactory);
		if (Main._inFile != null) {
			InputStream in = new FileInputStream(Main._inFile);
			ctrl.loadEvents(in);
		}
		
		SwingUtilities.invokeLater(() -> new MainWindow(ctrl));
	}

	private static void startBatchMode() throws IOException {
		System.out.println("Starting batch mode");
		TrafficSimulator ts;
		Controller c;
		OutputStream out;
		ts = new TrafficSimulator();
		InputStream in = new FileInputStream(Main._inFile);
		if (Main._outFile != null) {
			out = new FileOutputStream(Main._outFile);			
		} else {
			out = System.out;
		}
		c = new Controller(ts, _eventsFactory);
		
		c.loadEvents(in);
		in.close();
		c.run(_ticks, out);
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		
		if (Main.mode == Mode.BATCH) { // is .equals necessary?
			startBatchMode();
		} else if (Main.mode == Mode.GUI) {
			startGUIMode();
		} else {
			throw new IOException("False args");
		}
	}
	

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help
	
	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
