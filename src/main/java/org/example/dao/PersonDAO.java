package org.example.dao;

import org.example.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;
    {
        people = new ArrayList<>();
        people.add(new Person(PEOPLE_COUNT++, "Vlad"));
        people.add(new Person(PEOPLE_COUNT++, "Dasha"));
        people.add(new Person(PEOPLE_COUNT++, "Egor"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        Person person = null;
        for (Person p : people) if (p.getId() == id) person = p;
        return person;
    }

    public void save(Person person) {
        person.setId(PEOPLE_COUNT++);
        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToUpdate = show(id);
        personToUpdate.setName(updatedPerson.getName());
    }

    public void delete(int id) {
        people.remove(id);
    }
}
