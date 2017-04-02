
/* 
 * This is the node which will hold the file contents 
 * Ii also uses Java Generics for storing information
 * which will be determined at the runtime
 * 
 * */

public class MyMazeNode<T> {

	private int row, col; // nodes identified by rows and columns
	private T val;
	private T l, r, u, d;
	// the node will carry values for itself and its adjacent nodes

	public MyMazeNode() {
		// default constructor, explicitly init variables

		setRow(-1);	setCol(-1);
		setVal(null);
		setL(null);	setR(null);	setU(null);	setD(null);
	}

	public MyMazeNode(int r, int c, T v, T s) {
		this(); // calling default constructor

		// now modifying it with provided parameters
		setRow(r); setCol(c);
		setVal(v);
		setL(s); setR(s); setU(s); setD(s);
	}

	public String toString() {
		// a toString() method which prints out the contents of this class

		String s1, s2, s3, s4;

		s1 = "Location = (r" + getRow() + ", c" + getCol() + ")";
		s2 = "\n\t" + getU();
		s3 = "\n" + getL() + "\t" + val.toString() + "\t" + getR();
		s4 = "\n\t" + getD() + "\n";

		return s1 + s2 + s3 + s4;
	}

	public String getLocation() {
		// used for node addressing purpose in testing

		return "(r" + getRow() + ", c" + getCol() + ")";
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	private void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	private void setCol(int col) {
		this.col = col;
	}

	/**
	 * @return the val
	 */
	public T getVal() {
		/*
		 * provided an option to return the value in String since it's more
		 * human readable
		 */

		if ((val instanceof Character) || (val instanceof String))
			getStringVal();
		return val;
	}

	/**
	 * @return the val in String
	 */
	public String getStringVal() {
		// helper method to facilitate getVal() method

		String s = null;

		if (val instanceof Character)
			s = String.valueOf(val);
		else
			s = (String) val;

		return s;
	}

	/**
	 * @param val
	 *            the val to set
	 */
	public void setVal(T val) {
		this.val = val;
	}

	/**
	 * @return the l
	 */
	public T getL() {
		return l;
	}

	/**
	 * @param l
	 *            the l to set
	 */
	public void setL(T l) {
		this.l = l;
	}

	/**
	 * @return the r
	 */
	public T getR() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(T r) {
		this.r = r;
	}

	/**
	 * @return the u
	 */
	public T getU() {
		return u;
	}

	/**
	 * @param u
	 *            the u to set
	 */
	public void setU(T u) {
		this.u = u;
	}

	/**
	 * @return the d
	 */
	public T getD() {
		return d;
	}

	/**
	 * @param d
	 *            the d to set
	 */
	public void setD(T d) {
		this.d = d;
	}

}
