package model;

import java.util.List;

/**
 * Created by SteveQ on 2017-03-17.
 */
public class Subscription {
    private Student student;
    private Subject subject;
    private List<Integer> grades;

    public Subscription(Student student, Subject subject, List<Integer> grades) {
        this.student = student;
        this.subject = subject;
        this.grades = grades;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
