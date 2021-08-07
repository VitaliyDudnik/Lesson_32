package com.tms.dao.discipline;

import com.tms.Disciplinable;
import com.tms.Getable;
import com.tms.dao.connectionDB.ConnectionDAO;
import com.tms.entity.Discipline;
import com.tms.entity.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DisciplineDAO extends ConnectionDAO implements Disciplinable, Getable {

    @Override
    public void save(Discipline discipline) {
        try {
            if (discipline != null) {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection.prepareStatement("insert into discipline values(default,?)");
                preparedStatement.setString(1, discipline.getName());
                preparedStatement.execute();
            }
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
    public List<Discipline> getAll() {
        List<Discipline> disciplineList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from discipline");
            while (resultSet.next()) {
                int disciplineId = resultSet.getInt(1);
                String disciplineName = resultSet.getString(2);
                List teacherList = getAllDisciplineTeachers(disciplineId);
                Discipline discipline = new Discipline(disciplineId, disciplineName, teacherList);
                disciplineList.add(discipline);
            }
            return new ArrayList<>(disciplineList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List getAllDisciplineTeachers(int disciplineId) {
        List teacherList = new ArrayList<>();
        try {
            String sql = "SELECT teacher.id, teacher_name FROM teacher INNER JOIN td ON teacher.id = td.teacher_id WHERE discipline_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, disciplineId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int teacherId = resultSet.getInt(1);
                String teacherName = resultSet.getString(2);
                Teacher teacher = new Teacher(teacherId, teacherName);
                teacherList.add(teacher);
            }
            return new ArrayList<>(teacherList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getIdByName(String name) {
        try {
            if (name != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT discipline_id FROM discipline WHERE discipline_name = ?");
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT discipline_name FROM discipline WHERE discipline_id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateName(int id, String newName) {
        try {
            if (newName != null) {
                String sql = "UPDATE discipline SET discipline_name = ? WHERE discipline_id = ?";
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM discipline WHERE discipline_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM td WHERE discipline_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();
            preparedStatement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean exists(String name) {
        try {
            if (name != null) {
                String sql = "SELECT COUNT(discipline_name) FROM discipline WHERE discipline_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                int result = resultSet.getInt(1);
                resultSet.next();

                if (result > 0) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
