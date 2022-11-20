package fi.tuni.prog3.sisu;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;

/**
 * A class for reading and gathering information from Sisu's API.
 *
 */
public class SisuAPI {
    /**
    * Constructor for the class.
    *
    */
    SisuAPI() {
        
    }

    public ArrayList<DegreeProgramme> DegreeProgrammeList = new ArrayList();
    public DegreeProgramme degreeProgramme;
    
    /**
     * Gathers data from every known degree program
     *
     * @throws MalformedURLException if there are problems opening the URL
     * @throws IOException if there are problems reading/writing the file
     */
    public void getAllDegreeProgrammes() throws MalformedURLException, IOException {  
        URL url = new URL("https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000");
        String APIdataString = new String(url.openStream().readAllBytes());
        
        JsonObject APIjsonObject = new JsonParser().parse(APIdataString).getAsJsonObject();
        JsonArray results = APIjsonObject.getAsJsonArray("searchResults");
        for(JsonElement result : results) {
            JsonObject resultobj = result.getAsJsonObject();
            String degreeProgrammeName = resultobj.getAsJsonPrimitive("name").getAsString();
            String degreeProgrammeCode = resultobj.getAsJsonPrimitive("code").getAsString();           
            String Id = resultobj.getAsJsonPrimitive("id").getAsString();
            String degreeProgrammeLang = resultobj.getAsJsonPrimitive("lang").getAsString();
            int degreeProgrammeCredit = 0;
            DegreeProgramme degreeprogramme = new DegreeProgramme(degreeProgrammeName, degreeProgrammeCode, degreeProgrammeCredit);
            degreeprogramme.setId(Id);
            degreeprogramme.setLanguage(degreeProgrammeLang);
            DegreeProgrammeList.add(degreeprogramme);
        }
    }
    
    /**
     * Gathers data for wanted degree program.
     *
     * @param degreeprogramme degreeprogramme object of wanted degree program
     * @throws MalformedURLException if there are problems opening the URL
     * @throws IOException if there are problems reading/writing the file
     */
    public void getDegreeProgramme(DegreeProgramme degreeprogramme) throws MalformedURLException, IOException {
        String groupId = degreeprogramme.getId();
        String degreeProgrammeName = "";
        int degreeProgrammeCredit = 0;
        String degreeProgrammeCode;
        
        String Groupid = String.format("https://sis-tuni.funidata.fi/kori/api/modules/%s", groupId);
        URL DegreeURL = new URL(Groupid);
        String APIdataString = new String(DegreeURL.openStream().readAllBytes());
        JsonObject APIjsonObject = new JsonParser().parse(APIdataString).getAsJsonObject();
        degreeProgrammeName = getName(degreeprogramme, APIjsonObject);
        degreeProgrammeCode = APIjsonObject.getAsJsonPrimitive("code").getAsString();
        JsonObject credits = APIjsonObject.getAsJsonObject("targetCredits"); 
        degreeProgrammeCredit = credits.getAsJsonPrimitive("min").getAsInt();
        
        degreeProgramme = new DegreeProgramme(degreeProgrammeName, degreeProgrammeCode, degreeProgrammeCredit);
        degreeProgramme.setLanguage(degreeprogramme.getLanguage());
        getModuleData(degreeProgramme, APIjsonObject);
    }
    
