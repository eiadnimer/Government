package com.eiad.jpafirstproject.government.sql;

import com.eiad.jpafirstproject.government.core.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqlPersonRepository implements PersonRepository {

    private final DatabaseInitializer databaseInitializer;

    public SqlPersonRepository(DatabaseInitializer databaseInitializer) {
        this.databaseInitializer = databaseInitializer;
    }

    @Override
    public Person create(Person person) {
        runInTransaction(connection -> {
            String query = "insert into person " +
                    "(idNumber_person,firstName,fatherName,grandFatherName,familyName,motherName,birthday,bloodType,status,creationDate) " +
                    "values (" + person.getIdNumber() + ", '" + person.getFullName().getName() + "', '" + person.getFullName().getFatherName() +
                    "', '" + person.getFullName().getGrandFatherName() + "', '" + person.getFullName().getFamilyName() + "', '" + person.getMotherName() +
                    "', '" + person.getBirthDay() + "', '" + person.getBloodType() + "', 'active', '" + LocalDate.now() + "')";
            execute(query, connection);
            for (String address : person.getAddresses()) {
                String addressQuery = "insert into addresses (address, person_idNumber) values ('" + address + "', " + person.getIdNumber() + ")";
                execute(addressQuery, connection);
            }

        });
        Connection connection = databaseInitializer.getConnectionFroProduction();
        String retrievePersonSql = "select * from person where idNumber_person =" + person.getIdNumber() + ";";
        return selectPerson(retrievePersonSql, connection);
    }

    @Override
    public Person update(long idNumber) {
        if (!isFound(idNumber)){
            throw new IllegalArgumentException("this person dose not exist in the system");
        }
        runInTransaction(connection -> {
            LocalDateTime beforeTenYears = LocalDateTime.now().minusYears(10);
            LocalDateTime currentDate = LocalDateTime.now();
            String formattedOldDate = formatLocalDateTime(beforeTenYears);
            String formattedCurrentDate = formatLocalDateTime(currentDate);
            execute("update person set creationDate = " + "'" + formattedOldDate + "' " + "  where idNumber_person =  " + idNumber + ";", connection);
            execute("update person set creationDate = " + "'" + formattedCurrentDate + "' " + "  where idNumber_person =  " + idNumber + ";", connection);
        });
        return findById(idNumber);
    }


    @Override
    public Person findById(long id) {
        if (!isFound(id)) {
            throw new IllegalArgumentException("this person dose not exist in the system");
        }
        Connection connection = null;
        try {
            connection = databaseInitializer.getConnectionFroProduction();
            return selectPerson("select * from person where idNumber_person = " + id + ";", connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Person> findAll() {
        return executeQueryForList("select * from person;");
    }

//    @Override
//    public Person changeStatus(long idNumber) {
//        if (!isFound(idNumber)) {
//            throw new IllegalArgumentException("this person dose not exist in the system");
//        }
//        runInTransaction(connection -> {
//            execute("update person set status = " + "'" + "INACTIVE" + "'" + " where idNumber_person =" + idNumber + ";", connection);
//
//        });
//        return findById(idNumber);
//    }

    @Override
    public long generateCounter() {
        int counter = 0;
        Connection connection = databaseInitializer.getConnectionFroProduction();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select count(*) as count from person;");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                counter = resultSet.getInt("count");
                String stringValue = String.valueOf(counter);
                return Long.parseLong(stringValue);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    @Override
    public void delete(long idNumber) {
        if (!isFound(idNumber)) {
            throw new IllegalArgumentException("this person dose not exist in the system");
        }
        runInTransaction(connection -> {
            execute("delete from addresses where person_idNumber = " + idNumber + " ;", connection);
            execute("delete from person where idNumber_person = " + idNumber + " ;", connection);
        });
    }

    @Override
    public void deleteAll() {
        runInTransaction(connection -> {
            execute("delete from addresses", connection);
            execute("delete from person", connection);
        });
    }

    private List<String> getAddresses(long id, Connection connection) {
        List<String> addresses = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from addresses where person_idNumber = " + id + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String address = resultSet.getString("address");
                addresses.add(address);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return addresses;
    }

    private String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy, h:mm:ss a");
        return localDateTime.format(formatter);
    }

    private boolean isFound(long id) {
        Connection connection = databaseInitializer.getConnectionFroProduction();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from person where idNumber_person = " + id + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    private Person selectPerson(String sql, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long idNumber = resultSet.getLong("idNumber_person");
                String firstName = resultSet.getString("firstName");
                String fatherName = resultSet.getString("fatherName");
                String grandFatherName = resultSet.getString("grandFatherName");
                String familyName = resultSet.getString("familyName");
                FullName fullName = new FullName(firstName, fatherName, grandFatherName, familyName);
                String motherName = resultSet.getString("motherName");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                List<String> addresses = this.getAddresses(idNumber, connection);
                String bloodType = resultSet.getString("bloodType");
                Status status = Status.valueOf(resultSet.getString("status").toUpperCase());
                LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                return new Person(fullName,
                        motherName
                        , birthday
                        , addresses
                        , bloodType
                        , idNumber
                        , status
                        , creationDate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private List<Person> executeQueryForList(String sql) {
        List<Person> persons = new ArrayList<>();
        Connection connection = databaseInitializer.getConnectionFroProduction();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long idNumber = resultSet.getLong("idNumber_person");
                String firstName = resultSet.getString("firstName");
                String fatherName = resultSet.getString("fatherName");
                String grandFatherName = resultSet.getString("grandFatherName");
                String familyName = resultSet.getString("familyName");
                FullName fullName = new FullName(firstName, fatherName, grandFatherName, familyName);
                String motherName = resultSet.getString("motherName");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                List<String> addresses = this.getAddresses(idNumber, connection);
                String bloodType = resultSet.getString("bloodType");
                Status status = Status.valueOf(resultSet.getString("status").toUpperCase());
                LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                Person person = new Person(fullName,
                        motherName
                        , birthday
                        , addresses
                        , bloodType
                        , idNumber
                        , status
                        , creationDate);
                persons.add(person);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    private void execute(String sql, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }

    private void runInTransaction(TransactionBusiness transactionBusiness) {
        Connection connection = null;
        try {
            connection = databaseInitializer.getConnectionFroProduction();
            connection.setAutoCommit(false);
            //
            transactionBusiness.run(connection);
            //
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    // Strategy pattern
    private interface TransactionBusiness {

        void run(Connection connection) throws SQLException;

    }
}
