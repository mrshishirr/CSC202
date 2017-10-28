import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SortTest {

	final static String TESTFILE = "SortTest.csv";

	static FileReader fin;
	static String[] comp = new String[10];
	static FileWriter temp1, temp2, temp3, temp4;
	static Scanner scan, recordIn;

	public static void main(String[] args) {

		// Showing title of the application
		System.out.println("Sort Program");
		System.out.println("============");
		System.out.print("\n");
		System.out.println("Written by: Mahbub Rahman");
		System.out.print("\n\n");

		// Taking user input
		scan = new Scanner(System.in);
		System.out.print("Test file name? (hit ENTER to use default)\t --> ");
		String op1 = scan.nextLine();
		System.out.print("Comparison group? (Enter a number 1-5)\t\t --> ");
		int op2 = scan.nextInt();

		try {

			if (op1.trim()!= "") {
				fin = new FileReader(op1);
			} else {
				fin = new FileReader(TESTFILE);
			}

			recordIn = new Scanner(fin).useDelimiter("\\r|,");

			// Start reading first line and keep continuing
			int loopCounter = 0;
			while (recordIn.hasNextLine()) {
				loopCounter++;
				// start populating comp[]
				// line 1
				for (int i = 0; i < 5; i++) {
					comp[i] = recordIn.next();
				}
				recordIn.nextLine();
				// line 2
				if (recordIn.hasNextLine()) {
					for (int j = 5; j < 10; j++) {
						comp[j] = recordIn.next();
					}
				}
				// comp[] population complete

				String st1, st2;
				int int1, int2;
				float flt1, flt2;

				temp1 = new FileWriter("temp1");
				temp2 = new FileWriter("temp2");

				switch (op2) {
				case 1: // compare by Last Name (String)

					st1 = comp[0];
					st2 = comp[5];
					if (loopCounter % 2 == 0) {
						if (st1.compareToIgnoreCase(st2) <= 0) {
							splitToFile1(recInArray(comp, 0));
							splitToFile1(recInArray(comp, 5));
						} else {
							splitToFile1(recInArray(comp, 5));
							splitToFile1(recInArray(comp, 0));
						}
					} else {
						if (st1.compareToIgnoreCase(st2) <= 0) {
							splitToFile2(recInArray(comp, 0));
							splitToFile2(recInArray(comp, 5));
						} else {
							splitToFile2(recInArray(comp, 5));
							splitToFile2(recInArray(comp, 0));
						}

					}
					break;
				case 2: // compare by First Name (String)

					st1 = comp[1];
					st2 = comp[6];
					if (loopCounter % 2 == 0) {
						if (st1.compareToIgnoreCase(st2) <= 0) {
							splitToFile1(recInArray(comp, 0));
							splitToFile1(recInArray(comp, 5));
						} else {
							splitToFile1(recInArray(comp, 5));
							splitToFile1(recInArray(comp, 0));
						}
					} else {
						if (st1.compareToIgnoreCase(st2) <= 0) {
							splitToFile2(recInArray(comp, 0));
							splitToFile2(recInArray(comp, 5));
						} else {
							splitToFile2(recInArray(comp, 5));
							splitToFile2(recInArray(comp, 0));
						}

					}
					break;
				case 3: // compare by employee number (int)

					int1 = Integer.parseInt(comp[2]);
					int2 = Integer.parseInt(comp[7]);

					if (loopCounter % 2 == 0) {
						if (int1 <= int2) {
							splitToFile1(recInArray(comp, 0));
							splitToFile1(recInArray(comp, 5));
						} else {
							splitToFile1(recInArray(comp, 5));
							splitToFile1(recInArray(comp, 0));
						}
					} else {
						if (int1 <= int2) {
							splitToFile2(recInArray(comp, 0));
							splitToFile2(recInArray(comp, 5));
						} else {
							splitToFile2(recInArray(comp, 5));
							splitToFile2(recInArray(comp, 0));
						}
					}
					break;
				case 4: // compare by salary (float)

					flt1 = Float.parseFloat(comp[3]);
					flt2 = Float.parseFloat(comp[8]);

					if (loopCounter % 2 == 0) {
						if (flt1 <= flt2) {
							splitToFile1(recInArray(comp, 0));
							splitToFile1(recInArray(comp, 5));
						} else {
							splitToFile1(recInArray(comp, 5));
							splitToFile1(recInArray(comp, 0));
						}
					} else {
						if (flt1 <= flt2) {
							splitToFile2(recInArray(comp, 0));
							splitToFile2(recInArray(comp, 5));
						} else {
							splitToFile2(recInArray(comp, 5));
							splitToFile2(recInArray(comp, 0));
						}
					}
					break;
				case 5: // compare by zipcode (int)

					int1 = Integer.parseInt(comp[4]);
					int2 = Integer.parseInt(comp[9]);

					if (loopCounter % 2 == 0) {
						if (int1 <= int2) {
							splitToFile1(recInArray(comp, 0));
							splitToFile1(recInArray(comp, 5));
						} else {
							splitToFile1(recInArray(comp, 5));
							splitToFile1(recInArray(comp, 0));
						}
					} else {
						if (int1 <= int2) {
							splitToFile2(recInArray(comp, 0));
							splitToFile2(recInArray(comp, 5));
						} else {
							splitToFile2(recInArray(comp, 5));
							splitToFile2(recInArray(comp, 0));
						}
					}
					break;
				default:
					System.out.println("Wrong option! Please run the program again!");
				}

			}

			// closing file to finalize writing
			temp1.close();
			temp2.close();

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Need to close the file resource if exception occurs
			scan.close();
			try {
				if (fin != null)
					fin.close();
			} catch (IOException ex) {
				// throws an exception inside an exception when action fails
				System.out.println(ex.getMessage());
			}
		}
	}

	private static void splitToFile1(String s) throws IOException {

		temp1.append(s);
	}

	private static void splitToFile2(String t) throws IOException {

		temp2.append(t);
	}

	private static String recInArray(String[] s, int offset) {

		return (s[offset] + ", " + s[offset + 1] + " " + s[offset + 2] + " $" + s[offset + 3] + " " + s[offset + 4]
				+ "\n");

	}

}
