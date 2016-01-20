package com.example.onetomany;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
public class OneCustomController {

    private final OneRepository oneRepository;

    @Autowired
    public OneCustomController(OneRepository oneRepository) {
        this.oneRepository = oneRepository;
    }

    @RequestMapping(path = "ones", method = POST)
    public ResponseEntity<Void> create(@Valid @RequestBody One one) {
        oneRepository.saveAndFlush(one);
        return ResponseEntity.ok().build();
    }
}
