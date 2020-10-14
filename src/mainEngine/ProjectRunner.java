package mainEngine;

public class ProjectRunner {

	public static void main(String[] args) {
		// The project will initialize by running this main thread
		initialize();
		buildGUI();
		createStatThread();
		gamestart();
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
		
	}
	
	private static void createStatThread() {
		/*
		 * statistical calculations will be passed on to a statistical thread to do the math
		 */
	}
	
	private static void gamestart() {
		/*
		 * this is where game functionality actually begins. The game thread essentially executes here.
		 * Anything involving game behavior goes here including starting a new game, playing the game, and restarting (resetting game state)
		 * Statistical calculations may be handled in a separate thread
		 */
	}

}
