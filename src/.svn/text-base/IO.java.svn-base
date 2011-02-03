import java.awt.Robot;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class IO {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static Robot bot;
	
	/**
	 * Prints message in format of information
	 * @param msg
	 */
	public static void info(String msg){
		System.out.println(">> [I] " + msg);
	}
	
	/**
	 * Prints message in format of information
	 * 	Adds tab character [indent] times
	 * @param msg
	 * @param indent
	 */
	public static void info(String msg, int indent){
		for(int i = 0; i < indent; i++){
			System.out.print("\t");
		}
		System.out.println(">> [I] " + msg);
	}
	
	/**
	 * Prints message in format of error
	 * @param err
	 */
	public static void error(String err){
		System.out.println(">> [E] " + err);
	}
	
	/**
	 * Prints message in format of prompt
	 * @param q
	 */
	public static void prompt(String q){
		System.out.print("<< [Q] " + q + ": ");
	}
	
	/**
	 * Prints newline
	 */
	public static void endl(){
		System.out.println();
	}
}
