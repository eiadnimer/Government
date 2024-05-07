package com.eiad.jpafirstproject.government.core;

import lombok.Getter;

@Getter
public class InvalidFieldValueException extends RuntimeException {
    private final String fieldName;

    public InvalidFieldValueException(String fieldName) {
        this.fieldName = fieldName;
    }

}
