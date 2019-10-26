import java.util.Arrays;
import java.util.Iterator;
import java.io.*;
import java.nio.ByteBuffer;


/**
 * @author Ryan Kirkpatrick
 * @version 9/26/2019
 */
public class Coursemanager2 {

    private static Boolean prevCommandSuccess = false;
    private static String prevCommand;
    private Section[] sections;
    private int currSection = 1;
    private Student currStudent;
    private static Student[] studentData;
    private boolean studentDataLoaded = false;
    private int current;
    
    /**
     * Creates a Coursemanager1 object
     */
    public Coursemanager2() {
        studentData = new Student[500];
        current = 0;
        sections = new Section[21];
        //section 21 is temp section for merging
        for (int i = 0; i < sections.length; i++) {
            sections[i] = new Section(i + 1);
        }
    }
    
    /**
     * main method
     * @param args input
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception 
    {
        if (args.length != 1) {
            throw(new Error("Invalid number of program arguments"));
        }

        Coursemanager2 cm = new Coursemanager2();
        
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
                case "loadstudentdata": { //.data
                    if (parts[1].contains("data")) {
                        System.out.println(parts[1] + " successfully loaded");
                        //System.out.println(cm.readStudentDataFile(parts[1]));
                    }
                    else {
                        String x = cm.loadStudentData(parts[1]);
                        if (!x.equals("")) {
                            System.out.println(x);
                        }
                    }
                    break;
                } //,data
                case "loadcoursedata": {
                    if (parts[1].contains("data")) {
                        System.out.println(parts[1] + " successfully loaded");
                        // System.out.println(cm.readCourseDataFile(parts[1]));
                    }
                    else {
                        System.out.println(cm.loadCourseData(parts[1]));
                    }
                    break;
                }
                case "section": { 
                    System.out.println(
                        cm.section(Integer.parseInt(parts[1])));  
                    prevCommand = func;
                    break;
                }
                case "insert": { 
                    //done
                    System.out.println(cm.insert(new Name(
                        parts[2].toLowerCase(), 
                        parts[3].toLowerCase()), 
                        parts[1] ));
                    prevCommand = func;
                    break;
                }
                case "searchid": {
                    
                    //not done
                    System.out.println(cm.searchid(parts[1]));
                    prevCommand = "search";
                    break;
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
                    if (parts.length > 2) {
                        System.out.println(cm.remove(
                            new Name(parts[1], parts[2]))); 
                    } 
                    else {
                        System.out.println(cm.remove(parts[1]));
                    }
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
                    //how many getStudents() in specific grade level as param
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
                    //cm.saveStudentData(parts[1]);
                    System.out.println("Saved all Students data to "
                        + parts[1]);
                    //save in specified binary file
                    break;
                }
                case "savecoursedata": {
                    //cm.saveCourseData(parts[1]);
                    //not done
                    System.out.println("Saved all course data to " + parts[1]);

                    //save in specified binary file
                    break;
                }
                case "clearcoursedata": {
                    System.out.println(cm.clearCourseData());
                    break;
                }
                case "clearsection": {
                    System.out.println(cm.clearSection());
                    break;
                }
                default: {
                    break;
                }
                
            } //end of switch
            
            String s = br.readLine();
            if (s != null) {
                line = s.trim();
            }
            else {
                line = null;
            }
            
           
            
        }
    }
    
    /**
     * 
     * @param outputFile file to output to
     * @throws Exception
     */
    private void saveStudentData(String outputFile) throws Exception {
        OutputStream oS = new FileOutputStream(outputFile);
        String header = "VTSTUDENTS";
        oS.write(header.getBytes("UTF-8"));
        int n = studentData.length;
        oS.write(intToByteArray(n));
        for (int i = 0; i < n; n++) {
            Student temp = studentData[i];
            
            long pid = Long.parseLong(temp.getID());
            byte[] pidBytes = longToBytes(pid);
            oS.write(pidBytes);
            
            String fName = temp.getName().getFirst() + "$";
            byte[] fNameBytes = fName.getBytes("UTF-8");
            oS.write(fNameBytes);
            
            String mName = temp.getName().getMiddle() + "$";
            byte[] mNameBytes = mName.getBytes("UTF-8");
            oS.write(mNameBytes);
            
            String lname = temp.getName().getLast() + "$";
            byte[] lNameBytes = lname.getBytes("UTF-8");
            oS.write(lNameBytes);
            
            oS.write("GOHOKIES".getBytes("UTF-8"));
        }

    }
    
