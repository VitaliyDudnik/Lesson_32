package com.tms;

import com.tms.entity.Discipline;

import java.util.List;

public interface Disciplinable {
    void save(Discipline discipline);

    List<Discipline> getAll();

    int getIdByName(String name);

    String getById(int id);
}
