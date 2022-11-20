package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Collections;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    public StudentTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Student.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        String expResult = "Example Student";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStudentNumber method, of class Student.
     */
    @Test
    public void testGetStudentNumber() {
        System.out.println("getStudentNumber");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        String expResult = "12345";
        String result = instance.getStudentNumber();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStartYear method, of class Student.
     */
    @Test
    public void testGetStartYear() {
        System.out.println("getStartYear");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        int expResult = 2020;
        int result = instance.getStartYear();
        assertEquals(expResult, result);

    }

    /**
     * Test of getGraduationYear method, of class Student.
     */
    @Test
    public void testGetGraduationYear() {
        System.out.println("getGraduationYear");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        int expResult = 2025;
        int result = instance.getGraduationYear();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDegreeProgramme method, of class Student.
     */
    @Test
    public void testGetDegreeProgramme() {
        System.out.println("getDegreeProgramme");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 5);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        DegreeProgramme expResult = programme;
        DegreeProgramme result = instance.getDegreeProgramme();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAttainments method, of class Student.
     */
    @Test
    public void testGetAttainments() {
        System.out.println("getAttainments");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        CourseUnit course1 = new CourseUnit("programming 1", "comp.cs.100", 5);
        CourseUnit course2 = new CourseUnit("programming 2", "comp.cs.110", 5);
        CourseUnit course3 = new CourseUnit("programming 3", "comp.cs.140", 5);
        instance.addAttainment(course1, true, 4);
        instance.addAttainment(course2, true, 5);
        instance.addAttainment(course3, true, 3);
        ArrayList<Attainment> attainments = new ArrayList<>();
        attainments.add(new Attainment(course1, true, 4));
        attainments.add(new Attainment(course2, true, 5));
        attainments.add(new Attainment(course3, true, 3));
        ArrayList<Attainment> expArray = attainments;
        ArrayList<Attainment> resultArray = instance.getAttainments();
        boolean expResult = true;
        boolean result = true;
        int index = 0;
        for (Attainment entry : expArray) {
            if (!entry.getCourse().equals(resultArray.get(index).getCourse())) {
                result = false;
                break;
            }
            index += 1;
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of getCredits method, of class Student.
     */
    @Test
    public void testGetCredits() {
        System.out.println("getCredits");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        CourseUnit course1 = new CourseUnit("programming 1", "comp.cs.100", 5);
        CourseUnit course2 = new CourseUnit("programming 2", "comp.cs.110", 5);
        CourseUnit course3 = new CourseUnit("programming 3", "comp.cs.140", 5);
        instance.addAttainment(course1, true, 4);
        instance.addAttainment(course2, true, 5);
        instance.addAttainment(course3, true, 3);
        int expResult = 15;
        int result = instance.getCredits();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAverage method, of class Student.
     */
    @Test
    public void testGetAverage() {
        System.out.println("getAverage");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        CourseUnit course1 = new CourseUnit("programming 1", "comp.cs.100", 5);
        CourseUnit course2 = new CourseUnit("programming 2", "comp.cs.110", 5);
        CourseUnit course3 = new CourseUnit("programming 3", "comp.cs.140", 5);
        instance.addAttainment(course1, true, 4);
        instance.addAttainment(course3, true, 3);
        String expResult = String.format("%.2f", 3.5);
        String result = instance.getAverage();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Student.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        instance.setName("New Name");
        String expResult = "New Name";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStudentNumber method, of class Student.
     */
    @Test
    public void testSetStudentNumber() {
        System.out.println("setStudentNumber");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        instance.setStudentNumber("00000");
        String expResult = "00000";
        String result = instance.getStudentNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStartYear method, of class Student.
     */
    @Test
    public void testSetStartYear() {
        System.out.println("setStartYear");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        instance.setStartYear(2019);
        int expResult = 2019;
        int result = instance.getStartYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGraduationYear method, of class Student.
     */
    @Test
    public void testSetGraduationYear() {
        System.out.println("setGraduationYear");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        instance.setGraduationYear(2024);
        int expResult = 2024;
        int result = instance.getGraduationYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of setProgramme method, of class Student.
     */
    @Test
    public void testSetProgramme() {
        System.out.println("setProgramme");
        DegreeProgramme programme = new DegreeProgramme("bachelor's degree in computing science", "CS-100", 180);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        DegreeProgramme newProgramme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        instance.setProgramme(newProgramme);
        DegreeProgramme expResult = newProgramme;
        DegreeProgramme result = instance.getDegreeProgramme();
        assertEquals(expResult, result);
    }

    /**
     * Test of addAttainment method, of class Student.
     */
    @Test
    public void testAddAttainment() {
        System.out.println("addAttainment");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        CourseUnit course = new CourseUnit("programming 3", "comp.cs.140", 5);
        int grade = 5;
        boolean passed = true;
        instance.addAttainment(course, passed, grade);
        ArrayList<Attainment> attainments = new ArrayList<>();
        attainments.add(new Attainment(course, passed, grade));
        ArrayList<Attainment> expArray = attainments;
        ArrayList<Attainment> resultArray = instance.getAttainments();

        boolean expResult = true;
        boolean result = true;
        int index = 0;
        for (Attainment entry : expArray) {
            if (!entry.getCourse().equals(resultArray.get(index).getCourse())) {
                result = false;
                break;
            }
            index += 1;
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of the class StudentInfo
     *
     * @throws IOException if there are problems reading/writing the file
     */
    @Test
    public void testStudentInfo() throws IOException {

        System.out.println("StudentInfo");
        StudentInfo instance = new StudentInfo("students.json");

        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student matti = new Student("Matti Meikäläinen", "12345", 2020, 2025, programme);
        Student maija = new Student("Maija Mehiläinen", "020202", 2019, 2024, programme);

        CourseUnit course1 = new CourseUnit("programming 1", "comp.cs.100", 5);
        CourseUnit course2 = new CourseUnit("programming 2", "comp.cs.110", 5);
        CourseUnit course3 = new CourseUnit("programming 3", "comp.cs.140", 5);
        maija.addAttainment(course1, true, 4);
        maija.addAttainment(course2, true, 5);
        maija.addAttainment(course3, true, 3);

        StudyModule module1 = new StudyModule("language studies", "LANG");
        CourseUnit c1 = new CourseUnit("English 1", "ENG-1", 3);
        CourseUnit c2 = new CourseUnit("Swedish 1", "SWE-1", 2);
        module1.addCourse(c1);
        module1.addCourse(c2);

        StudyModule module2 = new StudyModule("programming studies", "PROG");
        module2.addCourse(course1);
        module2.addCourse(course2);
        module2.addCourse(course3);

        GroupingModule module3 = new GroupingModule("test group", "abc-123");
        module3.addModule(module1);
        module3.addModule(module2);

        instance.addStudent(matti);
        instance.addStudent(maija);

        Student erkki = new Student("Erkki Esimerkki", "3333", 2018, 2024, programme);

        // this is supposed to replace Matti meikäläinen with the same student number
        Student masa = new Student("Masa Meikäläinen", "12345", 2019, 2025, programme);

        masa.addAttainment(course3, true, 4);
        masa.addAttainment(course1, true, 3);

        instance.addStudent(erkki);
        instance.addStudent(masa);

        ArrayList<Student> expArray = new ArrayList<>();
        expArray.add(matti);
        expArray.add(maija);
        expArray.add(erkki);
        expArray.remove(matti);
        expArray.add(masa);
        Collections.sort(expArray, (a, b) -> {
            return a.compareTo(b);
        });

        ArrayList<Student> resultArray = instance.getStudents();

        boolean expResult = true;
        boolean result = true;

        if (resultArray.size() != expArray.size()) {
            result = false;
        } else {
            int index = 0;
            for (Student entry : expArray) {
                if (!entry.getStudentNumber().equals(resultArray.get(index).getStudentNumber())) {
                    result = false;
                    break;
                }
                index += 1;
            }
        }
        assertEquals(expResult, result);

        // Removing test students
        instance.removeStudent(masa);
        instance.removeStudent(maija);
        instance.removeStudent(erkki);
    }

    /**
     * Testing exceptions
     */
    @Test
    public void exceptionTests() {

        System.out.println("exceptionTesting");
        DegreeProgramme programme = new DegreeProgramme("master's degree in computing science", "CS-123", 120);
        Student instance = new Student("Example Student", "12345", 2020, 2025, programme);
        StudyModule module1 = new StudyModule("language studies", "LANG");
        CourseUnit course1 = new CourseUnit("English 1", "ENG-1", 3);
        CourseUnit course2 = new CourseUnit("Swedish 1", "SWE-1", 2);
        module1.addCourse(course1);
        module1.addCourse(course2);
        StudyModule module2 = new StudyModule("programming studies", "PROG");
        CourseUnit course4 = new CourseUnit("programming 1", "comp.cs.100", 5);
        CourseUnit course5 = new CourseUnit("programming 2", "comp.cs.110", 5);
        CourseUnit course6 = new CourseUnit("programming 3", "comp.cs.140", 5);
        module2.addCourse(course4);
        module2.addCourse(course5);
        module2.addCourse(course6);
        programme.addModule(module2);
        GroupingModule gm = new GroupingModule("group1", "G-1");
        gm.addModule(module1);
        gm.addModule(module2);

        assertThrows(IllegalArgumentException.class, () -> instance.addAttainment(course6, true, -1));
        assertThrows(IllegalArgumentException.class, () -> instance.addAttainment(course5, true, 6));
        assertThrows(IllegalArgumentException.class, () -> instance.setGraduationYear(2000));
        assertThrows(IllegalArgumentException.class, () -> programme.addModule(module2));
        assertThrows(UnsupportedOperationException.class, () -> programme.addCourse(course6));
        assertThrows(IllegalArgumentException.class, () -> course5.setCredits(-1));
        assertThrows(IllegalArgumentException.class, () -> gm.addModule(module2));
        assertThrows(IllegalArgumentException.class, () -> gm.addCourse(course1));
    }
}
