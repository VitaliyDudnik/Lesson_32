package com.tms.entity;

import java.util.List;
import java.util.Objects;

public class Discipline {
    private int id;
    private String name;
    private List teacherList;

    public Discipline(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Discipline(int id, String name, List teacherList) {
        this.id = id;
        this.name = name;
        this.teacherList = teacherList;
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

    public List getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return id == that.id && Objects.equals(name, that.name)  && Objects.equals(teacherList, that.teacherList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacherList);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherList=" + teacherList +
                '}';
    }
}
