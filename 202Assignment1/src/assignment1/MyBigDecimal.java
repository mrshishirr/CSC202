/**
 * 
 */
package assignment1;

/**
 * @author Mahbub Rahman
 *
 */
class MyBigDecimal implements NumericalOperand {

	// Need only two variables for input
	// and another variable for sign
	// declaring variable with explicit init
	private boolean negative = false;
	private int[] a = new int[201];
	private int[] b = new int[201];
	
	// this is the default constructor
	// which uses input validation inside constructor through custom method
	// throws exception when validation fails
	// the method called in this constructor uses finite state machine validation technique
	public MyBigDecimal(String sa, String sb) throws OutOfBoundException, InvalidNumberException {
		System.out.println("Validation begin through FSM...");
		int posA = validateFSM(sa);
		int posB = validateFSM(sb);
		System.out.println("Validation complete!");
		System.out.println();
		System.out.println("Processing operand 1...");
		insert(sa, posA, a);
		System.out.println("Processing operand 2...");
		insert(sb, posB, b);
	}
	
	// this is an alternative implementation of the constructor
	// uses another method to validate input numbers
	// overloaded with a boolean parameter to differentiate constructor
	public MyBigDecimal(String sa, String sb, boolean b) throws InvalidNumberException {
		System.out.println("Secondary validation begin without FSM...");
		int posA = validate(sa);
		int posB = validate(sb);
		System.out.println("Secondary validation complete!");
		System.out.println();
		System.out.println("Processing operand 1...");
		insert(sa, posA, a);
		System.out.println("Processing operand 2...");
		insert(sb, posB, this.b); // this keyword is used since class variable and method parameter name is same
	}

	// this is method for validation numbers in constructor
	// this one throws two kinds of exception
	// it uses a finite-state machine technique for validation
	// also returns the  
	private int validateFSM(String s) throws OutOfBoundException, InvalidNumberException {
		
		// checking for number size - validation # 1
		if (s.length() > 100)
			throw new OutOfBoundException();
		int state = 1, dptLocation = 0;
		
		// checking for decimal points - validation # 2
		// also validating correct number format in the same process
		// running a loop to validate each character in the input string
		for (int i = 0; i < s.length(); i++) {
			Character ch = new Character(s.charAt(i));
			
			// validating each character in the string through FSM
			switch (state) {
			case 1:
				if (Character.isDigit(ch)) {
					state = 2;
					continue;
				} else
					state = 5;
				break;
			case 2:
				if (Character.isDigit(ch))
					continue;
				else if (ch == '.') {
					state = 3;
					dptLocation = i;
					continue;
				} else {
					state = 5;
					break;
				}
			case 3:
				if (Character.isDigit(ch)) {
					state = 4;
					continue;
				} else
					state = 5;
				break;
			case 4:
				if (Character.isDigit(ch))
					continue;
				else
					state = 5;
				break;
			case 5:
				throw new InvalidNumberException();
			}
		}
		if (state != 5)
			return dptLocation;	// decimal point location for a valid entry
		else
			return -1; // this is ONLY to satisfy the method return
	}

	// this is another example of validation without using FSM technique
	// used in second constructor
	private int validate(String s) throws InvalidNumberException {
		int decimal = 0;
		int trim = 0;
		if (s.length() > 100)
			trim = 100;
		else
			trim = s.length();
		for (int i = 0; i < trim; i++) {
			if (i == 0 && !(Character.isDigit(s.charAt(i))))
				throw new InvalidNumberException();
			else {
				if (decimal == 0 && (Character.isDigit(s.charAt(i))))
					continue;
				if (decimal == 0 && s.charAt(i) == '.') {
					decimal = i;
					continue;
				}
				if (decimal > 0 && !(Character.isDigit(s.charAt(i))))
					throw new InvalidNumberException();
			}
		}
		if (decimal > 100)
			return 0;
		else
			return decimal;
	}
	
