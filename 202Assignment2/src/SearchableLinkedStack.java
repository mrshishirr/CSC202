
public class SearchableLinkedStack<T> extends LinkedStack<T> {

	LinkedStack<T> temp = new LinkedStack<T>();

	public int search(T info) {

		int index = 0;
		if (!super.isEmpty()) {
			while (!super.isEmpty()) {
				index++;
				temp.push(super.top());
				if (super.top().equals(info)) {
					while (!this.isEmpty()) {
						super.push(temp.top());
						temp.pop();
					}
					return index;
				} else
					super.pop();
			}

			// nothing found, so get back the old stack
			while (!temp.isEmpty()) {
				super.push(temp.top());
				temp.pop();
			}
			return -1;
		} else
			throw new StackUnderflowException("Search attempted on an empty stack.");
	}

	public int search(char ch) {
		int index = 0;
		if (!super.isEmpty()) {
			while (!super.isEmpty()) {
				index++;
				temp.push(super.top());
				if (super.top() instanceof MyMazeNode<?>) {
					if (((char) (((MyMazeNode<?>) super.top()).getVal())) == ch) {
						while (!this.isEmpty()) {
							super.push(temp.top());
							temp.pop();
						}
						return index;
					} else
						super.pop();

				}
				// nothing found, so get back the old stack
				while (!temp.isEmpty()) {
					super.push(temp.top());
					temp.pop();
				}
			}
			return -1;
		} else
			throw new StackUnderflowException("Search attempted on an empty stack.");
	}

	public void reverse() {

		if (!isEmpty()) {
			while (!isEmpty()) {
				temp.push(super.top());
				super.pop();
			}
			super.top = temp.top;
			temp.top = null;
		} else
			throw new StackUnderflowException("Reverse attempted on an empty stack.");
	}

	public String toString() {
		String s = "";
		int length = 0;
		String[] str;

		if (!super.isEmpty()) {
			while (!super.isEmpty()) {
				length++;
				temp.push(super.top());
				super.pop();
			}
			super.top = temp.top;
			temp.top = null;

			str = new String[length];

			while (!super.isEmpty()) {
				for (int i = length - 1; i >= 0; i--) {
					temp.push(super.top());
					str[i] = temp.top().toString();
					super.pop();
				}
			}
			super.top = temp.top;
			for (int j = 0; j < str.length; j++) {
				s = s + str[j];
			}

			return s;
		} else
			throw new StackUnderflowException("Stack empty. Nothing to print!");
	}
}
