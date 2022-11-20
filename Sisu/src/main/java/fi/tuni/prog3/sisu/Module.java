package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * Abstract class with subclasses DegreeProgramme, StudyModule and
 * GroupingModule.
 */
public abstract class Module implements Comparable<Module> {

    /**
     * Method for comparing two modules
     *
     * @param module another module for comparison
     * @return result of the comparison as an integer value
     */
    @Override
    public int compareTo(Module module) {
        int cmp = this.getName().compareTo(module.getName());
        if (cmp == 0) {
            cmp = this.getCode().compareTo(module.getCode());
            if (cmp == 0) {
                cmp = this.getType().compareTo(module.getType());
            }
        }
        return cmp;
    }

    /**
     * Returns the type of module
     *
     * @return the type of module - either degreeProgramme, studyModule or
     * groupingModule
     */
    public abstract String getType();

    /**
     * Returns the name of the module
     *
     * @return name of the module
     */
    public abstract String getName();

    /**
     * Returns the code of the module
     *
     * @return code of the module
     */
    public abstract String getCode();
    
    /**
     * Returns the language of the module
     *
     * @return language of the module
     */
    public abstract String getLanguage();
    
    /**
     * Returns the list of module's submodules
     *
     * @return
     */
    public abstract ArrayList<Module> getModules();

    /**
     * Returns the number of credits in the module
     *
     * @return number of credits
     */
    public abstract int getCredits();

    /**
     * Returns a list of the courses in the module
     *
     * @return a list of CourseUnit objects
     */
    public abstract ArrayList<CourseUnit> getCourses();
    
    /**
     * Returns a list of all courses (including courses in submodules)
     * 
     * @return a list of CourseUnit objects
     */
    public abstract ArrayList<CourseUnit> getAllCourses();
    
    /**
     * Sets a name for the module
     *
     * @param name name of the module
     */
    public abstract void setName(String name);

    /**
     * Sets a code for the module
     *
     * @param code code of the module
     */
    public abstract void setCode(String code);
    
    /**
     * Sets a language for the module
     *
     * @param language language of the module
     */
    public abstract void setLanguage(String language);

    /**
     * Adds a new submodule to the module
     *
     * @param module module to be added
     */
    public abstract void addModule(Module module);

    /**
     * Adds a new course to the module
     *
     * @param course course to be added
     */
    public abstract void addCourse(CourseUnit course);

}
