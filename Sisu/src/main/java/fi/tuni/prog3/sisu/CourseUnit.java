package fi.tuni.prog3.sisu;

/**
 * A class for representing a course unit.
 *
 */
public class CourseUnit implements Comparable<CourseUnit> {

    private String name;
    private String code;
    private int credits;
    private boolean numericGrading = true;
    private String courseInfo = "";

    /**
     * Constructs a new course unit.
     *
     * @param name name of the course
     * @param code code of the course
     * @param credits number of credits
     */
    CourseUnit(String name, String code, int credits) {
        this.name = name;
        this.code = code;
        this.credits = credits;
    }

    /**
     * Sets grading scale for the course.
     *
     * @param isNumeric boolean value indicating whether or not the course is
     * graded on a numeric scale. True if yes, false if not
     */
    public void setGradingScale(boolean isNumeric) {
        this.numericGrading = isNumeric;
    }

    /**
     * This method tells if the course is graded on a numeric scale
     *
     * @return true if grading scale is numeric (0-5), otherwise false
     * (passed/not passed)
     */
    public boolean gradingScaleIsNumeric() {
        return numericGrading;
    }

    /**
     * Sets the course information text
     *
     * @param infoText a string of text containing information about the course.
     */
    public void setCourseInfo(String infoText) {
        this.courseInfo = infoText;
    }

    /**
     * Returns information about the course.
     *
     * @return course info as a string of text
     */
    public String getCourseInfo() {
        return courseInfo;
    }

    /**
     * A method for comparing two CourseUnit objects. Helpful for sorting
     * courses in alphabetical order and determining if two CourseUnit objects
     * are representing the same course
     *
     * @param course the other course for comparison
     * @return result of the comparison as an integer value
     */
    @Override
    public int compareTo(CourseUnit course) {
        int cmp = name.compareTo(course.getName());
        if (cmp == 0) {
            cmp = code.compareTo(course.getCode());
        }
        return cmp;
    }

    /**
     * Returns the name of the course
     *
     * @return name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the course code
     *
     * @return course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns credits of the course
     *
     * @return the number of credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets a name for the course
     *
     * @param name name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a code for the course
     *
     * @param code new course code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets credits for the course
     *
     * @param credits number of credits
     * @throws IllegalArgumentException if trying to set less than 0 credits
     */
    public void setCredits(int credits) {
        if (credits > 0) {
            this.credits = credits;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns course code and name in string form
     *
     * @return course code and name
     */
    public String toString() {
        return code + " " + name;
    }

}
