#General

# TbD

# Biggest Problems

#Errors

#Lukas

#Lucas

#Tests

#Already done

#Improvments which have to be done 
Quite ok, please fix the errors below and resubmit

### TrafficSimulator and MVC

OK

### Controller (Done)

>  	TrafficSimulator getTrafficSim() {
>		return this.simulator;
>	}

Remove this method


### Main


> 			if (Main.mode == Mode.BATCH) {
>				parseOutFileOption(line);				
>			}

No needed, simply don't use in in startGUIMode

### GUI

- in the vehicles table, the location should include the road id (when travelling)

- in MyTable, swap the order of following lines (Done)

		ctlr.addObserver(this);
		rawData = new SortedArrayList<T>();

- In the dialog, you will end up with many objects registered as observers because you create the dialog every time you click the button, but you never remove them.		