
/**
 * 
 * @author Ryan Kirkpatrick, Jared Blumen
 * @version 1
 */
public class Section {

    private int num;
    private BST roster;
    
    /**
     * 
     * @param i section id
     */
    public Section(int i) {
        roster = new BST();
        num = i;
    }
    
    /**
     * 
     * @return this sections bst
     */
    public BST getRoster() {
        return roster;
    }
    
    /**
     * 
     * @return section id
     */
    public int getNum() {
        return num;
    }
    
    /**
     * @return BSTs toString
     */
    public String toString() {
        return roster.toString();
    }
}
