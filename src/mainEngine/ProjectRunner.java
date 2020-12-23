package mainEngine;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import eventObject.EventObject;
import eventObject.EventReader;

public class ProjectRunner {
	static GameWindow _gw;
	private static List<List<EventObject>> _eventsList;
	private static StatThread _st;
	private static ArrayBlockingQueue<StatRequest> _abq = new ArrayBlockingQueue<StatRequest>(100);
	
	public static void main(String[] args) {
		// The project will initialize by running this main thread
		initialize();
		buildGUI();
		createStatThread();
		gameStart();
	}
	
	private static void initialize() {
		/*
		 * Initialization will obtain the events list
		 */
		
		EventReader er = new EventReader();
		File file = new File(".");
		String filename = "eventFileUpdated";
		try {_eventsList = er.buildEvent(filename);} catch (Exception e) {e.printStackTrace();}
	}
	
	private static void buildGUI() {
		/*
		 * This function will build the gui window and elements for use by the game
		 */
		_gw= new GameWindow();
		_gw.pushText("Game Gui Instantiated");
	}
	
	private static void createStatThread() {
		/*
		 * statistical calculations will be passed on to a statistical thread to do the math
		 */
		_st = new StatThread(_abq);
		_st.start();
		_gw.pushText("Stat Thread Instantiated");
	}
	
	private static void gameStart() {
		/*
		 * this is where game functionality actually begins. The game thread essentially executes here.
		 * Anything involving game behavior goes here including starting a new game, playing the game, and restarting (resetting game state)
		 * Statistical calculations may be handled in a separate thread
		 */
		_gw.pushText("===================Game Starting!=====================");
		ThePandemicGame pg = new ThePandemicGame(_gw,_eventsList,_st, _abq);
		_gw.setGame(pg);
		pg.play();
	}
}

