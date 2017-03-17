package model;

/**
 * Created by SteveQ on 2017-03-15.
 */
public class Subject {
    private Integer id;
    private String subjectName;

    public Subject(Integer id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
