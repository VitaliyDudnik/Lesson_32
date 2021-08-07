package com.tms.entity;

import java.util.List;
import java.util.Objects;

public class Teacher {
    private int id;
    private String name;
    private String username;
    private String password;
    private String discipline;
    private List disciplineList;

    public Teacher(int id, String name, String username, String password, List disciplineList) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.disciplineList = disciplineList;
    }

    public Teacher(int id, String name, String username, String password, String discipline) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.discipline = discipline;

    }

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List getDisciplineList() {
        return disciplineList;
    }

    public void setDisciplineList(List disciplineList) {
        this.disciplineList = disciplineList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && Objects.equals(name, teacher.name) && Objects.equals(username, teacher.username) && Objects.equals(password, teacher.password) && Objects.equals(discipline, teacher.discipline) && Objects.equals(disciplineList, teacher.disciplineList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, password, discipline, disciplineList);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", discipline='" + discipline + '\'' +
                ", disciplineList=" + disciplineList +
                '}';
    }
}
