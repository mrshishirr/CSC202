//----------------------------------------------------------------------------
// BinarySearchTree.java          by Dale/Joyce/Weems                Chapter 8
//
// Defines all constructs for a reference-based BST
//----------------------------------------------------------------------------

package ch08.trees;

import ch05.queues.*;
import ch03.stacks.*;
import support.BSTNode;      

public class BinarySearchTree<T extends Comparable<T>> 
             implements BSTInterface<T>
{
  protected BSTNode<T> root;      // reference to the root of this BST

  boolean found;   // used by remove
  
  // for traversals
  protected LinkedUnbndQueue<T> inOrderQueue;    // queue of info
  protected LinkedUnbndQueue<T> preOrderQueue;   // queue of info
  protected LinkedUnbndQueue<T> postOrderQueue;  // queue of info

  public BinarySearchTree()
  // Creates an empty BST object.
  {
    root = null;
  }

  public boolean isEmpty()
  // Returns true if this BST is empty; otherwise, returns false.
  {
    return (root == null);
  }

  private int recSize(BSTNode<T> tree)
  // Returns the number of elements in tree.
  {
    if (tree == null)    
      return 0;
    else
      return recSize(tree.getLeft()) + recSize(tree.getRight()) + 1;
  }

  public int size()
  // Returns the number of elements in this BST.
  {
    return recSize(root);
  }

  public int size2()
  // Returns the number of elements in this BST.
  {
    int count = 0;
    if (root != null)
    {
      LinkedStack<BSTNode<T>> hold = new LinkedStack<BSTNode<T>>();
      BSTNode<T> currNode;
      hold.push(root);
      while (!hold.isEmpty())
      {
        currNode = hold.top();
        hold.pop();
        count++;
        if (currNode.getLeft() != null)
          hold.push(currNode.getLeft());
        if (currNode.getRight() != null)
          hold.push(currNode.getRight());
      }
    }
    return count;
  }


//private boolean recContains(T element, BSTNode<T> tree)
//  // Returns true if tree contains an element e such that 
//  // e.compareTo(element) == 0; otherwise, returns false.
//  {
//    if (tree == null)
//      return false;       // element is not found
//    else if (element.compareTo(tree.getInfo()) < 0)
//      return recContains(element, tree.getLeft());   // Search left subtree
//    else if (element.compareTo(tree.getInfo()) > 0)
//      return recContains(element, tree.getRight());  // Search right subtree
//    else
//      return true;        // element is found
//  }

  public boolean contains (T element)
  // Returns true if this BST contains an element e such that 
  // e.compareTo(element) == 0; otherwise, returns false.
  {
    return nonrecContains(element, root);
  }
  
  /**
   * Non recursive implementation of method "contains()"
   * @param element the stored item in the node
   * @param tree the BST pointer (usually the root)
   * @return a boolean value based on item found or not
   */  
  private boolean nonrecContains (T element, BSTNode<T> tree) {
	
	BSTNode<T> currentNode = tree;
	while (currentNode != null) {		// go all the way to the leaf
		if (currentNode.getInfo().compareTo(element) == 0)
			return true;		// item found
		else if (currentNode.getInfo().compareTo(element) > 0)
			currentNode = currentNode.getLeft();
		else if (currentNode.getInfo().compareTo(element) < 0)
			currentNode = currentNode.getRight();
	}
	return false;		// item not found after visiting every node
  }  
  

//private T recGet(T element, BSTNode<T> tree)
//  // Returns an element e from tree such that e.compareTo(element) == 0;
//  // if no such element exists, returns null.
//  {
//    if (tree == null)
//      return null;             // element is not found
//    else if (element.compareTo(tree.getInfo()) < 0)
//      return recGet(element, tree.getLeft());          // get from left subtree
//    else
//    if (element.compareTo(tree.getInfo()) > 0)
//      return recGet(element, tree.getRight());         // get from right subtree
//    else
//      return tree.getInfo();  // element is found
//  }

  public T get(T element)
  // Returns an element e from this BST such that e.compareTo(element) == 0;
  // if no such element exists, returns null.
  {
    return nonrecGet(element, root);
  }

  /**
   * Non recursive implementation of method "get()"
   * @param element the stored item in the node
   * @param tree the BST pointer (usually the root)
   * @return the value in the node or a NULL value
   */
  
  private T nonrecGet (T element, BSTNode<T> tree) {
		
	BSTNode<T> currentNode = tree;
	while (currentNode != null) {		// go all the way to the leaf
		if (currentNode.getInfo().compareTo(element) == 0)
			return currentNode.getInfo();		// item found
		else if (currentNode.getInfo().compareTo(element) > 0)
			currentNode = currentNode.getLeft();
		else if (currentNode.getInfo().compareTo(element) < 0)
			currentNode = currentNode.getRight();
	}
	return null;		// item not found after visiting every node
  }
  
//  private BSTNode<T> recAdd(T element, BSTNode<T> tree)
//  // Adds element to tree; tree retains its BST property.
//  {
//    if (tree == null)
//      // Addition place found
//      tree = new BSTNode<T>(element);
//    else if (element.compareTo(tree.getInfo()) <= 0)
//      tree.setLeft(recAdd(element, tree.getLeft()));    // Add in left subtree
//    else
//      tree.setRight(recAdd(element, tree.getRight()));   // Add in right subtree
//    return tree;
//  }

  public void add (T element)
  // Adds element to this BST. The tree retains its BST property.
  {
    root = nonrecAdd(element, root);
  }

  /**
   * Non recursive implementation of method "add()"
   * @param element the stored item in the node
   * @param tree the BST pointer (usually the root)
   * @return the BST node that is getting added
   */  
	private BSTNode<T> nonrecAdd(T element, BSTNode<T> tree)

	{
		// create a reference to the root of the tree
		BSTNode<T> currentNode = tree;		
		// if no node exists, just add it
		if (currentNode == null) {
			currentNode = new BSTNode<T>(element);
			return currentNode;
		} else {
			// loop until a leaf node is found
			while (currentNode != null) {
				if (currentNode.getInfo().compareTo(element) > 0) {
//*Debug*/					System.out.println((String) currentNode.getInfo());
					if (currentNode.getLeft() != null)	// Go left
						currentNode = currentNode.getLeft();
					else {
						currentNode.setLeft(new BSTNode<T>(element));
						return tree;
					}
				}
				else {
					if (currentNode.getRight() != null)	// Go right
						currentNode = currentNode.getRight();
					else {
						currentNode.setRight(new BSTNode<T>(element));
						return tree;
					}
				}			
			}
			return null;
			
		}
	}
  
  private T getPredecessor(BSTNode<T> tree)
  // Returns the information held in the rightmost node in tree
  {
    while (tree.getRight() != null)
      tree = tree.getRight();
    return tree.getInfo();
  }

  private BSTNode<T> removeNode(BSTNode<T> tree)
  // Removes the information at the node referenced by tree.
  // The user's data in the node referenced by tree is no
  // longer in the tree.  If tree is a leaf node or has only
  // a non-null child pointer, the node pointed to by tree is
  // removed; otherwise, the user's data is replaced by its
  // logical predecessor and the predecessor's node is removed.
  {
    T data;

    if (tree.getLeft() == null)
      return tree.getRight();
    else if (tree.getRight() == null) 
      return tree.getLeft();
    else
    {
      data = getPredecessor(tree.getLeft());
      tree.setInfo(data);
      tree.setLeft(nonrecRemove(data, tree.getLeft()));  
      return tree;
    }
  }

//  private BSTNode<T> recRemove(T element, BSTNode<T> tree)
//  // Removes an element e from tree such that e.compareTo(element) == 0
//  // and returns true; if no such element exists, returns false. 
//  {
//    if (tree == null)
//      found = false;
//    else if (element.compareTo(tree.getInfo()) < 0)
//      tree.setLeft(recRemove(element, tree.getLeft()));
//    else if (element.compareTo(tree.getInfo()) > 0)
//      tree.setRight(recRemove(element, tree.getRight()));
//    else  
//    {
//      tree = removeNode(tree);
//      found = true;
//	 }
//    return tree;
//  }

  public boolean remove (T element)
  // Removes an element e from this BST such that e.compareTo(element) == 0
  // and returns true; if no such element exists, returns false. 
  {
    root = nonrecRemove(element, root);
    return found;
  }
  
  /**
   * Non recursive implementation of method "remove()"
   * NOTE: this piece of code is still in progress! This uses
   * another method "removeNode(tree)" which calls the parent method.
   * @param element the stored item in the node
   * @param tree the BST pointer (usually the root)
   * @return the BST node that is getting removed
   */  
  private BSTNode<T> nonrecRemove(T element, BSTNode<T> tree)

  {
	  BSTNode<T> currentNode = tree;
	  //while ((currentNode.getLeft() != null) && (currentNode.getRight() != null))
	  if (currentNode == null)
		  found = false;
	  else {
		  while (currentNode != null) {
			  if (currentNode.getInfo().compareTo(element) > 0)
				  currentNode = currentNode.getLeft(); 
			  else if (currentNode.getInfo().compareTo(element) < 0)
				  currentNode = currentNode.getRight(); 
			  else  
			  {
				  currentNode = removeNode(currentNode);
				  found = true;
			  }
		  }
	  }
	  return currentNode;
  }

//  private void inOrder(BSTNode<T> tree)
//  // Initializes inOrderQueue with tree elements in inOrder order.
//  {
//    if (tree != null)
//    {
//      inOrder(tree.getLeft());
//      inOrderQueue.enqueue(tree.getInfo());
//      inOrder(tree.getRight());
//    }
//  }
  
  /**
   * Non recursive implementation of method "inOrder()"
   * @param tree the BST pointer (usually the root)
   */  
  private void inOrderNR(BSTNode<T> tree)
  // Initializes inOrderQueue with tree elements in inOrder order.
  // The method still uses the existing Queue to print store the item
  {
	  LinkedStack<BSTNode<T>> stack = new LinkedStack<BSTNode<T>>();
	  if (tree == null) throw new StackUnderflowException("given tree is empty");
	  else {
		  stack.push(tree);
		  BSTNode<T> parentNode = null;
		  while (!stack.isEmpty()) {
			  BSTNode<T> currNode = stack.top();
//*Debug*/	    	System.out.println("stack - " + (String) stack.top().getInfo()); 
			  if (parentNode == null || parentNode.getLeft() == currNode || parentNode.getRight() == currNode) {
				  if (currNode.getLeft() != null)
					  stack.push(currNode.getLeft());
				  else if (currNode.getRight() != null)
				  {
					  inOrderQueue.enqueue(currNode.getInfo());
					  stack.push(currNode.getRight());
				  }

				  else //if ((currNode.getLeft() == null) && (currNode.getRight() == null))
				  {
					  inOrderQueue.enqueue(currNode.getInfo());
					  System.out.println((String) currNode.getInfo()); 
					  stack.pop();
				  }
			  }
			  else if (currNode.getLeft() == parentNode) {	

				  inOrderQueue.enqueue(currNode.getInfo());
				  System.out.println((String) currNode.getInfo()); 

				  if (currNode.getRight() != null)
					  stack.push(currNode.getRight());
				  else stack.pop();

			  }
			  else if (currNode.getRight() == parentNode) {
				  //inOrderQueue.enqueue(currNode.getInfo());
				  System.out.println((String) currNode.getInfo()); 
				  stack.pop();
			  }
			  parentNode = currNode;
		  }
	  }
  }

//  private void preOrder(BSTNode<T> tree)
//  // Initializes preOrderQueue with tree elements in preOrder order.
//  {
//    if (tree != null)
//    {
//      preOrderQueue.enqueue(tree.getInfo());
//      preOrder(tree.getLeft());
//      preOrder(tree.getRight());
//    }
//  }

  /**
   * Non recursive implementation of method "preOrder()"
   * @param tree the BST pointer (usually the root)
   */  
  private void preOrderNR(BSTNode<T> tree)
  // Initializes preOrderQueue with tree elements in preOrder order.
  // The method still uses the existing Queue to print store the item
  {
	  LinkedStack<BSTNode<T>> stack = new LinkedStack<BSTNode<T>>();
	  if (tree == null) throw new StackUnderflowException("given tree is empty");
	  else {
		  stack.push(tree);
		  while (!stack.isEmpty()) {
			  BSTNode<T> node = stack.top();
			  preOrderQueue.enqueue(node.getInfo());        
			  stack.pop();
			  if (node.getRight() != null)
				  stack.push(node.getRight());
			  if (node.getLeft() != null)
				  stack.push(node.getLeft());

		  }
	  }    
  }
  
//  private void postOrder(BSTNode<T> tree)
//  // Initializes postOrderQueue with tree elements in postOrder order.
//  {
//    if (tree != null)
//    {
//      postOrder(tree.getLeft());
//      postOrder(tree.getRight());
//      postOrderQueue.enqueue(tree.getInfo());
//    }
//  }
  
  /**
   * Non recursive implementation of method "postOrder()"
   * @param tree the BST pointer (usually the root)
   */  
	private void postOrderNR(BSTNode<T> tree)
	// Initializes postOrderQueue with tree elements in postOrder order.
	// The method still uses the existing Queue to print store the item
	{
		LinkedStack<BSTNode<T>> stack = new LinkedStack<BSTNode<T>>();
		if (tree == null)
			throw new StackUnderflowException("given tree is empty");
		else {
			stack.push(tree);
			BSTNode<T> parentNode = null;

			while (!stack.isEmpty()) {
				BSTNode<T> currNode = stack.top();
				if (parentNode == null || parentNode.getLeft() == currNode || parentNode.getRight() == currNode) {
					if (currNode.getLeft() != null)
						stack.push(currNode.getLeft());
					else if (currNode.getRight() != null)
						stack.push(currNode.getRight());
					else // if ((currNode.getLeft() == null) && (currNode.getRight() == null))
					{
						postOrderQueue.enqueue(currNode.getInfo());
						stack.pop();
					}
				} else if (currNode.getLeft() == parentNode) {
					if (currNode.getRight() != null)
						stack.push(currNode.getRight());
					else {
						postOrderQueue.enqueue(currNode.getInfo());
						stack.pop();
					}
				} else if (currNode.getRight() == parentNode) {
					postOrderQueue.enqueue(currNode.getInfo());
					stack.pop();
				}

				parentNode = currNode;
			}
		}
	}


  public int reset(int orderType)
  // Initializes current position for an iteration through this BST
  // in orderType order. Returns current number of nodes in the BST.
  {
    int numNodes = size();

    if (orderType == INORDER)
    {
      inOrderQueue = new LinkedUnbndQueue<T>();
      inOrderNR(root);
    }
    else
    if (orderType == PREORDER)
    {
      preOrderQueue = new LinkedUnbndQueue<T>();
      preOrderNR(root);
    }
    if (orderType == POSTORDER)
    {
      postOrderQueue = new LinkedUnbndQueue<T>();
      postOrderNR(root);
    }
    return numNodes;
  }

  public T getNext (int orderType)
  // Preconditions: The BST is not empty
  //                The BST has been reset for orderType
  //                The BST has not been modified since the most recent reset
  //                The end of orderType iteration has not been reached
  //
  // Returns the element at the current position on this BST for orderType
  // and advances the value of the current position based on the orderType. 
  {
    if (orderType == INORDER)
      return inOrderQueue.dequeue();
    else
    if (orderType == PREORDER)
      return preOrderQueue.dequeue();
    else
    if (orderType == POSTORDER)
      return postOrderQueue.dequeue();
    else return null;
  }
}