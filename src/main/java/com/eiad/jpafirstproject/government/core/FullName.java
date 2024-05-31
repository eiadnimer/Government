package com.eiad.jpafirstproject.government.core;

import com.eiad.jpafirstproject.government.core.validations.fullname.validation.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class FullName {
    private final static List<FullNameValidations> fullNameValidations = new ArrayList<>();
    private final String name;
    private final String fatherName;
    private final String grandFatherName;
    private final String familyName;

    static {
        fullNameValidations.add(new FirstNameValidation());
        fullNameValidations.add(new FatherNameValidations());
        fullNameValidations.add(new GrandFatherValidation());
        fullNameValidations.add(new FamilyNameValidation());
    }

    public FullName(String name, String fatherName, String grandFatherName, String familyName) {
        this.name = name;
        this.fatherName = fatherName;
        this.grandFatherName = grandFatherName;
        this.familyName = familyName;
        validateFullName();
    }

    private void validateFullName() {
        for (FullNameValidations fullNameValidation : fullNameValidations) {
            fullNameValidation.IsValid(this);
        }
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
