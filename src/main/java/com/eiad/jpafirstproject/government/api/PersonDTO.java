package com.eiad.jpafirstproject.government.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class PersonDTO {
    private String firstName;
    private String fatherName;
    private String grandFatherName;
    private String familyName;
    private String motherName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String addresses;
    private String bloodType;
    private Long idNumber;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;


    public PersonDTO(String firstName, String fatherName, String grandFatherName,
                     String familyName, String motherName, LocalDate birthday,
                     String addresses, String bloodType, Long idNumber, String status, LocalDate creationDate) {
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.familyName = familyName;
        this.motherName = motherName;
        this.birthday = birthday;
        this.addresses = addresses;
        this.bloodType = bloodType;
        this.idNumber = idNumber;
        this.status = status;
        this.creationDate = creationDate;
    }

    public PersonDTO(String firstName, String fatherName, String grandFatherName,
                     String familyName, String motherName, LocalDate birthday,
                     String addresses, String bloodType){
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.familyName = familyName;
        this.motherName = motherName;
        this.birthday = birthday;
        this.addresses = addresses;
        this.bloodType = bloodType;
    }

    public PersonDTO() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(idNumber, personDTO.idNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, fatherName, motherName, birthday);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", grandFatherName='" + grandFatherName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", addresses='" + addresses + '\'' +
                ", bloodType='" + bloodType + '\'' +
                '}';
    }
}
