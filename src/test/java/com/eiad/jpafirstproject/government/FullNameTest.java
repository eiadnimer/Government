package com.eiad.jpafirstproject.government;

import com.eiad.jpafirstproject.government.core.FullName;
import com.eiad.jpafirstproject.government.exceptions.FieldMustNotBeEmpty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FullNameTest {

    @Test
    public void if_name_equal_to_null_must_return_false() {
        Assertions.assertThrows(FieldMustNotBeEmpty.class,
                () -> new FullName(null, "mohammad", "ali", "anas"));
    }

    @Test
    public void if_father_name_equal_to_null_must_return_false() {
        Assertions.assertThrows(FieldMustNotBeEmpty.class,
                () -> new FullName("muhannad", null, "ali", "anas"));
    }

    @Test
    public void if_grand_father_name_equal_to_null_must_return_false() {
        Assertions.assertThrows(FieldMustNotBeEmpty.class,
                () -> new FullName("muhannad", "mohammad", null, "anas"));
    }

    @Test
    public void if_family_name_equal_to_null_must_return_false() {
        Assertions.assertThrows(FieldMustNotBeEmpty.class,
                () -> new FullName("muhannad", "mohammad", "ali", null));
    }
}
