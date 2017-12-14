import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SimpleExpressionParser implements ExpressionParser {
	/*
	 * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
         * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * @param str the string to parse into an expression tree
	 * @param withJavaFXControls you can just ignore this variable for R1
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic 
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}
	/**
	 * Attempts to parse a str, dividing it up into smaller components through recursion
	 * @param str Expression to parse
	 * @return Returns the expression parsed from str
	 */
	protected Expression parseExpression (String str) {
		
		//           This code needs to be somewhere else, 
		////             NOT in recursive function
		String alphabet = "qwertyuiopasdfghjklzxcvbnm";
		ArrayList<Character> alphabetList = new ArrayList<Character>();
		for(char c: alphabet.toCharArray()) {
			alphabetList.add(c);
		}
		////
		//


/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
		//Check the grammar and create respective nodes through recursive parsing
		//Check for '+'
		if(findOuterMostChar(str, '+') > -1) { // A
			int plusIndex = findOuterMostChar(str, '+');
			// create node
			Add plus = new Add();
			// addSubexpression(left side of plus 'using recursion');
			String left = str.substring(0, plusIndex);
			plus.addSubexpression(parseExpression(left));
			// do the same for right side of recursion
			String right = str.substring(plusIndex+1);
			plus.addSubexpression(parseExpression(right));
			// set current node as parent for subexpressions
			for(Expression e: plus.getSubExpression()) {
				e.setParent(plus);
			}
			return plus;
		} 
		//Check for '*'
		else if(findOuterMostChar(str, '*') > -1) { // M
			int timesIndex = findOuterMostChar(str, '*');
			// create node
			Multiply times = new Multiply();
			// addSubexpression(left side of plus 'using recursion');
			String left = str.substring(0, timesIndex);
			times.addSubexpression(parseExpression(left));
			// do the same for right side of recursion
			String right = str.substring(timesIndex+1);
			times.addSubexpression(parseExpression(right));
			// set current node as parent for subexpressions
			for(Expression e: times.getSubExpression()) {
				e.setParent(times);
			}
			return times;
		}  
		//Check for '('
		else if(str.indexOf('(') > -1) { // X
			String inside = str.substring(1,str.length()-1);
			Parentheses paren = new Parentheses();
			paren.addSubexpression(parseExpression(inside));
			// set current node as parent for subexpressions
			for(Expression e: paren.getSubExpression()) {
				e.setParent(paren);
			}
			return paren;
		}
		//Else we know it is a terminal node
		else { // L
			Terminal terminal = new Terminal(str);
			return terminal;
		}
	}
	
	/**
	 * Will return the index of the last character inside the given string
	 * as long as that character is not inside a set of parentheses.
	 * @param str String to parse
	 * @param c char to get index of 
	 * @return return index of outer-most char
	 */
	private int findOuterMostChar(String str, char c) {
		int index = -1;
		int numOfOpenParens = 0;
		for(int i = 0; i < str.length(); i++) {
			char testChar = str.charAt(i);
			if(testChar == '(') {
				numOfOpenParens++;
			} else if (testChar == ')') {
				numOfOpenParens--;
			} else if (testChar == c && numOfOpenParens == 0) {
				index = i;
			}
		}
		return index;
	}
}