    /**
     * Main operator for getting module or course data
     *
     * @param module module object that can be any of its subclasses depending
     * on API data.
     * @param dataObj dataObj object for module or course
     * @throws MalformedURLException if there are problems opening the URL
     * @throws IOException if there are problems reading/writing the file
     */
    public void getModuleData(Module module, JsonObject dataObj) throws MalformedURLException, IOException {
        // determining module type
        if (dataObj.has("type")) {
            String datatype = dataObj.getAsJsonPrimitive("type").getAsString();
            if (datatype.equals("StudyModule")) {
                StudyModule studymodule = getStudyModule(module, dataObj);
                studymodule.setLanguage(module.getLanguage());
                module.addModule(studymodule);
                module = studymodule;
            }
            if (datatype.equals("GroupingModule")) {
                GroupingModule groupingmodule = getGroupingModule(module, dataObj);
                groupingmodule.setLanguage(module.getLanguage());
                module.addModule(groupingmodule);
                module = groupingmodule;
            }
        }
        
        if (dataObj.has("rule")) {
            JsonObject rule = dataObj.getAsJsonObject("rule");
            
            while(true) {
                if (rule.has("rule")) {
                    rule = rule.getAsJsonObject("rule");
                    continue;
                }
                if (rule.has("rules")) {
                    JsonArray list = rule.getAsJsonArray("rules");
                    if (list.size() == 0) {
                        break;
                    }
                    for (JsonElement element : list) {
                        JsonObject obj = element.getAsJsonObject();
                        rule = obj;
                        
                        if (obj.has("moduleGroupId")) {
                            String Id = obj.getAsJsonPrimitive("moduleGroupId").getAsString();
                            String ModuleURLString = "";

                            ModuleURLString = String.format("https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=%s&universityId=tuni-university-root-id", Id);
                            URL ModuleURL = new URL(ModuleURLString);
                            String APIdataString = new String(ModuleURL.openStream().readAllBytes());
                            JsonArray ModuleAPIArray = new JsonParser().parse(APIdataString).getAsJsonArray();
                            for(JsonElement i : ModuleAPIArray) {
                                JsonObject ModuleAPIObj = i.getAsJsonObject();
                                getModuleData(module, ModuleAPIObj);
                            }
                        }
                        
                        if (obj.has("courseUnitGroupId")) {
                            String CourseUnitGroupId = obj.getAsJsonPrimitive("courseUnitGroupId").getAsString();
                            String CourseURLString = String.format("https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=%s&universityId=tuni-university-root-id", CourseUnitGroupId);
                            URL CourseURL = new URL(CourseURLString);
                            String APIdataString = new String(CourseURL.openStream().readAllBytes());
                            JsonArray courseUnitAPIArray = new JsonParser().parse(APIdataString).getAsJsonArray();
                            getCourseUnit(module, courseUnitAPIArray);
                        }
                    }
                    continue;
                }
                break;
            }           
        }
    }
    
    /**
     * Gathers data of wanted studymodule.
     *
     * @param module module object that can be any of its subclasses depending
     * on API data.
     * @param dataObj dataObj object for module or course
     * @return returns data of wanted studymodule
     */
    public StudyModule getStudyModule(Module module, JsonObject dataObj) {
        String StudyModuleName = "";
        int StudyModuleCredit = 0;
        String StudyModuleCode = null;
        
        if(dataObj.has("code")) {
            StudyModuleCode = dataObj.getAsJsonPrimitive("code").getAsString();
        }
        if (dataObj.has("name")) {
            StudyModuleName = getName(module, dataObj);
        }
        if (dataObj.has("targetCredits")) {
            JsonObject credits = dataObj.getAsJsonObject("targetCredits"); 
            StudyModuleCredit = credits.getAsJsonPrimitive("min").getAsInt();
        }
        StudyModule studyModule = new StudyModule(StudyModuleName, StudyModuleCode);
        studyModule.setCredits(StudyModuleCredit);
        
        return studyModule;
    }
    
    /**
     * Gathers data of wanted groupingmodule.
     *
     * @param module module object that can be any of its subclasses depending
     * on API data.
     * @param dataObj dataObj object for module or course
     * @return returns data of wanted studymodule
     */
    public GroupingModule getGroupingModule(Module module, JsonObject dataObj) {
        String GroupingModuleName = "";
        String GroupingModuleCode = null;
        
        if (dataObj.has("name")) {
            GroupingModuleName = getName(module, dataObj);     
        }
        GroupingModule groupingModule = new GroupingModule(GroupingModuleName, GroupingModuleCode);
        return groupingModule;
    }
    
