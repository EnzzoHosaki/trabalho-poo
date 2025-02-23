package com.trabalhoPoo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gestao_atividades";
    private static final String USER = "root"; //  <<--  VERIFIQUE ESTE USUÁRIO
    private static final String PASSWORD = ""; //  <<--  VERIFIQUE ESTA SENHA

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado!", e);
        }
    }
}