package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for representing a student.
 *
 */
public class Student implements Comparable<Student> {

    private String name;
    private String studentNumber;
    private int startYear;
    private int graduationYear;
    private DegreeProgramme programme;
    private ArrayList<Attainment> attainments;
    private int credits;
    private double gradeSum;
    private int gradeCount;
    private double average;

    /**
     * Constructor for a Student object
     *
     * @param name student's name
     * @param studentNumber unique id for identifying the student
     * @param startYear starting year of studies
     * @param graduationYear year when the student is planning to graduate
     * @param programme student's degree programme
     */
    Student(String name, String studentNumber, int startYear, int graduationYear, DegreeProgramme programme) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.startYear = startYear;
        this.graduationYear = graduationYear;
        this.programme = programme;
        this.attainments = new ArrayList<>();
        this.credits = 0;
        this.gradeSum = 0;
        this.gradeCount = 0;
        this.average = 0;
    }

    /**
     * Returns name of the student.
     *
     * @return name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Returns student number of the student
     *
     * @return student number of the student
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * Returns the student's starting year of studies
     *
     * @return starting year of studies
     */
    public int getStartYear() {
        return startYear;
    }

    /**
     * Returns the student's targeted graduation year
     *
     * @return the targeted year of graduation
     */
    public int getGraduationYear() {
        return graduationYear;
    }

    /**
     * Returns student's degree programme
     *
     * @return student's degree programme
     */
    public DegreeProgramme getDegreeProgramme() {
        return programme;
    }

    /**
     * Returns a list of courses the student has completed
     *
     * @return student's attainments
     */
    public ArrayList<Attainment> getAttainments() {
        return attainments;
    }

    /**
     * Returns the amount of credits the student currently has
     *
     * @return the amount of credits the student currently has
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Returns the average grade of student's attainments that are graded on
     * numeric scale
     *
     * @return student's average grade
     */
    public String getAverage() {
        return String.format("%.2f", average);
    }

    /**
     * Sets a name for the student
     *
     * @param name student's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a student number for student
     *
     * @param studentNumber student number
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * Sets the starting year of studies
     *
     * @param startYear the starting year of studies
     */
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    /**
     * Sets the target year for graduation
     *
     * @param graduationYear the target year of graduation
     * @throws IllegalArgumentException if trying to set graduation year before
     * the starting year
     */
    public void setGraduationYear(int graduationYear) {
        if (graduationYear > this.startYear) {
            this.graduationYear = graduationYear;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Sets the degree programme for student
     *
     * @param programme student's degree programme
     */
    public void setProgramme(DegreeProgramme programme) {
        this.programme = programme;
    }

    /**
     * Adds a new attainment for student and updates student's credit count and
     * average grade. If student has previously completed the same course,
     * existing attainment gets replaced with the new one.
     *
     * @param course the course that has been completed
     * @param passed true if the course was passed, false if not
     * @param grade numeric grade on scale 0-5
     * @throws IllegalArgumentException if grade is not between 0 and 5
     */
    public void addAttainment(CourseUnit course, boolean passed, int grade) {
        Attainment att = new Attainment(course, passed, grade);
        // checking if student has an attainment for this course already
        // -> if does, old attainment is removed and replaced by the new one
        for (Attainment a : attainments) {
            if (a.equals(att)) {
                if (a.getGrade() > 0) {
                    this.gradeCount -= 1;
                    this.gradeSum -= grade;
                    this.average = gradeSum / gradeCount;
                }
                this.credits -= a.getCourse().getCredits();
                attainments.remove(a);
            }
        }
        this.attainments.add(att);
        if (grade > 0) {
            this.gradeSum += grade;
            this.gradeCount += 1;
            this.average = gradeSum / gradeCount;
        } else {
            // if grading scale is not numeric -> grade is set to 0
            att.setGrade(0);
        }
        if (passed) {
            this.credits += course.getCredits();
        }
    }

    /**
     * Removes course from student's attainments
     *
     * @param course course to be removed
     */
    public void removeAttainment(CourseUnit course) {
        for (Attainment a : attainments) {
            if (a.getCourse() == course) {
                if (course.gradingScaleIsNumeric()) {
                    this.gradeSum -= a.getGrade();
                    this.gradeCount -= 1;
                    this.average = gradeSum / gradeCount;
                }
                this.credits -= course.getCredits();
                this.attainments.remove(a);
                if (this.gradeCount == 0) {
                    this.average = 0.0;
                }
            }
        }

    }

    /**
     * Removes all attainments from student.
     *
     */
    public void removeAllAttainments() {
        this.attainments.clear();
        this.gradeSum = 0;
        this.gradeCount = 0;
        this.average = 0.0;
        this.credits = 0;
    }

    /**
     * Returns a string of student number and name.
     *
     * @return a string of student number and name
     */
    public String toString() {
        return studentNumber + " " + name;
    }

    /**
     * Compares two students first by name and then by student number. Makes it
     * possible to sort students in alphabetical order
     *
     * @param student a student object that this student is being compared to
     * @return result of the comparison as an integer value
     */
    @Override
    public int compareTo(Student student) {
        int cmp = name.compareTo(student.getName());
        if (cmp == 0) {
            cmp = studentNumber.compareTo(student.getStudentNumber());
        }
        return cmp;
    }

}