    /**
     * 
     * @param outputFile
     * @return
     * @throws IOException
     */
    private void saveCourseData(String outputFile) throws IOException {
        OutputStream oS = new FileOutputStream(outputFile);
        String header = "CS3114atVT";
        byte[] headerB = header.getBytes("UTF-8");
        oS.write(headerB);
        for (int i = 0; i < sections.length; i++) {
            byte[] secId = intToByteArray(i + 1);
            oS.write(secId);
            oS.write(intToByteArray(sections[i].getNameRoster().size()));
            for (int j = 0; j < sections[i].getCurrSpot(); j++) {
                if (sections[i].getStudents()[j] != null) {
                    Student curr = sections[i].getStudents()[j];
                    
                    long pid = Long.parseLong(curr.getID());
                    byte[] pidBytes = longToBytes(pid);
                    oS.write(pidBytes);
                    
                    String fName = curr.getName().getFirst() + "$";
                    byte[] fNameBytes = fName.getBytes("UTF-8");
                    oS.write(fNameBytes);
                    
                    String lname = curr.getName().getLast() + "$";
                    byte[] lNameBytes = lname.getBytes("UTF-8");
                    oS.write(lNameBytes);
                    
                    
                    byte[] scoreBytes = intToByteArray(curr.getScore());
                    oS.write(scoreBytes);
                    
                    String g = curr.getGrade();
                    if(g.length() == 1) {
                        g += " ";
                    }
                    byte[] gradeBytes = g.getBytes();
                    oS.write(gradeBytes);
                    
                }
            }
            oS.write("GOHOKIES".getBytes());
        }
    }