	// this is the method to insert the String input to an integer array
	// it also facilitates the trim feature if number is just too big (>100)
	private void insert(String s, int pos, int[] operand) {
		int trim = 0;
		if (s.length() > 100)
			trim = 100;
		else
			trim = s.length();
		
		// Some stats about input
		System.out.print("Decimal position\t: " + pos);
		System.out.print("\nInput length\t\t: " + s.length());
		System.out.print("\nTrim set at\t\t: " + trim);
		
		// no decimal point
		if (pos == 0) {			
			for (int i = 0; i < trim; i++) {
				operand[(101 - trim) + i] = Character.getNumericValue(s.charAt(i));
				// method 1 - converting char to int
			}
		}
		// a decimal point is found in the input
		else {
			for (int i = 0; i < pos; i++) {
				operand[(101 - pos) + i] = s.charAt(i) - '0';
				// method 2 - converting char to int
			}
			String part = s.substring(pos + 1, trim);
			System.out.print("\nFraction part\t\t: " + part);
			for (int i = 0; i < part.length(); i++) {
				operand[101 + i] = Integer.parseInt(String.valueOf(part.charAt(i)));
				// method 3 - - converting char to int
			}
		}
		// print an array for verification
		System.out.print("\nTesting array..   ");
		printArray(operand);
		System.out.print("\n\n");
	}

	// the add() method from the interface implemented here
	@Override
	public NumericalOperand add(NumericalOperand operand) {
		System.out.print("# Adding inputs...\n");
		int[] result = new int[201];
		int carry = 0;
		for (int i = 200; i >= 0; i--) {
			if (a[i] + b[i] + carry >= 10) {
				result[i] = (a[i] + b[i] + carry) - 10;
				carry = 1;
			} else {
				result[i] = a[i] + b[i] + carry;
				carry = 0;
			}
		}
		System.out.print("\nResultant array\t: ");
		printArray(result);
		System.out.println(resultFormatter(result, negative));
		return null;
	}

	// this is a result formatter
	// it omits the leading and trailing zero's from the output result
	// prints the value in the formatted way with it's sign
	private String resultFormatter(int[] result, boolean negative) {
		String decimal = "", fraction = "";
		int sigNumLoc = 0;
		
		// looking for significant number in fraction part
		for (int i = 200; i > 100; i--) {
			if (result[i] == 0)
				continue;
			else {
				sigNumLoc = i;
				break;
			}
		}
		if (sigNumLoc == 0) fraction = "0";
		else {
			for (int i = 101; i <= sigNumLoc; i++) {
				fraction = fraction + result[i];
			}
		}
		// now re-initializing for the decimal part
		sigNumLoc = 0;
		for (int i = 0; i <= 100; i++) {
			if (result[i] == 0)
				continue;
			else {
				sigNumLoc = i;
				break;
			}
		}
		if (sigNumLoc == 0) decimal = "0";
		else {
			for (int i = sigNumLoc; i <= 100; i++) {
				decimal = decimal + result[i];
			}
		}
		
		// now let's print the decimal and fraction part with the sign
		if (negative)
			return "\nFormatted\t: " + "- " + decimal + "." + fraction;
		else
			return "\nFormatted\t: " + decimal + "." + fraction;
	}

	// the subtract() method from the interface implemented here
	@Override
	public NumericalOperand subtract(NumericalOperand operand) {
		System.out.print("\nNow comparing inputs for subtraction...\n");
		// for the subtract method to work, we need to compare the two input
		compare();
		
		// checking the comparison result and passing parameters accordingly to the method
		if (negative)
			System.out.print(resultFormatter(subtract(b, a), negative));
		else
			System.out.print(resultFormatter(subtract(a, b), negative));
		return null;
	}
	
	// this is a compare method of the two input
	// this will compare the value and change the class variable 'negative'
	// by default it assumes the first operand is bigger than the second operand
	private void compare() {
		boolean equality = true;
		for (int i = 0; i < 201; i++) {
			if (a[i] < b[i]) {
				negative = true;
				equality = false;
				System.out.println("Operand 1 is smaller than (<) operand 2!");
				System.out.println("Setting negative = true");
				break;
			} else if (a[i] > b[i]) {
				equality = false;
				System.out.println("Operand 1 is bigger than (>) operand 2.");
				System.out.println("No change needed! (negative = false by default)");
				break;
			} else
				continue;
		}
		if (equality) System.out.println("Operand 1 is equal to (=) operand 2.");
	}
	
	// The actual subtract() logic is in here
	private int[] subtract(int[] a, int[] b) {
		System.out.print("\n# Subtracting inputs...\n");
		int[] result = new int[201];
		int borrow = 0;
		for (int i = 200; i >= 0; i--) {

			if (a[i] < (b[i] + borrow)) {
				result[i] = (a[i] + 10) - (b[i] + borrow);
				borrow = 1;
			} else {
				result[i] = (a[i] - (b[i] + borrow));
				borrow = 0;
			}
		}
		System.out.print("\nResultant array\t: ");
		printArray(result);
		return result;
	}
	
	// a method only to print an array
	// used only for testing purpose
	private void printArray(int[] array) {
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		System.out.print(s);
	}

}
