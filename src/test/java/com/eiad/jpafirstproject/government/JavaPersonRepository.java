package com.eiad.jpafirstproject.government;


import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.PersonRepository;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter
public class JavaPersonRepository implements PersonRepository {
    private Person createdPerson;
    public final List<Person> persons = new ArrayList<>();

    @Override
    public Person findById(long inNumber) {
        return this.createdPerson;
    }

    @Override
    public Person create(Person person) {
        this.createdPerson = person;
        persons.add(person);
        return person;
    }

    @Override
    public void delete(long inNumber) {
        persons.removeIf(person -> person.getIdNumber() == inNumber);
    }

    @Override
    public void deleteAll() {
        persons.remove(createdPerson);
    }

    @Override
    public List<Person> findAll() {
        return persons;
    }

    @Override
    public Person update(long idNumber) {
        return createdPerson;
    }

    @Override
    public long generateCounter() {
        return persons.size();
    }

    public void isNotFound(Person person) {
        if (!persons.contains(person)) {
            throw new IllegalArgumentException();
        }
    }
}
