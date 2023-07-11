package com.sokol.springcourse.util;

import com.sokol.springcourse.dao.PersonDAO;
import com.sokol.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // check if there is any person with the same email address already exists
        if (personDAO.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "There email is already taken");
        }
    }
}
