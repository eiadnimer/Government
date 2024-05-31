package com.eiad.jpafirstproject.government.config;

import com.eiad.jpafirstproject.government.api.PersonMapper;
import com.eiad.jpafirstproject.government.core.*;
import com.eiad.jpafirstproject.government.jpa.JpaPersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    @Bean
    public PersonMapper personMapper() {
        return new PersonMapper();
    }

    @Bean
    public PersonRepository personRepository(PersonMapper personMapper, EntityManager entityManager) {
        return new JpaPersonRepository(personMapper, entityManager);
    }

    @Bean
    public PersonService personService(PersonRepository personRepository) {
        return new PersonService(personRepository);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
