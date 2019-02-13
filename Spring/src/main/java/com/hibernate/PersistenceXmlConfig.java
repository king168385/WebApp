package com.hibernate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
//@EnableTransactionManagement
@ComponentScan({ "com.baeldung.persistence.dao", "com.baeldung.persistence.service" })
@ImportResource({ "classpath:hibernate4Config.xml" })
public class PersistenceXmlConfig {

    public PersistenceXmlConfig() {
        super();
    }

}