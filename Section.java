
/**
 * 
 * @author Ryan Kirkpatrick, Jared Blumen
 * @version 1
 */
public class Section {
    //not done(need to add 3 bst implementation)
    public Student[] students;
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
        students = new Student[200];
        num = i;
    }
    
    /**
     * 
     * @return this sections bst
     */
    public BST<Name> getNameRoster() {
        return name_roster;
    }
    public BST<String> getPidRoster() {
        return pid_roster;
    }
    public BST<Integer> getScoreRoster() {
        return score_roster;
    }
    
    
    private Student getStudent(Name n) {
        for(int i = 0; i < students.length; i++) {
            if(students[i].getName().compareTo(n) == 0) {
                return students[i];
            }
        }
        return null;
    }
    
    private int getIndex(Name n) {
        for(int i = 0; i < students.length; i++) {
            if(students[i].getName().compareTo(n) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    private int getIndex(String pid) {
        for(int i = 0; i < students.length; i++) {
            if(students[i].getID().compareTo(pid) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    public void insert(String id, Name n, int score, String grade) {
        Student newGuy = new Student(n , id, score, grade);
        students[currSpot] = newGuy;
        name_roster.insert(n, currSpot);
        score_roster.insert(score, currSpot);
        pid_roster.insert(id, currSpot);
        currSpot++;
    }
    
    public boolean contains(String pid) {
        if(getIndex(pid) == -1) {
            return false;
        }
        return true;
    }
    
    public void remove(Name n) {
        int index = getIndex(n);
        Student curr = students[index];
        name_roster.remove(curr.getName(), index);
        score_roster.remove(curr.getScore(), index);
        pid_roster.remove(curr.getID(), index);
        
    }
    
    /**
     * precondiiton: already know hes in there
     * @param n name to be found
     * @return reference to the student info from name
     */
    public Student find(Name n) {
        return students[getIndex(n)];
    }
    
    public Student find(String pid) {
        return students[getIndex(pid)];
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
        return pid_roster.toString() + name_roster.toString() + score_roster.toString();
    }
}
