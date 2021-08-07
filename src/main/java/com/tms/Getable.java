package com.tms;

public interface Getable {

    void updateName(int id, String newName);

    void deleteById(int id);

    boolean exists(String name);
}
