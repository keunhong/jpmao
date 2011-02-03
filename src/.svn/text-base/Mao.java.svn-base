import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.ArrayList;

/**
 * This file contains the main method.
 * 
 * @author park282
 */


public class Mao {
	public static Game game;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		printIntro();
		
		// Create Python objects
		IO.info("Initializing Python interpreter.");
		IO.info("Loading Python modules:");
		Python.init();
		
		System.out.println();
				
		// Get number of players
		int numPlayers = Player.inputNumPlayers();
		
		// Create game object
		game = new Game(numPlayers, Player.inputPlayerNames(numPlayers));
		game.play();
		
	}
	
	/**
	 * Prints first intro when game is started
	 */
	public static void printIntro(){
		try {
			IO.bot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		// Prints the welcome message
        System.out.println("__      _____ _    ___ ___  __  __ ___   _____ ___    __  __   _   ___  ");
        System.out.println("\\ \\    / / __| |  / __/ _ \\|  \\/  | __| |_   _/ _ \\  |  \\/  | /_\\ / _ \\ ");
        System.out.println(" \\ \\/\\/ /| _|| |_| (_| (_) | |\\/| | _|    | || (_) | | |\\/| |/ _ \\ (_) |");
        System.out.println("  \\_/\\_/ |___|____\\___\\___/|_|  |_|___|   |_| \\___/  |_|  |_/_/ \\_\\___/ ");
        System.out.println();
        System.out.println("The University of Illinois at Urbana Champaign");
        
        /*
         * Create array of authors, shuffle, then print
         * Does this so the order in which the authors are printed doesn't matter.
         */
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("Keunhong Park");
        authors.add("Deren Kudeki");
        authors.add("Tom Zhang");
        authors.add("Joon Park");
        Collections.shuffle(authors);
        System.out.println(authors.toString());
        System.out.println();
	}

}

