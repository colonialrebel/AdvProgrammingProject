package mainEngine;

public class ProjectRunner {
	static GameWindow _gw;
	public static void main(String[] args) {
		// The project will initialize by running this main thread
		initialize();
		buildGUI();
		createStatThread();
		gameStart();
	}
	
	private static void initialize() {
		/*
		 * Initialization will need to read in the events and 
		 * localization files into a format manipulatable by the game.
		 * 
		 * Initialization will also build the gui
		 * 
		 * Initialization's final step will be to put the game into a starting state.
		 */
		
		
	}
	
	private static void buildGUI() {
		/*
		 * This function will build the gui window and elements for use by the game
		 */
		_gw = new GameWindow();
		_gw.pushText("Game Gui Instantiated");
	}
	
	private static void createStatThread() {
		/*
		 * statistical calculations will be passed on to a statistical thread to do the math
		 */
		_gw.pushText("Stat Thread Instantiated");
	}
	
	private static void gameStart() {
		/*
		 * this is where game functionality actually begins. The game thread essentially executes here.
		 * Anything involving game behavior goes here including starting a new game, playing the game, and restarting (resetting game state)
		 * Statistical calculations may be handled in a separate thread
		 */
		_gw.pushText("Game starting...");
	}

}
