package com.tms;

import com.tms.entity.Teacher;

import java.util.List;

public interface Employee {
    void save(Teacher teacher);

    void addTeacherIdDiscId(Teacher teacher, String additionalDiscipline);

    boolean exists(String username);

    Teacher findByUsername(String username);

    Teacher getByName(String name);

    List<String> getAllTeacherDisc(int teacherId);

    List<Teacher> getAll();

    Teacher getById(int id);
}
