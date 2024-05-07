package com.eiad.jpafirstproject.government.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGrandFatherName() {
        return grandFatherName;
    }

    public void setGrandFatherName(String grandFatherName) {
        this.grandFatherName = grandFatherName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
