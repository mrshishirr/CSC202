import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * This is the test program that uses SearchableLinkedList and MyMazeNode class
 * The program utilizes the heavyweight GUI components in Java
 */

@SuppressWarnings("serial")
public class TestMyMaze extends JFrame implements ActionListener {

	// GUI elements needed for this test class
	JButton jButton = new JButton("Choose input file...");
	JFileChooser jFileChooser = new JFileChooser();
	JTextArea jta1 = new JTextArea();
	JTextArea jta2 = new JTextArea();

	// MyMazeNode will store Character type of data
	MyMazeNode<Character> maze;
	SearchableLinkedStack<MyMazeNode<Character>> sls = new SearchableLinkedStack<MyMazeNode<Character>>();

	public TestMyMaze() {

		setTitle("My Maze");

		// Setting layouts and placing components
		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		// Setting up panels
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(0, 2, 2, 2));

		// Adding components to panels
		p1.add(jButton, BorderLayout.NORTH);
		p1.add(new JLabel("Input file content:"), BorderLayout.WEST);
		p1.add(new JLabel("Path result:"), BorderLayout.EAST);
		p2.add(new JScrollPane(jta1));
		p2.add(new JScrollPane(jta2));

		// Adding components to container
		container.add(p1, BorderLayout.NORTH);
		container.add(p2, BorderLayout.CENTER);

		// Register action listener
		jButton.addActionListener(this);
		
	}

	public static void main(String[] args) {

		// Creating a GUI frame
		TestMyMaze frame = new TestMyMaze();
		frame.setSize(600, 400);
		
		// Setting a minimum size
		Dimension minsize = new Dimension(300, 300);
		frame.setMinimumSize(minsize);

		// Let's center it!

		// Get the dimension of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		// Get the dimension of the frame
		Dimension frameSize = frame.getSize();
		int x = (screenWidth - frameSize.width) / 2;
		int y = (screenHeight - frameSize.height) / 2;

		frame.setLocation(x, y);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// on click, open file selector dialog
		
		if (e.getSource() == jButton)
			try {
				openDialog();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}

	private void openDialog() throws Exception {
		
		// TEST - view current path
		System.out.println("Current Directory = " + jFileChooser.getCurrentDirectory().getPath());
		// Setting a preselected file for file selector 
		jFileChooser.setSelectedFile(new File(jFileChooser.getCurrentDirectory(), "Test.txt"));

		if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			openFile(jFileChooser.getSelectedFile());
		}
	}

	private void openFile(File selectedFile) throws Exception {
		// Opens the text file and shows it on the window
		
		// init area
		BufferedReader infile = null;
		String inLine;
		jta1.setText(null);
		jta2.setText(null);

		try {
			// Create a character-input stream from the file
			infile = new BufferedReader(new FileReader(selectedFile));

			// read each line
			while ((inLine = infile.readLine()) != null) {
				jta1.append(inLine + '\n');
			}
			
			/*
			 *  This is the main execution area
			 *  
			 * first method will insert MyMazeNode into a stack for each character
			 * second method will determine the path between '*' (start) and 'E' (finish)
			 *  
			 */
			
			insertMazeNode(jta1.getText());
			jta2.append(findPath());

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Need to close the file resource if exception occurs
			
			try {
				if (infile != null)
					infile.close();
			} catch (IOException ex) {
				// throws an exception inside an exception when action fails
				System.out.println(ex.getMessage());
			}
		}
	}

	private void insertMazeNode(String text) {
		// Inserts the character in the SearchableLinkedStack as MyMazeNode class
		// Starts form the beginning to the end of the file input
		
		int r = 1;
		int c = 1;
		sls = new SearchableLinkedStack<MyMazeNode<Character>>();

		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				r++;
				c = 1;
				// set row and column structure
			} else {
				// setting a marker 'U' (unexplored) for each of the node's direction data
				maze = new MyMazeNode<Character>(r, c, text.charAt(i), 'U');
				
				// setting up boundary for the maze
				// a 'W' (wall) value in the node's direction data will represent a boundary node
				if (r == 1)
					maze.setU('W');
				if (r == 10)
					maze.setD('W');
				if (c == 1)
					maze.setL('W');
				if (c == 10)
					maze.setR('W');

				// TEST - checking MyMazeNode condition logic
				System.out.println(maze.toString());

				sls.push(maze);
				c++;
			}
		}
		
		// need to reverse the stack to match file input
		sls.reverse();
		
		// TEST - checking proper stack insert
