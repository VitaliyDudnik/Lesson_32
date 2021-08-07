package com.tms.service.teacher;

import com.tms.Employee;
import com.tms.Getable;
import com.tms.dao.teacher.TeacherDAO;
import com.tms.entity.Teacher;

import java.util.List;

public class TeacherService implements Employee, Getable {
    private final TeacherDAO teacherDAO = new TeacherDAO();

    @Override
    public void save(Teacher teacher) {
        teacherDAO.save(teacher);
    }

    public boolean addTeacherDAO(Teacher teacher) {

        if (teacherDAO.exists(teacher.getUsername())) {
            return false;
        } else {
            teacherDAO.save(teacher);
            return true;
        }
    }

    @Override
    public void addTeacherIdDiscId(Teacher teacher, String discipline) {
        teacherDAO.addTeacherIdDiscId(teacher, discipline);
    }

    @Override
    public void updateName(int id, String newName) {
        teacherDAO.updateName(id, newName);
    }

    @Override
    public void deleteById(int id) {
        teacherDAO.deleteById(id);
    }

    @Override
    public boolean exists(String username) {
        return teacherDAO.exists(username);
    }

    @Override
    public Teacher findByUsername(String username) {
        return teacherDAO.findByUsername(username);
    }

    @Override
    public Teacher getByName(String name) {
        return teacherDAO.getByName(name);
    }

    @Override
    public List<String> getAllTeacherDisc(int teacherId) {
        return teacherDAO.getAllTeacherDisc(teacherId);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherDAO.getAll();
    }

    @Override
    public Teacher getById(int teacherId) {
        return teacherDAO.getById(teacherId);
    }
}
