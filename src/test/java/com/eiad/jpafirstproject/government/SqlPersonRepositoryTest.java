package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.core.*;
import com.eiad.jpafirstproject.government.sql.DatabaseInitializer;
import com.eiad.jpafirstproject.government.sql.SqlPersonRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.List;

public class SqlPersonRepositoryTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    private final FullName fullName1 = new FullName("eiad", "nimer", "omar", "alswaidat");
    private final FullName fullName2 = new FullName("rafa", "jamal", "omar", "albarouky");
    private Person person1;
    private Person person2;
    private SqlPersonRepository sqlPersonRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        person1 = new Person(fullName1, "noha", LocalDate.parse("1992-06-24"), List.of("amman", "irbid"),
                "A+", 1L, Status.ACTIVE, LocalDate.now());
        person2 = new Person(fullName2, "mona", LocalDate.parse("1992-11-30"), List.of("amman", "irbid"),
                "A+", 2L, Status.ACTIVE, LocalDate.now());
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        databaseInitializer.setup();
        sqlPersonRepository = new SqlPersonRepository(databaseInitializer);
        sqlPersonRepository.deleteAll();
    }

    @Test
    public void should_get_persons() {
        sqlPersonRepository.create(person1);
        sqlPersonRepository.create(person2);

        List<Person> persons = sqlPersonRepository.findAll();

        Assertions.assertEquals(2, persons.size());
    }

    @Test
    public void you_make_sure_that_the_person_you_have_added_has_been_saved_correctly() {
        sqlPersonRepository.create(person1);

        List<Person> personsCreated = sqlPersonRepository.findAll();

        Assertions.assertEquals(1, personsCreated.size());
    }

    @Test
    public void when_person_renew_his_idCard_the_creationDate_must_be_equal_to_currentDate() {
        sqlPersonRepository.create(person1);
        Person createdPerson = sqlPersonRepository.update(person1.getIdNumber());

        Assertions.assertEquals(LocalDate.now(), createdPerson.getIdCreationDate());
    }

//    @Test
//    public void if_you_change_status_in_the_system_must_status_equal_to_inActive() {
//        Person createdPerson = sqlPersonRepository.create(person1);
//        createdPerson.setStatus(Status.INACTIVE);
//
//        Person afterChange = sqlPersonRepository.update(createdPerson.getIdNumber());
//
//        Assertions.assertEquals(Status.INACTIVE, afterChange.getStatus());
//    }

    @Test
    public void when_first_person_created_then_his_idNumber_must_end_with_one() {
        Person createdPerson1 = sqlPersonRepository.create(person1);
        Person createdPerson2 = sqlPersonRepository.create(person2);

        int lastNumber1 = convertToInt(createdPerson1.getIdNumber().toString());
        int lastNumber2 = convertToInt(createdPerson2.getIdNumber().toString());

        Assertions.assertEquals(lastNumber1, 1);
        Assertions.assertEquals(lastNumber2, 2);
    }

    @Test
    public void after_delete_an_account_for_a_person_then_must_not_be_register_in_the_system_anymore() {
        Person createdPerson = sqlPersonRepository.create(person1);

        sqlPersonRepository.delete(createdPerson.getIdNumber());

        Assertions.assertEquals(0, sqlPersonRepository.findAll().size());
    }

    @Test
    public void you_can_not_delete_a_person_from_the_system_before_he_register_to_the_system() {
        Person createdPerson = sqlPersonRepository.create(person1);

        sqlPersonRepository.delete(createdPerson.getIdNumber());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> sqlPersonRepository.delete(createdPerson.getIdNumber()));
    }

    private int convertToInt(String number) {
        char lastChar = number.charAt(number.length() - 1);
        return Character.getNumericValue(lastChar);
    }
}
