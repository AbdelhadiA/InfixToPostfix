import java.util.*;

public class Calculator {

	static String infix;
	static String postFix = "";
	boolean converted;

	/**
	 * This constructor initializes the infix expression with the parameter.
	 * 
	 * @param exp : This parameter, of type String, is used to initialize the infix
	 *            with it.
	 */
	public Calculator(String exp) {
		infix = exp;

	}

	/**
	 * toString returns the infix value.
	 */
	public String toString() {
		return infix;
	} 

	/**
	 * This method is used to determine the precedence of operators in the infix
	 * expression.
	 * 
	 * @param ch : This parameter is used to represent the character for the
	 *           operator.
	 * @return : This method returns a integer value from 0 to 2, the higher the
	 *         value, the higher the precedence.
	 */
	private int precedence(char ch) {
		switch (ch) {
		case '*', '/':
			return 2;

		case '+', '-':
			return 1;

		default:
			return 0;
		}
	}

	/**
	 * This method converts the infix expression into the postfix expression.
	 * 
	 * @return: If the conversion is successful, a boolean value of true is
	 *          returned.
	 */
	private boolean convertPostfix() {

		Stack<Character> post = new Stack<Character>();
		Character ch = '.';

		if (infix.toString().length() <= 2) {
			return false;
		}

		/*
		 * A for loop is used to get each character value and put it through a switch
		 * statement to determine its precedence in the stack.
		 */
		for (int i = 0; i < infix.length(); i++) {

			ch = infix.charAt(i);
			switch (ch) {

			// Handles the digits for the postFix expression.
			default:
				postFix = postFix + ch;

				break;

			// Breaks if any spaces are found.
			case ' ':
				break;

			// Pushes the opening parenthesis if found.
			case '(':
				post.push(ch);
				// postFix += ' ';
				break;

			// Pops from the stack until the opening parenthesis is found.
			case ')':
				while (post.peek() != '(') {
					postFix += ' ';
					postFix = postFix + post.pop();

				}
				post.pop();

				break;

			/*
			 * In case of these operators, characters from the stack are popped and added to
			 * the expression
			 */
			case '+':
			case '-':
			case '*':
			case '/':

				postFix += ' ';

				while (!post.isEmpty() && post.peek() != '(' && precedence(ch) <= precedence(post.peek())) {

					postFix = postFix + post.pop();
					postFix += ' ';
				}

				post.push(ch);
				break;
			}
		}
		
		// Any remaining items in the stack are popped and added to the postFix expression.
		while (!post.isEmpty()) {
			postFix += ' ';
			postFix = postFix + post.pop();
			postFix += ' ';

		}

		/*
		 * If the postfix is the same as the infix, return false since the expression
		 * did not convert.
		 */
		if (postFix.equals(infix)) {
			return false;
		}

		// If the string contains an opening paranthesis and not a closing one, return
		// false.
		else if (postFix.contains("(") && !postFix.contains(")")) {
			return false;
		}

		// If the string does not contain any operators, return false.
		else if (!postFix.contains("-") && !postFix.contains("+") && !postFix.contains("/") && !postFix.contains("*")) {
			return false;
		}

		return true;
	}

	/**
	 * The getPostfix method takes the converted infix expression and returns the
	 * postfix form of it to the user.
	 * 
	 * @return: A String representing the postfix form of the expression is
	 *          returned.
	 * @throws IllegalStateException: If the conversion is unsuccessful, an
	 *                                IllegalStateException is thrown.
	 */
	public String getPostfix() throws IllegalStateException {

		Calculator postForm = new Calculator(infix);

		if (postForm.convertPostfix() == false) {
			throw new IllegalStateException("Conversion failed.");
		}
		return postFix.toString();

	}

	/**
	 * The evaluate method takes the postfix expression and solves it, returning the
	 * value of the postfix expression to the user.
	 * 
	 * @return : The value of the postfix expression is returned.
	 * @throws IllegalStateException : An IllegalStateException is thrown if the
	 *                               conversion was unsuccessful.
	 */
	public int evaluate() throws IllegalStateException {

		Calculator postForm = new Calculator(infix);

		if (postForm.convertPostfix() == false) {
			throw new IllegalStateException("Conversion failed.");
		}

		// Stack declaration to hold the integers being pushed in and popped.
		Stack<Integer> eval = new Stack<Integer>();
		int result = 0;

		Character ch;
		String post = "";
		int firstVal = 0;
		int secondVal = 0;
		String temp = "";
		int count = 0;

		/*
		 * A for loop is used to loop through the postfix expression and evaluate the
		 * expression.
		 */
		for (int i = 0; i < postFix.length(); i++) {
			ch = postFix.charAt(i);

			switch (ch) {

			default:

				post = post + ch;
				temp += post;

				if (postFix.charAt(i + 1) == ' ') {
					while (--count >= 0) {
						eval.pop();
					}
					eval.push(Integer.parseInt(String.valueOf(temp)));
					temp = "";
					count = 0;
				}

				else {
					eval.push(Integer.parseInt(String.valueOf(post)));
					count++;
				}
				break;

			// Performs addition on top two stack elements and stores sum in result.
			case '+':
				secondVal = eval.pop();
				firstVal = eval.pop();
				result = firstVal + secondVal;
				eval.push(result);
				break;

			// Performs substration on top two stack elements and stores difference in
			// result.
			case '-':
				secondVal = eval.pop();
				firstVal = eval.pop();
				result = firstVal - secondVal;
				eval.push(result);
				break;

			// Performs multiplications on top two stack elements and stores product in
			// result.
			case '*':
				secondVal = eval.pop();
				firstVal = eval.pop();
				result = firstVal * secondVal;
				eval.push(result);
				break;

			// Performs divisiion on top two stack elements and stores quotient in the
			// result.
			case '/':
				secondVal = eval.pop();
				firstVal = eval.pop();
				result = firstVal / secondVal;
				eval.push(result);

				// If there is a space, break.
			case ' ':
				break;

			}

			// Resets post to being empty.
			post = "";

		}

		return result;

	}

}
