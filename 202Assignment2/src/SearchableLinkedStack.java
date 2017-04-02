
public class SearchableLinkedStack<T> extends LinkedStack<T> {

	protected LLNode<T> current;

	public SearchableLinkedStack() {
		super();
		current = super.top;
	}

	public int search(T element) {
		if (element instanceof Character)
			return this.search((char) element);
		else {
			int index = -1;
			current = super.top;

			if (current != null) {
				while (current != null) {
					index++;
					if (current.getInfo().equals(element))
						return index;
				}
			}

			else
				throw new StackUnderflowException("Search attempted on an empty stack.");
			return -1;
		}
	}

	public int search(char ch) {
		int index = -1;
		current = super.top;
		
		if (current != null) {
			while (current != null) {
				index++;
				if (current.getInfo() instanceof MyMazeNode<?>) {

					MyMazeNode<?> m = (MyMazeNode<?>) current.getInfo();
					if ( m.getVal()  instanceof Character) {
//						 System.out.println("val: " + m.getVal());
						if ((char) m.getVal() == ch) {
//							System.out.println("found at: " + m.getLocation());
							return index;
						}
						else current = current.getLink();
							
					}
				}
			}					
		}
		else
			throw new StackUnderflowException("Search attempted on an empty stack.");
		return index;
		
	}
	
	public int search(int row, int col) {
		int index = -1;
		current = super.top;
		
		if (current != null) {
			while (current != null) {
				index++;
				if (current.getInfo() instanceof MyMazeNode<?>) {

					MyMazeNode<?> m = (MyMazeNode<?>) current.getInfo();
					if((m.getRow() == row) && (m.getCol() == col))
							return index;
					else current = current.getLink();
							
				}
			}
		}		
		else
			throw new StackUnderflowException("Search attempted on an empty stack.");
		return index;
		
	}

	public void reverse() {
		LinkedStack<T> temp = new LinkedStack<T>();
		if (!isEmpty()) {
			while (!isEmpty()) {
				temp.push(super.top());
				super.pop();
			}
			super.top = temp.top;
			current = super.top;
			temp.top = null;
		} else
			throw new StackUnderflowException("Reverse attempted on an empty stack.");
	}

	public int length() {
		int length = 0;
		current = super.top;

		if (current != null) {
			while (current != null) {
				length++;
				current = current.getLink();
			}
		}
		current = super.top;
		return length;
	}

	public T locator(int index) {
		current = super.top;
		
		if (index < 0)
			throw new StackUnderflowException("Index " + index + " lower than possible range!");
//		else if (index == 0)
//			throw new StackUnderflowException("Cannot locate node on an empty stack!");
		else if (index > this.length())
			throw new StackOverflowException("Index " + index + " higher than actual range!");
		else {
			
			if (current != null) {
				for (int i = 0; i < index; i++) {
					current = current.getLink();
				}
				return current.getInfo();	
			}
			else 
				throw new StackUnderflowException("Stack appears to be empty!");
		}
	}

	public String toString() {
		String s = "";

		current = super.top;
		if (current != null) {
			while (current != null) {

					s = s + current.getInfo().toString();
					current = current.getLink();
				}
			return s;

		} else
			throw new StackUnderflowException("Stack empty. Nothing to print!");
	}
}
