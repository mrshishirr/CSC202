
public class MyMazeNode <T> {

	private int row, col;
	private T val;
	private T l, r, u, d;
	
	public MyMazeNode() {
		setRow(-1); setCol(-1);
		setVal(null);
		setL(null); setR(null); setU(null); setD(null);
		
	}
	
	public MyMazeNode(int r, int c, T v, T s) {
		this();
		setRow(r); setCol(c);
		setVal(v);
		setL(s); setR(s); setU(s); setD(s);	
	}
	
	public String toString() {
		String s1, s2, s3, s4;
		s1 = "Location = (r" + getRow() + ", c" + getCol() +")"; 
		s2 = "\n\t" + getU();
		s3 = "\n" + getL() + "\t" + val.toString() + "\t" + getR();
		s4 = "\n\t" + getD() + "\n";
		return s1 + s2 + s3 + s4;
	}
	
	public String getLocation() {
		return "(r" + getRow() + ", c" + getCol() +")"; 
	}
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * @param row the row to set
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
	 * @param col the col to set
	 */
	private void setCol(int col) {
		this.col = col;
	}
	/**
	 * @return the val
	 */
	public T getVal() {
		if ((val instanceof Character) || (val instanceof String))
			getStringVal();
		return val;
	}
	/**
	 * @return the val in String
	 */
	public String getStringVal() {
		String s = null;
		
		if (val instanceof Character) 
			 s =  s.valueOf(val);
		else s = (String) val;
		
		return s;
	}
	/**
	 * @param val the val to set
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
	 * @param l the l to set
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
	 * @param r the r to set
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
	 * @param u the u to set
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
	 * @param d the d to set
	 */
	public void setD(T d) {
		this.d = d;
	}
	

}
