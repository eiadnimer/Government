package com.eiad.jpafirstproject.government.jpa;

import com.eiad.jpafirstproject.government.core.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
public class PersonEntity {
    @Id
    private long id;
    private String firstName;
    private String fatherName;
    private String grandFatherName;
    private String familyName;
    private String motherName;
    private LocalDate birthday;
    private String bloodType;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate creationDate;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PersonAddressEntity> addresses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", grandFatherName='" + grandFatherName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", birthday=" + birthday +
                ", bloodType='" + bloodType + '\'' +
                ", status=" + status +
                ", creationDate='" + creationDate + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
