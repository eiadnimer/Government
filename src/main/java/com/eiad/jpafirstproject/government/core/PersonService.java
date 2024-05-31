package com.eiad.jpafirstproject.government.core;
import java.time.LocalDate;
import java.util.List;

public class PersonService {
    private final PersonRepository personRepository;
    private final IdGenerator idGenerator;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.idGenerator = new IdGenerator(personRepository);
    }

    public Person create(Person person) {
        Person readyForCreation = person.forCreation(generateId());
        return personRepository.create(readyForCreation);
    }

    private Long generateId() {
        long sixDigitNumber = personRepository.generateCounter();
        return idGenerator.generate(sixDigitNumber);
    }

    public Person renew(long idNumber) {
        Person byId = personRepository.findById(idNumber);
        byId.setIdCreationDate(LocalDate.now());
        return personRepository.update(byId.getIdNumber());
    }

    public Person find(long idNumber) {
        return personRepository.findById(idNumber);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person changeStatus(long idNumber) {
        Person byId = personRepository.findById(idNumber);
        byId.setStatus(Status.INACTIVE);
        personRepository.update(byId.getIdNumber());
        return byId;
    }

    public void deleteAccount(long idNumber) {
        personRepository.delete(idNumber);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
