package com.eiad.jpafirstproject.government.core;

import lombok.Getter;

import java.util.Objects;

@Getter
public class FullName {
    private final String name;
    private final String fatherName;
    private final String grandFatherName;
    private final String familyName;

    public FullName(String name, String fatherName, String grandFatherName, String familyName) {
        if (name == null) {
            throw new InvalidFieldValueException("firstName");
        }
        this.name = name;
        if (fatherName == null) {
            throw new IllegalArgumentException();
        }
        this.fatherName = fatherName;
        if (grandFatherName == null) {
            throw new IllegalArgumentException();
        }
        this.grandFatherName = grandFatherName;
        if (familyName == null) {
            throw new IllegalArgumentException();
        }
        this.familyName = familyName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return name.equals(fullName.name) && Objects.equals(fatherName, fullName.fatherName) && Objects.equals(grandFatherName, fullName.grandFatherName) && familyName.equals(fullName.familyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "FullName{" +
                "name='" + name + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", grandFatherName='" + grandFatherName + '\'' +
                ", familyName='" + familyName + '\'' +
                '}';
    }
}
