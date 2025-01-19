package org.example.models;

import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 0, message = "Age shouldn't be below zero")
    private int age;

    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    private String mail;


    //Russia, Moscow, 123456
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal code (6 digits)")
    private String address;

    public Person(int id, String name, int age, String mail, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mail = mail;
        this.address = address;
    }

    public Person() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }
}
