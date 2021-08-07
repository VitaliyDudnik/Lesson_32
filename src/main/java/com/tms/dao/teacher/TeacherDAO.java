package com.tms.dao.teacher;

import com.tms.Employee;
import com.tms.Getable;
import com.tms.dao.connectionDB.ConnectionDAO;
import com.tms.dao.discipline.DisciplineDAO;
import com.tms.entity.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAO extends ConnectionDAO implements Employee, Getable {
    private final DisciplineDAO disciplineDAO = new DisciplineDAO();

    @Override
    public void save(Teacher teacher) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Teacher values(default,?,?,?) returning id");
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getUsername());
            preparedStatement.setString(3, teacher.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int teacherId = resultSet.getInt(1);
            connection.commit();

            //getting discipline ID from discipline DAO by teacher's main subject
            String teacherDiscipline = teacher.getDiscipline();
            int disciplineId = disciplineDAO.getIdByName(teacherDiscipline);

            PreparedStatement preparedStatement2 = connection.prepareStatement("insert into td values(?,?)");
            preparedStatement2.setInt(1, teacherId);
            preparedStatement2.setInt(2, disciplineId);
            preparedStatement2.execute();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void addTeacherIdDiscId(Teacher teacher, String discipline) {
        try {
            if (teacher != null & !discipline.trim().isEmpty()) {

                //getting discipline id from discipline DAO
                int disciplineId = disciplineDAO.getIdByName(discipline);
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO td VALUES(?,?)");
                preparedStatement.setInt(1, teacher.getId());
                preparedStatement.setInt(2, disciplineId);
                preparedStatement.execute();
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateName(int id, String newName) {
        try {
            if (newName != null) {
                String sql = "UPDATE teacher SET teacher_name = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newName);
                preparedStatement.setInt(2, id);
                preparedStatement.execute();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM teacher WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM td WHERE teacher_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();
            preparedStatement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exists(String username) {
        try {
            if (username != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(username) FROM teacher WHERE teacher_username = ?");
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int teacherUsername = resultSet.getInt(1);
                if (teacherUsername > 0) return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Teacher findByUsername(String username) {
        List disciplineList = new ArrayList<>();
        String teacherName = null, teacherUsername = null, password = null;
        int id = 0;
        try {
            if (username != null) {
                String sql = "SELECT id, teacher_name, teacher_username, password, td.discipline_id " +
                        "FROM teacher " +
                        "INNER JOIN td ON teacher.id = td.teacher_id " +
                        "WHERE teacher_username = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                    teacherName = resultSet.getString(2);
                    teacherUsername = resultSet.getString(3);
                    password = resultSet.getString(4);
                    String disciplineName = disciplineDAO.getById(resultSet.getInt(5));
                    disciplineList.add(disciplineName);
                }
                return new Teacher(id, teacherName, teacherUsername, password, disciplineList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Teacher getByName(String name) {
        List disciplineList = new ArrayList<>();
        String teacherName = null, teacherUsername = null, password = null;
        int id = 0;
        try {
            if (name != null) {
                String sql = "SELECT id, teacher_name, teacher_username, password, td.discipline_id " +
                        "FROM teacher " +
                        "INNER JOIN td ON teacher.id = td.teacher_id " +
                        "WHERE teacher_name = ? ";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                    teacherName = resultSet.getString(2);
                    teacherUsername = resultSet.getString(3);
                    password = resultSet.getString(4);
                    String disciplineName = disciplineDAO.getById(resultSet.getInt(5));
                    disciplineList.add(disciplineName);
                }
                return new Teacher(id, teacherName, teacherUsername, password, disciplineList);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<String> getAllTeacherDisc(int teacherId) {
        List<String> disciplineList = new ArrayList<>();
        try {

            String sql = "SELECT td.discipline_id " +
                    "FROM td " +
                    "INNER JOIN discipline ON td.discipline_id = discipline.discipline_id " +
                    "WHERE td.teacher_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String discName = disciplineDAO.getById(resultSet.getInt(1));
                disciplineList.add(discName);
            }
            return new ArrayList<>(disciplineList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Teacher> getAll() {
        List<Teacher> teacherList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM teacher";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String teacherName = resultSet.getString(2);
                String teacherUsername = resultSet.getString(3);
                String password = resultSet.getString(4);
                List<String> disciplineNames = getAllTeacherDisc(id);

                Teacher teacher = new Teacher(id, teacherName, teacherUsername, password, disciplineNames);
                teacherList.add(teacher);
            }
            return new ArrayList<>(teacherList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Teacher getById(int teacherId) {
        List disciplineNames = new ArrayList<>();
        String teacherName = null, teacherUsername = null, password = null;
        int id = 0;
        try {
            String sql = "SELECT id, teacher_name, teacher_username, password, td.discipline_id " +
                    "FROM teacher " +
                    "INNER JOIN td ON teacher.id = td.teacher_id " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(1);
                teacherName = resultSet.getString(2);
                teacherUsername = resultSet.getString(3);
                password = resultSet.getString(4);
                disciplineNames = getAllTeacherDisc(teacherId);
            }
            return new Teacher(id, teacherName, teacherUsername, password, disciplineNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
