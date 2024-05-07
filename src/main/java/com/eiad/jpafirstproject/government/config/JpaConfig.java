package com.eiad.jpafirstproject.government.config;

import com.eiad.jpafirstproject.government.api.PersonMapper;
import com.eiad.jpafirstproject.government.jpa.JpaPersonRepository;
import com.eiad.jpafirstproject.government.core.PersonRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
public class JpaConfig {

    @Bean
    public PersonRepository personRepository(PersonMapper personMapper, EntityManager entityManager) {
        return new JpaPersonRepository(personMapper, entityManager);
    }
}
