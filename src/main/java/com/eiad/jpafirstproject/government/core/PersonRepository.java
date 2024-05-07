package com.eiad.jpafirstproject.government.core;

import java.util.List;

public interface PersonRepository {

    Person findById(long inNumber);

    Person create(Person person);

    void delete(long inNumber);
    void deleteAll();

    List<Person> findAll();

    Person update(long idNumber);

    long generateCounter();
}
