package fi.tuni.prog3.sisu;

import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class for getting student information from a json file and updating it
 *
 */
public class StudentInfo {

    private String jsonFile;
    private ArrayList<Student> students;

    /**
     * Constructs a new StudentInfo object
     *
     * @param filename filepath of the json file where the student info is
     * located and where we want to store the information
     */
    StudentInfo(String filename) {
        this.jsonFile = filename;
        this.students = new ArrayList<>();
    }

    /**
     * Stores new student info to json file and this object's arraylist
     *
     * @param student Student object whose information is being saved
     * @throws IOException if there are problems reading/writing the file
     */
    public void addStudent(Student student) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (Student s : students) {
            if (s.getStudentNumber().equals(student.getStudentNumber())) {
                // if you update an existing student's info (or add a new
                // student with a student number that already exists), existing
                // student object gets replaced with updated info
                students.remove(s);
                break;
            }
        }
        students.add(student);
        Writer writer = new FileWriter(jsonFile);
        gson.toJson(students, writer);
        writer.close();
    }

    /**
     * Method for removing student info from the file
     *
     * @param student student to be removed
     * @throws IOException if there are problems reading/writing the file
     */
    public void removeStudent(Student student) throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        for (Student s : students) {
            if (s.getStudentNumber().equals(student.getStudentNumber())) {
                students.remove(s);
                break;
            }
        }
        Writer writer = new FileWriter(jsonFile);
        gson.toJson(students, writer);
        writer.close();
    }

    /**
     * Removes all students from file
     *
     * @throws IOException if there are problems reading/writing the file
     */
    public void removeAll() throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        students.clear();
        Writer writer = new FileWriter(jsonFile);
        gson.toJson(students, writer);
        writer.close();
    }

    /**
     * Helper function for getStudents. Reads info from modules and possible
     * submodules
     *
     * @param module module info as a json object
     * @param type type of the module (studymodule, groupingmodule or
     * degreeprogramme)
     * @return a module object
     * @throws IllegalArgumentException if module type is not one of the three
     * subtypes of abstract Module class
     */
    public Module getModuleAttributes(JsonObject module, String type) {
        Module mod;
        String name = module.get("name").getAsString();
        String code = module.get("code").getAsString();
        int credits = 0;
        if (module.get("credits") != null) {
            credits = module.get("credits").getAsInt();
        }
        if (type.equals("degreeProgramme")) {
            mod = new DegreeProgramme(name, code, credits);
        } else if (type.equals("groupingModule")) {
            mod = new GroupingModule(name, code);
            JsonArray courses = module.get("courses").getAsJsonArray();
            for (JsonElement c : courses) {
                JsonObject course = c.getAsJsonObject();
                String courseName = course.get("name").getAsString();
                String courseCode = course.get("code").getAsString();
                int courseCredits = course.get("credits").getAsInt();
                CourseUnit newCourse = new CourseUnit(courseName, courseCode, courseCredits);
                mod.addCourse(newCourse);
            }
        } else if (type.equals("studyModule")) {
            mod = new StudyModule(name, code);
            JsonArray courses = module.get("courses").getAsJsonArray();
            for (JsonElement c : courses) {
                JsonObject course = c.getAsJsonObject();
                String courseName = course.get("name").getAsString();
                String courseCode = course.get("code").getAsString();
                int courseCredits = course.get("credits").getAsInt();
                CourseUnit newCourse = new CourseUnit(courseName, courseCode, courseCredits);
                mod.addCourse(newCourse);
            }
        } else {
            throw new IllegalArgumentException();
        }

        JsonArray modules = module.get("modules").getAsJsonArray();
        if (!modules.isEmpty()) {
            for (JsonElement m : modules) {
                JsonObject submodule = m.getAsJsonObject();
                Module s = getModuleAttributes(submodule, submodule.get("type").getAsString());
                mod.addModule(s);
            }
        }

        return mod;
    }

    /**
     * Reads student data from json file and returns it as an arraylist
     *
     * @return a list of Student objects that were stored in the json file
     * @throws FileNotFoundException if there are problems with reading the file
     */
    public ArrayList<Student> getStudents() throws FileNotFoundException {
        ArrayList<Student> updatedList = new ArrayList<>();
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            JsonReader jr = new JsonReader(new BufferedReader(new FileReader(jsonFile)));
            if (jr.toString() != null) {
                JsonParser parser = new JsonParser();
                jr.setLenient(true);
                JsonArray data = parser.parse(jr).getAsJsonArray();
                for (int i = 0; i < data.size(); i++) {
                    JsonObject obj = data.get(i).getAsJsonObject();
                    String name = obj.get("name").getAsString();
                    String studentNumber = obj.get("studentNumber").getAsString();
                    int startYear = obj.get("startYear").getAsInt();
                    int graduationYear = obj.get("graduationYear").getAsInt();
                    JsonObject programme = obj.get("programme").getAsJsonObject();

                    // initializing student object
                    Student s = new Student(name, studentNumber, startYear, graduationYear,
                            (DegreeProgramme) getModuleAttributes(programme, "degreeProgramme"));
                    updatedList.add(s);

                    // checking for additional attributes
                    JsonArray attainments = obj.get("attainments").getAsJsonArray();
                    if (!attainments.isEmpty()) {
                        for (JsonElement a : attainments) {
                            JsonObject att = a.getAsJsonObject();
                            JsonObject courseObj = att.get("course").getAsJsonObject();
                            String courseName = courseObj.get("name").getAsString();
                            String courseCode = courseObj.get("code").getAsString();
                            int courseCredits = courseObj.get("credits").getAsInt();
                            CourseUnit course = new CourseUnit(courseName, courseCode, courseCredits);
                            int courseGrade = att.get("grade").getAsInt();
                            boolean passed = att.get("passed").getAsBoolean();
                            s.addAttainment(course, passed, courseGrade);
                        }
                    }
                }
            }
            jr.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // sorting the list of students (method compareTo of class Student
        // sorts first by name, then by student number)
        Collections.sort(updatedList, (a, b) -> {
            return a.compareTo(b);
        });

        students = updatedList;

        return students;
    }

}
