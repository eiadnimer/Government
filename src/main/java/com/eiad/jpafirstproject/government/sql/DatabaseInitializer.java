package com.eiad.jpafirstproject.government.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseInitializer {

    private final String url;
    private final String username;
    private final String password;

    public DatabaseInitializer(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void setup() {
        Connection connection = null;
        try {
            connection = getConnectionFroProduction();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("create schema if not exists government");
            statement.execute("create table if not exists person(\n" +
                    "    idNumber_person bigint primary key,\n" +
                    "    firstName varchar (50) not null,\n" +
                    "    fatherName varchar (50) not  null,\n" +
                    "    grandFatherName varchar (50) not  null,\n" +
                    "    familyName varchar (50) not  null,\n" +
                    "    motherName varchar (50) not null,\n" +
                    "    birthDay date not null ,\n" +
                    "    bloodType varchar (50) not null ,\n" +
                    "    status varchar (50) not null ,\n" +
                    "    creationDate date not null\n" +
                    ")");
            statement.execute("create table if not exists addresses(\n" +
                    "    id serial primary key,\n" +
                    "    address varchar(50) not null,\n" +
                    "    person_idNumber bigint,\n" +
                    "      constraint fk_person\n" +
                    "      foreign key(person_idNumber) \n" +
                    "\t    references person(idNumber_person)\n" +
                    ")");
            connection.commit();
            statement.close();
            connection.close();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public Connection getConnectionFroProduction() {
        String driverClassName = "org.postgresql.Driver";
        try {

            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(
                    url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
