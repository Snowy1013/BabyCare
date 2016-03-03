package com.snowy.babycare.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by snowy on 16/3/1.
 */
public class Student {

    private int id;
    private String name;
    private int age;
    private List<String> courses;
    private Map<String, Float> scores;
    private Date birthday;

    public Student() {
    }

    public Student(int id, String name, int age, Date birthday,
                   List<String> courses, Map<String, Float> scores) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.courses = courses;
        this.scores = scores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setScores(Map<String, Float> scores) {
        this.scores = scores;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getCourses() {
        return courses;
    }

    public Map<String, Float> getScores() {
        return scores;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", courses=" + courses +
                ", scores=" + scores +

                '}';
    }
}
