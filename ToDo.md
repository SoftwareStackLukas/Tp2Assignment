#General
- The following tests have to be executed:
	- VehicleTest, RoadTest, CirtRoadTest, InterCityRoadTest,
	- JunctionTest, MostCrowdedStrategyTest, RoundRobinStrategyTest,
	- MoveFirstStrategyTest, MoveAllStrategyTest.
- In which classes do we have to overwrite the toString() method?

#Errors
- advance() method in vehicles --> Should be fixed but in Road are problems
- Check in Road the method <b> void advance(int time) </b>
- Change the accessors in the Road classes from package private to private. The access should just be possible via the setter/getter

#Lukas
~5.1.3.3 Class Junction~
~5.3 Events~
~5.3.1. New Junction Event~
~5.3.3. New Vehicle Event~
~5.3.5. Set Contamination Class Event~
- Where shall I use this: 
List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
dqbs.add( new MoveFirstStrategyBuilder() );
dqbs.add( new MoveAllStrategyBuilder() );
Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(
dqbs);

- Test all Builder!
- How to make the SetWeatherEventBuilder???
- TravicSimulator's toJSON has to be improved!


#Lucas
~Road map~
~5.3.2. New Road Events~
~5.3.4. Set Weather Event~
5.4. The Simulator Class (Lets right about this in the next days maybe I can do some stuff of it but lets see how much time I have)

#Tests
- Simulator
	- Factories
		- BuilderBasedFactory 				[ ] 
		- MostCrowdedStrategyBuilder		[x]
		- MoveAllStrategyBuilder			[x] --> works but I had to change the type name from the assignment
		- MoveFirstStrategyBuilder			[x] 
		- NewCityRoadEventBuilder			[ ] 	Works but needs traffic simulator
		- NewInterCityRoadEventBuilder	[ ] 	Same as above
		- NewJunctionEventBuilder			[ ] 	Same as above
		- NewVehicleEventBuilder			[ ] 
		- RoundRobinStrategyBuilder		[x] 
	- Model
		- CityRoad 		 					[ ]		co2 does not update correctly with advance
		- InterCityRoad						[x]
		- Junction							[x]
		- MostCrowdedStrategy				[ ]
		- MoveAllStrategy					[x]
		- MoveFirstStrategy					[x]
		- NewCityRoad							[x]
		- NewInterCityRoad					[x]
	- NewJunction								[x]
		- NewVehicle							[x]
		- RoadMap								[ ]
		- RoundRobinStrategy				[x]
		- SetContClass						[x]
		- SetWeather							[x]
		- TrafficSimulator					[ ]
		- Vehicle								[ ]
		
		
		
		
		
		
	