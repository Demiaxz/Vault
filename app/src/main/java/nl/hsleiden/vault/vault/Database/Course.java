package nl.hsleiden.vault.vault.Database;

import java.io.Serializable;

public class Course implements Serializable {           // WAAROM serializable ????

    private String name;
    private String ects;
    private String grade;
    private String period;
    private String testdate;
    private String description;
    private String impact;
    private String concept;
    private String mutationdate;
    private String core;
    private String story;
    private String ID;

    public Course(String courseName, String ects, String grade, String period){
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

    public Course (String name, String ects, String grade, String period, String testdate, String description, String impact, String concept, String mutationdate, String core){
        this.name = name;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
        this.testdate = testdate;
        this.description = description;
        this.impact = impact;
        this.concept = concept;
        this.mutationdate = mutationdate;
        this.core = core;
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getEcts() {
        return ects;
    }
    public String getGrade() {
        return grade;
    }
    public String getPeriod() {
        return period;
    }
    public String getTestdate() {
        return testdate;
    }
    public String getDescription() {
        return description;
    }
    public String getImpact() {
        return impact;
    }
    public String getConcept() {
        return concept;
    }
    public String getMutationdate() {
        return mutationdate;
    }
    public String getCore() {return core;}
    public String getStory() {
        return story;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setEcts(String ects) {
        this.ects = ects;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public void setPeriod(String period) {
        this.period = period;
    }

    public void setTestdate(String testdate) {
        this.testdate = testdate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public void setMutationdate(String mutationdate) {
        this.mutationdate = mutationdate;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
