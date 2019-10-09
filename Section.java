
/**
 * 
 * @author Ryan Kirkpatrick, Jared Blumen
 * @version 1
 */
public class Section {
    //not done(need to add 3 bst implementation)
    private int num;
    private BST pid_roster;
    private BST roster;//by name
    private BST score_roster;
    
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
