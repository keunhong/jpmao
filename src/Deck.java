import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author park282 zhang156
 *
 */

public class Deck {
	private ArrayList<Card> cards;
	
	// Card: int number, color color, enum/String suit
	// Deck: card[] cards, methods: shuffle(), constructors (full/empty), give (deck c, int index)
	
	/**
	 * Constructor
	 * If isFull is true fills deck with all cards
	 */
	public Deck(boolean isFull){
		cards = new ArrayList<Card>();
		if(isFull){
			for (Card.Suit suit: Card.Suit.values()){
				for (Card.Number number: Card.Number.values()){
					cards.add(new Card(suit, number));
				}
			}
		}
	}
	
	/**
	 * Removes card
	 * @param i
	 * @return removed card
	 */
	private Card removeCard(int i){
		if(cards.size() > 0){
			Card temp = cards.get(i);
			cards.remove(i);
			return temp;
		}
		
		return null;
	}
	
	/**
	 * Receives a card
	 * @param newCard
	 */
	private void putCard(Card newCard){
		if(newCard != null){
			cards.add(newCard);
		}
	}
	
	/**
	 * Shuffles deck
	 */
	public void shuffle(){ // assigns a random number to each card
		Collections.shuffle(cards);
	}
	
	/**
	 * @param i
	 * @return card of index i
	 */
	public Card getCard(int i){
		return cards.get(i);
	}
	
	/**
	 * @return list of current cards (read-only)
	 */
	public final ArrayList<Card> getCards(){
		return cards;
	}
	
	/**
	 * Gets card from another deck to this deck
	 * @param from
	 */
	public void drawCard(Deck from){
		this.putCard(from.removeCard(0));
	}
	
	/**
	 * Gives a card from this deck to another deck
	 * @param to
	 */
	public void giveCard(Deck to, int i){
		to.putCard(this.removeCard(i));
	}
	
	/**
	 * @return number of cards
	 */
	public int size() {
		return cards.size();
	}
	
	/**
	 * Dumps cards in deck
	 */
	public void dump(){
		for(Card card: cards){
			System.out.println(card.getSuit());
		}
	}
	
	
}
