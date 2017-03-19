/**
 * 
 */
package assignment1;

import java.util.Scanner;

/**
 * @author Mahbub Rahman
 *
 */
class UseMyBigDecimal {

	/**
	 * @param args
	 * @throws InvalidNumberException
	 * @throws OutOfBoundException
	 * 
	 */
	public static void main(String[] args) throws OutOfBoundException, InvalidNumberException {
		
		// title of the application
		System.out.println("MY BIG DECIMAL");
		System.out.println("==============");
		System.out.print("\n");
		System.out.println("Written by: Mahbub Rahman");
		System.out.print("\n\n");
		
		// taking user inputs
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter first operand (100 digits max)\t: ");
		String op1 = scan.next();
		System.out.print("Enter second operand (100 digits max)\t: ");
		String op2 = scan.next();
		System.out.print("\n\n");
		
		// calling the  base constructor with the required operation method
		try {
			MyBigDecimal mbd = new MyBigDecimal(op1, op2);
			mbd.add(mbd);
			mbd.subtract(mbd);
		}
		
		// catching exception # 1 - too big number
		catch (OutOfBoundException e) {
			System.out.println("WARNING!");
			System.out.println("Operand(s) are too big!");
			System.out.println("Program will automatically truncate the input(s) if no other errors found.\n");
			
			// calling the second constructor (no more throwing OutOfBoundException) 
			MyBigDecimal mbd = new MyBigDecimal(op1, op2, true);
			mbd.add(mbd);
			mbd.subtract(mbd);
		}
		
		// catching exception # 2 - invalid characters in number
		// the error is severe and program will stop here
		// re-run the program to provide correct input
		catch (InvalidNumberException e) {
			System.out.println("ERROR!");
			System.out.println("Invalid input!!");
			System.out.println("Program cannot continue! Please run the program again with correct input!");
		} finally {
			scan.close();
		}
	}

}
