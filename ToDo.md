#General

# TbD
- RoadsTableModel
- MapViewers

# Biggest Problems
- ctrl.run(1); in the COntrolPanel

#Errors

#Lukas
- Run, Stop and Ticks (Ready for review)
- Exit the Simulator (Ready for review)
- Status Bar (Ready for review)
- JunctionTableModel (Ready for review)
- Changes Main Class 

- Where in the TrafficSimObserver shall be the method onError be called? I am not 100% sure about this.
- Did I consider everything in the toString() method. 

#Lucas
- VehiclesTableModel
- Center the dialogs

#Tests

#Already done
- The following tests have to be executed:
	- VehicleTest, RoadTest, CirtRoadTest, InterCityRoadTest,
	- JunctionTest, MostCrowdedStrategyTest, RoundRobinStrategyTest,
	- MoveFirstStrategyTest, MoveAllStrategyTest.
- In which classes do we have to overwrite the toString() method?
- to advance in TrafficSimulator
- Reading the data for the main
- some minor problems in the model
- advance() method in vehicles --> Should be fixed but in Road are problems
- Check in Road the method <b> void advance(int time) </b>
- Change the accessors in the Road classes from package private to private. The access should just be possible via the setter/getter

~5.1.3.3 Class Junction~
~5.3 Events~
~5.3.1. New Junction Event~
~5.3.3. New Vehicle Event~
~5.3.5. Set Contamination Class Event~
- One of this classes may not create the json correctly
- Are the moveBuilders correctly implemented?
- Where shall I use this: 
List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
dqbs.add( new MoveFirstStrategyBuilder() );
dqbs.add( new MoveAllStrategyBuilder() );
Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(
dqbs);

- Test all Builder!
~- How to make the SetWeatherEventBuilder and the SetWeatherEventBuilder???~
- TravicSimulator's toJSON has to be improved and the advance!
- the setcon and weather could not have been tested!

~Road map~
~5.3.2. New Road Events~
~5.3.4. Set Weather Event~
5.4. The Simulator Class (Lets right about this in the next days maybe I can do some stuff of it but lets see how much time I have)
- Test the main class
- Simulator
	- Factories
		- BuilderBasedFactory 				[x] 
		- MostCrowdedStrategyBuilder		[x]
		- MoveAllStrategyBuilder			[x] --> works but I had to change the type name from the assignment
		- MoveFirstStrategyBuilder			[x] 
		- NewCityRoadEventBuilder			[ ] 	Works but needs traffic simulator
		- NewInterCityRoadEventBuilder		[ ] 	Same as above
		- NewJunctionEventBuilder			[ ] 	Same as above
		- NewVehicleEventBuilder			[ ] 	Same as above
		- RoundRobinStrategyBuilder			[x] 
	- Model
		- CityRoad 		 					[x]		
		- InterCityRoad						[x]
		- Junction							[x]
		- MostCrowdedStrategy				[x]
		- MoveAllStrategy					[x]
		- MoveFirstStrategy					[x]
		- NewCityRoad						[x]
		- NewInterCityRoad					[x]
	- NewJunction							[x]
		- NewVehicle						[x]
		- RoadMap							[x]
		- RoundRobinStrategy				[x]
		- SetContClass						[x]
		- SetWeather						[x]
		- TrafficSimulator					[ ]
		- Vehicle							[x]
		
		
		
		