package nl.hsleiden.vault.vault.Database;

import java.io.Serializable;

public class Course implements Serializable {           // WAAROM serializable ????

    private String name;
    private String ects;
    private String grade;
    private String period;
    private String story;

    public Course(String courseName, String ects, String grade, String period, String story){
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
        this.story = story;
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

    public void setStory(String story) {
        this.story = story;
    }
}
