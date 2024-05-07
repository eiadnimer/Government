package com.eiad.jpafirstproject.government.springdata;
import com.eiad.jpafirstproject.government.jpa.PersonEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface SpringPersonRepository extends CrudRepository<PersonEntity, Long> {
//    @Modifying
//    @Query("update PersonEntity e set e.creationDate = ?2 where e.id= ?1")
//    void renew(Long id, LocalDate creationDate);
//
//    @Modifying
//    @Query("update PersonEntity e set e.status = ?2 where e.id= ?1")
//    void changeStatus(Long id, Status status);

}
