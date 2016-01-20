package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RepositoryRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private final PersonRepository personRepository;
    private final EntityLinks entityLinks;
    private final Validator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(path = "persons", method = POST)
    public ResponseEntity<Void> create(@Valid @RequestBody Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        Person savedPerson = personRepository.saveAndFlush(person);

        return ResponseEntity.created(entityLinks.linkForSingleResource(savedPerson.getClass(), savedPerson.getId()).toUri()).build();
    }
}
