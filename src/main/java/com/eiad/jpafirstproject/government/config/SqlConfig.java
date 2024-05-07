package com.eiad.jpafirstproject.government.config;

import com.eiad.jpafirstproject.government.sql.DatabaseInitializer;
import com.eiad.jpafirstproject.government.core.PersonRepository;
import com.eiad.jpafirstproject.government.sql.SqlPersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("sql")
public class SqlConfig {

    @Value("${db.url}")
    public String url;
    @Value("${db.username}")
    public String username;
    @Value("${db.password}")
    public String password;

    @Bean
    public DatabaseInitializer databaseInitializer() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(url, username, password);
        databaseInitializer.setup();
        return databaseInitializer;
    }

    @Bean
    public PersonRepository personRepository(DatabaseInitializer databaseInitializer) {
        return new SqlPersonRepository(databaseInitializer);
    }
}
