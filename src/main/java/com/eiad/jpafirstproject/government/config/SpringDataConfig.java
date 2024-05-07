package com.eiad.jpafirstproject.government.config;

import com.eiad.jpafirstproject.government.api.PersonMapper;
import com.eiad.jpafirstproject.government.core.PersonRepository;
import com.eiad.jpafirstproject.government.springdata.SpringDataPersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("spring-data")
public class SpringDataConfig {

    @Bean
    public PersonRepository personRepository(SpringPersonRepository springPersonRepository, PersonMapper personMapper) {
        return new SpringDataPersonRepository(springPersonRepository, personMapper);
    }
}
