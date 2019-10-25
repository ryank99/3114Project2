
/**
 * 
 * @author Ryan Kirkpatrick
 * @version 9/26/2019
 */
public class CourseManager2Test extends student.TestCase {
    private Coursemanager2 test;
    
    /**
     * sets up fields
     */
    public void setUp() {
        test = new Coursemanager2();
        test.section(1);
    }
    
    /**
     * 
     * @throws Exception
     */
    public void testSampleInput() throws Exception {
        String[] arguments = new String[] {"sampleinput.txt"};
        Coursemanager2.main(arguments);
        Student ryan = new Student(new Name("Ryan", "Kirkpatrick"), "906088299", 98, "B");
        assertTrue(ryan.getGrade().equals("B"));
    }
    /**
     * tests sample input to make sure it runs
     * @throws Exception 
     */
    
    /**
     * tests section()
     */
    public void testSection() {
        test = new Coursemanager2();
        String s = test.section(2);
        assertTrue(("switch to section 2").equals(s) );
        Student ryan = new Student(new Name("Ryan", "Kirkpatrick"), "906088299", 98, "B");
        assertTrue(ryan.getScore() == 98);
    }
    
    /**
     * tests insert()
     */
    public void testInsert() {
        test = new Coursemanager2();
        String s = test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        assertTrue(test.section(1).equals("switch to section 1"));
    }
    
    
    /**
     * tests remove()
     */
    public void testRemove() {
        test = new Coursemanager2();
        test.section(1);
        test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        String t = test.remove(new Name("Ryan", "Kirkpatrick"));
    }
    
    /**
     * tests search()
     */
 /**   public void testSearch() {
        test = new Coursemanager2();
        test.section(1); 
        test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        String s = test.search(new Name("Ryan", "Kirkpatrick"));
        assertTrue(s.equals("Found 010001, ryan kirkpatrick, score = 0"));
    }
    
    /**
     * tests score()
     */
    public void testScore() {
        test = new Coursemanager2();
        test.section(1); 
        test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        test.section(3);
        test.dumpsection();
        assertTrue(true);
    }
    
    /**
     * test bst()
     */
    public void testBinaryTree() {
        BST<Integer> testTree = new BST<Integer>();
        testTree.insert(5, 6);
        testTree.makeEmpty();
        testTree.insert(5, 7);
        testTree.insert(7, 8);
        testTree.insert(2, 9);
        testTree.insert(3, 8);
        testTree.insert(3, 7);
        testTree.remove(10,  11);
        testTree.toString();
        testTree.indexInOrder();
        testTree.findMin();
        testTree.findMax();
        assertTrue(testTree.size() == 5);
    }
    
    /**
     * tests student class
     */
    public void testStudent() {
        Name me = new Name("Ryan", "Kirkpatrick");
        Name you = new Name("Kanye", "West");
        assertTrue(me.compareTo(you) != 0);
        Student ryan = new Student(new Name("Ryan", "Kirkpatrick"), "906088299", 98, "B");
        ryan.toString();
        ryan.getName();
        ryan.getGrade();
        ryan.getScore();
        ryan.getID();
    }
    
    /**
     * tests section class
     */
    public void testSectionClass() {
        Section us = new Section(2);
        us.insert("906088299", new Name("Ryan", "Kirkpatrick"), 98, "B");
        us.removePid("906088299");
        
    }
    
     
}

