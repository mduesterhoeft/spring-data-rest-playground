package com.example.onetomany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class OneInitializer implements ApplicationRunner {

    private final OneRepository oneRepository;

    @Autowired
    public OneInitializer(OneRepository oneRepository) {
        this.oneRepository = oneRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        One one = new One();
        one.setName("some");
        one.getManies().add(new Many("many1"));
        one.getManies().add(new Many("many2"));

        oneRepository.saveAndFlush(one);
    }
}