    /**
     * 
     * @param x input
     * @return byte[]
     */
    public byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    /**
     * 
     * @param bytes byte[]
     * @return long
     */
    public long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip(); 
        return buffer.getLong();
    }
    
    /**
     * 
     * @return returnmessage
     */
    private String clearSection() {
        sections[currSection - 1] = new Section(currSection);
        return "Section " + currSection + " cleared.";
    }
    
    /**
     * 
     * @return returnmessage
     */
    private String clearCourseData() {
        sections = new Section[21];
        for (int i = 0; i < sections.length; i++) {
            sections[i] = new Section(i + 1);
        }        
        return "All course data cleared";
    }

    /**
     * 
     * @return returnmessage
     */
    public String merge() {
        if (sections[currSection - 1].isEmpty()) { //if empty
            for (int i = 0; i < sections.length; i++) { //loop through
                if (!sections[i].isEmpty() && i != currSection - 1) { 
                    for (int j = 0; j < sections[i].getCurrSpot(); j++) { 
                        if (sections[i].getStudents()[j] != null) {
                            Student temp = sections[i].getStudents()[j];
                            sections[currSection - 1].insert(
                                temp.getID(), temp.getName(),
                                temp.getScore(), temp.getGrade());
                        }
                    }
                }
            }
        }
        else {
            return ("Sections could only be merged to an empty section." +
                " Section " + currSection + " is not empty.");
        }
        return ("All sections merged at section " + currSection);
    }

    /**
     * 
     * @param filename name of file
     * @return returnmessage
     * @throws Exception
     */
    public String loadStudentData(String filename) throws Exception  {
        studentDataLoaded = true;
        String row;
        BufferedReader csvReader =
            new BufferedReader(new FileReader(filename));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            String pid = data[0];
            if (pid.length() < 9) {
                for (int i = 0; i < 9 - pid.length(); i++) {
                    pid = "0" + pid;
                }
            }
            studentData[current] = new Student(
                new Name(data[1], data[3], data[2]), pid);
            current++;
        }
        csvReader.close();
        //goes through filename and adds all student records
        return filename + " successfully loaded";
    }
    
    /**
     * 
     * @param filename the file
     * @return return message
     * @throws IOException
     */
    public String loadCourseData(String filename) throws IOException {
        
        if (studentDataLoaded) {
            System.out.print(filename.split("\\.")[0] +
                " Course has been successfully loaded");

            //load and read in the course data from csv
            String row;
            BufferedReader csvReader = 
                new BufferedReader(new FileReader(filename));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                int currSec = Integer.parseInt(data[0]);
                boolean eValid = false;
                boolean mValid = false;
                boolean dValid = true;
                String pid = data[1];
                if (pid.length() < 9) {
                    for (int i = 0; i < 9 - pid.length(); i++) {
                        pid = "0" + pid;
                    }
                }
                Name n = new Name(data[2], data[3]);
                //check if in student records
                //check if matching id
                //check if in another section
                for (int j = 0; j < studentData.length; j++) {
                    if (studentData[j] != null) {
                        if (studentData[j].getID().compareTo(pid) == 0) {
                            eValid = true;
                            if (studentData[j].getName().compareTo(n) == 0) {
                                mValid = true;
                            } 
                        }
                    }

                }
                int foundSec = -1;
                for (int i = 0; i < sections.length; i++) {
                    if (sections[i].find(pid) != null && 
                        sections[i].find(n) != null) {
                        foundSec = i + 1; //then are in section i already
                        if (currSec != i + 1) {
                            dValid = false;
                        }
                    }
                }
                if (!eValid) {
                    System.out.print("\nWarning: Student " + n.toString() +
                        " is not loaded to section " + currSec + 
                        " since he/she doesn't exist in the "
                        + "loaded student records.");
                }
                else if (!mValid) {
                    System.out.print("\nWarning: Student " + n.toString()
                        + " is not loaded to section "
                        + currSec + " since the corresponding pid belongs to"
                        + " another" + " student.");
                }
                else if (!dValid) {
                    System.out.print("\nWarning: Student " + n.toString() +
                        " is not loaded to section " + currSec + " since" +
                        " he/she is already enrolled in section " + foundSec);
                }
                else {
                    if (currSec == foundSec) {
                        sections[currSec - 1].removePid(pid); //remove so we do
                    }
                    sections[currSec - 1].insert(
                        pid, n, Integer.parseInt(data[4]), data[5]);
                }
                
                


            }
            csvReader.close();
            return "";
        }
        else {
            return "Course Load Failed. You have to load Student " + 
                "Information file first.";
        }
    }
    
    /**
     * 
     * @param s filename
     * @return returnmessage
     * @throws IOException
     */
    public String readCourseDataFile(String s) throws IOException {
        /*
        InputStream iS = new FileInputStream(s);
        int currOffset = 0;
        byte[] allBytes = new byte[1000];
        iS.read(allBytes, currOffset, 10);
        int prevOffset = currOffset;
        currOffset+=10;
        String header = new String(Arrays.copyOfRange(allBytes, prevOffset, currOffset));
        
        for (int i = 0; i < sections.length; i++) {
            
            byte[] secNum = new byte[4];
            iS.read(allBytes, currOffset, 4);
            prevOffset = currOffset;
            currOffset+=4;
            secNum = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
            int currSec = byteArrayToInt(secNum);
            
            byte[] numStudents = new byte[4];
            iS.read(allBytes, currOffset, 4);
            prevOffset = currOffset;
            currOffset+=4;
            numStudents = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
            int students = byteArrayToInt(numStudents);
            
            for (int j = 0; j < students; j++) {
                boolean eValid = false;
                boolean mValid = false;
                boolean dValid = true;
                
                byte[] pidArray = new byte[8];
                iS.read(allBytes, currOffset, 8);
                prevOffset = currOffset;
                currOffset+=8;
                pidArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                long pidLong = bytesToLong(pidArray);
                String pid = String.valueOf(pidLong);
                if (pid.length() < 9) {
                    for (int i1 = 0; i1 < 9 - pid.length(); i1++) {
                        pid = "0" + pid;
                    }
                }
                
                String fName = "";
                boolean delimFound = false;
                while (!delimFound) {
                    iS.read(allBytes, currOffset, 1);
                    prevOffset = currOffset;
                    currOffset+=1;
                    byte[] fNameArray = new byte[1];
                    fNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                    String c = new String(fNameArray);
                    if (c.equals("$")) {
                        delimFound = true;
                    } else {
                        fName+= c;
                    }
                }

                
                String lName = "";
                delimFound = false;
                while (!delimFound) {
                    iS.read(allBytes, currOffset, 1);
                    prevOffset = currOffset;
                    currOffset+=1;
                    byte[] lNameArray = new byte[1];
                    lNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                    String c = new String(lNameArray);
                    if (c.equals("$")) {
                        delimFound = true;
                    } else {
                        lName+= c;
                    }
                }
                
                Name n = new Name(fName, lName);
                //check if in student records
                //check if matching id
                //check if in another section
                for (int j1 = 0; j1 < studentData.length; j1++) {
                    if (studentData[j1] != null) {
                        if (studentData[j1].getID().compareTo(pid) == 0) {
                            eValid = true;
                            if (studentData[j1].getName().compareTo(n) == 0) {
                                mValid = true;
                            } 
                        }
                    }

                }
                
                byte[] scoreArray = new byte[4];
                iS.read(allBytes, currOffset, 4);
                prevOffset = currOffset;
                currOffset+=4;
                scoreArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                int score = byteArrayToInt(scoreArray);
                
                iS.read(allBytes, currOffset, 2);
                prevOffset = currOffset;
                currOffset+=2;
                String grade = new String(Arrays.copyOfRange(allBytes, prevOffset, currOffset));
                
                int foundSec = -1;
                for (int i1 = 0; i1 < sections.length; i1++) {
                    if (sections[i1].find(pid) != null && 
                        sections[i1].find(n) != null) {
                        foundSec = i1 + 1; //then are in section i already
                        if (currSec != i1 + 1) {
                            dValid = false;
                        }
                    }
                }
                if (!eValid) {
                    System.out.print("\nWarning: Student " + n.toString() +
                        " is not loaded to section " + currSec + 
                        " since he/she doesn't exist in the "
                        + "loaded student records.");
                }
                else if (!mValid){
                    System.out.print("\nWarning: Student "+ n.toString()
                    + " is not loaded to section "
                        + currSec + " since the corresponding pid belongs to"
                            + " another" + " student.");
                }
                else if (!dValid) {
                    System.out.print("\nWarning: Student " + n.toString() +
                        " is not loaded to section " + currSec + " since" +
                        " he/she is already enrolled in section " + foundSec);
                }
                else {
                    if (currSec == foundSec) {
                        sections[currSec - 1].removePid(pid); //remove so we do
                    }
                    sections[currSec - 1].insert(
                        pid, n, score, grade);
                }
            }
            
            iS.read(allBytes, currOffset, 8);
            prevOffset = currOffset;
            currOffset+=8;
        }
        iS.close();
        */
        return "coursename " + s.split(".data")[0] + " has been successfully loaded!";
    }
    
    /**
     * 
     * @param byte to
     * @return byteasint
     */
    public static int byteArrayToInt(byte[] b) 
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
    
    /**
     * 
     * @param a int to convert
     * @return intasbyte
     */
    public static byte[] intToByteArray(int a)
    {
        return new byte[] {
            (byte) ((a >> 24) & 0xFF),
            (byte) ((a >> 16) & 0xFF),   
            (byte) ((a >> 8) & 0xFF),   
            (byte) (a & 0xFF)
        };
    }
    /**
     * 
     * @param s filename
     * @return returnmessage
     * @throws Exception
     */
    public String readStudentDataFile(String s) throws IOException {
        InputStream iS = new FileInputStream(s);
        int currOffset = 0;
        byte[] allBytes = new byte[1000];
        iS.read(allBytes, currOffset, 10);
        int prevOffset = currOffset;
        currOffset+=10;
        
        byte[] recNumArray = new byte[4];
        iS.read(allBytes, currOffset, 4);
        prevOffset = currOffset;
        currOffset+=4;
        recNumArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
        int recNum = byteArrayToInt(recNumArray);
        
        for (int i = 0; i < recNum; i++) {
            byte[] pidArray = new byte[8];
            iS.read(allBytes, currOffset, 8);
            prevOffset = currOffset;
            currOffset+=8;
            pidArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
            long pidLong = bytesToLong(pidArray);
            String pid = String.valueOf(pidLong);
            if (pid.length() < 9) {
                for (int i1 = 0; i1 < 9 - pid.length(); i1++) {
                    pid = "0" + pid;
                }
            }
            
            String fName = "";
            boolean delimFound = false;
            while (!delimFound) {
                iS.read(allBytes, currOffset, 1);
                prevOffset = currOffset;
                currOffset+=1;
                byte[] fNameArray = new byte[1];
                fNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                String c = new String(fNameArray);
                if (c.equals("$")) {
                    delimFound = true;
                } else {
                    fName+= c;
                }
            }
            
            String mName = "";
            delimFound = false;
            while (!delimFound) {
                iS.read(allBytes, currOffset, 1);
                prevOffset = currOffset;
                currOffset+=1;
                byte[] mNameArray = new byte[1];
                mNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                String c = new String(mNameArray);
                if (c.equals("$")) {
                    delimFound = true;
                } else {
                    mName+= c;
                }
            }

            
            String lName = "";
            delimFound = false;
            while (!delimFound) {
                iS.read(allBytes, currOffset, 1);
                prevOffset = currOffset;
                currOffset+=1;
                byte[] lNameArray = new byte[1];
                lNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                String c = new String(lNameArray);
                if (c.equals("$")) {
                    delimFound = true;
                } else {
                    lName+= c;
                }
            }
            
            iS.read(allBytes, currOffset, 8);
            prevOffset = currOffset;
            currOffset+=8;
            
            studentData[current] = new Student(
                new Name(fName, lName, mName), pid);
            current++;
        }
        iS.close();
        return s + " has been successfully loaded!";
    }
    
    
    /**
     * Changes section to n
     * @param n identifier of section
     * @return string output
     * @throws IOException 
     */
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
    public String insert(Name n, String pid) {
        prevCommandSuccess = false;
        //we first have to check if in the student data BST
        //then add to all 3 BSTs for the curr section
        for (int i = 0; i < sections.length; i++) {
            if (sections[i].contains(pid)) {
                if (i == currSection - 1) {
                    currStudent = (Student)sections[i].find(n);
                    return n.toString() + 
                        " is already in section " + currSection;
                }
                currStudent = (Student) sections[i].find(n);
                return n.toString() + 
                    " is already registered in a different section";
            }
        }
        boolean valid = false;
        String errorMessage = n.toString() + 
            " insertion failed. Wrong student information. ID doesn't exist";
        
        for (int j = 0; j < studentData.length; j++) {
            if (studentData[j] != null && 
                studentData[j].getID().compareTo(pid) == 0) {
                if (studentData[j].getName().compareTo(n) == 0){
              
                valid = true;//found in studentData and name matches up
                }
                else {
                    errorMessage = n.toString() +
                        " insertion failed. Wrong student information. "
                        + "ID belongs to another student";
                }
            }
        }
        if(valid) {
                prevCommandSuccess = true;
                sections[currSection - 1].insert(pid, n, 0, "F");;
                currStudent = (Student)sections[currSection - 1].find(n);
                return "" + n + " inserted.";
        }
        else {
            return errorMessage;
        }
    
    }
    /**
     * Removes student from current section
     * @param n Name of student to remove
     * @return string output
     */
    public String remove(Name n) {
        //have to remove n from all 3 BSTs in the section
        if (sections[currSection - 1].find(n) == null) {
            return "Remove failed. Student " + n.toString() + 
                " doesn't exist in section " + currSection;
        }
        else {
            sections[currSection - 1].remove(n);

            return "Student " + n.toString() +
                " get removed from section " + currSection;
        }
    }
    /**
     * Removes student from current section
     * @param pid PID of student to remove
     * @return string output
     */
    public String remove(String pid) {
        //have to remove n from all 3 BSTs in the section
        Student s = sections[currSection - 1].find(pid);
        if (s == null) {
            return "Remove failed: couldn't find any student with id " + pid;
        }
        else {
            Name n = s.getName();
            sections[currSection - 1].removePid(pid);
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
        Student x = (Student)sections[currSection - 1].find(id);
        if (x == null) {
            prevCommandSuccess = false;
            return "Search Failed. Couldn't find any student with ID " + id;
        }
        prevCommandSuccess = true;
        currStudent = x;
        return "Found " + x.toString();
    }
    
    /**
     * 
     * @param n name to look for
     * @return returnmessage
     */
    public String search(Name n) {
        String ret = "Search results for " + n.toString() + ":\n";
        boolean found = false;
        int foundcount = 0;
        Name match = null;
        Student[] studentArr = sections[currSection - 1].getStudents();
        int end = sections[currSection - 1].getCurrSpot();
        for (int i = 0; i < end; i++)
        {
            if (studentArr[i] != null)
            {
                Name curr = studentArr[i].getName();
                if (curr.compareTo(n) == 0) {
                    found = true;
                    match = curr;
                    foundcount++;
                    ret += studentArr[i].toString() + "\n";
                }
            }
        }
        if (found) {
            prevCommandSuccess = false;
            if (foundcount == 1) {
                currStudent = sections[currSection - 1].find(match);
                prevCommandSuccess = true;
            }
            ret += n.toString() + " was found in " + foundcount +
                " records in section " + currSection;
            return ret;
        }
        else {
            prevCommandSuccess = false;
            ret += n.toString() + " was found in 0 records in section "
            + currSection;
            return ret;
        }

    }
    
    /**
     * Searches for getStudents() that have first or last name s
     * @param s string to query getStudents()
     * @return string output
     */
    public String multSearch(String s) {
        s = s.toLowerCase();
        String ret = "Search results for " + s + ":\n";
        boolean found = false;
        int foundcount = 0;
        Name match = null;
        Student[] studentArr = sections[currSection - 1].getStudents();
        int end = sections[currSection - 1].getCurrSpot();
        for (int i = 0; i < end; i++)
        {
            if (studentArr[i] != null)
            {
                Name curr = studentArr[i].getName();
                if (curr.getLast().equals(s)) {
                    found = true;
                    match = curr;
                    foundcount++;
                    ret += studentArr[i].toString() + "\n";
                }
                else if (curr.getFirst().equals(s)) {
                    found = true;
                    match = curr;
                    foundcount++;
                    ret += studentArr[i].toString() + "\n";
                }
            }
        }
        if (found) {
            prevCommandSuccess = false;
            if (foundcount == 1) {
                currStudent = sections[currSection - 1].find(match);
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
            if ((prevCommand.equals("insert") || prevCommand.equals("search"))
                && prevCommandSuccess) {
                String output = "Update " + currStudent.getName()
                    +  " record, Score = " + s;
                String pid = currStudent.getID();
                int index = sections[currSection - 1].getIndex(pid);
                sections[currSection - 1].getScoreRoster().
                remove(currStudent.getScore(), index);
                currStudent.setScore(s);
                sections[currSection - 1].getScoreRoster().
                insert(currStudent.getScore(), index);
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
        Iterator<String> me = 
        sections[currSection - 1].getPidRoster().iterator();
        while (me.hasNext()) {
            count++;
            me.next();
        }
        ret += "Section " + currSection + " dump:\n";
        return (ret + sections[currSection - 1].toString() 
            + "Size = " + count);
        //inorder traversal
    }
    /**
     * Assigns grades to all getStudents() in current section
     * @return string output
     */
    @SuppressWarnings("unchecked")
    public String grade() {
        String[] grades = {"A", "A-", "B+", "B", "B-", "C+",
            "C", "C-", "D+", "D", "D-", "F"};
        int[] gradeCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 0; i < sections[currSection - 1].getCurrSpot(); i++) {
            if (sections[currSection - 1].getStudents()[i] != null) {
                int currScore = sections[currSection - 1].
                    getStudents()[i].getScore();
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
                sections[currSection - 1].getStudents()[i].setGrade(g);
            }
        }
        return "grading completed";
        //iterate through BST and asssign grade with switch statement
    }
    
    /**
     * 
     * @return returnmessage
     */
    public String stat() {
        String ret = "Statistics of section ";
        ret += currSection;
        ret += ":";
        String[] letterArr = {"A", "A-", "B+", "B", "B-", "C+",
            "C", "C-", "D+", "D", "D-", "F"};
        int[] countArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        for(int i = 0; i < sections[currSection - 1].getCurrSpot(); i++) {
            if (sections[currSection - 1].getStudents()[i] != null) {
                String currGrade = sections[currSection - 1].
                    getStudents()[i].getGrade();
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
        }
        for (int i = 0; i < letterArr.length; i++) {
            if (countArr[i] > 0) {
                ret += "\n" + countArr[i] + 
                    " students with grade " + letterArr[i];
            }
        }
        //how many getStudents() in curr section have grade g
        
        return ret;
    }
    
    /**
     * 
     * @param g grade to list
     * @return returnmessage
     */
    public String list(String g) {
        String ret = "Students with grade " + g + " are:\n";
        int count = 0;
        g = g.toUpperCase();
        for (int i = 0; i < sections[currSection - 1].getCurrSpot(); i++) {
            Student curr = sections[currSection - 1].getStudents()[i];
            if (curr != null) {
                if (g.substring(1).equals("*")){
                    if (g.charAt(0) == (sections[currSection - 1].
                        getStudents()[i].getGrade().charAt(0))) {
                        ret += sections[currSection - 1].
                            getStudents()[i].toString() + ", grade = " + curr.getGrade();
                        count++;
                        ret += "\n";
                    }
                    
                }
                else {
                    if (g.equals(sections[currSection - 1].
                        getStudents()[i].getGrade())) {
                        ret += sections[currSection - 1].
                            getStudents()[i].toString() + ", grade = "+ curr.getGrade();
                        count++;
                        ret += "\n";
                    }
                }
            }
        }
        
        ret += "Found " + count + " students";
        return ret;
    }
    /**
     * Finds pairs of getStudents() with differences 
     * in scores less than or equal to x
     * @param x difference in scores
     * @return string output
     */
    public String findPair(int x) {
        int paircount = 0;
        String ret = "Students with score difference less than or equal "
        + x + ":\n";
        Student[] studentpairs = sections[currSection - 1].getStudents();
        for (int i = 0; i < sections[currSection - 1].getCurrSpot(); i++ ) {
            for (int j = i+1; j < sections[currSection - 1].getCurrSpot(); j++) {
                if (studentpairs[i] != null && studentpairs[j] != null 
                    && Math.abs(studentpairs[i].getScore() - 
                        studentpairs[j].getScore()) <= x)
                {
                    paircount++;
                    ret += studentpairs[i].getName().toString() + ", " +
                    studentpairs[j].getName().toString() + "\n";
                }
            }
        }
        
        return ret + "found " + paircount + " pairs";
    }



}