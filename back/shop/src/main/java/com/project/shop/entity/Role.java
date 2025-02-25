package com.project.shop.entity;


import lombok.Getter;

public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    @Getter
    private final String value;


    Role(String value) {
        this.value = value;
    }

}