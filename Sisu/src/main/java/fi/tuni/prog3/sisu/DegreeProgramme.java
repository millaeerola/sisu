package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for representing a degree programme.
 *
 */
public class DegreeProgramme extends Module {

    private String type = "degreeProgramme";
    private String name;
    private String code;
    private int credits;
    private String language;
    private String Id;
    private ArrayList<Module> modules;
    private ArrayList<CourseUnit> courses;

    /**
     * Constructs a new DegreeProgramme object.
     *
     * @param name name of the degree programme
     * @param code code of the degree programme
     * @param credits number of credits required for the degree programme
     */
    DegreeProgramme(String name, String code, int credits) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.modules = new ArrayList<>();
    }

    /**
     * Returns a string of degree programme name and credits.
     *
     * @return
     */
    @Override
    public String toString() {
        return name + "(" + credits + " op)";

    }

    /**
     * Returns the type of subclass of abstract class Module
     *
     * @return type of module, which is degreeProgramme
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Returns the name of the degree programme
     *
     * @return name of the degree programme
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the code of the degree programme
     *
     * @return code of the degree programme
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * Returns the number of credits in the programme.
     *
     * @return the number of credits
     */
    @Override
    public int getCredits() {
        return credits;
    }
    
    /**
     * Returns the language of the degree programme.
     *
     * @return the language of the degree programme
     */
    @Override
    public String getLanguage() {
        return language;
    }
    
    /**
     * Returns the Id of the degree programme.
     *
     * @return the Id of the degree programme
     */
    public String getId() {
        return Id;
    }

    /**
     * Returns a list of modules that are part of the programme.
     *
     * @return list of modules that are part of the programme
     */
    @Override
    public ArrayList<Module> getModules() {
        return modules;
    }

    /**
     * Returns a list of courses that are part of the programme.
     *
     * @return list of courses that are part of the programme
     */
    @Override
    public ArrayList<CourseUnit> getAllCourses() {
        ArrayList<CourseUnit> allCourses = new ArrayList<>();
        for (Module m : modules) {
            allCourses.addAll(m.getAllCourses());
        }
        return allCourses;
    }
    
    /**
     * Returns a list of all courses of DegreeProgramme
     * 
     * @return a lsit of CourseUnit objects
     */
    @Override
    public ArrayList<CourseUnit> getCourses() {
        ArrayList<CourseUnit> allCourses = new ArrayList<>();
        for (Module m : modules) {
            allCourses.addAll(m.getCourses());
        }
        return allCourses;
    }

    /**
     * Sets a name for the programme
     *
     * @param name name of the programme
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a code for the programme
     *
     * @param code code of the programme
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets credits for the programme
     *
     * @param credits number of credits in the programme
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
     * Returns the Id of the degree programme.
     *
     * @param Id Id of the programme
     */
    public void setId(String Id) {
        this.Id = Id;
    }
    
    /**
     * Adds a module to the programme
     *
     * @param module the new module to be added
     * @throws IllegalArgumentException if module already is a part of the
     * programme
     */
    @Override
    public void addModule(Module module) {
        if (!modules.contains(module)) {
            this.modules.add(module);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method is not meant to be used with DegreeProgramme objects. Method
     * is inherited from superclass Module because its other subclasses use it.
     * Courses in degree programmes are always a part of some other submodule.
     *
     * @param course a CourseUnit object
     * @throws UnsupportedOperationException always, because this method is not
     * supported for this subclass of Module
     */
    @Override
    public void addCourse(CourseUnit course) {
        throw new UnsupportedOperationException();
    }

}
