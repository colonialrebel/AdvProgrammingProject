package mainEngine;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import eventObject.EventObject;

public class ThePandemicGame {
	private  List<List<EventObject>> _eventsList;
	private  int _timeOfDay = 0;
	private  int _daysPassed =0;
	private  Random r = new Random();
	private GameWindow _gw;
	final String youChose = "You chose: ";
	private EventObject _eo;
	private StatThread _st;
	private ArrayBlockingQueue<StatRequest> _abq;
	private String[] _timesOfDay = {"Morning", "Midday", "Afternoon", "Evening"};
	public ThePandemicGame(GameWindow gw, List<List<EventObject>> el, StatThread st, ArrayBlockingQueue<StatRequest> abq) {
		_eventsList = el;
		_gw = gw;
		_st=st;
		_abq = abq;
	}
	public void play() {
		//System.out.println("day: "+_daysPassed);
		//System.out.println("Time: "+_timeOfDay);
		if(_daysPassed<8) {
			int dayDisplay = _daysPassed+1;//this is necessary because Java was doing java things and appending 1 to the days passed
			_gw.pushText("It is Day: " + dayDisplay);
			if(_timeOfDay<4) {
				_gw.pushText("It is now "+ _timesOfDay[_timeOfDay]);
				System.out.println("Day: " + _daysPassed + " TimeSlot: "+ _timeOfDay);
				// this line picks a random event from the pool of events that correspond to the current time of day
				_eo = _eventsList.get(_timeOfDay).get(r.nextInt(_eventsList.get(_timeOfDay).size()));  
				//I now need to push the event details to the GUI
				System.out.println(_eo._description);
				_gw.pushText(_eo._description);
				_gw.pushChoices(_eo.getChoiceDescription(1), _eo.getChoiceDescription(2), _eo.getChoiceDescription(3));
				//Note that the time of day isnt progressed here. Only the player can progress the time of day. and thus the number of days passed
			}else {
				//reset the time of day
				_daysPassed++;
				_timeOfDay=0;
				play();
			}

			
		}else {//this means a week has passed
			_gw.pushChoices("Wear", "A", "Mask!");
			_gw.pushText("A week has passed");
			_gw.pushText("In this week of the pandemic you came in contact with " + _st._peopleInContact + " people of whom: "+ _st._peopleWhoHadCovid +" People who had Covid-19");
			if(_st._doIHaveCovid) {
				_gw.pushText("And Unfortunately, you became one of them.");
				_gw.pushText("And as a result spread Covid-19 to "+ _st._peopleExposedToCovid + " People.");
			}else {
				_gw.pushText("But Fortunately you avoided contracting Covid-19");
			}
			//_daysPassed=0; //reset the number of days passed
			_gw.pushText("Thank you for playing. For the best replay experience, please close this client and open a new instance");
		}

		
		//HERE IS WHERE I GET THE STATISTICS
	}
	public void choiceMade(int i) {
		//game window affirms your choice
		if(_daysPassed<8) {
			_gw.pushText(youChose + _eo.getChoiceDescription(i));
		
			//need to translate a certain string element to an integer boolean (0 or 1)
			int bool = 1;
			if(_eo.getChoiceMask(i).equals("false")) {
				bool = 0;
			}
		
			StatRequest sr = new StatRequest(
				Integer.parseInt(_eo.getChoicePop(i)),
				Double.parseDouble(_eo.getChoiceDensity(i)),
				Double.parseDouble(_eo.getChoiceCovidRate(i)),
				bool); //everything was is in string form and needed conversion
			try {//here i try to put the stat request on the queue
				_abq.put(sr);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			_timeOfDay++;
			play();
		}
	}
}
