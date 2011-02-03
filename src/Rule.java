import java.io.IOException;

/**
 * 
 * @author park282
 *
 */

public class Rule {
	private String code;
	
	/**
	 * Constructor
	 * @param statement
	 */
	public Rule(String statement){
		this.code = statement;
	}
	
	/**
	 * Returns code
	 * @return
	 */
	public final String getCode(){
		return code;
	}
	
	/**
	 * Parses variables such as $current_suit and replaces them with actual values
	 * @param code
	 * @param current
	 * @param last
	 * @return parsed code
	 */
	public static String parse(String code, Card current, Card last){
		String parsed = code;
		parsed = parsed.replaceAll("\\\"(\\$.+)\\\"", "$1");
		parsed = parsed.replaceAll("(\\$[a-z]+_[a-z]+)(\\s|\\n)", "\\\"$1\\\"$2");
		
		parsed = parsed.replace("$current_suit", current.getSuit().toString());
		parsed = parsed.replace("$current_number", current.getNumber().toString());
		parsed = parsed.replace("$current_color", current.getColor().toString());
		
		parsed = parsed.replace("$last_suit", last.getSuit().toString());
		parsed = parsed.replace("$last_number", last.getNumber().toString());
		parsed = parsed.replace("$last_color", last.getColor().toString());
		
		parsed = parsed.replace("&&", "and");
		parsed = parsed.replace("||", "or");
		
		return parsed;
	}
	
	/**
	 * Fetched rule from user
	 * @return valid rule code string
	 */
	public static String inputRule(){
		String code = "";
		String result = "";
		
		boolean isValid = false;
		while(true){
			IO.endl();
			IO.info("Write the body a Python function that will return True if it is against your rule.");
			IO.info("You may also import modules by typing \"import [modulename]\" and hitting enter twice.");
			IO.prompt("You may write here");
			IO.endl();
			
			do{
				try{
					code = "\t" + IO.br.readLine().toString() + "\n";
				}catch(IOException e){
					e.printStackTrace();
				}
				result += code;
			}while(!code.trim().isEmpty());

			isValid = Python.isValid(result);
			
			if(!isValid){
				continue;
			}else{
				break;
			}
		}
		
		return result;
	}

}
