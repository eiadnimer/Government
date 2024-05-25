package com.eiad.jpafirstproject.government.api;


import com.eiad.jpafirstproject.government.core.*;
import com.eiad.jpafirstproject.government.jpa.PersonAddressEntity;
import com.eiad.jpafirstproject.government.jpa.PersonEntity;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper {

    public PersonDTO convertToDTO(Person person) {
        return new PersonDTO(person.getFullName().getName(),
                person.getFullName().getFatherName(),
                person.getFullName().getGrandFatherName(),
                person.getFullName().getFamilyName(),
                person.getMotherName(),
                person.getBirthDay(),
                listToString(person.getAddresses()),
                person.getBloodType(),
                person.getIdNumber(),
                person.getStatus().toString(),
                person.getIdCreationDate());
    }

    public Person convertToPerson(PersonDTO personDTO) {
        FullName fullName = new FullName(personDTO.getFirstName(),
                personDTO.getFatherName(),
                personDTO.getGrandFatherName(),
                personDTO.getFamilyName());
        return new Person(fullName, personDTO.getMotherName(),
                personDTO.getBirthday(), toList(personDTO.getAddresses()),
                personDTO.getBloodType(), personDTO.getIdNumber(),
                Status.valueOf(personDTO.getStatus()),
                personDTO.getCreationDate());
    }

    public Person convertFromEntity(PersonEntity entity) {
        FullName fullName = new FullName(entity.getFirstName(),
                entity.getFatherName(),
                entity.getGrandFatherName(),
                entity.getFamilyName());
        List<String> addresses = entity.getAddresses()
                .stream()
                .map(PersonAddressEntity::getAddresses)
                .collect(Collectors.toList());
        return new Person(fullName,
                entity.getMotherName(),
                entity.getBirthday(),
                addresses,
                entity.getBloodType(),
                entity.getId(),
                Status.valueOf(entity.getStatus().toString()),
                entity.getCreationDate());
    }

    public PersonEntity convertToEntity(Person person){
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(person.getIdNumber());
        personEntity.setFirstName(person.getFullName().getName());
        personEntity.setFamilyName(person.getFullName().getFamilyName());
        personEntity.setFatherName(person.getFullName().getFatherName());
        personEntity.setBirthday(person.getBirthDay());
        personEntity.setCreationDate(LocalDate.now());
        personEntity.setBloodType(person.getBloodType());
        personEntity.setGrandFatherName(person.getFullName().getGrandFatherName());
        personEntity.setMotherName(person.getMotherName());
        personEntity.setStatus(person.getStatus());

        List<PersonAddressEntity> addressEntities = person.getAddresses()
                .stream()
                .map(address -> toAddressEntity(personEntity, address))
                .collect(Collectors.toList());

        personEntity.setAddresses(addressEntities);

        return personEntity;
    }

    private static PersonAddressEntity toAddressEntity(PersonEntity personEntity, String address) {
        PersonAddressEntity addressEntity = new PersonAddressEntity();
        addressEntity.setAddresses(address);
        addressEntity.setPerson(personEntity);
        return addressEntity;
    }

    private String listToString(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i < list.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    private List<String> toList(String st) {
        return Arrays.asList(st.split(","));
    }

}
