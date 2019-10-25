import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 * @author Ryan Kirkpatrick, Jared Blumen
 * @version 1
 */
public class Section {
    //not done(need to add 3 bst implementation)
    /**
     * 
     */
    private Student[] students;
    
    /**
     * 
     */
    private int currSpot;
    
    private int num;
    private BST<String> pidRoster;
    private BST<Name> nameRoster; //by name
    private BST<Integer> scoreRoster;
    
    /**
     * 
     * @param i section id
     */
    public Section(int i) {
        currSpot = 0;
        nameRoster = new BST<Name>();
        pidRoster = new BST<String>();
        scoreRoster = new BST<Integer>();
        students = new Student[200];
        num = i;
    }
    
    /**
     * 
     * @return empty
     */
    public boolean isEmpty() {
        return nameRoster.isEmpty();
    }
    
    /**
     * 
     * @return nameRoster
     */
    public BST<Name> getNameRoster() {
        return nameRoster;
    }
    /**
     * 
     * @return pidRoster
     */
    public BST<String> getPidRoster() {
        return pidRoster;
    }
    /**
     * 
     * @return scoreRoster
     */
    public BST<Integer> getScoreRoster() {
        return scoreRoster;
    }
    
    /**
     * 
     * @param n
     * @return student
     */
    private Student getStudent(Name n) {
        for (int i = 0; i < students.length; i++) {
            if (students[i].getName().compareTo(n) == 0) {
                return students[i];
            }
        }
        return null;
    }
    
    /**
     * 
     * @param n
     * @return index
     */
    private int getIndex(Name n) {
        for (int i = 0; i < students.length; i++) {
            if (students[i] != null && students[i].getName().compareTo(n) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 
     * @param pid
     * @return index
     */
    public int getIndex(String pid) {
        for (int i = 0; i < students.length; i++) {
            if (students[i] != null && students[i].getID().compareTo(pid) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 
     * @param id
     * @param n
     * @param score
     * @param grade
     */
    public void insert(String id, Name n, int score, String grade) {
        Student newGuy = new Student(n , id, score, grade);
        students[currSpot] = newGuy;
        nameRoster.insert(n, currSpot);
        scoreRoster.insert(score, currSpot);
        pidRoster.insert(id, currSpot);
        currSpot++;
    }
    
    /**
     * 
     * @return currspot
     */
    public int getCurrSpot() {
        return this.currSpot;
    }
    
    /**
     * 
     * @return students
     */
    public Student[] getStudents() {
        return this.students;
    }
    
    /**
     * 
     * @param pid
     * @return
     */
    public boolean contains(String pid) {
        if (getIndex(pid) == -1) {
            return false;
        }
        return true;
    }
    
    public void remove(Name n) {
        int index = getIndex(n);
        Student curr = students[index];
        students[index] = null;
        nameRoster.remove(curr.getName(), index);
        scoreRoster.remove(curr.getScore(), index);
        pidRoster.remove(curr.getID(), index);
    }
    
    public void removePid(String pid) {
        int index = getIndex(pid);
        Student curr = students[index];
        students[index] = null;
        nameRoster.remove(curr.getName(), index);
        scoreRoster.remove(curr.getScore(), index);
        pidRoster.remove(curr.getID(), index);
    }
    
    public void remove(String pid) {
        int index = getIndex(pid);
        Student curr = students[index];
        students[index] = null;
        nameRoster.remove(curr.getName(), index);
        scoreRoster.remove(curr.getScore(), index);
        pidRoster.remove(curr.getID(), index);
    }
    
    /**
     * precondiiton: already know hes in there
     * @param n name to be found
     * @return reference to the student info from name
     */
    public Student find(Name n) {
        int index = getIndex(n);
        if (index == -1)
            return null;
        return students[getIndex(n)];
    }
    
    public Student find(String pid) {
        int index = getIndex(pid);
        if (index == -1)
            return null;
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
        String ret = "";
        ArrayList<Integer> pidIndexes = pidRoster.indexInOrder();
        ret += "BST by ID:\n";
        for (int index: pidIndexes) {
            Student s = students[index];
            if (s != null) {
                ret += s.toString() + "\n";
            }
        }

        ArrayList<Integer> nameIndexes = nameRoster.indexInOrder();
        ret += "BST by name:\n";
        for (int index: nameIndexes) {
            Student s = students[index];
            if (s != null) {
                ret += s.toString() + "\n";
            }        }
        ArrayList<Integer> scoreIndexes = scoreRoster.indexInOrder();
        ret += "BST by score:\n";
        for (int index: scoreIndexes) {
            Student s = students[index];
            if (s != null) {
                ret += s.toString() + "\n";
            }        }
        return ret;
    }
}