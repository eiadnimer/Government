package com.eiad.jpafirstproject.government.jpa;

import com.eiad.jpafirstproject.government.api.PersonMapper;
import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.PersonRepository;
import jakarta.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class JpaPersonRepository implements PersonRepository {

    private final PersonMapper personMapper;
    private final EntityManager entityManager;

    public JpaPersonRepository(PersonMapper personMapper, EntityManager entityManager) {
        this.personMapper = personMapper;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Person findById(long idNumber) {
        PersonEntity personEntity = entityManager.find(PersonEntity.class, idNumber);
        return personMapper.convertFromEntity(personEntity);
    }

    @Override
    @Transactional
    public Person create(Person person) {
        PersonEntity entity = personMapper.convertToEntity(person);
        entityManager.persist(entity);
        return personMapper.convertFromEntity(entity);
    }

    @Override
    @Transactional
    public void delete(long idNumber) {
        PersonEntity personEntity = entityManager.find(PersonEntity.class, idNumber);
        entityManager.remove(personEntity);
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM PersonAddressEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM PersonEntity").executeUpdate();

    }

    @Override
    @Transactional
    public List<Person> findAll() {
        List<PersonEntity> personEntities = entityManager.createQuery("SELECT p FROM PersonEntity  p"
                        , PersonEntity.class)
                .getResultList();
        return personEntities.stream()
                .map(personMapper::convertFromEntity).toList();
    }

    @Override
    @Transactional
    public Person update(long idNumber) {
        PersonEntity person = entityManager.find(PersonEntity.class, idNumber);
        entityManager.merge(person);
        return personMapper.convertFromEntity(person);
    }

    @Override
    @Transactional
    public long generateCounter() {
        List<Person> persons = this.findAll();
        if (persons.isEmpty()) {
            return 1;
        }
        return persons.size() + 1;
    }
}
