package com.eiad.jpafirstproject.government.jpa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String addresses;
    @ManyToOne
    @JoinColumn(name = "person_idnumber",referencedColumnName = "id")
    private PersonEntity person;

    @Override
    public String toString() {
        return "PersonAddressEntity{" +
                "id=" + id +
                ", addresses='" + addresses + '\'' +
                ", person=" + person +
                '}';
    }
}
