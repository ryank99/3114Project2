import java.io.IOException;

/**
 * 
 * @author Ryan Kirkpatrick
 * @version 9/26/2019
 */
public class CourseManager1Test extends student.TestCase {
    private Coursemanager1 test;
    
    /**
     * sets up fields
     */
    public void setUp() {
        test = new Coursemanager1();
        test.section(1);
    }
    
    /**
     * tests sample input to make sure it runs
     * @throws Exception 
     */
    public void testSampleInput() throws Exception {
        boolean successfulRun = false;
        String[] arguments = new String[] {"sampleinput.txt"};
        Coursemanager1.main(arguments);
        successfulRun = true;
        assertTrue(successfulRun);
    }
    
    /**
     * tests section()
     */
    public void testSection() {
        test = new Coursemanager1();
        String s = test.section(2);
        assertTrue(("switch to section 2").equals(s) );
    }
    
    /**
     * tests insert()
     */
    public void testInsert() {
        test = new Coursemanager1();
        String s = test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        assertTrue(s.equals("ryan kirkpatrick inserted"));
    }
    
    
    /**
     * tests remove()
     */
    public void testRemove() {
        test = new Coursemanager1();
        test.section(1);
        test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        String t = test.remove(new Name("Ryan", "Kirkpatrick"));
        System.out.println(t);
        assertTrue(t.equals("Student ryan kirkpatrick"
            + " get removed from section 1"));
    }
    
    /**
     * tests search()
     */
    public void testSearch() {
        test = new Coursemanager1();
        test.section(1); 
        test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        String s = test.search(new Name("Ryan", "Kirkpatrick"));
        assertTrue(s.equals("Found 010001, ryan kirkpatrick, score = 0"));
    }
    
    /**
     * tests score()
     */
    public void testScore() {
        test = new Coursemanager1();
        test.section(1); 
        test.insert(new Name("Ryan", "Kirkpatrick"), "906088299");
        test.search(new Name("ryan", "kirkpatrick"));
        String s = test.score(89);
        System.out.println(s);
        assertTrue(s.equals("Update ryan kirkpatrick record, Score = 89"));
    }
}

