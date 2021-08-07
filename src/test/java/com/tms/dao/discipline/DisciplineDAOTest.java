package com.tms.dao.discipline;

import com.tms.entity.Discipline;
import com.tms.service.discipline.DisciplineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DisciplineDAOTest {
    private final DisciplineService disciplineService = new DisciplineService();


    Discipline Anthropology;
    Discipline Astronomy;
    Discipline Bioinformatics;
    Discipline Economics;
    Discipline Engineering;
    Discipline History;
    Discipline Physics;
    Discipline Psychology;

    @Test
    @DisplayName("save into discipline DAO")
    void save() {
        disciplineService.save(Anthropology = new Discipline(0, "Anthropology"));
        disciplineService.save(Astronomy = new Discipline(0, "Astronomy"));
        disciplineService.save(Bioinformatics = new Discipline(0, "Bioinformatics"));
        disciplineService.save(History = new Discipline(0, "History"));
        disciplineService.save(Engineering = new Discipline(0, "Engineering"));
        disciplineService.save(Physics = new Discipline(0, "Physics"));
        disciplineService.save(Psychology = new Discipline(0, "Psychology"));
        disciplineService.save(Economics = new Discipline(0, "Economics"));
    }

    @Test
    @DisplayName("getting all discipline with teacher list")
    void getAll() {
        List<Discipline> disciplines = disciplineService.getAll();
        if (disciplines != null) {
            for (Discipline item : disciplines) {
                assertAll("item",
                        () -> assertNotEquals(0, item.getTeacherList()),
                        () -> assertFalse(item.getName().isEmpty()));
                break;
            }
        }
    }

    @Test
    @DisplayName("return all disciplines teacher by discipline ID")
    void getAllDisciplineTeachers() {
        int disciplineId = 3;
        disciplineService.getAllDisciplineTeachers(disciplineId);
    }


    @Test
    @DisplayName("getting discipline ID from DAO by name, return discipline ID")
    void getIdByName() {
        String actualDiscName = "Engineering";
        int id = disciplineService.getIdByName(actualDiscName);
        assertNotEquals(0, id);
    }

    @Test
    @DisplayName("getting discipline name by ID")
    void getById() {
        int id = 3;
        disciplineService.getById(id);
    }

    @Test
    @DisplayName("update discipline name by id")
    void updateName() {
        String newName = "newName";
        int id = 3;
        disciplineService.updateName(id, newName);
    }

    @Test
    @DisplayName(" delete discipline from table discipline and TD by ID")
    void deleteById() {
        int id = 3;
        disciplineService.deleteById(id);
    }

    @Test
    @DisplayName("checking if discipline exists in discipline DAO by name")
    void exists() {
        String actualDisciplineName = "Engineering";
        assertTrue(disciplineService.exists(actualDisciplineName));
    }
}