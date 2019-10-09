import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;


/**
 * @author Ryan Kirkpatrick
 * @version 9/26/2019
 */
public class Coursemanager1 {

    private static Boolean prevCommandSuccess = false;
    private static String prevCommand;
    private Section[] sections;
    private int currSection = 1;
    private Student currStudent;
    private BST studentData;
    
    /**
     * Creates a Coursemanager1 object
     */
    public Coursemanager1() {
        sections = new Section[21];
        //section 21 is temp section for merging
        for (int i = 0; i < sections.length; i++) {
            sections[i] = new Section(i + 1);
        }
    }
    
    /**
     * main method
     * @param args input
     */
    public static void main(String[] args) 
    {
        if (args.length != 1) {
            throw(new Error("Invalid number of program arguments"));
        }

        Coursemanager1 cm = new Coursemanager1();
        
        String commandFileName = args[0];
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFileName));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            line = br.readLine().trim();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        while (line != null) {
            String[] parts = line.split("\\s+");
            String func = parts[0];
            switch(func) {
                case "loadstudentdata":{
                    
                    //not done
                    System.out.println(cm.loadStudentData(parts[1]));
                }
                case "loadcoursedata": {
                    
                    //not done
                    System.out.println(cm.loadCourseData(parts[1]));
                }
                case "section": { 
                    System.out.println(
                        cm.section(Integer.parseInt(parts[1])));  
                    prevCommand = func;
                    break;
                }
                case "insert": { 
                    //not done
                    System.out.println(cm.insert(
                        new Name(parts[1].toLowerCase(), 
                            parts[2].toLowerCase())));
                    prevCommand = func;
                    break;
                }
                case "searchid": {
                    
                    //not done
                    System.out.println(cm.searchid(parts[1]));
                }
                case "search": {
                    if (parts.length == 2) {
                        System.out.println(
                            cm.multSearch(parts[1].toLowerCase()));
                        prevCommand = func;
                        break;
                    }
                    else {
                        System.out.println(cm.search(
                            new Name(parts[1].toLowerCase(), 
                                parts[2].toLowerCase())));
                        prevCommand = func;
                        break;
                    }
                } 
                case "score": { 
                    System.out.println(cm.score(Integer.parseInt(parts[1])));
                    prevCommand = func;
                    break;
                }
                case "remove": { 
                    //not done
                    System.out.println(cm.remove(
                        new Name(parts[1], parts[2]))); 
                    prevCommand = func;
                    break;
                }
                case "removesection": { 
                    if (parts.length == 1) {
                        System.out.println(
                            cm.removeSection(cm.currSection)); 
                    }
                    else {
                        System.out.println(
                            cm.removeSection(Integer.parseInt(parts[1]))); 
                    }
                    prevCommand = func;
                    break;
                }
                case "dumpsection": { 
                    //not done
                    System.out.println(cm.dumpsection());
                    prevCommand = func;
                    break;
                }
                case "grade": { 
                    //not done
                    System.out.println(cm.grade()); 
                    prevCommand = func;
                    break;
                }
                case "stat": {
                    //not done
                    System.out.println(cm.stat());
                    break;
                }
                case "list": {
                    //not done
                    System.out.println(cm.list(parts[1]));
                    //how many students in specific grade level as param
                    break;
                }
                case "findpair": { 
                    if (parts.length == 1) {
                        System.out.println(cm.findPair(0)); 
                    }
                    else {
                        System.out.println(cm.findPair(
                            Integer.parseInt(parts[1]))); 
                    }
                    prevCommand = func;
                    break;
                }
                case "merge": { 
                    //not done
                    System.out.println(cm.merge());
                    //put all nonempty sections into one empty section
                    break;
                }
                case "savestudentdata": {
                    //not done
                    //save in specified binary file
                    break;
                }
                case "savecoursedata": {
                    //not done
                    //save in specified binary file
                    break;
                }
                case "clearcoursedata": {
                       //not done
                    break;
                }
                default: {
                    break;
                }
                
            }
            try {
                String s = br.readLine();
                if (s != null) {
                    line = s.trim();
                }
                else {
                    line = null;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Changes section to n
     * @param n identifier of section
     * @return string output
     */
    public String loadStudentData(String filename) {
        
        //goes through filename and adds all student records to studentRecords BST
        return filename + " successfully loaded";
    }
    
    public String loadCourseData(String filename) {
        boolean success = true;
        String ret = "coursename Course has been successfully loaded";
        //load stuff here
        if(success) {
            return ret;
        }
        else {
            return "Course Load Failed. You have to load Student " + 
                "Information file first.";
        }
        //
    }
    
    
    public String section(int n) {
        currSection = n;
        return "switch to section " + currSection;
    }
    
    
    /**
     * Inserts new student into current section
     * @param n Name of student to insert
     * @return string output
     */
    @SuppressWarnings("unchecked")
    public String insert(Name n) {
        //we first have to check if in the student data BST
        //then add to all 3 BSTs for the curr section
        if (search(n).contains("failed")) {
            Student newGuy = new Student(n, generateID());
            sections[currSection - 1].getRoster().insert(newGuy);
            currStudent = newGuy;
            return "" + n + " inserted";
        }
        else {
            String info = search(n);
            return "" + n + " is already in section " +
                currSection + "\n" + info.substring(6, info.length());
        }
    
    }
    /**
     * Removes student from current section
     * @param n Name of student to remove
     * @return string output
     */
    @SuppressWarnings("unchecked")
    public String remove(Name n) {
        //have to remove n from all 3 BSTs in the section
        if (search(n).contains("failed")) {
            return "Remove failed. Student " + n.toString() + 
                " doesn't exist in section " + currSection;
        }
        else {
            sections[currSection - 1].getRoster().remove(new Student(n, ""));
            return "Student " + n.toString() +
                " get removed from section " + currSection;
        }
    }
    /**
     * Removes section n from sections
     * @param n Identifier of section
     * @return string output
     */
    public String removeSection(int n) {
        sections[n - 1] = new Section(n);
        return "Section " + n + " removed";
    }
    /**
     * Searches for student with name n in current section
     * @param n Name of student
     * @return string output
     */
    public String searchid(String id) {
        //search by id
        return "Found!";
    }
    public String search(Name n) {
        @SuppressWarnings("unchecked")
        Student x = (Student)sections[currSection - 1]
            .getRoster().find(new Student(n, ""));
        if (x == null) {
            prevCommandSuccess = false;
            return "Search failed. Student " + n.toString()
                + " doesn't exist in section " + currSection;
        }
        else {
            prevCommandSuccess = true;
            currStudent = x;
            return ("Found " + x.toString());

        }

    }
    /**
     * Searches for students that have first or last name s
     * @param s string to query students
     * @return string output
     */
    public String multSearch(String s) {
        s = s.toLowerCase();
        String ret = "Search results for " + s + ":\n";
        boolean found = false;
        int foundcount = 0;
        Student match = null;
        @SuppressWarnings("unchecked")
        Iterator<Student> me = sections[currSection - 1].getRoster().iterator();
        while (me.hasNext()) {
            Student curr = me.next();
            if (curr.getName().getLast().equals(s)) {
                found = true;
                match = curr;
                foundcount++;
                ret += curr.toString() + "\n";
            }
            else if (curr.getName().getFirst().equals(s)) {
                found = true;
                match = curr;
                foundcount++;
                ret += curr.toString() + "\n";
            }
        }
        if (found) {
            prevCommandSuccess = false;
            if (foundcount == 1) {
                currStudent = match;
                prevCommandSuccess = true;
            }
            ret += s + " was found in " + foundcount +
                " records in section " + currSection;
            return ret;
        }
        else {
            prevCommandSuccess = false;
            ret += s + " was found in 0 records in section " + currSection;
            return ret;
        }
    }  
    /**
     * Assigns score to current student if previous
     *  command was an insert or successful search
     * @param s score integer
     * @return string output
     */
    public String score(int s) {
        if (s > 100 || s < 0) {
            return "Scores have to be integers in range 0 to 100.";
        }
        else {
            if (prevCommand.equals("insert") ||
                (prevCommand.equals("search") && prevCommandSuccess)) {
                String output = "Update " + currStudent.getName()
                    +  " record, Score = " + s;
                currStudent.setScore(s);
                return output;
            }
            return "score command can only be called after an "
                + "insert command or a successful search "
                + "command with one exact output.";
        }
    }
    /**
     * Lists information about current section
     * @return string output
     */
    public String dumpsection() {
       
        String ret = "";
        int count = 0;
        @SuppressWarnings("unchecked")
        Iterator<Student> me = sections[currSection - 1].getRoster().iterator();
        while (me.hasNext()) {
            count++;
            me.next();
        }
        ret += "Section " + currSection + " dump:";
        if (count > 0) {
            ret += "\n";
        }
        return (ret + sections[currSection - 1].toString() 
            + "\nSize = " + count);
        //inorder traversal
    }
    /**
     * Assigns grades to all students in current section
     * @return string output
     */
    @SuppressWarnings("unchecked")
    public String grade() {
        String ret = "Grading Completed:";
        String[] grades = {"A", "A-", "B+", "B", "B-", "C+",
            "C", "C-", "D+", "D", "D-", "F"};
        int[] gradeCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Iterator<Student> me = sections[currSection - 1].getRoster().iterator();
        while (me.hasNext()) {
            Student curr = me.next();
            int currScore = curr.getScore();
            String g = "E";
            if (currScore >= 90) {
                gradeCount[0]++;
                g = "A";
            }
            else if (currScore >= 85 && currScore < 90) {
                gradeCount[1]++;
                g = "A-";
            }
            else if (currScore >= 80 && currScore < 85) {
                gradeCount[2]++;
                g = "B+";
            }
            else if (currScore >= 75 && currScore < 80) {
                gradeCount[3]++;
                g = "B";   
            }
            else if (currScore >= 70 && currScore < 75) {
                gradeCount[4]++;
                g = "B-";
            }
            else if (currScore >= 65 && currScore < 70) {
                gradeCount[5]++;
                g = "C+";
            }
            else if (currScore >= 60 && currScore < 65) {
                gradeCount[6]++;
                g = "C";
            }
            else if (currScore > 57 && currScore < 60) {
                gradeCount[7]++;
                g = "C-";
            }
            else if (currScore >= 55 && currScore <= 57) {
                gradeCount[8]++;
                g = "D+";
            }
            else if (currScore >= 53 && currScore < 55) {
                gradeCount[9]++;
                g = "D";
            }
            else if (currScore >= 50 && currScore < 53) {
                gradeCount[10]++;
                g = "D-";
            }
            else {
                gradeCount[11]++;
                g = "F";
            }
            currStudent.setGrade(g);
        }
        for (int i = 0; i < grades.length; i++) {
            if (gradeCount[i] > 0) {
                ret += "\n" + gradeCount[i] +
                    " students with grade " + grades[i];
            }
        }
        return "grading completed";
        //iterate through BST and asssign grade with switch statement
    }
    public String stat() {
        String ret = "Statistics of section ";
        ret += currSection-1;
        ret += ":";
        int count = 0;
        String[] letterArr = {"A", "A-", "B+", "B", "B-", "C+",
            "C", "C-", "D+", "D", "D-", "F"};
        int[] countArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        @SuppressWarnings("unchecked")
        Iterator<Student> me = sections[currSection - 1].getRoster().iterator();
        while (me.hasNext()) {
            Student f = me.next();
            String currGrade = f.getGrade();
            switch(currGrade) {
                case "A": {
                    countArr[0]++;
                    break;

                }
                case "A-": {
                    countArr[1]++;
                    break;

                }
                case "B+": {
                    countArr[2]++;
                    break;

                }
                case "B": {
                    countArr[3]++;
                    break;

                }   
                case "B-": {
                    countArr[4]++;
                    break;

                }
                case "C+": {
                    countArr[5]++;
                    break;

                }
                case "C": {
                    countArr[6]++;
                    break;

                }
                case "C-": {
                    countArr[7]++;
                    break;

                }
                case "D+": {
                    countArr[8]++;
                    break;

                }
                case "D": {
                    countArr[9]++;
                    break;

                }
                case "D-": {
                    countArr[10]++;
                    break;

                }
                case "F": {
                    countArr[11]++;
                    break;

                }
                default: {
                    break;
                }
            }
            
        }
        for(int i = 0; i < letterArr.length; i++) {
            ret += "\n" + countArr[i] + " students with grade " + letterArr[i];
        }
        //how many students in curr section have grade g
        
        return "";
    }
    
    public String list(String g) {
        String ret = "Students with grade " + g + " are:";
        
        Iterator<Student> me = sections[currSection - 1].getRoster().iterator();
        int count = 0;
        if(g.substring(1).equals("*")){
            //any + or - counts
        }
        else {
            while (me.hasNext()) {
                Student f = me.next();
                String currGrade = f.getGrade();
                if (currGrade.equals(g)) {
                    ret += "\n" + f.toString();
                    count++;
                }
            }
        }
        ret += "Found " + count + " students";
        return ret;
    }
    /**
     * Finds pairs of students with differences 
     * in scores less than or equal to x
     * @param x difference in scores
     * @return string output
     */
    public String findPair(int x) {
        @SuppressWarnings("unchecked")
        Iterator<Student> me = sections[currSection - 1]
            .getRoster().iterator();
        ArrayList<Student> students = new ArrayList<Student>();
        int paircount = 0;
        while (me.hasNext()) {
            Student f = me.next();
            students.add(f);
        }
        String ret = "Students with score difference less "
            + "than or equal " + x + ":\n";
        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (Math.abs(students.get(i).getScore() -
                    students.get(j).getScore()) <= x) {
                    paircount++;
                    ret += students.get(i).getName().toString() + ", " +
                    students.get(j).getName().toString() + "\n";
                }
            }
        }
        return ret + "found " + paircount + " pairs";
    }

    /**
     * Generates a unique ID for the current section
     * @return id formatted
     */

}