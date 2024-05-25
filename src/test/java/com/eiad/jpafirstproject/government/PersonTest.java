package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.core.FullName;
import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.Status;
import com.eiad.jpafirstproject.government.exceptions.FieldMustBeNotEmpty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonTest {

    private final FullName fullName = new FullName("muhannad", "mohammad", "ali",
            "anas");
    private final List<String> addresses = new ArrayList<>();

    @Test
    public void if_full_name_equal_to_null_must_return_false() {
        addresses.add("amman");
        addresses.add("irbid");
        Assertions.assertThrows(FieldMustBeNotEmpty.class,
                () -> new Person(null, "nawal", LocalDate.parse("1995-08-16"), addresses, "o+"));
    }

    @Test
    public void if_mother_name_equal_to_null_must_return_false() {
        addresses.add("amman");
        addresses.add("irbid");
        Assertions.assertThrows(FieldMustBeNotEmpty.class,
                () -> new Person(fullName, null, LocalDate.parse("1995-08-16"), addresses, "o+"));
    }

    @Test
    public void if_birthday_equal_to_null_must_return_false() {
        addresses.add("amman");
        addresses.add("irbid");
        Assertions.assertThrows(FieldMustBeNotEmpty.class,
                () -> new Person(fullName, "nawal", null, addresses, "o+"));
    }

    @Test
    public void if_birthday_date_is_after_the_current_date_must_return_false() {
        addresses.add("amman");
        addresses.add("irbid");
        Assertions.assertThrows(FieldMustBeNotEmpty.class,
                () -> new Person(fullName, "nawal", LocalDate.parse("2222-08-16"), addresses, "o+"));
    }

    @Test
    public void if_the_addresses_equal_to_null_must_return_false() {
        Assertions.assertThrows(FieldMustBeNotEmpty.class,
                () -> new Person(fullName, "nawal", LocalDate.parse("1995-08-16"), addresses, "o+"));
    }

    @Test
    public void if_the_blood_type_equal_to_null_must_return_false() {
        addresses.add("amman");
        addresses.add("irbid");
        Assertions.assertThrows(FieldMustBeNotEmpty.class,
                () -> new Person(fullName, "nawal", LocalDate.parse("1995-08-16"), addresses, null));
    }

    @Test
    public void if_the_status_equal_to_null_must_return_false() {
        addresses.add("amman");
        addresses.add("irbid");
        Assertions.assertThrows(FieldMustBeNotEmpty.class,
                () -> new Person(fullName, "nawal", LocalDate.parse("1995-08-16"), addresses, "A+", 1L, null, LocalDate.now()));
    }

    @Test
    public void if_the_idCreation_is_before_the_current_date_must_return_true() {
        addresses.add("amman");
        addresses.add("irbid");
        Person person = new Person(fullName, "noha", LocalDate.parse("1992-06-24"), addresses, "A+", 1L, Status.ACTIVE, LocalDate.parse("2023-01-01"));

        Assertions.assertTrue(person.getIdCreationDate().isBefore(LocalDate.now()));
    }

    @Test
    public void if_the_idCreation_is_after_the_current_date_must_return_false() {
        addresses.add("amman");
        addresses.add("irbid");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Person(fullName, "nawal", LocalDate.parse("1995-08-16"), addresses, "A+", 1L, Status.ACTIVE, LocalDate.parse("2222-06-02")));
    }
}

