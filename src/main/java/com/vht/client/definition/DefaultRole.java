package com.vht.client.definition;

public enum DefaultRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String text;

    DefaultRole(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
