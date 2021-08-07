package com.tms.service.discipline;

import com.tms.Disciplinable;
import com.tms.Getable;
import com.tms.dao.discipline.DisciplineDAO;
import com.tms.entity.Discipline;

import java.util.List;

public class DisciplineService implements Disciplinable, Getable {
    private final DisciplineDAO disciplineDAO = new DisciplineDAO();

    public boolean addDisciplineDAO(Discipline discipline) {
        if (disciplineDAO.exists(discipline.getName())) {
            return false;
        } else {
            disciplineDAO.save(discipline);
            return true;
        }
    }

    @Override
    public void save(Discipline discipline) {
        disciplineDAO.save(discipline);
    }

    @Override
    public List<Discipline> getAll() {
        return disciplineDAO.getAll();
    }

    public List getAllDisciplineTeachers(int disciplineId) {
        return disciplineDAO.getAllDisciplineTeachers(disciplineId);
    }

    @Override
    public int getIdByName(String name) {
        return disciplineDAO.getIdByName(name);
    }

    @Override
    public String getById(int id) {
        return disciplineDAO.getById(id);
    }

    @Override
    public void updateName(int id, String newName) {
        disciplineDAO.updateName(id, newName);
    }

    @Override
    public void deleteById(int id) {
        disciplineDAO.deleteById(id);

    }

    @Override
    public boolean exists(String name) {
        return disciplineDAO.exists(name);
    }
}
