package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.core.FullName;
import com.eiad.jpafirstproject.government.jpa.JpaPersonRepository;
import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"test", "jpa"})
public class JpaPersonRepositoryTest {
    private final FullName fullName1 = new FullName("eiad", "nimer", "omar", "alswaidat");
    private final FullName fullName2 = new FullName("rafa", "jamal", "omar", "albarouky");
    private Person person1;
    private Person person2;
    @Autowired
    private JpaPersonRepository personRepository;

    @BeforeEach
    void setUp() {
        person1 = new Person(fullName1, "noha", LocalDate.parse("1992-06-24"),
                List.of("amman", "irbid"), "A+", 1L, Status.ACTIVE, LocalDate.now());
        person2 = new Person(fullName2, "mona", LocalDate.parse("1992-11-30"),
                List.of("amman", "irbid"), "A+", 2L, Status.ACTIVE, LocalDate.now());
        personRepository.deleteAll();
    }

    @Test
    public void create_person() {
        Person createdPerson = personRepository.create(person1);
        Assertions.assertNotNull(createdPerson.getIdNumber());
    }

    @Test
    public void find_by_id() {
        Person createdPerson = personRepository.create(person2);

        Person byId = personRepository.findById(createdPerson.getIdNumber());

        Assertions.assertEquals(byId, createdPerson);
    }

    @Test
    public void find_all() {
        personRepository.create(person1);
        personRepository.create(person2);

        List<Person> allPersons = personRepository.findAll();

        Assertions.assertEquals(2, allPersons.size());
    }

    @Test
    public void renew_id_card() {
        person1.setIdCreationDate(LocalDate.parse("2022-06-02"));
        Person createdPerson = personRepository.create(person1);
        Person afterRenewal = personRepository.update(createdPerson.getIdNumber());

        Assertions.assertEquals(LocalDate.now(), afterRenewal.getIdCreationDate());
    }

    @Test
    public void delete_all() {
        personRepository.create(person1);
        personRepository.create(person2);

        personRepository.deleteAll();

        Assertions.assertEquals(0, personRepository.findAll().size());
    }

    @Test
    public void delete_person() {
        personRepository.create(person1);

        personRepository.delete(person1.getIdNumber());
        List<Person> persons = personRepository.findAll();

        Assertions.assertEquals(0, persons.size());
    }

    @Test
    public void change_status() {
        person1.setStatus(Status.INACTIVE);
        personRepository.create(person1);

        personRepository.update(person1.getIdNumber());

        Assertions.assertEquals(Status.INACTIVE, person1.getStatus());
    }

    @Test
    public void generate_idNumber() {
        long counter = personRepository.generateCounter();

        Assertions.assertEquals(1, counter);
    }
}
