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

@SuppressWarnings("serial")
public class TestMyMaze extends JFrame implements ActionListener {

	JButton jButton = new JButton("Choose input file...");
	JFileChooser jFileChooser = new JFileChooser();
	JTextArea jta1 = new JTextArea();
	JTextArea jta2 = new JTextArea();

	MyMazeNode<Character> maze;
	SearchableLinkedStack< MyMazeNode<Character> > sls = new SearchableLinkedStack< MyMazeNode<Character> >();


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

		//Adding components to container		
		container.add(p1, BorderLayout.NORTH);
		container.add(p2, BorderLayout.CENTER);

		// Register listener
		jButton.addActionListener(this);

	}

	public static void main(String[] args){
		// Creating a GUI frame
		TestMyMaze frame  = new TestMyMaze();
		frame.setSize(600, 400);

		Dimension minsize = new Dimension (300, 300);
		frame.setMinimumSize(minsize);

		// Let's center it!

		// Get the dimension of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		// Get the dimension of the frame
		Dimension frameSize = frame.getSize();
		int x = (screenWidth - frameSize.width)/2;
		int y = (screenHeight - frameSize.height)/2;

		frame.setLocation(x, y);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jButton)
			try {
				openDialog();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	}

	private void openDialog() throws Exception {
		System.out.println(jFileChooser.getCurrentDirectory().getPath());
		jFileChooser.setSelectedFile(new File(jFileChooser.getCurrentDirectory(), "2Test.txt"));

		if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			openFile(jFileChooser.getSelectedFile());			

		}

	}

	private void openFile(File selectedFile) throws Exception {
		BufferedReader infile = null;
		String inLine;
		jta1.setText(null);
		jta2.setText(null);

		try {
			// Read from the file
			infile = new BufferedReader(new FileReader(selectedFile));

			while((inLine = infile.readLine()) != null) {
				jta1.append(inLine + '\n');

			}
			insertMazeNode(jta1.getText());

			//			jta2.append(searchStartNode().getLocation());
			// TODO create a linkedstack where we have to search things
			jta2.append(findPath());

		}
		catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		finally {
			try {
				if (infile != null)
					infile.close();
			}
			catch(IOException ex) {
				System.out.println(ex.getMessage());
			}
		}		
	}

	private void insertMazeNode(String text){
		int r = 1;
		int c = 1;
		sls = new SearchableLinkedStack< MyMazeNode<Character> >();

		for (int i = 0; i<text.length(); i++){
			if (text.charAt(i)=='\n'){
				r++;
				c = 1;
			}
			else {
				maze = new MyMazeNode<Character> (r, c, text.charAt(i), 'U');
				if (r == 1) maze.setU('W');
				if (r == 10) maze.setD('W');
				if (c == 1) maze.setL('W');
				if (c == 10) maze.setR('W');				

				// Testing proper MyMazeNode reading
				//				jta2.append(maze.toString());

				sls.push(maze);
				c++;
			}
		}
		sls.reverse();
//		 Testing proper stack insert
//				jta2.append(sls.toString());
	}

	public String findPath() {
		String path = "";

		int startPt =  sls.search('*');
		if (startPt == -1) {
			path = "No start point!!";
			return path;
		}

		int endPt =  sls.search('E');
		if (endPt == -1) {
			path = "No end point!!";
			return path;
		}


		SearchableLinkedStack< MyMazeNode<Character> > slshelper 
		= new SearchableLinkedStack< MyMazeNode<Character> >();
		//left-right-up-down lookup

		//		MyMazeNode<Character> startNode = sls.locator(startPt);
		slshelper.push(sls.locator(startPt));

		//		MyMazeNode<Character> node = startNode;

		//check for wall
		boolean exitFound = false;

		while (!exitFound) {
				System.out.println("helper: " + slshelper.top().getLocation());
				
				if (slshelper.top().getL() == 'U') {
					//value needs to be set

					MyMazeNode<Character> locatedNode = sls.locator(sls.search(slshelper.top().getRow(), slshelper.top().getCol() - 1));
					slshelper.top().setL((locatedNode.getVal()));
					
					System.out.println(" L locator: " + locatedNode.getLocation());
					
					if(locatedNode.getVal() == '0') {
						locatedNode.setR(slshelper.top().getVal());
						slshelper.push(locatedNode);
					}
					else if(locatedNode.getVal() == 'E') {
						locatedNode.setR(slshelper.top().getVal());
						slshelper.push(locatedNode);
						exitFound = true;
						break;
					}
//					else if(locatedNode.getVal() == '1') {
//						// do nothing, the located cell is a roadblock
//					}
				}
				
				

				
				
				else if (slshelper.top().getU() == 'U') {
					//value needs to be set

					MyMazeNode<Character> locatedNode = sls.locator(sls.search(slshelper.top().getRow() - 1, slshelper.top().getCol()));
					slshelper.top().setU((locatedNode.getVal()));
					
					System.out.println(" U locator: " + locatedNode.getLocation());
					
					if(locatedNode.getVal() == '0') {
						locatedNode.setD(slshelper.top().getVal());
						slshelper.push(locatedNode);
					}
					else if(locatedNode.getVal() == 'E') {
						locatedNode.setD(slshelper.top().getVal());
						slshelper.push(locatedNode);
						exitFound = true;
						break;
					}
//					else if(locatedNode.getVal() == '1') {
//						// do nothing, the located cell is a roadblock
//					}
				}				

				else if (slshelper.top().getD() == 'U') {
						//value needs to be set

						MyMazeNode<Character> locatedNode = sls.locator(sls.search(slshelper.top().getRow() + 1, slshelper.top().getCol()));
						slshelper.top().setD((locatedNode.getVal()));
						
						System.out.println(" D locator: " + locatedNode.getLocation());
						
						
						if(locatedNode.getVal() == '0') {
							locatedNode.setU(slshelper.top().getVal());
							slshelper.push(locatedNode);

						}
						else if(locatedNode.getVal() == 'E') {
							locatedNode.setU(slshelper.top().getVal());
							slshelper.push(locatedNode);
							exitFound = true;
							break;

						}
//						else if(locatedNode.getVal() == '1') {
//							// do nothing, the located cell is a roadblock
//						}
					}	
				

					else if (slshelper.top().getR() == 'U') {
							//value needs to be set

							MyMazeNode<Character> locatedNode = sls.locator(sls.search(slshelper.top().getRow(), slshelper.top().getCol() + 1));
							slshelper.top().setR((locatedNode.getVal()));
							
							System.out.println(" R locator: " + locatedNode.getLocation());
							
							if(locatedNode.getVal() == '0') {
								locatedNode.setL(slshelper.top().getVal());
								slshelper.push(locatedNode);
							}
							else if(locatedNode.getVal() == 'E') {
								locatedNode.setL(slshelper.top().getVal());
								slshelper.push(locatedNode);
								exitFound = true;
								break;
							}
//							else if(locatedNode.getVal() == '1') {
//								// do nothing, the located cell is a roadblock
//							}
						}
					
					
					else {
						System.out.println( "popped : " + slshelper.top().getLocation());
						slshelper.pop();
					}
				
							
		}			
				
				


		if (!slshelper.isEmpty()) {
			slshelper.reverse();
//			System.out.println(slshelper.toString());
			int helperLength = slshelper.length();
//			System.out.println("len = " + slshelper.length());
//					System.out.println(helperLength);
			for (int p = 1; p<= helperLength; p++) {
//					System.out.println(p);

				path = path + slshelper.top().getLocation() + "\t" + slshelper.top().getVal() +  "\n";
				slshelper.pop();
			}
//			System.out.print(path);
			return path;
		}
		else return "No way out!";

	}
}





