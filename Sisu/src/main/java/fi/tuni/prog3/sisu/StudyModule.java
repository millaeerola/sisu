package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for representing a study module.
 *
 */
public class StudyModule extends Module implements Comparable<Module> {

    private String type = "studyModule";
    private String name;
    private String code;
    private int credits;
    private String language;
    private ArrayList<Module> modules;
    private ArrayList<CourseUnit> courses;

    /**
     * Constructs a new empty study module
     *
     * @param name name of the module
     * @param code code of the module
     */
    StudyModule(String name, String code) {
        this.name = name;
        this.code = code;
        this.credits = 0;
        this.modules = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    /**
     * Returns a string of module name and credits
     *
     * @return
     */
    @Override
    public String toString() {
        return name + "(" + credits + " op)";
    }

    /**
     * Returns the type of module (for identifying the type of submodule of
     * abstract class Module)
     *
     * @return string "studyModule"
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Returns study module name
     *
     * @return name of the module
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns code of the study module
     *
     * @return code of the study module
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the number of credits in studyModule
     *
     * @return sum of credits of the courses in the module
     */
    @Override
    public int getCredits() {
        return credits;
    }

    /**
     * Returns the language of the study module.
     *
     * @return language of the study module
     */
    @Override
    public String getLanguage() {
        return language;
    }

    /**
     * Returns a list of module's submodules, that can be StudyModule or
     * GroupingModule objects.
     *
     * @return a list of module's submodules
     */
    @Override
    public ArrayList<Module> getModules() {
        return modules;
    }

    /**
     * Returns a list of courses in the module (not including the courses in
     * submodules)
     *
     * @return a list of courses in the module
     */
    @Override
    public ArrayList<CourseUnit> getCourses() {
        return courses;
    }

    /**
     * Returns a list of courses in the module (including the courses in
     * submodules)
     *
     * @return a list of courses in the module
     */
    @Override
    public ArrayList<CourseUnit> getAllCourses() {
        if (modules.isEmpty()) {
            return courses;
        } else {
            ArrayList<CourseUnit> allCourses = new ArrayList<>();
            allCourses.addAll(courses);
            for (Module m : modules) {
                allCourses.addAll(m.getCourses());
            }
            return allCourses;
        }
    }

    /**
     * Sets a name for the module
     *
     * @param name name of the module
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a code for the module
     *
     * @param code code of the module
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets credits to the module
     *
     * @param credits number of credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Returns the language of the degree programme.
     *
     * @param language language of the programme
     */
    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Adds a course to the module.
     *
     * @param course CourseUnit object to be added
     * @throws IllegalArgumentException if module already contains the course
     */
    public void addCourse(CourseUnit course) {
        if (!courses.contains(course)) {
            this.courses.add(course);
            this.credits += course.getCredits();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Adds a submodule to the study module.
     *
     * @param module module object to be added
     * @throws IllegalArgumentException if module is already this studymodule's
     * submodule
     */
    @Override
    public void addModule(Module module) {
        if (!modules.contains(module)) {
            this.modules.add(module);
            for (CourseUnit c : module.getCourses()) {
                if (!courses.contains(c)) {
                    this.addCourse(c);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

}
