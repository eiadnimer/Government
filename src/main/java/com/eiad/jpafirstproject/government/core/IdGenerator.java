package com.eiad.jpafirstproject.government.core;

import java.time.LocalDate;

public class IdGenerator {

    private final PersonRepository personRepository;

    public IdGenerator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public long generate(long sixDigitNumber) {
        if (sixDigitNumber == 0) {
            sixDigitNumber++;
            return Long.parseLong(LocalDate.now().toString().replace("-", "") + String.format("%06d", sixDigitNumber));
        } else {
            return Long.parseLong(LocalDate.now().toString().replace("-", "") + String.format("%06d", sixDigitNumber + counter()));
        }
    }

    private long counter() {
        return personRepository.generateCounter();
    }
}

