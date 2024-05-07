package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.core.FullName;
import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.Status;
import com.eiad.jpafirstproject.government.springdata.SpringDataPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"test","spring-data"})
public class SpringDataPersonRepositoryTest {
    private final FullName fullName1 = new FullName("eiad", "nimer", "omar", "alswaidat");
    private final FullName fullName2 = new FullName("rafa", "jamal", "omar", "albarouky");
    private Person person1;
    private Person person2;
    @Autowired
    private SpringDataPersonRepository personRepository;

    @BeforeEach
    void setUp() {
        person1 = new Person(fullName1, "noha", LocalDate.parse("1992-06-24"),
                List.of("amman", "irbid"), "A+", 1L, Status.ACTIVE, LocalDate.now());
        person2 = new Person(fullName2, "mona", LocalDate.parse("1992-11-30"),
                List.of("amman", "irbid"), "A+",2L, Status.ACTIVE, LocalDate.now());
        personRepository.deleteAll();
    }

    @Test
    public void create(){
        Person createdPerson = personRepository.create(person1);

        Assertions.assertNotNull(createdPerson.getIdNumber());
    }
    @Test
    public void find_byId(){
        Person createdPerson = personRepository.create(person1);

        Person byId = personRepository.findById(person1.getIdNumber());

        Assertions.assertEquals(createdPerson,byId);
    }

    @Test
    public void findAll(){
        personRepository.create(person1);
        personRepository.create(person2);

        List<Person> persons = personRepository.findAll();

        Assertions.assertEquals(2,persons.size());
    }

    @Test
    public void deleteAll(){
        personRepository.create(person1);
        personRepository.create(person2);

        personRepository.deleteAll();
        List<Person> persons = personRepository.findAll();

        Assertions.assertEquals(0,persons.size());
    }
    @Test
    public void delete_person(){
        personRepository.create(person1);
        personRepository.create(person2);

        personRepository.delete(person1.getIdNumber());
        List<Person> persons = personRepository.findAll();

        Assertions.assertEquals(1, persons.size());
    }

    @Test
    public void renewal(){
        person1.setIdCreationDate(LocalDate.parse("2022-01-01"));
        personRepository.create(person1);

        Person updatedPerson = personRepository.update(person1.getIdNumber());

        Assertions.assertEquals(LocalDate.now(),updatedPerson.getIdCreationDate());
    }

    @Test
    public void status(){
        person1.setStatus(Status.INACTIVE);
        personRepository.create(person1);

        Person updatedPerson = personRepository.update(person1.getIdNumber());

        Assertions.assertEquals(LocalDate.now(),updatedPerson.getIdCreationDate());
    }
}
