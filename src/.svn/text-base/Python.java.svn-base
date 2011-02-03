import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * 
 * @author park282
 *
 */

public class Python {
	private static PythonInterpreter interpreter = new PythonInterpreter();
	private static Pattern p;
	private static Matcher m;
	
	/**
	 * Initializes PythonInterpreter and imports modules
	 */
	public static void init(){
		importModule(null, "sys");
		importModule(null, "datetime");
		importModule("datetime", "datetime");
		importModule("datetime", "date");
		importModule("datetime", "time");
		importModule(null, "math");
	}
	
	/**
	 * Imports module and prints result
	 * @param from
	 * @param module
	 */
	public static void importModule(String from, String module){
		if(from == null){
			interpreter.exec("import " + module);
		}else{
			interpreter.exec("from " + from + " import " + module);
		}
		
		interpreter.exec("print " + module);
	}
	
	/**
	 * Evaluates code and returns boolean value
	 * @param code
	 * @param current
	 * @param last
	 * @return 
	 * @throws PyException
	 */
	public static boolean eval(String code, Card current, Card last){
		String parsed = Rule.parse(code, current, last);
		try{
			interpreter.exec("def check():\n" + parsed);
			interpreter.exec("x = check()");
		}catch(PyException e){
			IO.error(e.toString());
			IO.error(code);
			return false;
		}
		return interpreter.get("x").toString().contains("True");
	}
	
	/**
	 * Checks if Python code is valid
	 * @param code
	 * @return true iff valid false if not
	 */
	public static boolean isValid(String code){	
		
		if(code.trim().isEmpty()){
			IO.error("Rule cannot be empty.");
			return false;
		}
		
		/*
		 * Import modules if importing code is only code
		 */
		p = Pattern.compile("from (.+) import (.+)\\n");
		m = p.matcher(code);
		if(m.find() == true){
			IO.info("Importing module \"" + m.group(2) + "\" from \"" + m.group(1) + "\"");
			try{
				interpreter.exec("from " + m.group(1) + " import " + m.group(2));
			}catch(PyException e){
				IO.error(e.toString());
			}
			
			IO.info("Module \"" + m.group(1) + "\" has been imported from \"" + m.group(1) + "\"");
			return false;
		}
		
		p = Pattern.compile("import (.+)\\n");
		m = p.matcher(code);
		
		if(m.find() == true){
			IO.info("Importing module \"" + m.group(1) + "\"");
			try{
				interpreter.exec("import " + m.group(1));
			}catch(PyException e){
				IO.error(e.toString());
			}
			
			IO.info("Module \"" + m.group(1) + "\" has been imported");
			return false;
		}
		
		
		
		
		
		IO.info("Your code's validity is being checked.");		
		
		// Parse to see if code syntax is valid
		String parsed = Rule.parse(code, new Card(Card.Suit.DIAMONDS, Card.Number.ACE), new Card(Card.Suit.HEARTS, Card.Number.ACE));
		IO.info("Unparsed code:\n\tdef check():\n" + code.replace("\n\t\t", "").replace("\t", "\t\t"));
		IO.info("Parsed code:\n\tdef check():\n" + parsed.replace("\n\t\t", "").replace("\t", "\t\t"));
		
		if(!code.contains("return")){
			IO.error("Function must return a boolean value. (e.g. return True)");
			return false;
		}
		
		
		try{
			interpreter.exec("def check():\n" + parsed);
			interpreter.exec("t = check()");
			
		}
		// If exception is caught return false since it means it's not valid code
		catch(PyException e){
			IO.error(e.toString().replace("\n", "\n\t"));
			return false;
		}
		
		// Make sure the necessary components are included
		//if((!code.contains("and") && !code.contains("&&")) || !code.contains("$current_") ){
		if( !code.contains("$current_") ){
			IO.error("There must be code checking with current card. (e.g. date.today().weekday() == 5 and \"$current_suit\" == \"HEARTS\")");
			return false;
		}
		
		// If type is not a boolean return false
		if(!interpreter.get("t").getType().toString().contains("bool")){
			IO.error("Statement must return boolean value.");
			return false;
		}
		
		
		return true;
	}
	
}
