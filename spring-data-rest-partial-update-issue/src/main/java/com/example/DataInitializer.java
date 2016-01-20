package com.example;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializer implements InitializingBean {

    private final PersonRepository personRepository;
    private final ChildRepository childRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        Child child1 = childRepository.saveAndFlush(new Child("child1"));
        Child child2 = childRepository.saveAndFlush(new Child("child2"));
        Child child3 = childRepository.saveAndFlush(new Child("child3"));

        personRepository.saveAndFlush(new Person("md", child1));
        personRepository.saveAndFlush(new Person("name", child2));
    }
}
