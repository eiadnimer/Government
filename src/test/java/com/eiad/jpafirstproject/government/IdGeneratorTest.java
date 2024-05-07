package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.core.IdGenerator;
import com.eiad.jpafirstproject.government.core.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class IdGeneratorTest {
    private final PersonRepository personRepository = new JavaPersonRepository();

    private final IdGenerator idGenerator = new IdGenerator(personRepository);


    @Test
    void generation_test_first_count() {
        long result = idGenerator.generate(1);
        long idNumber = Long.parseLong(LocalDate.now().toString().replace("-", "") + "000001");

        Assertions.assertEquals( idNumber , result);
    }

    @Test
    void generation_test_last_count() {
        long result = idGenerator.generate(999999);
        long idNumber = Long.parseLong(LocalDate.now().toString().replace("-", "") + "999999");

        Assertions.assertEquals( idNumber , result);
    }
}