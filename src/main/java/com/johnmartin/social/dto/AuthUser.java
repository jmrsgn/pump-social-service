package com.johnmartin.social.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUser {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public AuthUser withId(String id) {
        setId(id);
        return this;
    }

    public AuthUser withFirstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public AuthUser withLastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public AuthUser withEmail(String email) {
        setEmail(email);
        return this;
    }

    public AuthUser withPhone(String phone) {
        setPhone(phone);
        return this;
    }

    @Override
    public String toString() {
        return "AuthUser{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
               + ", email='" + email + '\'' + ", phone='" + phone + '\'' + '}';
    }
}
