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
            statement.execute("""
                    create table if not exists person(
                        idNumber_person bigint primary key,
                        firstName varchar (50) not null,
                        fatherName varchar (50) not  null,
                        grandFatherName varchar (50) not  null,
                        familyName varchar (50) not  null,
                        motherName varchar (50) not null,
                        birthDay date not null ,
                        bloodType varchar (50) not null ,
                        status varchar (50) not null ,
                        creationDate date not null
                    )""");
            statement.execute("""
                    create table if not exists addresses(
                        id serial primary key,
                        address varchar(50) not null,
                        person_idNumber bigint,
                          constraint fk_person
                          foreign key(person_idNumber)\s
                    \t    references person(idNumber_person)
                    )""");
            connection.commit();
            statement.close();
            connection.close();
        } catch (Exception e) {
            try {
                assert connection != null;
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
