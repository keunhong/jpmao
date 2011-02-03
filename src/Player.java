/**
 * class Player
 * 
 * @author park282
 *
 */

public class Player {
	private String name;
	private Deck hand;
	private int index;
	
	/********************
	 * Class Methods
	 */
	
	/**
	 * Gets number of players playing the game.
	 * Gets input from user using BufferedStream then parses that into an integer.
	 * Uses try and catch to check for NumberFormatExceptions.
	 * If input is not a valid integer, the program re-prompts the user.
	 * @return numPlayers
	 */
	public static int inputNumPlayers(){
		int numPlayers = 0;
		
		try{
			// Loop for input until proper format is entered
			while(true){
				IO.prompt("Number of players");
				String input = IO.br.readLine();
				try{
					
					numPlayers = Integer.parseInt(input);
					// if number of players is below 2 then print error message and restart loop
					if(numPlayers < 2){
						IO.error("At least 2 people must be playing. Please try again.");
						continue;
					}
					if(numPlayers > 10){
						IO.error("No more than 10 people can play. Please try again.");
					}
					// otherwise break loop
					else{
						IO.info("Number of players has been set to " + numPlayers, 1);
						break;
					}
					
				} 
				// If number format is incorrect print error message and prompt again
				catch(NumberFormatException e){
					IO.error("Invalid format. Please try again.");
					continue;
				}
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return numPlayers;
	}
	
	/**
	 * Gets name of each player by using BufferedStream.
	 * Check if name is blank and if it is continues to prompt user.
	 * Prompts for each player.
	 * @param numPlayers
	 * @return names
	 */
	public static String[] inputPlayerNames(int numPlayers){
		String[] names = new String[numPlayers];
		try{
			// Loop through for all players
			for(int i = 0; i < numPlayers; i++){
				// Prompt until non-empty string is entered as name
					IO.prompt("Player " + (i) + "\'s name");
					String input = IO.br.readLine();	
					names[i] = input;
					
					if(names[i] != null && names[i].trim().isEmpty()){

						names[i] = "AnonymousCoward" + i;
					}
					
					IO.info("Player " + (i + 1) + "'s name has been set to " + names[i], 1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return names;
	}
	
	
	
	/********************
	 * Instance Methods
	 */
	
	public Player(String _name){
		name = _name;
	}
	
	public Player(String _name, Deck _hand){
		name = _name;
		hand = _hand;
	}
	
	public String getName(){
		return name;
	}
	
	public final Deck getHand(){
		return hand;
	}
	
	public void drawCard(Deck deck){
		hand.drawCard(deck);
	}
	
	public void discardCard(Deck deck, int i){
		hand.giveCard(deck, i);
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int i){
		index = i;
	}
}
