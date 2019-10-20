
/**
 * 
 * @author Ryan Kirkpatrick, Jared Blumen
 * @version 1
 */
public class Section {
    //not done(need to add 3 bst implementation)
    private Student[] students;
    int currSpot;
    
    private int num;
    private BST<String> pid_roster;
    private BST<Name> name_roster;//by name
    private BST<Integer> score_roster;
    
    /**
     * 
     * @param i section id
     */
    public Section(int i) {
        currSpot = 0;
        name_roster = new BST<Name>();
        pid_roster = new BST<String>();
        score_roster = new BST<Integer>();
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
