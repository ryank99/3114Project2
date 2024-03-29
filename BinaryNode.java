import java.util.ArrayList;

//-------------------------------------------------------------------------
/**
* A basic node stored in an unbalanced binary search tree.
* This class is not accessible outside of this package.
*
* @param <T> The type of data element contained in the node.
* @author Mark Allen Weiss (with modifications by John Lewis)
* @version 2010.04.28
*/
class BinaryNode<T>
{
  //~ Instance/static variables .............................................
    private int index;
    private T element;
    private BinaryNode<T> left;
    private BinaryNode<T> right;


  //~ Constructor ...........................................................

  // ----------------------------------------------------------
  /**
   * Creates a node with no children.
   * @param theElement the element to store in this node.
   */
    BinaryNode(T theElement)
    {
        element = theElement;
        left = null;
        right = null;
    }
    
    /**
     * 
     * @param theElement data
     * @param index in array
     */
    BinaryNode(T theElement, int index) {
        this.index = index;
        element = theElement;
        left = null;
        right = null;
    }
    
    /**
     * 
     * @return the index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * 
     * @param n new index
     */
    public void setIndex(int n) {
        index = n;
    }


  //~ Public methods ........................................................

  // ----------------------------------------------------------
  /**
   * Get the current data value stored in this node.
   * @return the element
   */
    public T getElement()
    {
        return element;
    }


  // ----------------------------------------------------------
  /**
   * Set the data value stored in this node.
   * @param value the new data value to set
   */
    public void setElement(T value)
    {
        element = value;
    }


  // ----------------------------------------------------------
  /**
   * Get the left child of this node.
   * @return a reference to the left child.
   */
    public BinaryNode<T> getLeft()
    {
        return left;
    }


  // ----------------------------------------------------------
  /**
   * Set this node's left child.
   * @param value the node to point to as the left child.
   */
    public void setLeft(BinaryNode<T> value)
    {
        left = value;
    }


  // ----------------------------------------------------------
  /**
   * Get the right child of this node.
   * @return a reference to the right child.
   */
    public BinaryNode<T> getRight()
    {
        return right;
    }


  // ----------------------------------------------------------
  /**
   * Set this node's right child.
   * @param value the node to point to as the right child.
   */
    public void setRight(BinaryNode<T> value)
    {
        right = value;
    }

  /**
   * Provides an in-order representation of the node
   * @return a string representation of the node
   */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (left != null)
        {
            builder.append(left.toString() + "\n");
        }
        builder.append(element.toString());
        if (right != null)
        {
            builder.append("\n" + right.toString());
        }
        return builder.toString();
    }
    
    /**
     * 
     * @return array of indices
     */
    public ArrayList<Integer> indexBuilder() {
        ArrayList<Integer> builder = new ArrayList<Integer>();
        if (left != null) {
            builder.addAll(left.indexBuilder());
        }
        if (right != null) {
            builder.addAll(right.indexBuilder());
        }
        builder.add(index);
        return builder;
    }
}