package com.eiad.jpafirstproject.government.springdata;

import com.eiad.jpafirstproject.government.api.PersonMapper;
import com.eiad.jpafirstproject.government.core.Person;
import com.eiad.jpafirstproject.government.core.PersonRepository;
import com.eiad.jpafirstproject.government.jpa.PersonEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
public class SpringDataPersonRepository implements PersonRepository {

    private final SpringPersonRepository springPersonRepository;
    private final PersonMapper personMapper;

    public SpringDataPersonRepository(SpringPersonRepository springPersonRepository, PersonMapper personMapper) {
        this.springPersonRepository = springPersonRepository;
        this.personMapper = personMapper;
    }

    @Override
    public Person findById(long idNumber) {
        PersonEntity personEntity = springPersonRepository.findById(idNumber).orElse(null);
        assert personEntity != null;
        return personMapper.convertFromEntity(personEntity);

    }

    @Override
    public Person create(Person person) {
        PersonEntity saved = springPersonRepository.save(personMapper.convertToEntity(person));
        return findById(saved.getId());
    }

    @Override
    public void delete(long idNumber) {
        springPersonRepository.deleteById(idNumber);
    }

    @Override
    public void deleteAll() {
        springPersonRepository.deleteAll();
    }

    @Override
    public List<Person> findAll() {
        Iterable<PersonEntity> persons = springPersonRepository.findAll();
        return StreamSupport.stream(persons.spliterator(), false)
                .map(personMapper::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Person update(long idNumber) {
        Person byId = findById(idNumber);
        springPersonRepository.save(personMapper.convertToEntity(byId));
        return byId;
    }

    @Override
    public long generateCounter() {
        return 0;
    }

}
