package com.tms.dao.connectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAO {
    protected Connection connection;

    protected ConnectionDAO() {
        try {
            ConnectionParameterDAO in = new ConnectionParameterDAO();
            connection = DriverManager.getConnection(in.getUrl(), in.getUser(), in.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
