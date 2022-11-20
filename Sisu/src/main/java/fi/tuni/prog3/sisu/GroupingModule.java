package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for representing a grouping module.
 *
 */
public class GroupingModule extends Module {

    private String type = "groupingModule";
    private String name;
    private String code;
    private String language;
    private ArrayList<Module> modules;
    private ArrayList<CourseUnit> courses;
    private int credits = 0;

    /**
     * Constructs an empty grouping module.
     *
     * @param name name of the module
     * @param code code of the module
     */
    GroupingModule(String name, String code) {
        this.name = name;
        this.code = code;
        this.modules = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    /**
     * Returns the type of module, which is groupingModule
     *
     * @return string "groupingModule"
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Returns the name of the module
     *
     * @return name of the module
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns code of the module
     *
     * @return code of the module
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the language of the module
     *
     * @return language of the module
     */
    @Override
    public String getLanguage() {
        return language;
    }

    /**
     * Returns a list of groupingmodule's submodules
     *
     * @return list of module objects
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
     * Returns a list of courses in groupingmodule (including the courses in
     * submodules)
     *
     * @return a list of CourseUnit objects
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
     * Returns the number of credits in groupingmodule
     *
     * @return sum of credits of the courses in this module
     */
    @Override
    public int getCredits() {
        return credits;
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
     * Returns the language of the degree programme.
     *
     * @param language language of the programme
     */
    @Override
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Adds a course to the grouping module.
     *
     * @param course course to be added
     * @throws IllegalArgumentException if the course is already a part of
     * groupingmodule
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
     * Adds a submodule to the grouping module.
     *
     * @param module module object to be added
     * @throws IllegalArgumentException if module is already this
     * groupingmodule's submodule
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
