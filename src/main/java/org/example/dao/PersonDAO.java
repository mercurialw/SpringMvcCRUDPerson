package org.example.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String mail) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE mail=?", new Object[]{mail}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age, mail, address) VALUES(?, ?, ?, ?)", person.getName(), person.getAge(),
                person.getMail(), person.getAddress());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, mail=?, address=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getMail(), updatedPerson.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    //Тестируем производительность проектной вставки
    public void testMultipleUpdate() {
        List<Person> list = create1000People();
        long before = System.currentTimeMillis();

        for (Person person : list) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)", person.getId(),
                    person.getName(), person.getAge(), person.getMail());
        }

        long after = System.currentTimeMillis();
        System.out.println("time: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> list = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?, ?, ?, ?)",
        new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    preparedStatement.setInt(1, list.get(i).getId());
                    preparedStatement.setString(2, list.get(i).getName());
                    preparedStatement.setInt(3, list.get(i).getAge());
                    preparedStatement.setString(4, list.get(i).getMail());
                }

                @Override
                public int getBatchSize() {
                    return list.size();
                }
        });

        long after = System.currentTimeMillis();
        System.out.println("time: " + (after - before));
    }
    public List<Person> create1000People() {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new Person(i, "Name" + i, 30, "test" + i + "@gmail.com",  "some address"));
        }
        return list;
    }
}