    /**
     * Gathers data of wanted course unit.
     *
     * @param module module object that can be any of its subclasses depending
     * on API data.
     * @param dataArray dataArray represents course data as an array
     */
    public void getCourseUnit(Module module, JsonArray dataArray) {
        String CourseName = "";
        int CourseCredit = 0;
        String CourseCode;
        String CourseContent = "";
        
        for (JsonElement courseData : dataArray) {
            JsonObject courseDataObject = courseData.getAsJsonObject();

            CourseCode = courseDataObject.getAsJsonPrimitive("code").getAsString();
            
            if (courseDataObject.has("name")) {
                CourseName = getName(module, courseDataObject);
            }
            
            if (courseDataObject.has("content")) {
                if (courseDataObject.get("content").isJsonObject()) {
                    JsonObject content = courseDataObject.get("content").getAsJsonObject();
                
                    if (module.getLanguage().equals("fi")) {
                        if (content.has("fi")) {
                            CourseContent = content.getAsJsonPrimitive("fi").getAsString()
                            .replace("<p>", "").replace("</p>", "").replace("<ul>", "")
                            .replace("</ul>", "").replace("<li>", "").replace("</li>", "")
                            .replace("<br />", "").replace("<h5>", "").replace("</h5>", "")
                            .replace("<h6>", "").replace("</h6>", "").replace("</b>", "")
                            .replace("<b>", "");
                        }
                        else if (content.has("en")) {
                            CourseContent = content.getAsJsonPrimitive("en").getAsString()
                            .replace("<p>", "").replace("</p>", "").replace("<ul>", "")
                            .replace("</ul>", "").replace("<li>", "").replace("</li>", "")
                            .replace("<br />", "").replace("<h5>", "").replace("</h5>", "")
                            .replace("<h6>", "").replace("</h6>", "").replace("</b>", "")
                            .replace("<b>", "");;
                        }
                    }
                    else {
                        if (content.has("en")) {
                            CourseContent = content.getAsJsonPrimitive("en").getAsString()
                            .replace("<p>", "").replace("</p>", "").replace("<ul>", "")
                            .replace("</ul>", "").replace("<li>", "").replace("</li>", "")
                            .replace("<br />", "").replace("<h5>", "").replace("</h5>", "")
                            .replace("<h6>", "").replace("</h6>", "").replace("</b>", "")
                            .replace("<b>", "");;
                        }
                        else if (content.has("fi")) {
                            CourseContent = content.getAsJsonPrimitive("fi").getAsString()
                            .replace("<p>", "").replace("</p>", "").replace("<ul>", "")
                            .replace("</ul>", "").replace("<li>", "").replace("</li>", "")
                            .replace("<br />", "").replace("<h5>", "").replace("</h5>", "")
                            .replace("<h6>", "").replace("</h6>", "").replace("</b>", "")
                            .replace("<b>", "");;
                        }
                    }
                }
            }
            JsonObject credits = courseDataObject.getAsJsonObject("credits"); 
            CourseCredit = credits.getAsJsonPrimitive("min").getAsInt();
            CourseUnit course = new CourseUnit(CourseName, CourseCode, CourseCredit);
            course.setCourseInfo(CourseContent);
            module.addCourse(course);
        }
    }
    
    /**
     * Helper function for getting module or course name
     *
     * @param module module object that can be any of its subclasses depending
     * on API data.
     * @param dataobj dataobj object for module or course
     * @return returns name of module or course
     */
    public String getName(Module module, JsonObject dataobj) {
        String nameObject = "";
        JsonObject names = dataobj.getAsJsonObject("name");
        
        if (module.getLanguage().equals("fi")) {
            if (names.has("fi")) {
                nameObject = names.getAsJsonPrimitive("fi").getAsString();
            }
            else {
                nameObject = names.getAsJsonPrimitive("en").getAsString();
            }
        }   
        else
        {
            if (names.has("en")) {
                nameObject = names.getAsJsonPrimitive("en").getAsString();
            }
            else {
                nameObject = names.getAsJsonPrimitive("fi").getAsString();
            }
        }
        return nameObject;
    }
}