package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringApplicationConfiguration(classes = {SpringDataRestValidationApplication.class })
@WebAppConfiguration
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonRestIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private EntityLinks entityLinks;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired PersonRepository personRepository;

    private MockMvc mockMvc;

    private Person person;

    @Before
    public void init() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void should_fail_on_incomplete_input_in_create() throws Exception {
        String input = objectMapper.writeValueAsString(ImmutableMap.of(
                "name", "some"
        ));
        mockMvc.perform(
                        post(entityLinks.linkFor(Person.class).toUri())
                                .content(input)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0].property", is("email")))

        ;
    }

    @Test
    public void should_create_person() throws Exception {
        String input = objectMapper.writeValueAsString(ImmutableMap.of(
                "name", "some",
                "email", "some@me.de"
        ));
        mockMvc.perform(
                post(entityLinks.linkFor(Person.class).toUri())
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, notNullValue()))
        ;
    }

    @Test
    public void should_fail_on_invalid_input_in_full_update() throws Exception {
        givenPerson();

        String input = objectMapper.writeValueAsString(ImmutableMap.of(
                "name", "some"
        ));
        mockMvc.perform(
                put(entityLinks.linkForSingleResource(Person.class, person.getId()).toUri())
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0].property", is("email")))
        ;
    }

    private void givenPerson() {
        person = new Person();
        person.setName("name");
        person.setEmail("me@some.de");
        person = personRepository.saveAndFlush(person);
    }
}
