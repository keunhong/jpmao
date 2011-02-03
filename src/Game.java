import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Game
 * This file stores and handles the state of the game such as the players, decks, hands, etc.
 * 
 * @author park282
 */

public class Game{
	private ArrayList<Player> players;
	private Deck drawDeck;
	private Deck discardDeck;
	private Rules rules;
	
	private Player dictator;
	private Player currentPlayer;
	private int round;
	private int turn;
	private int game;
	private static boolean Drew = false;
	
	/**
	 * Constructor
	 * @param numPlayers
	 * @param names
	 */
	public Game(int numPlayers, String[] names){
		
		/*
		 * Initialize decks
		 */
		drawDeck = new Deck(true);
		drawDeck.shuffle(); // Shuffle drawing deck
		discardDeck = new Deck(false);
				
		// Create array list of players and initialize them
		players = new ArrayList<Player>();
		
		IO.endl();
		IO.info("Shuffling main deck.");
		Collections.shuffle(players);
		
		IO.info("Distributing cards to players.");
		for(int i = 0; i < numPlayers; i++){
			Deck newHand = new Deck(false);
			// Give 5 cards to each player
			for(int n = 0; n < 5; n++){
				newHand.drawCard(drawDeck);
			}
			Player newPlayer = new Player(names[i], newHand);
			newPlayer.setIndex(i);
			players.add(newPlayer);
		}
		
		IO.info("Drawing top card from main deck as first card.");
		drawDeck.giveCard(discardDeck, 0);
		
		System.out.println("\n>> The main deck now has " + drawDeck.size() + " cards.");
		
		rules = new Rules();
		
		currentPlayer = players.get(0);
		dictator = players.get(0);
		turn = 0;
	}

	
	/**
	 * Starts game
	 */
	public void play(){
		while(true){
			IO.endl();
			System.out.println("***** [GAME #" + game + ", ROUND #" + round + ", TURN #" + turn + "] *****");
			
			IO.info(currentPlayer.getName() + " (Player " + currentPlayer.getIndex() + ")'s turn. Everyone look away!");
			
			/*
			 * If current player is dictator
			 * 	Make them add a rule
			 */
			if(currentPlayer == dictator && round == 0 && game != 0){
				IO.info("You are the dictator. Please add a rule.");
				Rules.add(new Rule(Rule.inputRule()));
				IO.info("A rule has been set.");
			}
			
			/*
			 * Loop until player plays valid card
			 */
			Card played = null;
			while(true){
				// Get player's current hand
				Deck hand = currentPlayer.getHand();
				
				// If main deck is empty, get cards from discard deck;
				if(drawDeck.size() == 0){
					discardToDraw();
				}
				
				// Print last played card and current player's hand
				System.out.println();
				IO.info("The last card played is " + getLastCardPlayed().toString());
				
				int choiceLimit = printPlayerChoices();
				
				// Get user's choice on option
				int choice = inputChoice(choiceLimit);
				// If user chose to draw a card instead of playing
				// 	(Which will always be the last choice)
				if(choice == choiceLimit){
					IO.info("Your chose to draw a card.");
					currentPlayer.drawCard(drawDeck);
					IO.info("You drew a " + hand.getCard(hand.size() - 1));
					//Assign a non-ace value to played so that later checks don't
					//get null pointer exceptions
					Drew = true;
					played = getLastCardPlayed();
					while( Rules.aceCheck(played) || Rules.eightCheck(played) )
					{
						played = drawDeck.getCard(0);
					}
					break;
				}
				// If user chose a card to play
				else{
					played = hand.getCard(choice);
					IO.info("Your chose " + choice + " which is " + played.toString());
					
					// Check if chosen card is valid
					// If it isn't make user choose again
					if( Rules.isValid(played, getLastCardPlayed(), Drew) ){
						currentPlayer.discardCard(discardDeck, choice);
						IO.info("Your card has been played.");
						IO.prompt("Follow any rules that apply by typing them in and pressing enter. You have 5 seconds");
						Rules.ruleImplimentation(hand, drawDeck, played);
						Drew = false;
						
						if(Rules.isAgainstRules(played, getLastCardPlayed())){
							IO.info("Your card was against a rule.");
							IO.info("Drawing card from deck.");
							currentPlayer.drawCard(drawDeck);
							Drew = true;
							played = getLastCardPlayed();
							while( Rules.aceCheck(played) || Rules.eightCheck(played) )
							{
								played = drawDeck.getCard((int)(drawDeck.size()*Math.random()));
							}
						}
						
						break;
					}else{
						IO.error("Illegal card. Please try again.");
					}
				}
			} // while(true)
			
			
			/*
			 * If someone runs out of cards start next game 
			 */
			if(currentPlayer.getHand().size() == 0){
				startNextGame();
			}else{
				//If an ace was just played, the next player's turn is skipped
				if( Rules.aceCheck(played) )
				{
					IO.info("An ace was played. Skipping one turn.");
					turn = (++turn) % players.size();
					turn = (++turn) % players.size();
					if(turn == 0){
						round++;
					}
					currentPlayer = players.get(turn);
				}
				//If an eight was just played, reverse player order.
				else if ( Rules.eightCheck(played) )
				{
					IO.info("An eight was played. Reversing play order.");
					Collections.reverse(players);
					
					// Refresh player indexes
					int n = 0;
					for(Player player: players){
						player.setIndex(n);
						n++;
					}
					turn = (players.size()-1) - turn;
					turn = (++turn) % players.size();
					if(turn == 0){
						round++;
					}
					currentPlayer = players.get(turn);
				}
				// Increment turn. If turn # players size then reset to zero
				// If turn == 0 then increment round
				else
				{
					turn = (++turn) % players.size();
					if(turn == 0){
						round++;
					}
					currentPlayer = players.get(turn);
				}
			}
		}
	} // play()

	
	///////////////////////
	
