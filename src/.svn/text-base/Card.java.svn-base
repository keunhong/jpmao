/**
 * Contains static enums of Suit, Number, and Color
 * Stores Suit, Color, and Number of one card
 * This class is immutable
 * 
 * @author park282
 *
 */
public class Card {
	public static enum Suit{
		SPADES, HEARTS, DIAMONDS, CLUBS
	}
	public static enum Number{
		ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	}
	public static enum Color{
		RED, BLACK
	}
	
	private Suit suit;
	private Color color;
	private Number number;
	
	/**
	 * Constructor
	 * @param s
	 * @param n
	 */
	public Card(Suit s, Number n){
		suit = s;
		number = n;
		switch(s){
			case CLUBS:
				color = Color.BLACK;
				break;
			case SPADES:
				color = Color.BLACK;
				break;
			case HEARTS:
				color = Color.RED;
				break;
			case DIAMONDS:
				color = Color.RED;
				break;
		}
	}
	
	/**
	 * Returns Color
	 * @return
	 */
	public Color getColor(){
		return color;
	}
	
	/**
	 * Returns Suit
	 * @return
	 */
	public Suit getSuit(){
		return suit;
	}
	
	/**
	 * Returns Number
	 * @return
	 */
	public Number getNumber(){
		return number;
	}
	
	/**
	 * Returns string representation of card
	 * 	e.g. KING of HEARTS
	 */
	public String toString(){
		return number + " of " + suit + " (" + color + ")";
	}

}
