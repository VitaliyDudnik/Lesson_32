package com.tms.dao.teacher;

import com.tms.entity.Teacher;
import com.tms.service.teacher.TeacherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDAOTest {
    private final TeacherService teacherService = new TeacherService();
    private Teacher teacher;

    @Test
    @DisplayName("save() into teacher DAO")
    void save() {
        teacherService.addTeacherDAO(teacher = new Teacher(0, "Simon Simon", "test1", "test", "Engineering"));
        teacherService.addTeacherDAO(teacher = new Teacher(0, "Kate Jenkins", "test1234", "test", "Bioinformatics"));
        teacherService.addTeacherDAO(teacher = new Teacher(0, "Jack Button", "test123", "test", "Physics"));
        teacherService.addTeacherDAO(teacher = new Teacher(0, "Alex Jonson", "test234", "test", "Anthropology"));
        teacherService.addTeacherDAO(teacher = new Teacher(0, "Garry White", "test2234", "test", "Economics"));

    }

    @Test
    @DisplayName("adding values into TD table in teacher DAO ")
    void addTeacherIdDiscId() {
        String discipline = "discipline";
        getByName();
        teacherService.addTeacherIdDiscId(teacher, discipline);
    }

    @Test
    @DisplayName("update teacher name in DAO")
    void updateName() {
        int id = 38;
        String newName = "newName";
        teacherService.updateName(id, newName);
    }

    @Test
    @DisplayName("delete from table Teacher and TD table by teacher ID")
    void deleteById() {
        int id = 35;
        teacherService.deleteById(id);
    }

    @Test
    @DisplayName("checking if available user in DAO by username")
    void exists() {
        String actualUsername = "Username";
        assertTrue(teacherService.exists(actualUsername));
    }

    @Test
    @DisplayName("find teacher by username method should return teacher with his discipline list")
    void findByUsername() {
        String username = "Username1234";
        String secondUsername = "Username12345";
        teacher = teacherService.findByUsername(username);
        assertAll("teacher",
                () -> assertEquals(username, teacher.getUsername()),
                () -> assertFalse(teacher.getUsername().equalsIgnoreCase(secondUsername)),
                () -> assertFalse(teacher.getDisciplineList().isEmpty()),
                () -> assertNotEquals(0, teacher.getDisciplineList().size()));
    }

    @Test
    @DisplayName("getting teacher by name, method should return teacher with his discipline list")
    void getByName() {
        String actualTeacherName = "Garry White";
        teacher = teacherService.getByName(actualTeacherName);
        assertAll("teacher",
                () -> assertEquals(actualTeacherName, teacher.getUsername()),
                () -> assertFalse(teacher.getDisciplineList().isEmpty()),
                () -> assertNotEquals(0, teacher.getDisciplineList().size()));
    }

    @Test
    @DisplayName("Should return teacher list and they discipline list")
    void getAll() {
        teacherService.getAll();
    }

    @Test
    @DisplayName("getting all teachers discipline by teacher ID")
    void getAllTeacherDiscipline() {
        int teacherId = 4;
        List disciplineList = teacherService.getAllTeacherDisc(teacherId);
        assertNotEquals(0, disciplineList.size());
    }

    @Test
    @DisplayName("getting teacher, teacher discipline list by teacher ID")
    void getById() {
        int teacherId = 35;
        String name = "Garry White";
        teacher = teacherService.getById(teacherId);
        assertAll("teacher",
                () -> assertEquals(name, teacher.getName()),
                () -> assertNotNull(teacher.getDisciplineList()),
                () -> assertNotEquals(0, teacher.getDisciplineList().size()));
    }
}