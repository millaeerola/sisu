package fi.tuni.prog3.sisu;

/**
 * 
 * Class for course attainments
 */
public class Attainment implements Comparable<Attainment> {

        private CourseUnit course;
        private boolean passed;
        private int grade;

        /**
         * Constructor for an attainment
         *
         * @param course the course unit
         * @param passed true if student passed the course, false if not.
         * @param grade Numeric grade on scale 0-5. If the grading scale is not
         * numeric (course is graded as passed/not passed), grade is set to 0.
         */
        Attainment(CourseUnit course, boolean passed, int grade) {
            this.course = course;
            this.passed = passed;
            this.grade = grade;
            if (grade > 0) {
                this.passed = true;
            }
        }

        /**
         * Method for comparing if two attainments are the same
         *
         * @param att another attainment for comparison
         * @return result of the comparison as an integer value
         */
        @Override
        public int compareTo(Attainment att) {
            return this.course.getCode().compareTo(att.course.getCode());
        }

        /**
         * Method for setting the grade
         *
         * @param grade numeric grade on scale 0-5
         * @throws IllegalArgumentException if trying to set a grade less than 0
         * or greater than 5
         */
        public void setGrade(int grade) {
            if (grade >= 0 || grade <= 5) {
                this.grade = grade;
            } else {
                throw new IllegalArgumentException();
            }
        }

        /**
         * Method to tell if student passed the course or not
         *
         * @return true if course is passed, false if not.
         */
        public boolean isPassed() {
            return passed;
        }

        /**
         * Returns the CourseUnit object of the attainment.
         *
         * @return the CourseUnit object of the attainment
         */
        public CourseUnit getCourse() {
            return course;
        }

        /**
         * Returns grade of the attainment
         *
         * @return grade of the attainment
         */
        public int getGrade() {
            return grade;
        }

        /**
         * Returns the grade as string
         *
         * @return numeric grade converted to string, or if the course is not
         * graded on scale 1-5, returns information whether the course is passed
         * or not.
         */
        public String getGradeAsString() {
            if (course.gradingScaleIsNumeric() && grade > 0) {
                return grade + "";
            } else if (passed) {
                return "Hyväksytty";
            } else {
                return "Hylätty";
            }
        }

        /**
         * Returns attainment in string form
         *
         * @return name of the course + grade
         */
        public String toString() {
            String gradeStr = "";
            if (course.gradingScaleIsNumeric() && grade > 0) {
                gradeStr = grade + "";
            } else if (passed) {
                gradeStr = "HYV";
            } else {
                gradeStr = "HYL";
            }
            return course.toString() + " --- " + gradeStr;
        }
    }
