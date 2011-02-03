import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class should contain mostly static methods that check if a rule is valid.  If so
 * it should then check if the player has taken the right action.
 * 
 * @author dkudeki
 * 
 */

public class Rules {
	private static ArrayList<Rule> rules = new ArrayList<Rule>();
	
	/**
	 * Add rule to ArrayList
	 * @param rule
	 */
	public static void add(Rule rule){
		rules.add(rule);
	}
	
	/**
	 * Checks if card is against any rules
	 * @param current
	 * @param last
	 * @return true iff it is false if not
	 */
	public static boolean isAgainstRules(Card current, Card last){
		for(Rule rule: rules){
			if(Python.eval(rule.getCode(), current, last) == true){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The player must placed down a card with the same number or same suit as the last card
	 * played.
	 * 
	 * @param lastDiscard - card being put down
	 * @param previousDiscard - card that was on top of discard deck at start of turn,
	 * 		determines what moves are legal this turn
	 * @return true if move is legal, false if illegal.  Other classes should keep prompting
	 * 		until this is true or until the player draws a card.
	 */
	
	public static boolean isValid(Card lastDiscard, Card previousDiscard, boolean Drew)
	{
		if ( !Drew && sevenCheck(previousDiscard) )
		{
			return lastDiscard.getNumber().equals(previousDiscard.getNumber());
		}
		else
		{
			return ( lastDiscard.getNumber().equals(previousDiscard.getNumber()) ||
					lastDiscard.getSuit().equals(previousDiscard.getSuit()) );
		}
	}
	
	/**
	 * When the player places down a Queen card, they are required to enter "All hail the queen"
	 * (case insensitive).  If they play the queen of spades, they may also meet the spade rule
	 * by typing "All hail the queen of spades" (case insensitive). This method checks if the
	 * card is a queen.  If so the program will start running another method that checks if
	 * the appropriate text has been entered.
	 * 
	 * 
	 * @param - lastDiscard is the card just played into the discard pile.
	 * 
	 * @return - true means that the player has played a queen
	 * 		   - false means that they have not
	 */
	
	public static boolean allHailTheQueenCheck(Card lastDiscard)
	{
		
		return ( lastDiscard.getNumber() == Card.Number.QUEEN );
	}
	
	/**
	 * Reads input by the user.  Once the user types some correctly spelled variation of
	 * "all hail the queen" the rule has been carried out.
	 * 
	 * @return true means that the player has carried out the requirements of the rule
	 * 		   false means that they have not
	 */
	
	public static boolean allHailTheQueen(String line)
	{
		return ( line.indexOf("ALL HAIL THE QUEEN") > -1 );
	}
	
	/**
	 * When the player places down a 6, they are required to enter "phat" text insensitive. This 
	 * method checks if the card is a 6.  If so the program will start running another 
	 * method that checks if the appropriate text has been entered.
	 * 
	 * @param - lastDiscard is the card just played into the discard pile.
	 * 
	 * @return - true means that the player has played a six
	 * 		   - false means that they have not
	 */
	
	public static boolean phatCheck(Card lastDiscard)
	{
		return ( lastDiscard.getNumber() == Card.Number.SIX );
	}
	
	/**
	 * Player has played a 6, so the program checks if they have entered "phat"
	 * 
	 * @return true if they have
	 * 		   false if they haven't
	 */
	
	public static boolean phat(String line)
	{
		return ( line.indexOf("PHAT") > -1 );
	}
	
	/**
	 * When the player places down a spade they must enter "n of spades" where n is the number
	 * or name of the card. This  method checks if the card is a spade.  If so the program will 
	 * start running another method that checks if the appropriate text has been entered.
	 * 
	 * @param - lastDiscard is the card just played into the discard pile.
	 * 
	 * @return - true means that the player has played a spade
	 * 		   - false means that they have not
	 */
	
	public static boolean spadeCheck(Card lastDiscard)
	{
		return ( lastDiscard.getSuit() == Card.Suit.SPADES );
	}
	
	/**
	 * Checks that the player has entered "_ of spades"
	 * 
	 * @param lastDiscard is the card just played, use it to determine correct number
	 * @return true if the player has entered the correct phrase.
	 * 		   false if they have not
	 */
	
	public static boolean spade(Card lastDiscard, String line)
	{
		return ( line.indexOf(lastDiscard.getNumber() + " OF SPADES" ) > -1 );
	}
	
	/**
	 * When the player has only one card left in their hand they are required to enter "Mao."
	 * This  method checks if there is only one card left in the palyer's hand.  If so the 
	 * program will start running another method that checks if the appropriate text has 
	 * been entered.
	 * 
	 * @param - hand is the current player's hand, after they have taken their turn (i.e. drawn
	 * 			or put down a card)
	 * @return - true means that there is only one card left in the palyer's hand
	 * 		   - false means that they do not have only one card in their hand
	 */
	
	public static boolean maoCheck(Deck hand)
	{
		return ( hand.size() == 1 );
	}
	
	/**
	 * Checks if the player has typed in "Mao"
	 * @return
	 */
	
	public static boolean mao(String line)
	{
		return ( line.indexOf("MAO") > -1 );
	}
	
	/**
	 * When the player places down an eight, the order of play reverses.  This means that the
	 * next person to play was the last person to play before the current player.
	 * 
	 * @param - lastDiscard is the card just placed in the discard pile
	 * @return - true means that the player has played an eight
	 * 		   - false means that they have not
	 */
	
	public static boolean eightCheck(Card lastDiscard)
	{
		return ( lastDiscard.getNumber() == Card.Number.EIGHT );
	}
	
	/**
	 * When the player places down an ace, the next player's turn is skipped.
	 * @param lastDiscard
	 * @return
	 */
	
	public static boolean aceCheck(Card lastDiscard)
	{
		return ( lastDiscard.getNumber() == Card.Number.ACE );
	}
	
	/**
	 * When a player places down a seven, the next player must either place down a seven
	 * themselves, or draw two cards.
	 * 
	 * @param - previousDiscard is the card played in the previous round
	 * @return - true means that last round a seven was played
	 * 		   - false means that last round a seven was not played
	 */
	
	public static boolean sevenCheck(Card previousDiscard)
	{
		return ( previousDiscard.getNumber() == Card.Number.SEVEN );
	}
	
	/**
	 * Checks what rules are in play, and if they have been followed.  Time sensitive.  See the parameters (especially play) for further implementation notes.
	 * 
	 * @param hand - The hand of the current player.  If the move is illegal, add appropriate 
	 * number of penalty cards to hand.
	 * @param discard - Deck of cards that have been put down.  Mostly used for checking the last card
	 * or two put down to see if a rule applies.
	 * @param draw - The draw deck.  Used to get penalty cards.
	 * @param play - The card that has been put down.  Running on the assumption that it has already
	 * been approved as technically legal (i.e. appropriate suit and number).  The legality check
	 * should be a loop that takes place before this function in the Game class, that keeps going
	 * until a technically legal card has been put down, after which this method will run to see if
	 * any penalty cards should be added to the player's hand.  If the player draws a card, this method
	 * should be skipped.
	 */
	
	public static void ruleImplimentation(Deck hand, Deck draw, Card play)
	{
		boolean QUEEN = allHailTheQueenCheck(play);
		boolean queenFollowed = false;
		boolean queenFlag = true;
		boolean PHAT = phatCheck(play);
		boolean phatFollowed = false;
		boolean phatFlag = true;
		boolean SPADES = spadeCheck(play);
		boolean spadesFollowed = false;
		boolean spadesFlag = true;
		boolean MAO = maoCheck(hand);
		boolean maoFollowed = false;
		boolean maoFlag = true;
		
		class PressEnter extends TimerTask{
			public void run(){
				IO.bot.keyPress(KeyEvent.VK_ENTER);
				IO.bot.keyRelease(KeyEvent.VK_ENTER);
			}
		}
		
		Timer timer = new Timer();
		timer.schedule(new PressEnter(), 5000);
		
		long start = System.currentTimeMillis();
		String line = null;
		
		while((System.currentTimeMillis() - start) < 5000 && line == null )
		{
			try {
				line = IO.br.readLine().toUpperCase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			timer.cancel();
			
			if(line.trim().isEmpty()){
				IO.info("You chose to not enter anything.");
			}
			
			if( (QUEEN && queenFlag) || (PHAT && phatFlag) || (SPADES && spadesFlag) || (MAO && maoFlag) )
			{
				//line = TextIO.getln().toUpperCase();
				
			if ( QUEEN && queenFlag )
			{
				queenFollowed = allHailTheQueen(line);
				if (queenFollowed == true)
				{
					queenFlag = false;
				}
			}
			if ( PHAT && phatFlag )
			{
				phatFollowed = phat(line);
				if ( phatFollowed == true )
				{
					phatFlag = false;
				}
			}
			if ( SPADES && spadesFlag )
			{
				spadesFollowed = spade(play, line);
				if ( spadesFollowed == true )
				{
					spadesFlag = false;
				}
			}
			if ( MAO && maoFlag )
			{
				maoFollowed = mao(line);
				if ( maoFollowed == true )
				{
					maoFlag = false;
				}
			}
			}
		}
		
		if ( QUEEN && !queenFollowed )
		{
			hand.drawCard(draw);
			IO.info("Carded for not saying \"ALL HAIL THE QUEEN.\"");
		}
		if ( PHAT && !phatFollowed )
		{
			hand.drawCard(draw);
			IO.info("Carded for not saying \"PHAT.\"");
		}
		if ( SPADES && !spadesFollowed )
		{
			hand.drawCard(draw);
			IO.info("Carded for not saying \"" + play.getNumber() + " OF SPADES.\"");
		}
		if ( MAO && !maoFollowed )
		{			
			hand.drawCard(draw);
			IO.info("Carded for not saying \"MAO.\"");
		}
	}
}
