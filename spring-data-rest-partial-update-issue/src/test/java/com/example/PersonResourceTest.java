package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRestPartialUpdateIssueApplication.class)
@WebAppConfiguration
public class PersonResourceTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private EntityLinks entityLinks;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ChildRepository childRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	private Child anotherChild;
	private Child child;
	private Person person;

	@Before
	public void setup() {
		mockMvc = webAppContextSetup(context).build();
	}

	@Test
	public void should_update_name() throws Exception {
		givenChildren();
		givenPerson();

		String patchInput = objectMapper.writeValueAsString(ImmutableMap.of( //
				"name", "newName"
		));
		mockMvc.perform(patch(entityLinks.linkForSingleResource(person.getClass(), person.getId()).toUri())
					.content(patchInput) //
					.contentType(MediaType.APPLICATION_JSON_VALUE)) //
				.andExpect(status().isNoContent())
		;
	}

    @Test
    public void should_update_name_and_child() throws Exception {
        givenChildren();
        givenPerson();

        String patchInput = objectMapper.writeValueAsString(ImmutableMap.of(
                "name", "newName", //
                "child", entityLinks.linkForSingleResource(anotherChild.getClass(), anotherChild.getId()).toUri()
        ));
        mockMvc.perform(patch(entityLinks.linkForSingleResource(person.getClass(), person.getId()).toUri())
                .content(patchInput) //
                .contentType(MediaType.APPLICATION_JSON_VALUE)) //
                .andExpect(status().isNoContent())
        ;
    }

	private void givenPerson() {
		person = personRepository.saveAndFlush(new Person("name", child));
	}

	private void givenChildren() {
		child = childRepository.saveAndFlush(new Child("child"));
		anotherChild = childRepository.saveAndFlush(new Child("anotherChild"));
	}

}
