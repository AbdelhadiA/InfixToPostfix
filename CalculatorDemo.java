import java.util.*;

public class CalculatorDemo {

	public static void main(String[] args) {

		Scanner kbd = new Scanner(System.in);
		String input = "";

		/*
		 * Loops until the user inputs -1.
		 */
		while (input != "-1") {

			System.out.println("Enter an infix expression (-1 to exit): ");

			input = kbd.nextLine();

			if (input.equals("-1")) {
				System.exit(0);
			}

			Calculator convert = new Calculator(input);

			// Tries to get and evalulate the postfix expression.
			try {

				System.out.println("\nThe postfix form is: " + convert.getPostfix());

				System.out.println("After evaluating, the answer is: " + convert.evaluate() + "\n");
			}

			// Catches any thrown exceptions.
			catch (IllegalStateException e) {
				System.out.println("\nConversion failed.\n");

			}

			// Sets postfix variable back to "".
			Calculator.postFix = "";
		}

		// Closes the kbd variable.
		kbd.close();

	}

}
