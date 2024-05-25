package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.api.*;
import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.Status;
import com.eiad.jpafirstproject.government.sql.SqlPersonRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private SqlPersonRepository sqlPersonRepository;
    @Autowired
    private PersonMapper personMapper;

    private PersonDTO personDTO1;
    private PersonDTO personDTO2;

    @BeforeEach
    void setUp() {
        personDTO1 = new PersonDTO("eiad", "nimer",
                "omar", "alswaidat", "noha",
                LocalDate.parse("1992-06-24"), "amman,irbid", "A+");
        personDTO2 = new PersonDTO("rafat", "jamal",
                "rami", "albarouky", "mona",
                LocalDate.parse("1992-11-30"), "amman,irbid", "A+");
        sqlPersonRepository.deleteAll();
    }

    @Test
    public void get_persons() {
        testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO1, PersonDTO.class);
        testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO2, PersonDTO.class);

        ResponseEntity<PersonDTO[]> listOfPersons = testRestTemplate.getForEntity("http://localhost:" + port + "/persons", PersonDTO[].class);

        Assertions.assertEquals(2, listOfPersons.getBody().length);
    }

    @Test
    public void when_person_created_must_have_idNumber() {
        ResponseEntity<PersonDTO> response = testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO1, PersonDTO.class);

        Assertions.assertNotNull(response.getBody().getIdNumber());
    }

    @Test
    public void when_create_new_person_must_return_new_person_with_exact_information() {
        ResponseEntity<PersonDTO> response = testRestTemplate.postForEntity("http://localhost:" + port + "/cardId",
                personDTO1, PersonDTO.class);

        PersonDTO person = response.getBody();

        Assertions.assertEquals(personDTO1.getFirstName(), person.getFirstName());
        Assertions.assertEquals(personDTO1.getFatherName(), person.getFatherName());
        Assertions.assertEquals(personDTO1.getGrandFatherName(), person.getGrandFatherName());
        Assertions.assertEquals(personDTO1.getFamilyName(), person.getFamilyName());
        Assertions.assertEquals(personDTO1.getMotherName(), person.getMotherName());
        Assertions.assertEquals(personDTO1.getBloodType(), person.getBloodType());
        Assertions.assertEquals(personDTO1.getBirthday(), person.getBirthday());
    }

    @Test
    public void when_user_search_for_person_must_return_the_exact_person() {
        ResponseEntity<PersonDTO> body = testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO1, PersonDTO.class);
        Person createdPerson = personMapper.convertToPerson(body.getBody());

        ResponseEntity<PersonDTO> response = testRestTemplate.getForEntity("http://localhost:" +
                port + "/find?idNumber=" + createdPerson.getIdNumber() + "", PersonDTO.class);
        Person convertedPerson = personMapper.convertToPerson(response.getBody());

        Assertions.assertEquals(createdPerson, convertedPerson);
    }

    @Test
    public void when_person_renew_his_idCard_must_the_creationDate_equal_to_current_date() {
        ResponseEntity<PersonDTO> createdPerson = testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO1, PersonDTO.class);
        IdNumberDTO idNumberDTO = new IdNumberDTO(createdPerson.getBody().getIdNumber());
        createdPerson.getBody().setCreationDate(LocalDate.parse("2022-01-01"));

        ResponseEntity<PersonDTO> afterRenewal = testRestTemplate.postForEntity("http://localhost:" + port + "/renewal", idNumberDTO.getIdNumber(), PersonDTO.class);

        Assertions.assertEquals(LocalDate.now(), afterRenewal.getBody().getCreationDate());
    }

    @Test
    public void after_change_status_must_be_equal_to_InActive() {
        ResponseEntity<PersonDTO> createdPerson = testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO1, PersonDTO.class);
        IdNumberDTO idNumberDTO = new IdNumberDTO(createdPerson.getBody().getIdNumber());

        ResponseEntity<PersonDTO> personUpdatedStatus = testRestTemplate.postForEntity("http://localhost:" + port + "/status",
                idNumberDTO.getIdNumber(), PersonDTO.class);

        Assertions.assertEquals(Status.INACTIVE.toString(), personUpdatedStatus.getBody().getStatus());
    }

    @Test
    public void after_delete_person_must_be_not_register_in_the_system() {
        ResponseEntity<PersonDTO> createdPerson = testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO1, PersonDTO.class);
        testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO2, PersonDTO.class);
        IdNumberDTO idNumberDTO = new IdNumberDTO(createdPerson.getBody().getIdNumber());

        ResponseEntity<Response> response = testRestTemplate.postForEntity("http://localhost:" + port + "/delete", idNumberDTO.getIdNumber(), Response.class);
        ResponseEntity<PersonDTO[]> listOfPersons = testRestTemplate.getForEntity("http://localhost:" + port + "/persons", PersonDTO[].class);

        Assertions.assertEquals("account was deleted", response.getBody().getMessage());
        Assertions.assertEquals(1, listOfPersons.getBody().length);
    }

    @Test
    public void after_delete_all_accounts_must_return_zero() {
        testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO1, PersonDTO.class);
        testRestTemplate.postForEntity("http://localhost:" + port + "/cardId", personDTO2, PersonDTO.class);

        ResponseEntity<Response> response = testRestTemplate.postForEntity("http://localhost:" + port + "/deleteAll", null, Response.class);
        ResponseEntity<PersonDTO[]> listOfPersons = testRestTemplate.getForEntity("http://localhost:" + port + "/persons", PersonDTO[].class);

        Assertions.assertEquals("all accounts was deleted", response.getBody().getMessage());
        Assertions.assertEquals(0, listOfPersons.getBody().length);
    }

}