//		jta2.append(sls.toString());
	}

	public String findPath() {
		// This method finds the path between the start node and the end node
		// using entirely the SearchableLinkedStack
		
		// first, let's do some error checking
		String path = "";

		int startPt = sls.search('*');
		if (startPt == -1) {
			path = "No start point!!";
			return path;
		}

		int endPt = sls.search('E');
		if (endPt == -1) {
			path = "No end point!!";
			return path;
		}

		// creating a helper SearchableLinkedStack which will store the path solution
		SearchableLinkedStack<MyMazeNode<Character>> slshelper = new SearchableLinkedStack<MyMazeNode<Character>>();
		slshelper.push(sls.locator(startPt));

		// setting up a loop condition
		boolean exitFound = false;
		
		while (!exitFound) {
			// TEST - trace current location in helper stack
			System.out.println("helper: " + slshelper.top().getLocation());
			
			/*
			 * the system will look for paths in LFET-UP-DOWN-RIGHT order
			 * if multiple paths are present, system will choose the one
			 * that goes in LFET-UP-DOWN-RIGHT order
			 */			
			
			// checking LEFT side
			if (slshelper.top().getL() == 'U') {
				// Left unexplored, value needs to be set

				MyMazeNode<Character> locatedNode = sls
						.locator(sls.search(slshelper.top().getRow(), slshelper.top().getCol() - 1));
				slshelper.top().setL((locatedNode.getVal()));
				
				// TEST - trace left adjacent location
				System.out.println("  L located: " + locatedNode.getLocation());

				// accepted path values are '0' or 'E' 
				if (locatedNode.getVal() == '0') {
					locatedNode.setR(slshelper.top().getVal());
					slshelper.push(locatedNode);
				} else if (locatedNode.getVal() == 'E') {
					locatedNode.setR(slshelper.top().getVal());
					slshelper.push(locatedNode);
					exitFound = true;
					break;
				}
//				else if(locatedNode.getVal() == '1') {
//				// do nothing, the located cell is a roadblock
//				}
			}
			
			// checking UP side
			else if (slshelper.top().getU() == 'U') {

				MyMazeNode<Character> locatedNode = sls
						.locator(sls.search(slshelper.top().getRow() - 1, slshelper.top().getCol()));
				slshelper.top().setU((locatedNode.getVal()));

				System.out.println("  U located: " + locatedNode.getLocation());

				if (locatedNode.getVal() == '0') {
					locatedNode.setD(slshelper.top().getVal());
					slshelper.push(locatedNode);
				} else if (locatedNode.getVal() == 'E') {
					locatedNode.setD(slshelper.top().getVal());
					slshelper.push(locatedNode);
					exitFound = true;
					break;
				}
			}
			
			// checking DOWN side
			else if (slshelper.top().getD() == 'U') {

				MyMazeNode<Character> locatedNode = sls
						.locator(sls.search(slshelper.top().getRow() + 1, slshelper.top().getCol()));
				slshelper.top().setD((locatedNode.getVal()));

				System.out.println("  D located: " + locatedNode.getLocation());

				if (locatedNode.getVal() == '0') {
					locatedNode.setU(slshelper.top().getVal());
					slshelper.push(locatedNode);

				} else if (locatedNode.getVal() == 'E') {
					locatedNode.setU(slshelper.top().getVal());
					slshelper.push(locatedNode);
					exitFound = true;
					break;
				}
			}
			
			// checking RIGHT side
			else if (slshelper.top().getR() == 'U') {

				MyMazeNode<Character> locatedNode = sls
						.locator(sls.search(slshelper.top().getRow(), slshelper.top().getCol() + 1));
				slshelper.top().setR((locatedNode.getVal()));

				System.out.println("  R located: " + locatedNode.getLocation());

				if (locatedNode.getVal() == '0') {
					locatedNode.setL(slshelper.top().getVal());
					slshelper.push(locatedNode);
				} else if (locatedNode.getVal() == 'E') {
					locatedNode.setL(slshelper.top().getVal());
					slshelper.push(locatedNode);
					exitFound = true;
					break;
				}
			}

			else {
				// TEST - trace blocked node
				System.out.println("BLOCK! popping: " + slshelper.top().getLocation());
				slshelper.pop();
			}
		} // end of while loop

		// the helper stack should hold the path nodes at this point
		if (!slshelper.isEmpty()) {
			
			// reversing the stack to print it in right order
			slshelper.reverse();

			int helperLength = slshelper.length();
			for (int p = 1; p <= helperLength; p++) {
				
				path = path + slshelper.top().getLocation() + "\t" + slshelper.top().getVal() + "\n";
				slshelper.pop();
			}
			
		return path;
			
		} else
			return "No way out!";

	}
}
