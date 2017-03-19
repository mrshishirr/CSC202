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
	
	MyMazeNode<Character> maze; // = new MyMaze<Character>();
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

	public static void main(String[] args) {
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
			openDialog();

	}

	private void openDialog() {
		if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			openFile(jFileChooser.getSelectedFile());

			
			
		}
		
	}

	private void openFile(File selectedFile) {
		BufferedReader infile = null;
		String inLine;
		jta1.setText(null);
		
		try {
			// Read from the file
			infile = new BufferedReader(new FileReader(selectedFile));
			
			while((inLine = infile.readLine()) != null) {
				jta1.append(inLine + '\n');
				
			}
			insertMazeNode(jta1.getText());
			// TODO create a linkedstack where we have to search things
			
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
		
		for (int i = 0; i<text.length(); i++){
			if (text.charAt(i)=='\n'){
				r++;
				c = 1;
			}
			else {
				maze = new MyMazeNode<Character> (r, c, text.charAt(i));
				// Testing proper MyMazeNode reading
				//jta2.append(maze.toString());
				
				sls.push(maze);
				

				
				c++;

			}
		}
		// Testing proper stack insert
		//jta2.append(sls.toString());
	}
	
	public int searchStartNode(){
		 return sls.search('S');
	
	}
	
	public void findPath() {
		//left-right-up-down lookup
		
		//check left side
		//check for wall
		if (getCol() != 0) {
			if (getVal() == ) {
				if (getL() == 1)
					setL(1);
			}
		}
		
	}
	}
	

}
