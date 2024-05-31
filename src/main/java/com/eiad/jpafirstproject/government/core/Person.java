package com.eiad.jpafirstproject.government.core;

import com.eiad.jpafirstproject.government.core.validations.personValidation.fullname.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Person {
    private final static List<PersonValidationsBeforeCreation> PERSON_VALIDATION_BEFORE_CREATIONS = new ArrayList<>();
    private final static List<PersonValidationsAfterCreation> PERSON_VALIDATION_AFTER_CREATIONS = new ArrayList<>();
    private final FullName fullName;
    private final String motherName;
    private final LocalDate birthDay;
    private final List<String> addresses;
    private final String bloodType;
    private Long idNumber;
    private Status status;
    private LocalDate idCreationDate;

    static {
        PERSON_VALIDATION_BEFORE_CREATIONS.add(new MotherNameValidation());
        PERSON_VALIDATION_BEFORE_CREATIONS.add(new BirthdayValidation());
        PERSON_VALIDATION_BEFORE_CREATIONS.add(new AddressesValidation());
        PERSON_VALIDATION_BEFORE_CREATIONS.add(new BloodTypeValidation());
        PERSON_VALIDATION_BEFORE_CREATIONS.add(new FullNameValidation());
        PERSON_VALIDATION_AFTER_CREATIONS.add(new IDValidation());
        PERSON_VALIDATION_AFTER_CREATIONS.add(new StatusValidation());
        PERSON_VALIDATION_AFTER_CREATIONS.add(new IDCreationTimer());
    }

    public Person(FullName fullName,
                  String motherName,
                  LocalDate birthDay,
                  List<String> addresses,
                  String bloodType) {
        this.fullName = fullName;
        this.motherName = motherName;
        this.birthDay = birthDay;
        this.addresses = addresses;
        this.bloodType = bloodType;
        validatePersonBeforeCreation();
    }

    public Person(FullName fullName,
                  String motherName,
                  LocalDate birthDay,
                  List<String> addresses,
                  String bloodType,
                  Long idNumber,
                  Status status,
                  LocalDate creationDate) {
        this(fullName,
                motherName,
                birthDay,
                addresses,
                bloodType);
        this.idNumber = idNumber;
        this.status = status;
        this.idCreationDate = creationDate;
        validatePersonAfterCreation();
    }

    private void validatePersonAfterCreation() {
        for (PersonValidationsBeforeCreation personValidation : PERSON_VALIDATION_BEFORE_CREATIONS) {
            personValidation.isValid(this);
        }
        for (PersonValidationsAfterCreation personValidationAfterCreation : PERSON_VALIDATION_AFTER_CREATIONS) {
            personValidationAfterCreation.isValid(this);
        }
    }

    private void validatePersonBeforeCreation() {
        for (PersonValidationsBeforeCreation personValidation : PERSON_VALIDATION_BEFORE_CREATIONS) {
            personValidation.isValid(this);
        }
    }

    public Person forCreation(Long generateId) {
        return new Person(this.fullName, this.motherName, this.birthDay,
                this.addresses, this.bloodType, generateId,
                Status.ACTIVE, LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(idNumber, person.idNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber);
    }

    @Override
    public String toString() {
        return "Person{" +
                " full Name=" + fullName +
                ", mother name='" + motherName + '\'' +
                ", birthday=" + birthDay +
                ", addresses =" + addresses +
                ", blood type='" + bloodType + '\'' +
                ", id number='" + idNumber + '\'' +
                ", status='" + status + '\'' +
                ", id creation date='" + idCreationDate + '\'' +
                '}';
    }
}
