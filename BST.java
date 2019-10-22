

import java.util.Iterator;
import java.util.Stack;
/**
 * 
 * @author Ryan Kirkpatrick
 * @version 9/26/2019
 * @param <T>
 */
public class BST<T extends Comparable<? super T>>
    implements BinarySearchTree<T>, Iterable<T> {

    private int elements;
    private int actualElements;
    private BinaryNode<T> root;

    // ~ Constructor ...........................................................


    // ----------------------------------------------------------
    /**
     * Constructs an empty tree.
     */
    public BST() {
        root = null;
        elements = 0;
        actualElements = 0;
    }

    // ~ Public methods ........................................................


    // ----------------------------------------------------------
    /**
     * Insert into the tree.
     *
     * @param x
     *            the item to insert.
     */
    public void insert(T x) {

        root = insert(x, root);

    }
    
    public void insert(T x, int index) {
        root = insert(x, root, index);
    }


    // ----------------------------------------------------------
    /**
     * Remove the specified value from the tree.
     *
     * @param x
     *            the item to remove.
     */
    public void remove(T x) {
        if (x.equals(root)) {
            makeEmpty();
        }
        root = remove(x, root);

    }
    
    public void remove(T x, int index) {
        if (x.equals(root)) {
            makeEmpty();
        }
        root = remove(x, root, index);

    }


    // ----------------------------------------------------------
    /**
     * Find the smallest item in the tree.
     *
     * @return The smallest item, or null if the tree is empty.
     */
    public T findMin() {
        return elementAt(findMin(root));
    }


    // ----------------------------------------------------------
    /**
     * Find the largest item in the tree.
     *
     * @return The largest item in the tree, or null if the tree is empty.
     */
    public T findMax() {
        return elementAt(findMax(root));
    }


    // ----------------------------------------------------------
    /**
     * Find an item in the tree.
     *
     * @param x
     *            the item to search for.
     * @return the matching item or null if not found.
     */
    public T find(T x) {
        return elementAt(find(x, root));
    }
    
    public T find(T x, int index) {
        return elementAt(find(x, root, index));
    }


    // ----------------------------------------------------------
    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }


    // ----------------------------------------------------------
    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return (root == null);
    }


    // ----------------------------------------------------------
    /**
     * Internal method to get element value stored in a tree node, with safe
     * handling of null nodes.
     *
     * @param node
     *            the node.
     * @return the element field or null if node is null.
     */
    private T elementAt(BinaryNode<T> node) {
        return (node == null) ? null : node.getElement();
    }


    // ----------------------------------------------------------
    /**
     * Internal method to insert a value into a subtree.
     * setting additional duplicates to left
     * @param x
     *            the item to insert.
     * @param node
     *            the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> insert(T x, BinaryNode<T> node) {
        if (node == null) {
            elements++;
            actualElements++;
            return new BinaryNode<T>(x);
        }
        else if (x.compareTo(node.getElement()) < 0) {
            node.setLeft(insert(x, node.getLeft()));
        }
        else if (x.compareTo(node.getElement()) > 0) {
            node.setRight(insert(x, node.getRight()));
        }
        else {
            return null;
        }
        return node;
    }
    
    private BinaryNode<T> insert(T x, BinaryNode<T> node, int index) {
        if (node == null) {
            elements++;
            actualElements++;
            return new BinaryNode<T>(x, index);
        }
        else if (x.compareTo(node.getElement()) < 0) {
            node.setLeft(insert(x, node.getLeft(), index));
        }
        else if (x.compareTo(node.getElement()) > 0) {
            node.setRight(insert(x, node.getRight(), index));
        }
        else if (index < node.getIndex()) {
            node.setLeft(insert(x, node.getLeft(), index));
        }
        else if (index > node.getIndex()){
            node.setRight(insert(x, node.getLeft(), index));
        }
        else 
            return null;
        
        return node;
    }
    


    // ----------------------------------------------------------
    /**
     * Internal method to remove a specified item from a subtree.
     *
     * @param x
     *            the item to remove.
     * @param node
     *            the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<T> remove(T x, BinaryNode<T> node) {
        // This local variable will contain the new root of the subtree,
        // if the root needs to change.
        BinaryNode<T> result = node;

        // If there's no more subtree to examine
        if (node == null) {
            return null;
        }

        // if value should be to the left of the root
        if (x.compareTo(node.getElement()) < 0) {
            node.setLeft(remove(x, node.getLeft()));
        }
        // if value should be to the right of the root
        else if (x.compareTo(node.getElement()) > 0) {
            node.setRight(remove(x, node.getRight()));
        }
        // If value is on the current node
        else {
            // If there are two children
            if (node.getLeft() != null && node.getRight() != null) {
                BinaryNode<T> tempMin = findMin(node.getRight());
                remove(tempMin.getElement());
                node.setElement(tempMin.getElement());
            }
            // If there is only one child on the left
            else if (node.getLeft() != null) {
                result = node.getLeft();
            }
            // If there is only one child on the right
            else {
                result = node.getRight();
            }
        }
        actualElements--;
        return result;
    }
    
    
    private BinaryNode<T> remove(T x, BinaryNode<T> node, int index) {
        // This local variable will contain the new root of the subtree,
        // if the root needs to change.
        BinaryNode<T> result = node;

        // If there's no more subtree to examine
        if (node == null) {
            return null;
        }

        // if value should be to the left of the root
        if (x.compareTo(node.getElement()) < 0) {
            node.setLeft(remove(x, node.getLeft()));
        }
        // if value should be to the right of the root
        else if (x.compareTo(node.getElement()) > 0) {
            node.setRight(remove(x, node.getRight()));
        }
        // If value is on the current node
        else {
            if (node.getIndex() < index) {
                node.setLeft(remove(x, node.getLeft()));
            }
            else if(node.getIndex() > index) {
                node.setRight(remove(x, node.getLeft()));
            }
            else {
             // If there are two children
                if (node.getLeft() != null && node.getRight() != null) {
                    BinaryNode<T> tempMin = findMin(node.getRight());
                    remove(tempMin.getElement(), tempMin.getIndex());
                    node.setElement(tempMin.getElement());
                    node.setIndex(tempMin.getIndex());
                }
                // If there is only one child on the left
                else if (node.getLeft() != null) {
                    result = node.getLeft();
                }
                // If there is only one child on the right
                else {
                    result = node.getRight();
                }
            }
            
            
        }
        actualElements--;
        return result;
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param node
     *            the node that roots the tree.
     * @return node containing the smallest item.
     */
    private BinaryNode<T> findMin(BinaryNode<T> node) {
        if (node == null) {
            return node;
        }
        else if (node.getLeft() == null) {
            return node;
        }
        else {
            return findMin(node.getLeft());
        }
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param node
     *            the node that roots the tree.
     * @return node containing the largest item.
     */
    private BinaryNode<T> findMax(BinaryNode<T> node) {
        if (node == null) {
            return node;
        }
        else if (node.getRight() == null) {
            return node;
        }
        else {
            return findMax(node.getRight());
        }
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find an item in a subtree.
     *
     * @param x
     *            is item to search for.
     * @param node
     *            the node that roots the tree.
     * @return node containing the matched item.
     */
    private BinaryNode<T> find(T x, BinaryNode<T> node) {
        if (node == null) {
            return null; // Not found
        }
        else if (x.compareTo(node.getElement()) < 0) {
            // Search in the left subtree
            return find(x, node.getLeft());
        }
        else if (x.compareTo(node.getElement()) > 0) {
            // Search in the right subtree
            return find(x, node.getRight());
        }
        else {
            return node; // Match
        }
    }
    
    
    private BinaryNode<T> find(T x, BinaryNode<T> node, int index) {
        if (node == null) {
            return null; // Not found
        }
        else if (x.compareTo(node.getElement()) < 0) {
            // Search in the left subtree
            return find(x, node.getLeft(), index);
        }
        else if (x.compareTo(node.getElement()) > 0) {
            // Search in the right subtree
            return find(x, node.getRight(), index);
        }
        else if (index < node.getIndex()) {
            return find(x, node.getLeft(), index);
        }
        else if (index > node.getIndex()) {
            return find(x, node.getRight(), index);
        }
        else {
            return node; // Match
        }
    }
    /**
     * 
     * @return sdf
     */
    public int getElements() {
        return elements;
    }
    
    /**
     * 
     * @return asdf
     */
    public int getActualElements() {
        return actualElements;
    }
    /**
     * Gets an in-order string representation of the tree
     * If the tree holds 5
     * / \
     * 2 6
     * \
     * 3
     * It would print (2, 3, 5, 6)
     * 
     * @return an in-order string representation of the tree
     */
    @Override
    public String toString() {
        if (root == null) {
            return "";
        }
        else {
            return root.toString();
        }
    }
    
    /**
     * 
     * @author Ryan Kirkpatrick
     *
     */
    private class MyIterator implements Iterator<T> {

        private Stack<BinaryNode<T>> myStack;
        
        MyIterator(BinaryNode<T> input) {
            myStack = new Stack<BinaryNode<T>>();
            BinaryNode<T> x = input;
            while (x != null) {
                myStack.push(x);
                x = x.getLeft();
            }
        }
        
        @Override
        public boolean hasNext() {
            
            return !myStack.isEmpty();
        }

        @Override
        public T next() {
            BinaryNode<T> curr = myStack.pop();
            T data = curr.getElement();
            if (curr.getRight() != null) {
                curr = curr.getRight();
                while (curr != null) {
                    myStack.push(curr);
                    curr = curr.getLeft();
                }
            }
            
            return data;
        }
        
    }
    
    public int[] indexInOrder() {
        int[] result = new int[elements];
        int count = 0;
        Stack<BinaryNode<T>> myStack = new Stack<BinaryNode<T>>();
        BinaryNode<T> x = root;
        while (x != null) {
            myStack.push(x);
            x = x.getLeft();
        }
        while (!myStack.isEmpty()) {
            BinaryNode<T> curr = myStack.pop();
            int index = curr.getIndex();
            if (curr.getRight() != null) {
                curr = curr.getRight();
                while (curr != null) {
                    myStack.push(curr);
                    curr = curr.getLeft();
                }
            }     
            result[count] = index;
            count++;
        }
        return result;
    }
    
    


    @Override
    public Iterator<T> iterator() {
        return new MyIterator(root);
    }
    
    
    
}