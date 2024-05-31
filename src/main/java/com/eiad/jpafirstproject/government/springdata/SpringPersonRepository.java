package com.eiad.jpafirstproject.government.springdata;
import com.eiad.jpafirstproject.government.jpa.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface SpringPersonRepository extends CrudRepository<PersonEntity, Long> {

}
