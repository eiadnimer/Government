package com.eiad.jpafirstproject.government.api;

import com.eiad.jpafirstproject.government.core.FullName;
import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {

    private final PersonService personService;
    private final PersonMapper personMapper;

    public Controller(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("persons")
    public List<PersonDTO> getPersons() {
        return personService.findAll().stream()
                .map(personMapper::convertToDTO)
                .toList();
    }

    @PostMapping("cardId")
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        FullName fullName = new FullName(personDTO.getFirstName(),
                personDTO.getFatherName(),
                personDTO.getGrandFatherName(),
                personDTO.getFamilyName());
        Person convertedPerson = new Person(fullName,
                personDTO.getMotherName(),
                personDTO.getBirthday(),
                convertToList(personDTO.getAddresses()),
                personDTO.getBloodType());
        Person createdPerson = personService.create(convertedPerson);
        return personMapper.convertToDTO(createdPerson);
    }

    @GetMapping("find")
    public PersonDTO getPerson(@RequestParam long idNumber) {
        Person person = personService.find(idNumber);
        return personMapper.convertToDTO(person);
    }

    @PostMapping("renewal")
    public PersonDTO renewalIdCard(@RequestBody IdNumberDTO idNumber) {
        Person person = personService.renew(idNumber.getIdNumber());
        return personMapper.convertToDTO(person);
    }

    @PostMapping("status")
    public PersonDTO changeStatus(@RequestBody IdNumberDTO idNumber) {
        Person updatedPerson = personService.changeStatus(idNumber.getIdNumber());
        return personMapper.convertToDTO(updatedPerson);
    }

    @PostMapping("delete")
    public Response deletePerson(@RequestBody IdNumberDTO idNumberDTO) {
        personService.deleteAccount(idNumberDTO.getIdNumber());
        Response response = new Response();
        response.setMessage("account was deleted");
        return response;
    }

    @PostMapping("deleteAll")
    public Response deleteAll() {
        personService.deleteAll();
        Response response = new Response();
        response.setMessage("all accounts was deleted");
        return response;
    }

    private List<String> convertToList(String string) {
        String[] strings = string.split(",");
        return Arrays.asList(strings);
    }

}