	/**
	 * Returns unused cards from discard pile to draw deck
	 * 	Invoked when draw deck runs out of cards.
	 */
	private void discardToDraw(){
		IO.info("Main deck has run out of cards.");
		
		for(int i = 1; i < discardDeck.size() - 1; i++){
			discardDeck.giveCard(drawDeck, 1);
		}
		IO.info("Draw deck has been filled with cards from discard deck");
		IO.info("Shuffling main deck.");
		drawDeck.shuffle();
	}
	
	/**
	 * Fetched player choices
	 * @return # of choices
	 */
	private int printPlayerChoices(){
		IO.info("These are your options:");
		int n = 0;
		for(Card card: currentPlayer.getHand().getCards()){
			System.out.println("\t" + n + ". " + card.toString());
			n++;
		}
		System.out.println("\t" + n + ". Draw card");
		
		return n;
	}
	
	/**
	 * Starts next game
	 * 	Resets several variables
	 * 	Moves card back to main deck
	 * 	Re-distributes cards
	 */
	private void startNextGame(){
		System.out.println();
		System.out.println("*************************");
		System.out.println();
		IO.info(currentPlayer.getName() + " has run out of cards. " + currentPlayer.getName() + " wins this game.");
		IO.info("This game has ended. The next game will start shortly.");
		game++;
		round = 0;
		turn = 0;
		
		// Reset everything
		IO.info("Returning cards from players' hands into main deck.");
		for(Player player: players){
			while(player.getHand().size() != 0){
				player.discardCard(drawDeck, 0);
			}
		}
		
		IO.info("Returning cards from discard deck into main deck.");
		while(discardDeck.size() != 0){
			discardDeck.giveCard(drawDeck, 0);
		}
		
		IO.info("Shuffling main deck.");
		drawDeck.shuffle();
		
		// Distribute cards back to players
		IO.info("Distributing cards to players.");
		for(Player player: players){
			// Give 5 cards to each player
			for(int n = 0; n < 5; n++){
				player.drawCard(drawDeck);
			}
		}
		
		// Draw top card from main deck into discard deck
		IO.info("Drawing top card from main deck as first card.");
		drawDeck.giveCard(discardDeck, 0);
		
		System.out.println();
		IO.info(currentPlayer.getName() + " is the new dictator.");
		dictator = currentPlayer;
	}
	
	
	
	/////////////////////
	
	
	
	
	/**
	 * @return last card played
	 */
	public final Card getLastCardPlayed(){
		return discardDeck.getCard(discardDeck.size() - 1);
	}
	
	/**
	 * Gets input from user as choice for multiple choice questions
	 * limit is the last number the user can legally enter
	 * @param limit
	 * @return input if not illegal
	 */
	private int inputChoice(int limit){
		while(true){
			IO.prompt("Which option would you like to choose (number)");
			int choice = -1;
			try {
				choice = Integer.parseInt(IO.br.readLine());
			} catch (NumberFormatException e) {
				IO.error("Invalid choice. Please try again.");
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(choice > limit || choice < 0){
				IO.error("Invalid choice. Please try again.");
				continue;
			}else{
				return choice;
			}
		}
	}
}
