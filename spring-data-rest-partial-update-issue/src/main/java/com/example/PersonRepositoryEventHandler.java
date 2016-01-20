package com.example;

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.persistence.sessions.UnitOfWork;
import org.eclipse.persistence.sessions.changesets.ChangeRecord;
import org.eclipse.persistence.sessions.changesets.ObjectChangeSet;
import org.eclipse.persistence.sessions.changesets.UnitOfWorkChangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RepositoryEventHandler
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonRepositoryEventHandler {

    private final PersonRepository personRepository;
    private final EntityManager entityManager;

    @HandleBeforeSave
    public void compareName(Person newPerson) {
        log.info("start HandleBeforeSave");

        UnitOfWorkChangeSet changes = entityManager.unwrap(UnitOfWork.class).getCurrentChanges();
        ObjectChangeSet objectChanges = changes.getObjectChangeSetForClone(newPerson);
        List<String> changedAttributeNames = objectChanges.getChangedAttributeNames();
        if (objectChanges.hasChangeFor("name")) {
            ChangeRecord changeRecordForName = objectChanges.getChangesForAttributeNamed("name");
            log.info("objectChanges - old name '{}' - new name '{}'", changeRecordForName.getOldValue(), newPerson.getName());
        }
        entityManager.detach(newPerson);

        Person oldPerson = personRepository.findOne(newPerson.getId());

        log.info("name for person {} - old name '{}' - new name '{}'", newPerson.getId(),
                oldPerson.getName(), newPerson.getName());

        if (oldPerson.getName().equals(newPerson.getName())) {
            throw new IllegalArgumentException("names not different in HandleBeforeSave");
        }
    }

    @HandleAfterSave
    public void logAfterSave(Person person) {
        log.info("start HandleAfterSave");
    }
}
