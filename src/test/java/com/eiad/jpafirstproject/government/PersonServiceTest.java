package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PersonServiceTest {
    private final JavaPersonRepository javaPersonRepository = new JavaPersonRepository();
    private final List<String> addresses = new ArrayList<>();
    private final PersonService personService = new PersonService(javaPersonRepository);

    PersonServiceTest() {
        addresses.add("amman");
        addresses.add("irbid");
    }

    @Test
    public void the_person_must_be_the_same_person_after_register_to_system_and_must_have_an_idNumber() {
        FullName fullName = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person = new Person(fullName, "noha", LocalDate.parse("1992-06-24"), addresses, "A+");

        Person createdPerson = personService.create(person);

        Assertions.assertNotNull(createdPerson.getIdNumber());
        Assertions.assertEquals(createdPerson, javaPersonRepository.getCreatedPerson());
    }

    @Test
    public void you_can_not_add_person_that_already_exist_in_the_system() {
        FullName fullName = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person = new Person(fullName, "noha", LocalDate.parse("1992-06-24"), addresses, "A+");

        personService.create(person);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> javaPersonRepository.isNotFound(person));
    }

    @Test
    public void when_person_renew_his_card_the_creationDate_must_be_equal_to_the_current_date() {
        FullName fullName = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person = new Person(fullName, "noha", LocalDate.parse("1992-06-24"), addresses, "A+");

        Person personToCreate = personService.create(person);
        Person createdPerson = javaPersonRepository.update(personToCreate.getIdNumber());

        Assertions.assertEquals(createdPerson.getIdCreationDate(), LocalDate.now());
    }

    @Test
    public void when_person_ask_to_view_his_information_then_all_the_information_must_be_equal_to_his_exact_information() {
        FullName fullName = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person = new Person(fullName, "noha", LocalDate.parse("1992-06-24"), addresses, "A+");

        Person personToCreate = personService.create(person);
        javaPersonRepository.create(personToCreate);
        Person createdPerson = javaPersonRepository.findById(personToCreate.getIdNumber());

        Assertions.assertEquals(personToCreate.getFullName(), createdPerson.getFullName());
        Assertions.assertEquals(personToCreate.getAddresses(), createdPerson.getAddresses());
        Assertions.assertEquals(personToCreate.getIdCreationDate(), createdPerson.getIdCreationDate());
        Assertions.assertEquals(personToCreate.getBirthDay(), createdPerson.getBirthDay());
        Assertions.assertEquals(personToCreate.getBloodType(), createdPerson.getBloodType());
        Assertions.assertEquals(personToCreate.getStatus(), createdPerson.getStatus());
        Assertions.assertEquals(personToCreate.getMotherName(), createdPerson.getMotherName());
    }

    @Test
    public void must_find_all_the_persons_that_already_exist_in_the_system_when_you_ask_to_show_them() {
        FullName fullName1 = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person1 = new Person(fullName1, "noha", LocalDate.parse("1992-06-24"), addresses, "A+");
        FullName fullName2 = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person2 = new Person(fullName2, "noha", LocalDate.parse("1992-06-24"), addresses, "A+");

        personService.create(person1);
        personService.create(person2);

        Assertions.assertEquals(2,javaPersonRepository.persons.size());
    }

    @Test
    public void when_person_ask_to_change_status_for_other_person_must_be_equal_to_INACTIVE() {
        FullName fullName = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person = new Person(fullName, "noha", LocalDate.parse("1992-06-24"), addresses, "A+",1L,Status.ACTIVE,LocalDate.now());

        Person createdPerson = personService.create(person);
        createdPerson.setStatus(Status.INACTIVE);
        javaPersonRepository.update(createdPerson.getIdNumber());

        Assertions.assertEquals(createdPerson.getStatus(), Status.INACTIVE);
    }
    @Test
    public void the_person_must_be_not_exist_in_the_system_after_delete_his_account(){
        FullName fullName = new FullName("eiad", "nimer", "omar", "alswaidat");
        Person person = new Person(fullName, "noha", LocalDate.parse("1992-06-24"), addresses, "A+");

        Person createdPerson = personService.create(person);
        javaPersonRepository.delete(createdPerson.getIdNumber());

        Assertions.assertEquals(0,javaPersonRepository.persons.size());
    }
}

