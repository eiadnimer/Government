package com.eiad.jpafirstproject.government.core;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Setter
@Getter
public class Person {
    private final FullName fullName;
    private final String motherName;
    private final LocalDate birthDay;
    private final List<String> addresses;
    private final String bloodType;
    private Long idNumber;
    private Status status;
    private LocalDate idCreationDate;

    public Person(FullName fullName, String motherName, LocalDate birthDay,
                  List<String> addresses, String bloodType) {
        if (fullName == null) {
            throw new IllegalArgumentException();
        }
        this.fullName = fullName;
        if (motherName == null) {
            throw new IllegalArgumentException();
        }
        this.motherName = motherName;
        if (birthDay == null || birthDay.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException();
        }
        this.birthDay = birthDay;
        if (addresses.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.addresses = addresses;
        if (bloodType == null) {
            throw new IllegalArgumentException();
        }
        this.bloodType = bloodType;
    }

    public Person(FullName fullName,
                  String motherName,
                  LocalDate birthDay,
                  List<String> addresses,
                  String bloodType,
                  Long idNumber,
                  Status status,
                  LocalDate creationDate) {
        this(fullName, motherName, birthDay, addresses, bloodType);
        this.idNumber = idNumber;
        if (status == null) {
            throw new IllegalArgumentException();
        }
        this.status = status;
        if (creationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException();
        }
        this.idCreationDate = creationDate;
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
